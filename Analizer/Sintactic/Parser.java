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






//#line 2 "grammar.txt"
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


//#line 32 "Parser.java"




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
   23,   16,   17,   17,   17,   18,   18,   18,   25,   26,
   19,   28,   29,   20,   24,   27,   27,   27,   27,   27,
   27,   27,   27,   27,   27,   30,   21,   22,   22,   22,
   31,   31,   31,   32,   32,   32,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    1,    2,    1,    1,    1,    2,
    1,    3,    3,    1,    3,    3,    0,    6,    6,    2,
    1,    2,    1,    1,    1,    2,    2,    2,    2,    2,
    0,    6,    4,    3,    3,    4,    4,    4,    0,    0,
   10,    0,    0,   10,    3,    4,    4,    3,    3,    4,
    3,    3,    2,    1,    1,    0,    4,    3,    3,    1,
    3,    3,    1,    1,    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    0,    7,    8,    9,   14,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   56,    0,    0,
    0,    0,    0,    6,   20,    0,   26,   27,   28,   29,
   30,   12,    0,   13,    0,   34,    0,    0,    0,    0,
   17,    0,    0,    0,   64,   66,    0,    0,    0,   63,
   11,    0,   16,   15,   33,   37,    0,   38,   36,    0,
   39,    0,    0,    0,    0,    0,    0,    0,   10,    0,
   24,   23,   25,    0,    0,    0,    0,    0,    0,   42,
   61,   62,   31,    0,   22,    0,   18,    0,    0,    0,
   19,    0,    0,    0,    0,    0,    0,   43,    0,    0,
   53,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,   51,    0,   32,    0,   46,   47,   50,   44,
};
final static short yydgoto[] = {                          1,
    9,    2,  104,  105,   12,   13,   14,   62,   15,   24,
   16,   70,   80,   17,   82,   83,   18,   19,   20,   21,
   22,   57,  100,   58,   86,  118,  108,   99,  116,   49,
   59,   60,
};
final static short yysindex[] = {                         0,
    0, -112, -225, -234, -241, -218, -229, -203,    0, -112,
 -169,    0,    0,    0,    0, -194,    0, -196, -178, -175,
 -163, -154, -214, -205, -197, -141, -151,    0, -131, -135,
 -108, -145, -169,    0,    0,  -97,    0,    0,    0,    0,
    0,    0,  -96,    0,  -92,    0, -101,  -87, -145, -266,
    0,  -85,  -84, -242,    0,    0, -238,  -86, -148,    0,
    0, -245,    0,    0,    0,    0, -140,    0,    0,  -67,
    0, -145, -145, -145,  -78, -145, -145,  -82,    0, -243,
    0,    0,    0,  -66,  -80, -145, -140, -148, -148,    0,
    0,    0,    0,  -76,    0,  -75,    0,  -77, -136, -145,
    0, -252, -124, -252, -169,    0,    0,    0, -173, -252,
    0, -112, -169,  -70,  -65,  -69,  -72, -169,  -89,  -62,
  -61,    0,    0, -136,    0, -169,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  204,
  205,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  206,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -64,    0,    0,    0,
    0,    0,    0, -192,    0,    0,    0,    0, -118,    0,
    0,    0,    0,    0,    0,    0,  -63,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -68, -111, -104,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -209, -193,    0,    0, -116,
    0,    0,    0, -226,  -81,    0,    0,    0,    0, -208,
 -177,    0,    0,    0,    0, -154,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    5,   -2,    1,  -33,    0,    0,  140,    0,
    0,    0,    0,   -7,  131,    0,    0,  -30,    0,    0,
  181,  -48,    0,  127,    0,    0,   90,    0,    0,    0,
  107,  106,
};
final static int YYTABLESIZE=214;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         11,
   67,   56,   61,   35,    3,   68,   10,   33,   69,    6,
   34,    3,    4,    5,    4,    5,   27,   27,   56,    7,
   94,    7,    8,   87,    8,   35,   25,   72,   79,   28,
    6,   29,   29,   23,   78,    6,   78,   73,   74,   26,
   30,   56,   56,   56,   31,   56,   56,    7,    6,    6,
    6,  109,    7,    6,   81,   56,    6,   42,   43,    6,
   54,   49,   54,   49,   21,   21,   44,   45,   36,   56,
   32,   21,   81,   65,   21,   37,   55,   46,   55,   65,
   20,   20,   65,   65,   65,   65,   65,   20,    4,    5,
   20,  107,   52,   38,   52,    7,   39,  115,    8,  106,
  113,  117,   73,   74,  114,  121,  110,  112,   40,  119,
   34,   35,  120,   54,   55,  126,  107,   41,   35,   47,
    3,    4,    5,   48,  106,    6,  103,   50,    7,   76,
   77,    8,    3,    4,    5,   73,   74,    6,   51,  111,
    7,   40,   40,    8,    3,    4,    5,   60,   40,    6,
   52,   40,    7,   60,   58,    8,   60,   60,   60,    3,
   58,   59,   63,   58,   58,   58,   64,   59,    4,    5,
   59,   59,   59,   65,  127,    7,   20,   20,    8,   88,
   89,   91,   92,   20,   66,   28,   20,   71,   75,   84,
   90,   93,   96,  122,   97,  101,   42,  102,  123,  125,
  124,  128,  129,    3,    5,    4,   45,   35,   57,   85,
   95,   53,   98,  130,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   49,   32,   36,   11,  257,  272,    2,   10,  275,  262,
   10,  257,  258,  259,  258,  259,  259,  259,   49,  265,
  264,  265,  268,   72,  268,   33,  261,  266,   62,  271,
  257,  274,  274,  259,  280,  262,  280,  276,  277,  274,
  259,   72,   73,   74,  274,   76,   77,  257,  257,  258,
  259,  100,  262,  262,   62,   86,  265,  272,  273,  268,
  270,  270,  272,  272,  258,  259,  272,  273,  263,  100,
  274,  265,   80,  266,  268,  272,  270,  275,  272,  272,
  258,  259,  275,  276,  277,  278,  279,  265,  258,  259,
  268,   99,  270,  272,  272,  265,  272,  105,  268,   99,
  103,  275,  276,  277,  104,  113,  102,  103,  272,  112,
  110,  119,  112,  259,  260,  118,  124,  272,  126,  261,
  257,  258,  259,  275,  124,  262,  263,  259,  265,  278,
  279,  268,  257,  258,  259,  276,  277,  262,  274,  264,
  265,  258,  259,  268,  257,  258,  259,  266,  265,  262,
  259,  268,  265,  272,  266,  268,  275,  276,  277,  257,
  272,  266,  259,  275,  276,  277,  259,  272,  258,  259,
  275,  276,  277,  275,  264,  265,  258,  259,  268,   73,
   74,   76,   77,  265,  272,  271,  268,  272,  275,  257,
  269,  274,  259,  264,  275,  272,  272,  275,  264,  272,
  270,  264,  264,    0,    0,    0,  275,  272,  272,   70,
   80,   31,   86,  124,
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
"$$3 :",
"ret : RETURN OPENPAREN $$3 expression CLOSEPAREN SEMICOLON",
"print : PRINT OPENPAREN CHARCHAIN CLOSEPAREN",
"print : PRINT CHARCHAIN CLOSEPAREN",
"print : PRINT OPENPAREN CHARCHAIN",
"functionExecution : ID OPENPAREN ID CLOSEPAREN",
"functionExecution : ID ID CLOSEPAREN SEMICOLON",
"functionExecution : ID OPENPAREN ID SEMICOLON",
"$$4 :",
"$$5 :",
"iteration : FOR OPENPAREN assign SEMICOLON $$4 condition CLOSEPAREN declarationList $$5 executions",
"$$6 :",
"$$7 :",
"selection : IF OPENPAREN condition CLOSEPAREN THEN $$6 block $$7 ELSE block",
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
"$$8 :",
"assign : ID ASSIGN $$8 expression",
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

