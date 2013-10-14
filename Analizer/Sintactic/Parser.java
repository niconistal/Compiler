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
}
//#line 43 "Parser.java"




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
   19,   19,   19,   20,   20,   20,   20,   20,   20,   20,
   20,   23,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   21,   22,   22,   22,   26,   26,   26,   27,   27,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    1,    2,    1,    1,    1,    2,
    1,    3,    3,    1,    3,    3,    0,    6,    6,    2,
    1,    2,    1,    1,    1,    2,    2,    2,    2,    2,
    5,    4,    4,    4,    3,    3,    4,    4,    4,    0,
    9,    7,    7,    8,    7,    7,    6,    6,    5,    5,
    5,    3,    4,    4,    3,    3,    4,    3,    3,    1,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    0,    7,    8,    9,   14,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   70,   69,    0,    0,    0,    0,   68,
    0,    6,   20,    0,   26,   27,   28,   29,   30,   12,
    0,   13,    0,   35,    0,    0,    0,    0,   17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   11,    0,
   16,   15,   34,   38,   39,   37,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   66,   67,    0,   10,    0,   24,   23,   25,    0,
    0,    0,    0,    0,    0,    0,    0,   51,    0,    0,
    0,    0,    0,    0,    0,   22,    0,   18,    0,    0,
    0,    0,    0,    0,    0,    0,   55,   58,   47,    0,
    0,    0,   19,    0,    0,    0,   53,   54,   57,   46,
    0,   45,   33,    0,   32,    0,   44,   31,    0,
};
final static short yydgoto[] = {                          1,
    9,    2,   83,   84,   85,   13,   14,   70,   15,   24,
   16,   77,   96,   86,   98,   99,   18,   19,   20,   21,
   22,   37,   38,  146,   87,   39,   40,
};
final static short yysindex[] = {                         0,
    0,   22, -252, -236, -248, -238, -240, -202,    0,   22,
 -105,    0,    0,    0,    0, -246,    0, -225, -216, -213,
 -209, -206, -172, -164, -180, -148, -145, -143, -186, -138,
 -128, -121, -124,    0,    0, -143, -187, -118, -157,    0,
 -105,    0,    0,  -93,    0,    0,    0,    0,    0,    0,
  -81,    0,  -74,    0,  -88,  -73, -136, -223,    0,  -66,
 -143, -188, -143, -143, -143,  -61, -143, -143,    0, -215,
    0,    0,    0,    0,    0,    0,  -69, -143,  -55,   22,
   10,   -5, -233, -105,    0,    0,  -43, -136, -157, -157,
   10,    0,    0, -175,    0, -204,    0,    0,    0,  -50,
  -41, -242, -233,   22, -105,  -38,   10,    0,  -24,  -22,
   10,  -35, -143, -171,  -26,    0,  -17,    0, -233,   22,
   22,  -42,  -19,  -16,   10,  -20,    0,    0,    0,   10,
 -179,  -13,    0, -233, -105, -105,    0,    0,    0,    0,
   10,    0,    0,  -11,    0, -105,    0,    0, -105,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  256,
  262,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -107,    0,
  265,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   -6,    0,   -1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -235, -133,    0,  -44,  -86,  -65,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -17,    0,    0, -217,  -84,
    0, -206,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -29,  -21,    0, -209,    0,    0,    0,    0,
    0,    0,    0,  -63,    2,    4,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    5,
};
final static short yygindex[] = {                         0,
    0,    0,   12,    3,    8,  -40,    0,    0,  193,    0,
    0,    0,    0,   -2,  186,    0,    0,    0,    0,    0,
    9,  -27,  -30,    0,  -79,   81,   99,
};
final static int YYTABLESIZE=290;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
   57,  106,  108,   69,   11,   62,   23,   17,   43,   12,
   27,  112,   41,   10,    3,   33,   44,   42,   31,    6,
   30,    7,   28,    3,   25,   29,    7,  126,    6,   95,
   79,  129,  119,   32,   60,   88,   60,   26,   43,    6,
   60,    3,    4,    5,    6,  140,   45,  102,   75,    7,
  142,   76,    8,    4,    5,   46,   34,   35,   47,  115,
    7,  147,   48,    8,   94,   49,  114,   97,    3,    4,
    5,   36,   58,    6,   80,   94,    7,   17,   63,    8,
   81,  110,  105,   34,   35,  131,   82,   12,   64,   65,
  109,  104,  143,   97,   54,  144,   64,   65,  113,   50,
   51,   17,  124,  132,   64,   65,  122,   52,   53,   12,
   12,  123,   55,  120,  121,   34,   35,   17,   17,   43,
   67,   68,  135,  136,   21,   21,   12,   42,   42,   56,
  134,   21,   43,   43,   21,   59,   61,   31,   61,   64,
   65,   42,   28,   17,   89,   90,   43,   61,  149,   65,
   65,   65,    4,    5,   65,   65,   66,   65,   65,    7,
   65,   65,    8,    3,   65,   92,   93,   65,   65,   65,
   63,   63,   63,   20,   20,   63,   63,   71,   63,   63,
   20,   63,   63,   20,   72,   63,   73,  100,   63,   63,
   63,   64,   64,   64,   40,   40,   64,   64,   74,   64,
   64,   40,   64,   64,   40,   78,   64,   91,  117,   64,
   64,   64,   52,   52,   52,    4,    5,   52,   52,  103,
   52,  137,    7,   52,   52,    8,  111,    6,    6,    6,
   52,  125,    6,  118,  130,    6,   20,   20,    6,  127,
   56,  128,   56,   20,  138,  133,   20,  139,   59,  141,
   59,    3,    4,    5,   50,    3,    6,   80,  145,    7,
  148,    5,    8,  107,    4,   36,    3,    4,    5,  101,
   62,    6,   80,   43,    7,   42,   41,    8,    3,    4,
    5,  116,    0,    6,    0,    0,    7,    0,    0,    8,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   28,   81,   82,   44,    2,   36,  259,   10,   11,    2,
  259,   91,   10,    2,  257,    7,  263,   10,  259,  262,
  259,  257,  271,  257,  261,  274,  262,  107,  262,   70,
   61,  111,  275,  274,  270,   63,  272,  274,   41,  257,
   32,  257,  258,  259,  262,  125,  272,   78,  272,  265,
  130,  275,  268,  258,  259,  272,  259,  260,  272,  264,
  265,  141,  272,  268,  280,  272,   94,   70,  257,  258,
  259,  274,  259,  262,  263,  280,  265,   80,  266,  268,
  269,   84,   80,  259,  260,  113,  275,   80,  276,  277,
   83,   80,  272,   96,  275,  275,  276,  277,  274,  272,
  273,  104,  105,  275,  276,  277,  104,  272,  273,  102,
  103,  104,  261,  102,  103,  259,  260,  120,  121,  122,
  278,  279,  120,  121,  258,  259,  119,  120,  121,  275,
  119,  265,  135,  136,  268,  274,  270,  259,  272,  276,
  277,  134,  271,  146,   64,   65,  149,  272,  146,  257,
  258,  259,  258,  259,  262,  263,  275,  265,  266,  265,
  268,  269,  268,  257,  272,   67,   68,  275,  276,  277,
  257,  258,  259,  258,  259,  262,  263,  259,  265,  266,
  265,  268,  269,  268,  259,  272,  275,  257,  275,  276,
  277,  257,  258,  259,  258,  259,  262,  263,  272,  265,
  266,  265,  268,  269,  268,  272,  272,  269,  259,  275,
  276,  277,  257,  258,  259,  258,  259,  262,  263,  275,
  265,  264,  265,  268,  269,  268,  270,  257,  258,  259,
  275,  270,  262,  275,  270,  265,  258,  259,  268,  264,
  270,  264,  272,  265,  264,  272,  268,  264,  270,  270,
  272,  257,  258,  259,  272,    0,  262,  263,  272,  265,
  272,    0,  268,  269,    0,  272,  257,  258,  259,   77,
  272,  262,  263,  272,  265,  272,  272,  268,  257,  258,
  259,   96,   -1,  262,   -1,   -1,  265,   -1,   -1,  268,
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
"iteration : FOR OPENPAREN assign SEMICOLON condition CLOSEPAREN declarationList $$3 executions",
"iteration : FOR assign SEMICOLON condition CLOSEPAREN declarationList executions",
"iteration : FOR OPENPAREN assign SEMICOLON condition declarationList executions",
"selection : IF OPENPAREN condition CLOSEPAREN THEN block ELSE block",
"selection : IF condition CLOSEPAREN THEN block ELSE block",
"selection : IF OPENPAREN condition THEN block ELSE block",
"selection : IF OPENPAREN condition block ELSE block",
"selection : IF OPENPAREN condition CLOSEPAREN THEN block",
"selection : IF condition CLOSEPAREN THEN block",
"selection : IF OPENPAREN condition THEN block",
"selection : IF OPENPAREN condition CLOSEPAREN block",
"condition : expression COMPARATOR expression",
"block : BEGIN declarationList executions END",
"block : BEGIN declarationList declaration END",
"block : declarationList declaration END",
"block : BEGIN declarationList declaration",
"block : BEGIN executions execution END",
"block : executions execution END",
"block : BEGIN executions execution",
"block : declaration",
"block : execution",
"assign : ID ASSIGN expression",
"expression : expression PLUS term",
"expression : expression MINUS term",
"expression : term",
"term : term PRODUCT factor",
"term : term DIVISION factor",
"term : factor",
"factor : CTE",
"factor : ID",
};

