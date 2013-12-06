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
    2,    0,    1,    1,    1,    1,    3,    3,    5,    5,
    8,    8,    9,    6,    6,   10,   10,   12,   11,    7,
    4,    4,   13,   13,   15,   15,   14,   14,   14,   14,
   14,   16,   16,   16,   22,   24,   17,   17,   17,   18,
   18,   18,   19,   25,   26,   28,   30,   20,   20,   20,
   20,   20,   20,   20,   20,   31,   32,   33,   27,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   34,
   21,   23,   23,   23,   35,   35,   35,   36,   36,   36,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    1,    2,    2,    1,    1,    1,
    2,    1,    3,    3,    1,    3,    3,    0,    6,    6,
    2,    1,    2,    1,    1,    1,    2,    2,    2,    2,
    2,    7,    6,    6,    0,    0,    4,    3,    3,    4,
    4,    4,   11,    0,    0,    0,    0,   11,   10,   10,
   10,    8,    8,    8,    7,    0,    0,    0,    3,    4,
    4,    3,    3,    3,    2,    2,    2,    1,    1,    0,
    4,    3,    3,    1,    3,    3,    1,    1,    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    2,
    0,    0,    8,    9,   10,   15,    0,   22,    0,    0,
    0,    0,    0,    6,    0,    0,    0,    0,    0,   70,
    0,    0,   44,    0,    0,   78,    0,   80,    0,    0,
    0,   77,    0,    7,   21,    0,   27,   28,   29,   30,
   31,   13,    0,   14,    0,   38,    0,    0,    0,    0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   12,    0,   17,   16,   37,   41,    0,   42,   40,    0,
    0,    0,    0,    0,   56,    0,    0,    0,    0,   56,
   75,   76,    0,   11,    0,   25,   24,   26,    0,    0,
   45,   56,   56,    0,   56,    0,    0,   35,    0,    0,
   23,    0,   19,    0,    0,    0,    0,    0,    0,    0,
    0,   57,    0,    0,   57,    0,    0,   20,    0,   58,
   58,   67,    0,    0,    0,   65,    0,    0,    0,   55,
    0,    0,    0,   46,   53,   54,    0,    0,   64,   62,
    0,    0,   52,    0,    0,   36,    0,   36,    0,   60,
   61,   58,    0,   58,   58,   34,   36,   33,   47,   50,
   58,   51,   49,   32,   43,   48,
};
final static short yydgoto[] = {                          1,
   10,    2,  118,  119,  120,   14,   15,   72,   16,   26,
   17,   80,   95,  121,   97,   98,   19,   20,   21,   22,
   23,  109,   39,  166,   62,  114,   40,  159,  122,  175,
  104,  137,  140,   59,   41,   42,
};
final static short yysindex[] = {                         0,
    0,  -48, -270, -237, -216, -168, -233, -226, -149,    0,
    2,  -16,    0,    0,    0,    0, -194,    0, -171, -164,
 -150, -137, -134,    0, -131, -119, -181, -111, -110,    0,
  -76, -130,    0,  -91, -253,    0,  -91,    0, -193,  -99,
  -83,    0,  -16,    0,    0,  -78,    0,    0,    0,    0,
    0,    0,  -72,    0,  -70,    0,  -71,  -81,  -91, -190,
    0,  -53,  -60, -236,  -91,  -91,  -91,  -33,  -91,  -91,
    0, -178,    0,    0,    0,    0,  -79,    0,    0,  -41,
  -34,  -18,  -30,  -21,    0,  -14,  -79,  -83,  -83,    0,
    0,    0,  -17,    0, -234,    0,    0,    0,    4,  -13,
    0,    0,    0,  -36,    0,  -36,  -36,    0,  -91,   -7,
    0,   -6,    0,  -91,  -36,  -36,  -24, -157, -135,    0,
    0,    0,  -36,    0,    0,  -91, -163,    0,    5,    0,
    0,    0,    2, -102,   10,    0,    6,    0,    7,    0,
    8,   -4,   -3,    0,    0,    0,  -40,   11,    0,    0,
  -36,    9,    0,  -36,  -36,    0,   12,    0,  -36,    0,
    0,    0,  -36,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  258,  281,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -180,    0,    0,    0,    0,    0,
 -129,    0,  282,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   13,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   14,    0,    0,    0,
    0,    0,    0,    0,    0,  -12, -197, -117, -105,    0,
    0,    0,  -58,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -80,
  -65,    0,    0, -228,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -144,  -77,    0,    0, -228,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -84,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -1,    3,    1,  -29,    0,    0,  203,    0,
    0,    0,    0,   -2,  192,    0,    0,   -5,    0,    0,
  226,  181,  -52,  -90,    0,    0,  -26,    0,  -88,    0,
  -50,  -87, -115,    0,  146,  161,
};
final static int YYTABLESIZE=289;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   11,   24,   13,   38,   12,   29,   77,   63,   18,   45,
   64,   44,   87,   43,  145,  146,   71,  124,  125,   84,
   31,   25,  153,    5,    6,   32,  130,  131,   38,  110,
    8,   38,   85,    9,  138,  106,  139,  141,   86,  107,
   45,   57,   94,   58,   27,   93,  170,   33,  172,  173,
  152,  115,  116,   38,  123,  176,  127,   28,   59,   38,
   38,   38,  162,   38,   38,  164,  165,  168,   46,   96,
  169,   59,   65,  142,  171,   79,  174,   59,    4,    5,
    6,   78,   66,   67,   79,   79,    8,  129,   79,    9,
   29,   79,   96,   56,   79,   79,   79,   79,   79,    4,
   47,   93,   30,   38,    7,   31,   34,   48,   38,   35,
   36,  143,   66,   67,   18,  133,   45,   13,  135,  134,
   38,   49,    5,    6,   37,   66,   74,   66,  136,    8,
   18,   45,    9,  148,   50,  147,   74,   51,   72,   74,
   52,   53,   74,   61,   45,   74,   74,   74,   72,   57,
   73,   72,   54,   55,   72,    5,    6,   72,   72,   72,
   73,  149,    8,   73,   58,    9,   73,   35,   36,   73,
   73,   73,    7,    7,    7,   68,    8,    7,    4,    7,
    7,    8,   60,    7,    7,   63,   73,   63,   74,   68,
   76,   68,   22,   22,   69,   70,   66,   67,   22,   22,
   35,   35,   22,   75,   69,   81,   69,    3,    4,    5,
    6,   88,   89,    7,   83,   99,    8,    5,    6,    9,
    4,    5,    6,  160,    8,    7,  117,    9,    8,   91,
   92,    9,    4,    5,    6,   90,   30,    7,  102,  132,
    8,    5,    6,    9,   56,   56,   56,  103,    8,   56,
   56,    9,   56,  101,  105,   56,  108,    3,    4,    5,
    6,  113,  112,    7,  128,   52,    8,  156,  158,    9,
  157,   66,   67,  150,  161,  151,  154,  155,  163,  144,
    5,    4,  100,  167,   39,   71,  111,   82,  126,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    2,  272,    2,    9,    2,  259,   59,   34,   11,   12,
   37,   11,   65,   11,  130,  131,   46,  106,  107,  256,
  274,  259,  138,  258,  259,  259,  115,  116,   34,  264,
  265,   37,  269,  268,  123,   86,  124,  125,  275,   90,
   43,  270,   72,  272,  261,  280,  162,  274,  164,  165,
  138,  102,  103,   59,  105,  171,  109,  274,  256,   65,
   66,   67,  151,   69,   70,  154,  155,  158,  263,   72,
  159,  269,  266,  126,  163,  256,  167,  275,  257,  258,
  259,  272,  276,  277,  275,  266,  265,  114,  269,  268,
  259,  272,   95,  275,  275,  276,  277,  278,  279,  257,
  272,  280,  271,  109,  262,  274,  256,  272,  114,  259,
  260,  275,  276,  277,  117,  117,  119,  117,  118,  117,
  126,  272,  258,  259,  274,  270,  256,  272,  264,  265,
  133,  134,  268,  133,  272,  133,  266,  272,  256,  269,
  272,  273,  272,  274,  147,  275,  276,  277,  266,  261,
  256,  269,  272,  273,  272,  258,  259,  275,  276,  277,
  266,  264,  265,  269,  275,  268,  272,  259,  260,  275,
  276,  277,  257,  258,  259,  275,  257,  262,  257,  257,
  265,  262,  259,  268,  262,  270,  259,  272,  259,  270,
  272,  272,  258,  259,  278,  279,  276,  277,  264,  265,
  259,  260,  268,  275,  270,  259,  272,  256,  257,  258,
  259,   66,   67,  262,  275,  257,  265,  258,  259,  268,
  257,  258,  259,  264,  265,  262,  263,  268,  265,   69,
   70,  268,  257,  258,  259,  269,  271,  262,  269,  264,
  265,  258,  259,  268,  257,  258,  259,  269,  265,  262,
  263,  268,  265,  272,  269,  268,  274,    0,  257,  258,
  259,  275,  259,  262,  272,  272,  265,  272,  272,  268,
  275,  276,  277,  264,  264,  270,  270,  270,  270,  275,
    0,    0,   80,  272,  272,  272,   95,   62,  108,
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
"program : error SEMICOLON",
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