//#line 495 "grammar.txt"

String ins;
StringTokenizer st;

void yyerror(String s) {
 System.out.println("par:"+s);
}

boolean newline;

public class ParserUtils {
	ArrayList<String> variableList = new ArrayList<String>();
	//there will be one reverse polish vector for each function, and one for the main. Each vector
	//will be able to be accessed by the context name
	public HashMap<String,ArrayList<String>> intermediateCode = new HashMap<String,ArrayList<String>>();
	ErrorHandler errorHandler = ErrorHandler.getInstance();
	LexicalAnalizer lexical = new LexicalAnalizer(PathContainer.getPath());
	String context = "main";
	//variable to handle the intermediate code generation of the FOR loop
	String forConditionVar;
	//a stack to handle index handling in IF and FOR statements
	ArrayList<Integer> indexStack = new ArrayList<Integer>();
}

public ParserUtils parserUtils = new ParserUtils();

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
//#line 446 "Parser.java"
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
//#line 51 "grammar.txt"
{
	/*initialize the Main intermediate code vector*/
	parserUtils.intermediateCode.put("MAIN", new ArrayList<String>());
}
break;
case 12:
//#line 73 "grammar.txt"
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
		/*return variable name*/
		yyval=val_peek(1);
	}
break;
case 13:
//#line 99 "grammar.txt"
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
//#line 126 "grammar.txt"
{
		/*add variable name to a list that will be consulted later*/
		/*to add type to each variable in the symbol table*/
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 16:
//#line 132 "grammar.txt"
{
		String varName = ((Token)val_peek(2).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
		varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 17:
//#line 139 "grammar.txt"
{
		/*Set function name as the current context*/
		String functionName = ((Token)val_peek(1).obj).getLiteralValue();
		parserUtils.context = functionName;
	}
break;
case 18:
//#line 143 "grammar.txt"
{
		Error error;
		SymbolElement element = new SymbolElement();
		String varNameWithContext = "";
		String varName = ((Token)val_peek(1).obj).getLiteralValue();
		SymbolTable symbolTable = SymbolTable.getInstance();
		String functionName = ((Token)val_peek(4).obj).getLiteralValue();
		ArrayList<String> currentIntCodeVector;
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
		/*default to 0 the return value*/
		currentIntCodeVector = parserUtils.intermediateCode.get(parserUtils.context.toUpperCase());
		currentIntCodeVector.add("rtn");
		currentIntCodeVector.add("0");
		currentIntCodeVector.add("=");

	}
break;
case 19:
//#line 174 "grammar.txt"
{
		/*once the function declaration is over, set the current context as main*/
		parserUtils.context = "main";
	}
break;
case 31:
//#line 199 "grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add("rtn");
	}
break;
case 32:
//#line 203 "grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add("=");
		currentIntCodeVector.add("RET");
	}
break;
case 34:
//#line 219 "grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 35:
//#line 223 "grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 36:
//#line 229 "grammar.txt"
{
		Error error;
		String parameterName = ((Token)val_peek(1).obj).getLiteralValue();
		String functionName = ((Token)val_peek(3).obj).getLiteralValue();
		String parameterNameWContext = parserUtils.context+"_"+parameterName;
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*check if parameter is declared*/
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
case 37:
//#line 249 "grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 38:
//#line 253 "grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 39:
//#line 258 "grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String forConditionVar = ((Token)val_peek(1).obj).getLiteralValue();
		parserUtils.forConditionVar = forConditionVar;
		parserUtils.indexStack.add(currentIntCodeVector.size());
	}
break;
case 40:
//#line 264 "grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());

		/*save the index where the false bifurcation will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[BF]");
	}
break;
case 41:
//#line 272 "grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		Integer falseBifurcationIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1);
		String forReevaluationIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1).toString();
		String falseBifurcationDirection;
		/*increment the condition variable*/
		currentIntCodeVector.add(context+"_"+parserUtils.forConditionVar);
		currentIntCodeVector.add(context+"_"+parserUtils.forConditionVar);
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
case 42:
//#line 302 "grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*save the index where the false bifurcation will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[BF]");

	}
