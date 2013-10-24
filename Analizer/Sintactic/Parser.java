//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "./grammar.txt"
package Sintactic;
import java.util.StringTokenizer;
import Lexical.LexicalAnalizer;
import Lexical.Token;
import Lexical.ErrorHandler;
import Lexical.Error;
import Lexical.SymbolTable;
import java.util.ArrayList;
import utils.PathContainer;
import Lexical.SymbolElement;
import java.util.HashMap;
import java.util.HashSet;

class parserUtils {
	static ArrayList<String> variableList = new ArrayList<String>();
	/*there will be one reverse polish vector for each function, and one for the main. Each vector*/
	/*will be able to be accessed by the context name */
	static HashMap<String,ArrayList<String>> intermediateCode = new HashMap<String,ArrayList<String>>();
	static ErrorHandler errorHandler = ErrorHandler.getInstance();
	static LexicalAnalizer lexical = new LexicalAnalizer(PathContainer.getPath());
	static String context = "main";	
	/*holds the value of the last function return*/
	static long functionReturn;
	/*true if the function has returned explicitly*/
	static boolean functionHasReturned;
	/*variable to handle the intermediate code generation of the FOR loop*/
	static String forConditionVar;
	/*a stack to handle index handling in IF and FOR statements*/
	static ArrayList<Integer> indexStack = new ArrayList<Integer>();
}
//#line 48 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short VARTYPE=257;
public final static short PRINT=258;
public final static short ID=259;
public final static short CTE=260;
public final static short CHARCHAIN=261;
public final static short FUNCTION=262;
public final static short BEGIN=263;
public final static short END=264;
public final static short FOR=265;
public final static short COMPARATOR=266;
public final static short ERROR=267;
public final static short IF=268;
public final static short THEN=269;
public final static short ELSE=270;
public final static short ASSIGN=271;
public final static short SEMICOLON=272;
public final static short COMMA=273;
public final static short OPENPAREN=274;
public final static short CLOSEPAREN=275;
public final static short PLUS=276;
public final static short MINUS=277;
public final static short PRODUCT=278;
public final static short DIVISION=279;
public final static short RETURN=280;
public final static short IT=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    2,    0,    1,    1,    1,    3,    3,    5,    5,    8,
    8,    9,    6,    6,   10,   10,   12,   11,    7,    4,
    4,   13,   13,   15,   15,   14,   14,   14,   14,   14,
   16,   16,   16,   17,   17,   17,   18,   18,   18,   24,
   25,   19,   27,   28,   20,   23,   26,   26,   26,   26,
   26,   26,   26,   26,   26,   26,   29,   21,   22,   22,
   22,   30,   30,   30,   31,   31,   31,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    1,    2,    1,    1,    1,    2,
    1,    3,    3,    1,    3,    3,    0,    6,    6,    2,
    1,    2,    1,    1,    1,    2,    2,    2,    2,    2,
    5,    4,    4,    4,    3,    3,    4,    4,    4,    0,
    0,   10,    0,    0,   10,    3,    4,    4,    3,    3,
    4,    3,    3,    2,    1,    1,    0,    4,    3,    3,
    1,    3,    3,    1,    1,    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    0,    7,    8,    9,   14,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   57,    0,    0,
    0,    0,    0,    6,   20,    0,   26,   27,   28,   29,
   30,   12,    0,   13,    0,   35,    0,    0,    0,    0,
   17,    0,    0,    0,   65,   67,    0,    0,    0,   64,
   11,    0,   16,   15,   34,   38,    0,   39,   37,    0,
   40,    0,    0,    0,    0,    0,    0,    0,   10,    0,
   24,   23,   25,    0,    0,    0,    0,    0,    0,   43,
   62,   63,    0,    0,    0,   22,    0,   18,    0,    0,
    0,    0,   19,    0,    0,    0,    0,    0,    0,   44,
   33,    0,   32,    0,   54,    0,    0,    0,    0,    0,
   31,    0,    0,    0,    0,   49,   52,    0,    0,   47,
   48,   51,   45,
};
final static short yydgoto[] = {                          1,
    9,    2,  106,  107,   12,   13,   14,   62,   15,   24,
   16,   70,   80,   17,   82,   83,   18,   19,   20,   21,
   22,   57,   58,   86,  122,  110,  100,  120,   49,   59,
   60,
};
final static short yysindex[] = {                         0,
    0, -104, -253, -223, -247, -229, -212, -207,    0, -104,
 -108,    0,    0,    0,    0, -194,    0, -213, -195, -190,
 -188, -168, -199, -185, -183, -152, -143,    0, -136, -141,
 -123, -164, -108,    0,    0, -119,    0,    0,    0,    0,
    0,    0, -120,    0, -115,    0, -130, -125, -164, -215,
    0, -112, -101, -245,    0,    0, -201, -107, -180,    0,
    0, -248,    0,    0,    0,    0, -175,    0,    0,  -82,
    0, -164, -164, -164,  -87, -164, -164, -224,    0, -243,
    0,    0,    0,  -71,  -84, -164, -175, -180, -180,    0,
    0,    0, -164,  -72,  -79,    0,  -78,    0,  -77, -128,
  -75,  -66,    0, -244, -116, -244, -108,    0,    0,    0,
    0,  -65,    0, -244,    0, -104, -108,  -56,  -55,  -60,
    0, -108,  -81,  -53,  -52,    0,    0, -128, -108,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  213,
  214,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  215,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -54,    0,    0,    0,
    0,    0,    0, -151,    0,    0,    0,    0, -110,    0,
    0,    0,    0,    0,    0,    0,  -51,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -183, -103,  -96,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -231, -187,    0,
    0,    0,    0,  -73,    0,    0,    0, -217,  -69,    0,
    0,    0,    0, -204, -179,    0,    0,    0,  -78,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    1,   -2,   -6,  -29,    0,    0,  146,    0,
    0,    0,    0,  -10,  137,    0,    0,  -30,    0,    0,
  188,  -44,  134,    0,    0,   94,    0,    0,    0,   38,
   40,
};
final static int YYTABLESIZE=222;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         11,
   35,   56,   10,   34,   67,   23,   61,   33,    3,    4,
    5,   27,    3,   27,    4,    5,    7,    6,   56,    8,
   95,    7,   35,   28,    8,    7,   29,   87,   29,   30,
    7,   78,   79,   94,   54,   55,   78,   25,   55,    6,
   55,   56,   56,   56,    6,   56,   56,   56,  101,   93,
   26,   81,    6,    6,    6,   56,   68,    6,   37,   69,
    6,   31,   56,    6,   72,   50,   32,   50,   36,   81,
   21,   21,   42,   43,   73,   74,   38,   21,   20,   20,
   21,   39,   56,   40,   56,   20,   44,   45,   20,  109,
   53,   46,   53,  108,   54,   55,  119,   76,   77,  118,
   73,   74,  117,   41,  114,  116,  125,   34,   47,  124,
   88,   89,   35,  123,   66,   91,   92,  109,   35,  129,
   66,  108,   50,   66,   66,   66,   66,   66,    3,    4,
    5,   48,   51,    6,  105,   52,    7,    3,   63,    8,
    3,    4,    5,   64,   65,    6,   66,  115,    7,    4,
    5,    8,    3,    4,    5,   61,    7,    6,   28,    8,
    7,   61,   59,    8,   61,   61,   61,   75,   59,   60,
   71,   59,   59,   59,   84,   60,    4,    5,   60,   60,
   60,   90,  130,    7,   41,   41,    8,   97,   20,   20,
   98,   41,  103,   42,   41,   20,  111,  104,   20,  112,
   73,   74,  102,   73,   74,  113,  121,  126,  127,  128,
  131,  132,    3,    5,    4,   85,   96,   36,   53,   99,
   58,  133,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   11,   32,    2,   10,   49,  259,   36,   10,  257,  258,
  259,  259,  257,  259,  258,  259,  265,  262,   49,  268,
  264,  265,   33,  271,  268,  257,  274,   72,  274,  259,
  262,  280,   62,   78,  259,  260,  280,  261,  270,  257,
  272,   72,   73,   74,  262,   76,   77,   78,   93,  274,
  274,   62,  257,  258,  259,   86,  272,  262,  272,  275,
  265,  274,   93,  268,  266,  270,  274,  272,  263,   80,
  258,  259,  272,  273,  276,  277,  272,  265,  258,  259,
  268,  272,  270,  272,  272,  265,  272,  273,  268,  100,
  270,  275,  272,  100,  259,  260,  107,  278,  279,  106,
  276,  277,  105,  272,  104,  105,  117,  114,  261,  116,
   73,   74,  123,  116,  266,   76,   77,  128,  129,  122,
  272,  128,  259,  275,  276,  277,  278,  279,  257,  258,
  259,  275,  274,  262,  263,  259,  265,  257,  259,  268,
  257,  258,  259,  259,  275,  262,  272,  264,  265,  258,
  259,  268,  257,  258,  259,  266,  265,  262,  271,  268,
  265,  272,  266,  268,  275,  276,  277,  275,  272,  266,
  272,  275,  276,  277,  257,  272,  258,  259,  275,  276,
  277,  269,  264,  265,  258,  259,  268,  259,  258,  259,
  275,  265,  272,  272,  268,  265,  272,  275,  268,  275,
  276,  277,  275,  276,  277,  272,  272,  264,  264,  270,
  264,  264,    0,    0,    0,   70,   80,  272,   31,   86,
  272,  128,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"VARTYPE","PRINT","ID","CTE","CHARCHAIN","FUNCTION","BEGIN",