//#line 559 "./grammar.txt"

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
		case "UINT" :
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
//#line 495 "Parser.java"
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
case 6:
//#line 59 "./grammar.txt"
{
			Error error = new Error(Error.TYPE_FATAL,"Unhandled error", parserUtils.lexical.getLine());
			parserUtils.errorHandler.addError(error);
		}
break;
case 13:
//#line 77 "./grammar.txt"
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
case 14:
//#line 103 "./grammar.txt"
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
case 16:
//#line 130 "./grammar.txt"
{
		/*add variable name to a list that will be consulted later*/
		/*to add type to each variable in the symbol table*/
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 17:
//#line 136 "./grammar.txt"
{
		String varName = ((Token)val_peek(2).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
		varName = ((Token)val_peek(0).obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 18:
//#line 143 "./grammar.txt"
{
		/*Set function name as the current context*/
		String functionName = ((Token)val_peek(1).obj).getLiteralValue();
		parserUtils.context = functionName;
	}
break;
case 19:
//#line 147 "./grammar.txt"
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
case 20:
//#line 191 "./grammar.txt"
{
		/*once the function declaration is over, set the current context as main*/
		parserUtils.context = "main";
	}
break;
case 33:
//#line 217 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 34:
//#line 221 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 35:
//#line 226 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add("rtn");
	}
break;
case 36:
//#line 232 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add("=");
		currentIntCodeVector.add("[RET]");
	}
break;
case 37:
//#line 240 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String message = ((Token)val_peek(1).obj).getLiteralValue();
		currentIntCodeVector.add(message);
		currentIntCodeVector.add("[PRINT]");
	}
break;
case 38:
//#line 247 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 39:
//#line 251 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 40:
//#line 257 "./grammar.txt"
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
case 41:
//#line 293 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 42:
//#line 297 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 44:
//#line 318 "./grammar.txt"
{
		parserUtils.forAssignment = true;
	}
break;
case 45:
//#line 322 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		parserUtils.indexStack.add(currentIntCodeVector.size());
	}