//#line 392 "./grammar.txt"

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
//#line 463 "Parser.java"
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
//#line 62 "./grammar.txt"
{
	/*initialize the Main intermediate code vector*/
	parserUtils.intermediateCode.put("MAIN", new ArrayList<String>());
}
break;
case 12:
//#line 84 "./grammar.txt"
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
//#line 110 "./grammar.txt"
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
//#line 137 "./grammar.txt"
{
		/*add variable name to a list that will be consulted later*/
		/*to add type to each variable in the symbol table*/
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 16:
//#line 143 "./grammar.txt"
{
		String varName = ((Token)val_peek(2).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
		varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 17:
//#line 150 "./grammar.txt"
{
		/*Set function name as the current context*/
		String functionName = ((Token)val_peek(1).obj).getLiteralValue();
		parserUtils.context = functionName;
	}
break;
case 18:
//#line 154 "./grammar.txt"
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
//#line 178 "./grammar.txt"
{
		/*once the function declaration is over, set the current context as main*/
		parserUtils.context = "main";
	}
break;
case 32:
//#line 204 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 33:
//#line 208 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 35:
//#line 214 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 36:
//#line 218 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 37:
//#line 224 "./grammar.txt"
{
		Error error;
		String parameterName = ((Token)val_peek(1).obj).getLiteralValue();
		String parameterNameWContext = parserUtils.context+"_"+parameterName;
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		/*check if parameter is declared */
		element = symbolTable.identify(parameterNameWContext);
		if(!(element != null && element.getUse() != "FUNC")) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+parameterName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		}
	}
break;
case 38:
//#line 237 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 39:
//#line 241 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 40:
//#line 246 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*set the following index as the TRUE bifurcation*/
		/* currentIntCodeVector.add()*/
	}
break;
case 42:
//#line 252 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in iteration execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 43:
//#line 256 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in iteration execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 45:
//#line 262 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 46:
//#line 266 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 47:
//#line 270 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 49:
//#line 275 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 50:
//#line 279 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 51:
//#line 283 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 52:
//#line 288 "./grammar.txt"
{
		String comparatorSymbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(comparatorSymbol);
	}
break;
case 55:
//#line 297 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 56:
//#line 301 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 58:
//#line 306 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 59:
//#line 310 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 62:
//#line 317 "./grammar.txt"
{
		Error error;
		String varName = ((Token)val_peek(2).obj).getLiteralValue();
		String assignSymbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		String parameterNameWContext = context+"_"+varName;
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*check if parameter is declared */
		element = symbolTable.identify(parameterNameWContext);
		if(!(element != null && element.getUse() != "FUNC")) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+varName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		} else {
			currentIntCodeVector.add(varName);
			currentIntCodeVector.add(assignSymbol);
		}
	}
break;
case 63:
//#line 336 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 64:
//#line 342 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 66:
//#line 351 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 67:
//#line 357 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 69:
//#line 366 "./grammar.txt"
{
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(varName);
	}
break;
case 70:
//#line 372 "./grammar.txt"
{
		Error error;
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		String context = parserUtils.context;
		String parameterNameWContext = context+"_"+varName;
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*check if parameter is declared */
		element = symbolTable.identify(parameterNameWContext);
		if(!(element != null && element.getUse() != "FUNC")) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+varName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		} else {
			currentIntCodeVector.add(varName);
		}
	}
break;
//#line 982 "Parser.java"
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