"END","FOR","COMPARATOR","ERROR","IF","THEN","ELSE","ASSIGN","SEMICOLON",
"COMMA","OPENPAREN","CLOSEPAREN","PLUS","MINUS","PRODUCT","DIVISION","RETURN",
"IT",
};
final static String yyrule[] = {
"$accept : start",
"$$1 :",
"start : $$1 program",
"program : declarationList",
"program : declarationList executions",
"program : executions",
"declarationList : declarationList declaration",
"declarationList : declaration",
"declaration : varDeclaration",
"declaration : functionDeclaration",
"varDeclarationList : varDeclarationList varDeclaration",
"varDeclarationList : varDeclaration",
"singleVarDeclaration : VARTYPE ID SEMICOLON",
"varDeclaration : VARTYPE varList SEMICOLON",
"varDeclaration : singleVarDeclaration",
"varList : varList COMMA ID",
"varList : ID COMMA ID",
"$$2 :",
"functionHeader : FUNCTION ID OPENPAREN $$2 singleVarDeclaration CLOSEPAREN",
"functionDeclaration : functionHeader BEGIN varDeclarationList executionWReturnList END SEMICOLON",
"executions : executions execution",
"executions : execution",
"executionWReturnList : executionWReturnList executionWReturn",
"executionWReturnList : executionWReturn",
"executionWReturn : execution",
"executionWReturn : ret",
"execution : print SEMICOLON",
"execution : functionExecution SEMICOLON",
"execution : iteration SEMICOLON",
"execution : selection SEMICOLON",
"execution : assign SEMICOLON",
"ret : RETURN OPENPAREN expression CLOSEPAREN SEMICOLON",
"ret : RETURN expression CLOSEPAREN SEMICOLON",
"ret : RETURN OPENPAREN expression SEMICOLON",
"print : PRINT OPENPAREN CHARCHAIN CLOSEPAREN",
"print : PRINT CHARCHAIN CLOSEPAREN",
"print : PRINT OPENPAREN CHARCHAIN",
"functionExecution : ID OPENPAREN ID CLOSEPAREN",
"functionExecution : ID ID CLOSEPAREN SEMICOLON",
"functionExecution : ID OPENPAREN ID SEMICOLON",
"$$3 :",
"$$4 :",
"iteration : FOR OPENPAREN assign SEMICOLON $$3 condition CLOSEPAREN declarationList $$4 executions",
"$$5 :",
"$$6 :",
"selection : IF OPENPAREN condition CLOSEPAREN THEN $$5 block $$6 ELSE block",
"condition : expression COMPARATOR expression",
"block : BEGIN declarationList executions END",
"block : BEGIN declarationList declaration END",
"block : declarationList declaration END",
"block : BEGIN declarationList declaration",
"block : BEGIN executions execution END",
"block : executions execution END",
"block : BEGIN executions execution",
"block : BEGIN END",
"block : declaration",
"block : execution",
"$$7 :",
"assign : ID ASSIGN $$7 expression",
"expression : expression PLUS term",
"expression : expression MINUS term",
"expression : term",
"term : term PRODUCT factor",
"term : term DIVISION factor",
"term : factor",
"factor : CTE",
"factor : ID",
"factor : functionExecution",
};