break;
case 43:
//#line 310 "grammar.txt"
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
case 44:
//#line 320 "grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String endBlockDirection = Integer.toString(currentIntCodeVector.size());
		Integer endBlockIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1);
		currentIntCodeVector.set(endBlockIndex,endBlockDirection);
	}
break;
case 45:
//#line 357 "grammar.txt"
{
		String comparatorSymbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(comparatorSymbol);
	}
break;
case 48:
//#line 366 "grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 49:
//#line 370 "grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 51:
//#line 375 "grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 52:
//#line 379 "grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 56:
//#line 387 "grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String varName = ((Token)val_peek(1).obj).getLiteralValue();
		currentIntCodeVector.add(context+"_"+varName);
	}
break;
case 57:
//#line 392 "grammar.txt"
{
		Error error;
		String varName = ((Token)val_peek(3).obj).getLiteralValue();
		String assignSymbol = ((Token)val_peek(2).obj).getLiteralValue();
		String context = parserUtils.context;
		String parameterNameWContext;
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		HashSet<String> contextList = new HashSet<String>();
		boolean found = false;
		contextList.add(context);
		contextList.add("MAIN");
		/*check if identifier is declared*/
		for(String cont: contextList) {
			parameterNameWContext= cont+"_"+varName;
			element = symbolTable.identify(parameterNameWContext);
			if(element != null && element.getUse() != "FUNC") {
				found = true;
				break;
			}
		}
		if(!found) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+varName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		} else {
			currentIntCodeVector.add(assignSymbol);
		}
	}
break;
case 58:
//#line 421 "grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 59:
//#line 427 "grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 61:
//#line 436 "grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 62:
//#line 442 "grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 64:
//#line 451 "grammar.txt"
{
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		SymbolTable symbolTable = SymbolTable.getInstance();
		SymbolElement element = new SymbolElement();

		element = symbolTable.identify(varName);
		/*The only allowed type for constants is ULONG*/
		element.setVarType("ULONG");
		currentIntCodeVector.add(varName);
	}
break;
case 65:
//#line 463 "grammar.txt"
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
		/*check if parameter is declared*/
		if(!found) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+varName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		} else {
			currentIntCodeVector.add(parserUtils.context+"_"+varName);
		}
	}
break;
//#line 1033 "Parser.java"
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