break;
case 46:
//#line 328 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());

		/*save the index where the false bifurcation will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[BF]");
	}
break;
case 47:
//#line 338 "./grammar.txt"
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
case 49:
//#line 361 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 50:
//#line 365 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 51:
//#line 369 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 53:
//#line 374 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing left paretheses in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 54:
//#line 378 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing right paretheses in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 55:
//#line 382 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in IF statement  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 56:
//#line 387 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		/*save the index where the false bifurcation will be stored*/
		currentIntCodeVector.add("PLACEHOLDER");
		parserUtils.indexStack.add(currentIntCodeVector.size()-1);
		currentIntCodeVector.add("[BF]");
	}
break;
case 57:
//#line 396 "./grammar.txt"
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
case 58:
//#line 408 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String endBlockDirection = Integer.toString(currentIntCodeVector.size());
		Integer endBlockIndex = parserUtils.indexStack.remove(parserUtils.indexStack.size()-1);
		currentIntCodeVector.set(endBlockIndex,endBlockDirection);
	}
break;
case 59:
//#line 416 "./grammar.txt"
{
		String comparatorSymbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(comparatorSymbol);
	}
break;
case 62:
//#line 425 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 63:
//#line 429 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 65:
//#line 434 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 66:
//#line 438 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 70:
//#line 446 "./grammar.txt"
{
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		String varName = ((Token)val_peek(1).obj).getLiteralValue();
		currentIntCodeVector.add(context+"_"+varName);
	}
break;
case 71:
//#line 451 "./grammar.txt"
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
case 72:
//#line 485 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 73:
//#line 491 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 75:
//#line 500 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 76:
//#line 506 "./grammar.txt"
{
		String symbol = ((Token)val_peek(1).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		currentIntCodeVector.add(symbol);
	}
break;
case 78:
//#line 515 "./grammar.txt"
{
		String varName = ((Token)val_peek(0).obj).getLiteralValue();
		String context = parserUtils.context;
		ArrayList<String> currentIntCodeVector = parserUtils.intermediateCode.get(context.toUpperCase());
		SymbolTable symbolTable = SymbolTable.getInstance();
		SymbolElement element = new SymbolElement();

		element = symbolTable.identify(varName);
		/*The only allowed type for constants is UINT*/
		element.setVarType("UINT");
		currentIntCodeVector.add(varName);
	}
break;
case 79:
//#line 527 "./grammar.txt"
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
//#line 1192 "Parser.java"
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
