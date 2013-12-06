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
   16,   16,   16,   22,   24,   17,   17,   17,   18,   18,
   18,   19,   25,   26,   28,   30,   20,   20,   20,   20,
   20,   20,   20,   20,   31,   32,   33,   27,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   34,   21,
   23,   23,   23,   35,   35,   35,   36,   36,   36,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    1,    2,    1,    1,    1,    2,
    1,    3,    3,    1,    3,    3,    0,    6,    6,    2,
    1,    2,    1,    1,    1,    2,    2,    2,    2,    2,
    7,    6,    6,    0,    0,    4,    3,    3,    4,    4,
    4,   11,    0,    0,    0,    0,   11,   10,   10,   10,
    8,    8,    8,    7,    0,    0,    0,    3,    4,    4,
    3,    3,    3,    2,    2,    2,    1,    1,    0,    4,
    3,    3,    1,    3,    3,    1,    1,    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    0,    7,    8,    9,   14,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   69,    0,    0,
   43,    0,    0,   77,    0,   79,    0,    0,    0,   76,
    0,    6,   20,    0,   26,   27,   28,   29,   30,   12,
    0,   13,    0,   37,    0,    0,    0,    0,   17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   11,    0,
   16,   15,   36,   40,    0,   41,   39,    0,    0,    0,
    0,    0,   55,    0,    0,    0,    0,   55,   74,   75,
    0,   10,    0,   24,   23,   25,    0,    0,   44,   55,
   55,    0,   55,    0,    0,   34,    0,    0,   22,    0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,   56,
    0,    0,   56,    0,    0,   19,    0,   57,   57,   66,
    0,    0,    0,   64,    0,    0,    0,   54,    0,    0,
    0,   45,   52,   53,    0,    0,   63,   61,    0,    0,
   51,    0,    0,   35,    0,   35,    0,   59,   60,   57,
    0,   57,   57,   33,   35,   32,   46,   49,   57,   50,
   48,   31,   42,   47,
};
final static short yydgoto[] = {                          1,
    9,    2,  116,  117,  118,   13,   14,   70,   15,   24,
   16,   78,   93,  119,   95,   96,   18,   19,   20,   21,
   22,  107,   37,  164,   60,  112,   38,  157,  120,  173,
  102,  135,  138,   57,   39,   40,
};
final static short yysindex[] = {                         0,
    0,  -19, -257, -220, -202, -216, -223, -224,    0,  -19,
 -132,    0,    0,    0,    0, -185,    0, -168, -160, -148,
 -144, -133, -253, -172, -127,  -98,  -95,    0,  -78,  -91,
    0, -108, -207,    0, -108,    0, -102,  -90, -124,    0,
 -132,    0,    0,  -68,    0,    0,    0,    0,    0,    0,
  -61,    0,  -59,    0,  -70,  -66, -108, -228,    0,  -50,
  -64, -198, -108, -108, -108,  -57, -108, -108,    0, -175,
    0,    0,    0,    0, -116,    0,    0,  -40,  -53,  -52,
  -18,   -8,    0,   -7, -116, -124, -124,    0,    0,    0,
  -30,    0, -235,    0,    0,    0,   -9,  -12,    0,    0,
    0,  -55,    0,  -55,  -55,    0, -108,  -20,    0,  -16,
    0, -108,  -55,  -55,  -43, -177,  -35,    0,    0,    0,
  -55,    0,    0, -108,  -85,    0,  -10,    0,    0,    0,
  -19,  -23,    2,    0,   -6,    0,   -3,    0,    4,  -17,
    5,    0,    0,    0,  -11,    6,    0,    0,  -55,    8,
    0,  -55,  -55,    0,    7,    0,  -55,    0,    0,    0,
  -55,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  268,
  269,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -180,    0,    0,    0,    0,    0, -131,    0,
  271,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   10,    0,    0,    0,    0,    0,
    0,    0,    0,  -31, -181, -119, -107,    0,    0,    0,
  -93,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -151,  -71,    0,
    0, -191,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -130, -155,    0,    0, -191,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -86,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -1,    3,    1,  -28,    0,    0,  194,    0,
    0,    0,    0,   -2,  180,    0,    0,   -4,    0,    0,
  215,  170,  -51,  -34,    0,    0,  -25,    0,  -87,    0,
  -63,  -13, -114,    0,  113,  128,
};
final static int YYTABLESIZE=282;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
   10,   23,   12,   36,   11,   75,   61,   17,   43,   62,
   42,   85,   41,  143,  144,   69,  122,  123,   50,   51,
  104,  151,    4,    5,  105,  128,  129,   36,  108,    7,
   36,   32,    8,  136,   33,   34,  113,  114,   43,  121,
   25,   92,   30,   76,   91,  168,   77,  170,  171,   35,
   31,   27,   36,   26,  174,  125,   27,   82,   36,   36,
   36,  160,   36,   36,  162,  163,   29,   94,   28,  167,
   83,   29,  140,  169,   58,   78,   84,   44,   56,    3,
   57,    3,    4,    5,    6,   78,  127,   58,   78,    7,
   94,   78,    8,   58,   78,   78,   78,   78,   78,   52,
   53,    6,   36,   45,   91,    7,    6,   36,  137,  139,
    7,   46,   17,  131,   43,   12,  133,  132,   67,   36,
   67,  166,  150,   47,   73,    4,    5,   48,   17,   43,
  172,  146,    7,  145,   73,    8,   71,   73,   49,   65,
   73,   65,   43,   73,   73,   73,   71,   54,   72,   71,
   33,   34,   71,   67,   68,   71,   71,   71,   72,   64,
   65,   72,   55,   63,   72,   34,   34,   72,   72,   72,
    6,    6,    6,   64,   65,    6,   86,   87,    6,   56,
   58,    6,   59,   62,   66,   62,   21,   21,    3,  141,
   64,   65,   21,   21,   89,   90,   21,   71,   68,   72,
   68,    3,    4,    5,   73,   74,    6,  115,   79,    7,
   81,   88,    8,    3,    4,    5,   97,   28,    6,   99,
  130,    7,    4,    5,    8,   55,   55,   55,  134,    7,
   55,   55,    8,   55,    4,    5,   55,    3,    4,    5,
  147,    7,    6,  106,    8,    7,    4,    5,    8,  110,
  100,  126,  158,    7,  154,   50,    8,  155,   64,   65,
  101,  103,  111,  149,  142,  148,  152,    3,    5,  159,
    4,   98,  109,  153,   80,  124,  156,  161,  165,    0,
   38,   70,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    2,  259,    2,    8,    2,   57,   32,   10,   11,   35,
   10,   63,   10,  128,  129,   44,  104,  105,  272,  273,
   84,  136,  258,  259,   88,  113,  114,   32,  264,  265,
   35,  256,  268,  121,  259,  260,  100,  101,   41,  103,
  261,   70,  259,  272,  280,  160,  275,  162,  163,  274,
  274,  259,   57,  274,  169,  107,  259,  256,   63,   64,
   65,  149,   67,   68,  152,  153,  274,   70,  271,  157,
  269,  274,  124,  161,  256,  256,  275,  263,  270,  257,
  272,  257,  258,  259,  262,  266,  112,  269,  269,  265,
   93,  272,  268,  275,  275,  276,  277,  278,  279,  272,
  273,  257,  107,  272,  280,  257,  262,  112,  122,  123,
  262,  272,  115,  115,  117,  115,  116,  115,  270,  124,
  272,  156,  136,  272,  256,  258,  259,  272,  131,  132,
  165,  131,  265,  131,  266,  268,  256,  269,  272,  270,
  272,  272,  145,  275,  276,  277,  266,  275,  256,  269,
  259,  260,  272,  278,  279,  275,  276,  277,  266,  276,
  277,  269,  261,  266,  272,  259,  260,  275,  276,  277,
  257,  258,  259,  276,  277,  262,   64,   65,  265,  275,
  259,  268,  274,  270,  275,  272,  258,  259,  257,  275,
  276,  277,  264,  265,   67,   68,  268,  259,  270,  259,
  272,  257,  258,  259,  275,  272,  262,  263,  259,  265,
  275,  269,  268,  257,  258,  259,  257,  271,  262,  272,
  264,  265,  258,  259,  268,  257,  258,  259,  264,  265,
  262,  263,  268,  265,  258,  259,  268,  257,  258,  259,
  264,  265,  262,  274,  268,  265,  258,  259,  268,  259,
  269,  272,  264,  265,  272,  272,  268,  275,  276,  277,
  269,  269,  275,  270,  275,  264,  270,    0,    0,  264,
    0,   78,   93,  270,   60,  106,  272,  270,  272,   -1,
  272,  272,
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
"ret : RETURN OPENPAREN retRule1 expression CLOSEPAREN SEMICOLON retRule2",
"ret : RETURN retRule1 expression CLOSEPAREN SEMICOLON retRule2",
"ret : RETURN OPENPAREN retRule1 expression SEMICOLON retRule2",
"retRule1 :",
"retRule2 :",
"print : PRINT OPENPAREN CHARCHAIN CLOSEPAREN",
"print : PRINT CHARCHAIN CLOSEPAREN",
"print : PRINT OPENPAREN CHARCHAIN",
"functionExecution : ID OPENPAREN ID CLOSEPAREN",
"functionExecution : ID ID CLOSEPAREN SEMICOLON",
"functionExecution : ID OPENPAREN ID SEMICOLON",
"iteration : FOR OPENPAREN forRule0 assign SEMICOLON forRule1 condition CLOSEPAREN forRule2 block forRule3",
"forRule0 :",
"forRule1 :",
"forRule2 :",
"forRule3 :",
"selection : IF OPENPAREN condition CLOSEPAREN THEN ifrule1 block ifrule2 ELSE block ifrule3",
"selection : IF condition CLOSEPAREN THEN ifrule1 block ifrule2 ELSE block ifrule3",
"selection : IF OPENPAREN condition THEN ifrule1 block ifrule2 ELSE block ifrule3",
"selection : IF OPENPAREN condition CLOSEPAREN ifrule1 block ifrule2 ELSE block ifrule3",
"selection : IF OPENPAREN condition CLOSEPAREN THEN ifrule1 block ifrule3",
"selection : IF error condition CLOSEPAREN THEN ifrule1 block ifrule3",
"selection : IF OPENPAREN condition error THEN ifrule1 block ifrule3",
"selection : IF OPENPAREN condition CLOSEPAREN ifrule1 block ifrule3",
"ifrule1 :",
"ifrule2 :",
"ifrule3 :",
"condition : expression COMPARATOR expression",
"block : BEGIN declarationList executions END",
"block : BEGIN declarationList declaration END",
"block : declarationList declaration END",
"block : BEGIN declarationList declaration",
"block : BEGIN executions END",
"block : executions END",
"block : BEGIN executions",
"block : BEGIN END",
"block : declaration",
"block : execution",
"$$3 :",
"assign : ID ASSIGN $$3 expression",
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