//#line 478 "./grammar.txt"

String ins;
StringTokenizer st;

void yyerror(String s) {
 System.out.println("par:"+s);
}

boolean newline;

int yylex() {
	Token token = parserUtils.lexical.getToken();
	yylval = new ParserVal(token);
	
	switch(token.getTokenValue()) {
		case "ID":
			return Parser.ID;
		case "CTE" :
			return Parser.CTE;
		case "CHARCHAIN" :
			return Parser.CHARCHAIN;
		case "ULONG" :
			return Parser.VARTYPE;
		case "RETURN" :
			return Parser.RETURN;
		case ">" :
			return Parser.COMPARATOR;
		case ">=" :
			return Parser.COMPARATOR;
		case "<" :
			return Parser.COMPARATOR;
		case "<=" :
			return Parser.COMPARATOR;
		case "==" :
			return Parser.COMPARATOR;
		case "!=" :
			return Parser.COMPARATOR;
		case "=" :
			return Parser.ASSIGN;
		case ";" :
			return Parser.SEMICOLON;
		case "," :
			return Parser.COMMA;
		case "(" :
			return Parser.OPENPAREN;
		case ")" :
			return Parser.CLOSEPAREN;
		case "+" :
			return Parser.PLUS;
		case "-" :
			return Parser.MINUS;
		case "*" :
			return Parser.PRODUCT;
		case "/" :
			return Parser.DIVISION;
		case  "PRINT":
			return Parser.PRINT;
		case  "FUNCTION":
			return Parser.FUNCTION;
		case  "BEGIN" :
			return Parser.BEGIN;
		case  "END":
			return Parser.END;
		case  "FOR":
			return Parser.FOR;
		case  "IF":
			return Parser.IF;
		case  "THEN":
			return Parser.THEN;
		case  "ELSE":
			return Parser.ELSE;
		case  "#":
			return -1;
	}
	return -1;
}
//#line 452 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 67 "./grammar.txt"
{
	/*initialize the Main intermediate code vector*/
	parserUtils.intermediateCode.put("MAIN", new ArrayList<String>());
}
break;
case 12:
//#line 89 "./grammar.txt"
{
		Error error;
		String varName = ((Token)val_peek(1).obj).getLiteralValue();
		String varNameWithContext = "";
		SymbolTable symbolTable = SymbolTable.getInstance();
		SymbolElement element = symbolTable.identify(varName);
		String literalTypeValue = ((Token)val_peek(2).obj).getLiteralValue();

		/*clear prior defined variables*/
		parserUtils.variableList.clear();
		/*update symbole table by applying name mangling*/
		varNameWithContext = parserUtils.context+"_"+varName;
		/*check if variable was already declared*/
		if(symbolTable.contains(varNameWithContext)) {
			error = new Error(Error.TYPE_FATAL,"Variable redeclartion", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		}
		symbolTable.remove(varName);
		symbolTable.addSymbol(varNameWithContext,element);

		element.setVarType(literalTypeValue);
		element.setUse("VAR");
		/*return variable name */
		yyval=val_peek(1);
	}
break;
case 13:
//#line 115 "./grammar.txt"
{
		Error error;
		SymbolTable symbolTable = SymbolTable.getInstance();
		SymbolElement element = new SymbolElement();
		String varNameWithContext = "";
		String literalTypeValue = ((Token)val_peek(2).obj).getLiteralValue();

		/*add type to each symbol table entryof the varList*/
		for(String varName : parserUtils.variableList) {
			element = symbolTable.identify(varName);
			/*update symbole table by applying name mangling*/
			varNameWithContext = parserUtils.context+"_"+varName;
			/*check if variable was already declared*/
			if(symbolTable.contains(varNameWithContext)) {
				error = new Error(Error.TYPE_FATAL,"Variable redeclartion", parserUtils.lexical.getLine());
				parserUtils.errorHandler.addError(error);
			}
			symbolTable.remove(varName);
			symbolTable.addSymbol(varNameWithContext,element);
			element.setVarType(literalTypeValue);
			element.setUse("VAR");
		}
		/*clear prior defined variables*/
		parserUtils.variableList.clear();
	}
break;
case 15:
//#line 142 "./grammar.txt"
{
		/*add variable name to a list that will be consulted later*/
		/*to add type to each variable in the symbol table*/
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 16:
//#line 148 "./grammar.txt"
{
		String varName = ((Token)val_peek(2).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
		varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 17:
//#line 155 "./grammar.txt"
{
		/*Set function name as the current context*/
		String functionName = ((Token)val_peek(1).obj).getLiteralValue();
		parserUtils.context = functionName;
	}
break;
case 18:
//#line 159 "./grammar.txt"
{
		Error error;
		SymbolElement element = new SymbolElement();
		String varNameWithContext = "";
		String varName = ((Token)val_peek(1).obj).getLiteralValue();
		SymbolTable symbolTable = SymbolTable.getInstance();
		String functionName = ((Token)val_peek(4).obj).getLiteralValue();
		/*add FUNCTION use to the function identifier*/
		element = symbolTable.identify(functionName);
		element.setUse("FUNC");
		/*check for function redeclaration*/
		if(symbolTable.contains(functionName)) {
			error = new Error(Error.TYPE_FATAL,"Function redeclartion", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		}
		/*initialize intermediate code vector*/
		parserUtils.intermediateCode.put(parserUtils.context.toUpperCase(), new ArrayList<String>());
		varNameWithContext = parserUtils.context+"_"+varName;
		element = symbolTable.identify(varNameWithContext);
		
		/*set PARAMETER use to each identifier in the parameters declaration*/
		element.setUse("PARAM");
		parserUtils.variableList.clear();
	}
break;
case 19:
//#line 183 "./grammar.txt"
{
		/*once the function declaration is over, set the current context as main*/
		parserUtils.context = "main";
	}
break;
case 32:
//#line 209 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 33:
//#line 213 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 35:
//#line 219 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 36:
//#line 223 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 37:
//#line 229 "./grammar.txt"
{
		Error error;
		String parameterName = ((Token)val_peek(1).obj).getLiteralValue();
		String functionName = ((Token)val_peek(3).obj).getLiteralValue();
		String parameterNameWContext = parserUtils.context+"_"+parameterName;
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*check if parameter is declared */
		element = symbolTable.identify(parameterNameWContext);
		if(!(element != null && element.getUse() != "FUNC")) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+parameterName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		}
		/*add function call to the intermediate code vector*/
		currentIntCodeVector.add(functionName);
		currentIntCodeVector.add("[CALL]");
		currentIntCodeVector.add("rtn");
	}
break;
case 38:
//#line 249 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 39:
//#line 253 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 40:
//#line 258 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String forConditionVar = ((Token)val_peek(1).obj).getLiteralValue();
		parserUtils.forConditionVar = forConditionVar;
		parserUtils.indexStack.add(currentIntCodeVector.size());
	}
break;
case 41:
//#line 264 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());

		/*save the index where the false bifurcation will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[BF]");
	}
break;
case 42:
//#line 272 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		Integer falseBifurcationIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1);
		String forReevaluationIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1).toString();
		String falseBifurcationDirection;
		/*increment the condition variable*/
		currentIntCodeVector.add(parserUtils.forConditionVar);
		currentIntCodeVector.add(parserUtils.forConditionVar);
		currentIntCodeVector.add("1");
		currentIntCodeVector.add("+");
		currentIntCodeVector.add("=");
		/*Add inconditional jump to the FOR reevaluation index*/
		currentIntCodeVector.add(forReevaluationIndex);
		currentIntCodeVector.add("[JMP]");
		/*Set the end of the FOR statement as the false bifurcation direction*/
		falseBifurcationDirection = Integer.toString(currentIntCodeVector.size());
		currentIntCodeVector.set(falseBifurcationIndex,falseBifurcationDirection);
	}
break;
case 43:
//#line 302 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*save the index where the false bifurcation will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[BF]");		

	}
break;
case 44:
//#line 310 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String elseBlockDirection = Integer.toString(currentIntCodeVector.size()+2);
		Integer elseBlockIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1);
		currentIntCodeVector.set(elseBlockIndex,elseBlockDirection);
		/*save the index where the end of else block will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[JMP]");
	}
break;
case 45:
//#line 320 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String endBlockDirection = Integer.toString(currentIntCodeVector.size());
		Integer endBlockIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1);
		currentIntCodeVector.set(endBlockIndex,endBlockDirection);
	}
break;
case 46:
//#line 357 "./grammar.txt"
{
		String comparatorSymbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(comparatorSymbol);
	}
break;
case 49:
//#line 366 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 50:
//#line 370 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 52:
//#line 375 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 53:
//#line 379 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 57:
//#line 387 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String varName = ((Token)val_peek(1).obj).getLiteralValue();
		currentIntCodeVector.add(varName);
	}
break;
case 58:
//#line 392 "./grammar.txt"
{
		Error error;
		String varName = ((Token)val_peek(3).obj).getLiteralValue();
		String assignSymbol = ((Token)val_peek(2).obj).getLiteralValue();
		String context = parserUtils.context;
		String parameterNameWContext = context+"_"+varName;
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*check if identifier is declared */
		element = symbolTable.identify(parameterNameWContext);
		if(!(element != null && element.getUse() != "FUNC")) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+varName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		} else {
			currentIntCodeVector.add(assignSymbol);
		}
	}
break;
case 59:
//#line 410 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 60:
//#line 416 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 62:
//#line 425 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 63:
//#line 431 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 65:
//#line 440 "./grammar.txt"
{
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(varName);
	}
break;
case 66:
//#line 446 "./grammar.txt"
{
		Error error;
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		String context = parserUtils.context;
		HashSet<String> contextList = new HashSet<String>();
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String parameterNameWContext;
		boolean found = false;
		contextList.add(context);
		contextList.add("MAIN");
		for(String cont: contextList) {
			parameterNameWContext= cont+"_"+varName;
			element = symbolTable.identify(parameterNameWContext);
			if(element != null && element.getUse() != "FUNC") {
				found = true;
				break;
			}
		}
		/*check if parameter is declared */
		if(!found) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+varName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		} else {
			currentIntCodeVector.add(varName);
		}
	}
break;
//#line 1012 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