//#line 555 "./grammar.txt"

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

	boolean forAssignment = false;
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
//#line 492 "Parser.java"
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
//#line 51 "./grammar.txt"
{
	/*initialize the Main intermediate code vector*/
	parserUtils.intermediateCode.put("MAIN", new ArrayList<String>());
}
break;
case 12:
//#line 73 "./grammar.txt"
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
			error = new Error(Error.TYPE_WARNING,"Variable redeclartion", parserUtils.lexical.getLine());
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
//#line 99 "./grammar.txt"
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
				error = new Error(Error.TYPE_WARNING,"Variable redeclartion", parserUtils.lexical.getLine());
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
//#line 126 "./grammar.txt"
{
		/*add variable name to a list that will be consulted later*/
		/*to add type to each variable in the symbol table*/
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 16:
//#line 132 "./grammar.txt"
{
		String varName = ((Token)val_peek(2).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
		varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 17:
//#line 139 "./grammar.txt"
{
		/*Set function name as the current context*/
		String functionName = ((Token)val_peek(1).obj).getLiteralValue();
		parserUtils.context = functionName;
	}
break;
case 18:
//#line 143 "./grammar.txt"
{
		Error error;
		SymbolElement element = new SymbolElement();
		/*newElement will be the auxiliar variable to store de parameter passed to the current function*/
		SymbolElement newElement = new SymbolElement("ID",null);
		String varNameWithContext = "";
		String varName = ((Token)val_peek(1).obj).getLiteralValue();
		SymbolTable symbolTable = SymbolTable.getInstance();
		String functionName = ((Token)val_peek(4).obj).getLiteralValue();
		ArrayList<String> currentIntCodeVector;

		/*add auxiliar parameter variable*/
		symbolTable.addSymbol(parserUtils.context+"_parameter",newElement);
		/*add FUNCTION use to the function identifier*/
		element = symbolTable.identify(functionName);

		/*check for function redeclaration*/
		if(element!= null && element.getUse() == "FUNC") {
			error = new Error(Error.TYPE_WARNING,"Function redeclartion", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		}
		element.setUse("FUNC");
		/*initialize intermediate code vector*/
		parserUtils.intermediateCode.put(parserUtils.context.toUpperCase(), new ArrayList<String>());
		varNameWithContext = parserUtils.context+"_"+varName;
		element = symbolTable.identify(varNameWithContext);

		/*set PARAMETER use to each identifier in the parameters declaration*/
		element.setUse("PARAM");
		parserUtils.variableList.clear();
		/*default to 0 the return value*/
		currentIntCodeVector = parserUtils.intermediateCode.get(parserUtils.context.toUpperCase());

		/*add direction to function parameter*/
		currentIntCodeVector.add(((Token)val_peek(4).obj).getLiteralValue()+"_"+((Token)val_peek(1).obj).getLiteralValue()); /*function variable*/
		currentIntCodeVector.add(((Token)val_peek(4).obj).getLiteralValue()+"_parameter"); /*auxiliar*/
		currentIntCodeVector.add("=");

		currentIntCodeVector.add("rtn");
		currentIntCodeVector.add("0");
		currentIntCodeVector.add("=");


	}
break;
case 19:
//#line 187 "./grammar.txt"
{
		/*once the function declaration is over, set the current context as main*/
		parserUtils.context = "main";
	}
break;
case 32:
//#line 213 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 33:
//#line 217 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 34:
//#line 222 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add("rtn");
	}
break;
case 35:
//#line 228 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add("=");
		currentIntCodeVector.add("[RET]");
	}
break;
case 36:
//#line 236 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String message = ((Token)val_peek(1).obj).getLiteralValue();
		currentIntCodeVector.add(message);
		currentIntCodeVector.add("[PRINT]");
	}
break;
case 37:
//#line 243 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 38:
//#line 247 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 39:
//#line 253 "./grammar.txt"
{
		Error error;
		String parameterName = ((Token)val_peek(1).obj).getLiteralValue();
		String functionName = ((Token)val_peek(3).obj).getLiteralValue();
		String parameterNameWContext = parserUtils.context+"_"+parameterName;
		SymbolElement element = new SymbolElement();
		SymbolElement functionElement = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*check if parameter is declared*/
		element = symbolTable.identify(parameterNameWContext);
		if(!(element != null && element.getUse() != "FUNC")) {
			error = new Error(Error.TYPE_FATAL,"Identifier "+parameterName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		}
		/*check if function is declared*/
		functionElement = symbolTable.identify(functionName);
		if(!(functionElement != null && functionElement.getUse() == "FUNC")) {
			error = new Error(Error.TYPE_FATAL,"Function "+functionName+" not found.", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		}
		/*assign the parameter value to the auxiliar parameter previously reserved*/
		currentIntCodeVector.add(functionName+"_parameter");

		SymbolElement SE = new SymbolElement();
		SE.setUse("VAR");
		SE.setType("PARAM");
		symbolTable.addSymbol(functionName+"_parameter", SE);
		currentIntCodeVector.add(context+"_"+parameterName);
		currentIntCodeVector.add("[LEA]");
		/*add function call to the intermediate code vector*/
		currentIntCodeVector.add(functionName);
		currentIntCodeVector.add("[CALL]");
		currentIntCodeVector.add("rtn");
	}
break;
case 40:
//#line 289 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 41:
//#line 293 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 43:
//#line 314 "./grammar.txt"
{
		parserUtils.forAssignment = true;
	}
break;
case 44:
//#line 318 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		parserUtils.indexStack.add(currentIntCodeVector.size());
	}
break;
case 45:
//#line 324 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());

		/*save the index where the false bifurcation will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[BF]");
	}
break;
case 46:
//#line 334 "./grammar.txt"
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
case 48:
//#line 357 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 49:
//#line 361 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 50:
//#line 365 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 52:
//#line 370 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 53:
//#line 374 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 54:
//#line 378 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 55:
//#line 383 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*save the index where the false bifurcation will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[BF]");
	}
break;
case 56:
//#line 392 "./grammar.txt"
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
case 57:
//#line 404 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String endBlockDirection = Integer.toString(currentIntCodeVector.size());
		Integer endBlockIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1);
		currentIntCodeVector.set(endBlockIndex,endBlockDirection);
	}
break;
case 58:
//#line 412 "./grammar.txt"
{
		String comparatorSymbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(comparatorSymbol);
	}
break;
case 61:
//#line 421 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 62:
//#line 425 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 64:
//#line 430 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 65:
//#line 434 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 69:
//#line 442 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String varName = ((Token)val_peek(1).obj).getLiteralValue();
		currentIntCodeVector.add(context+"_"+varName);
	}
break;
case 70:
//#line 447 "./grammar.txt"
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
		if(parserUtils.forAssignment) {
			parserUtils.forConditionVar = varName;
			parserUtils.forAssignment = false;
		}

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
		}
		currentIntCodeVector.add(assignSymbol);

	}
break;
case 71:
//#line 481 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 72:
//#line 487 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 74:
//#line 496 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 75:
//#line 502 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 77:
//#line 511 "./grammar.txt"
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
case 78:
//#line 523 "./grammar.txt"
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
		}
		currentIntCodeVector.add(parserUtils.context+"_"+varName);

	}
break;
//#line 1182 "Parser.java"
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
