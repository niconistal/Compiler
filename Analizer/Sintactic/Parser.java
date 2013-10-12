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

class parserUtils {
	static ArrayList<String> variableList = new ArrayList<String>(); 
	static ErrorHandler errorHandler = ErrorHandler.getInstance();
	static LexicalAnalizer lexical = new LexicalAnalizer(PathContainer.getPath());	
}
//#line 33 "Parser.java"




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
    0,    1,    1,    1,    2,    2,    4,    4,    7,    7,
    5,    8,    8,    6,    6,    6,    6,    6,    3,    3,
    9,    9,   11,   11,   10,   10,   10,   10,   10,   12,
   12,   12,   13,   13,   13,   14,   14,   14,   15,   15,
   15,   16,   16,   16,   16,   16,   16,   16,   16,   19,
   20,   20,   20,   20,   20,   20,   20,   20,   20,   17,
   18,   18,   18,   21,   21,   21,   22,   22,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    2,    1,    1,    1,    2,    1,
    3,    3,    1,   10,    9,    9,    9,    9,    2,    1,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    5,
    4,    4,    5,    4,    4,    5,    4,    4,   13,   12,
   12,    9,    8,    8,    7,    7,    6,    6,    6,    3,
    4,    4,    3,    3,    4,    3,    3,    1,    1,    4,
    3,    3,    1,    3,    3,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    6,    7,    8,   20,   25,   26,   27,   28,   29,   13,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   68,
   67,    0,    0,    0,    0,   66,    0,    5,   19,   11,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   12,   34,   35,
    0,   60,   38,    0,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   64,   65,   33,   36,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   10,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   48,    0,   49,   53,   56,    0,    0,   47,    0,    9,
    0,   23,   22,   24,    0,    0,    0,    0,    0,   51,
   52,   55,    0,    0,   46,   45,    0,    0,    0,    0,
   21,    0,    0,    0,    0,    0,   44,    0,   43,    0,
    0,   16,    0,   18,   17,   15,    0,    0,   42,   32,
    0,   31,   14,    0,    0,    0,   30,    0,    0,    0,
   40,    0,   41,   39,
};
final static short yydgoto[] = {                          7,
    8,   73,   74,   75,   12,   13,  101,   21,  121,   76,
  123,  124,   15,   16,   17,   18,   19,   33,   34,   77,
   35,   36,
};
final static short yysindex[] = {                        74,
 -213, -224, -187, -189, -250, -174,    0,    0,   74,   50,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -175, -271, -185, -145, -213, -219, -255, -181, -165,    0,
    0, -145,  -43, -143,   44,    0,   50,    0,    0,    0,
 -136, -128, -198,  -91,  -29, -125, -119, -117, -145,  -76,
   -6, -145, -145, -145,  -53, -145, -145,    0,    0,    0,
  -47,    0,    0,  -35,    0, -214,  -23,   16, -145,   74,
   62,   48, -161,   50,    0,    0,  -12,   19,   44,   44,
   62,    0,    0,    0,    0, -119, -168, -119,    6,   22,
   74,   50, -242,   62,  -25,   73,   77,   62,  -68,    0,
 -245, -119, -245, -245,    7,   11,   -4,   86,   87,   62,
    0,   31,    0,    0,    0,   14,   62,    0, -120,    0,
 -147,    0,    0,    0, -245, -134, -123, -145,   46,    0,
    0,    0,   42,   62,    0,    0,   54, -145,  -97,   80,
    0, -217,   81,   82,    4, -145,    0,   83,    0,   68,
   84,    0,   85,    0,    0,    0, -161, -218,    0,    0,
   88,    0,    0,   74, -161,   74,    0, -152,   74,  -67,
    0,  -46,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  283,  334,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -69,    0,  358,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -236,   17,    0,    9,  -48,  -27,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -107,   70,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -106,   32,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    3,    8,   18,  -22,    0,   61,   -3,  -72,    1,
  -59,    0,    0,    0,    0,    0,    0,   -9,  327,  -65,
  293,  292,
};
final static int YYTABLESIZE=360;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         26,
   14,    1,    9,   42,   48,   93,   95,   10,   28,   14,
   39,    1,    2,    3,   44,   99,   37,   11,   47,    5,
    6,   45,    6,   29,   66,    6,   38,  110,  112,  111,
  126,  127,  116,   58,  119,   58,   22,   39,    1,   68,
    2,    3,   78,    4,  133,   20,  153,    5,   86,   23,
    6,  137,  142,   41,  154,   46,  165,   53,   54,   90,
   87,  141,  119,  100,  100,  100,  141,  141,  148,   27,
   14,   20,   91,   60,   97,   43,   61,   92,  120,  100,
  120,  120,  141,   24,   30,   31,   25,   11,    1,   49,
   96,   14,  109,   50,  102,    1,   40,   41,  107,   32,
    4,  122,  120,  122,  122,    2,    3,   39,  108,  139,
    2,    3,    5,   30,   31,    6,  140,    5,  145,  171,
    6,  122,   58,    2,    3,  122,  122,  122,  150,  143,
    5,   55,  119,    6,    2,    3,  158,    1,   30,   31,
  144,    5,  122,   59,    6,  119,   65,  103,  104,    5,
    5,    5,    5,  138,    5,    5,  119,   67,    5,  164,
  166,    5,  125,   54,   14,   54,   14,  169,   39,   14,
   39,  168,   39,  170,   11,   11,  172,  151,   53,   54,
   62,   38,   11,   38,   53,   54,   38,   63,   63,   63,
    2,    3,   63,   63,   69,   63,   63,    5,   63,   63,
    6,  117,   63,  118,  173,   63,   63,   63,   61,   61,
   61,    2,    3,   61,   61,   81,   61,   61,    5,   61,
   61,    6,   52,   61,   84,  174,   61,   61,   61,   62,
   62,   62,   53,   54,   62,   62,   85,   62,   62,   88,
   62,   62,   63,   41,   62,   64,  113,   62,   62,   62,
    1,    2,    3,    2,    3,    4,   70,   98,    5,  130,
    5,    6,   71,    6,  105,   50,   50,   50,   72,  129,
   50,   50,  128,   50,   20,   20,   50,   50,  157,   53,
   54,   20,    2,   50,   20,  136,   59,   89,   59,   19,
   19,   53,   54,  106,   53,   54,   19,   53,   54,   19,
  134,   57,  135,   57,    1,    2,    3,    2,    3,    4,
   70,  146,    5,  147,    5,    6,   94,    6,    1,    2,
    3,   56,   57,    4,   70,  149,    5,   19,   19,    6,
    1,    2,    3,    4,   19,    4,  114,   19,    5,  160,
  115,    6,  161,   53,   54,   79,   80,   82,   83,  131,
  132,  152,  155,  156,  159,  162,  163,    3,   51,  167,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
    0,  257,    0,  275,   27,   71,   72,    0,  259,    9,
   10,  257,  258,  259,   24,   81,    9,    0,  274,  265,
  257,   25,  268,  274,   47,  262,    9,  270,   94,  272,
  103,  104,   98,  270,  280,  272,  261,   37,  257,   49,
  258,  259,   52,  262,  110,  259,  264,  265,  263,  274,
  268,  117,  125,  273,  272,  275,  275,  276,  277,   69,
  275,  121,  280,   86,   87,   88,  126,  127,  134,  259,
   70,  259,   70,  272,   74,  261,  275,   70,  101,  102,
  103,  104,  142,  271,  259,  260,  274,   70,  257,  271,
   73,   91,   92,  259,  263,  257,  272,  273,   91,  274,
  262,  101,  125,  103,  104,  258,  259,  107,   91,  119,
  258,  259,  265,  259,  260,  268,  264,  265,  128,  272,
  268,  121,  259,  258,  259,  125,  126,  127,  138,  264,
  265,  275,  280,  268,  258,  259,  146,  257,  259,  260,
  264,  265,  142,  272,  268,  280,  272,   87,   88,  257,
  257,  258,  259,  274,  262,  262,  280,  275,  265,  157,
  158,  268,  102,  270,  164,  272,  166,  165,  168,  169,
  170,  164,  172,  166,  157,  158,  169,  275,  276,  277,
  272,  164,  165,  166,  276,  277,  169,  257,  258,  259,
  258,  259,  262,  263,  271,  265,  266,  265,  268,  269,
  268,  270,  272,  272,  272,  275,  276,  277,  257,  258,
  259,  258,  259,  262,  263,  269,  265,  266,  265,  268,
  269,  268,  266,  272,  272,  272,  275,  276,  277,  257,
  258,  259,  276,  277,  262,  263,  272,  265,  266,  263,
  268,  269,  272,  273,  272,  275,  272,  275,  276,  277,
  257,  258,  259,  258,  259,  262,  263,  270,  265,  264,
  265,  268,  269,  268,  259,  257,  258,  259,  275,  259,
  262,  263,  266,  265,  258,  259,  268,  269,  275,  276,
  277,  265,    0,  275,  268,  272,  270,  272,  272,  258,
  259,  276,  277,  272,  276,  277,  265,  276,  277,  268,
  270,  270,  272,  272,  257,  258,  259,  258,  259,  262,
  263,  266,  265,  272,  265,  268,  269,  268,  257,  258,
  259,  278,  279,  262,  263,  272,  265,  258,  259,  268,
  257,  258,  259,    0,  265,  262,  264,  268,  265,  272,
  264,  268,  275,  276,  277,   53,   54,   56,   57,  264,
  264,  272,  272,  272,  272,  272,  272,    0,   32,  272,
};
}
final static short YYFINAL=7;
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
"start : program",
"program : declarationList",
"program : declarationList executions",
"program : executions",
"declarationList : declarationList declaration",
"declarationList : declaration",
"declaration : varDeclaration",
"declaration : functionDeclaration",
"varDeclarationList : varDeclarationList varDeclaration",
"varDeclarationList : varDeclaration",
"varDeclaration : VARTYPE varList SEMICOLON",
"varList : varList COMMA ID",
"varList : ID",
"functionDeclaration : FUNCTION ID OPENPAREN varDeclaration CLOSEPAREN BEGIN varDeclarationList executionWReturnList END SEMICOLON",
"functionDeclaration : FUNCTION ID varDeclaration CLOSEPAREN BEGIN varDeclarationList executionWReturnList END SEMICOLON",
"functionDeclaration : FUNCTION ID OPENPAREN varDeclaration BEGIN varDeclarationList executionWReturnList END SEMICOLON",
"functionDeclaration : FUNCTION ID OPENPAREN varDeclaration CLOSEPAREN varDeclarationList executionWReturnList END SEMICOLON",
"functionDeclaration : FUNCTION ID OPENPAREN varDeclaration CLOSEPAREN BEGIN varDeclarationList executionWReturnList SEMICOLON",
"executions : executions execution",
"executions : execution",
"executionWReturnList : executionWReturnList executionWReturn",
"executionWReturnList : executionWReturn",
"executionWReturn : execution",
"executionWReturn : ret",
"execution : print",
"execution : functionExecution",
"execution : iteration",
"execution : selection",
"execution : assign",
"ret : RETURN OPENPAREN expression CLOSEPAREN SEMICOLON",
"ret : RETURN expression CLOSEPAREN SEMICOLON",
"ret : RETURN OPENPAREN expression SEMICOLON",
"print : PRINT OPENPAREN CHARCHAIN CLOSEPAREN SEMICOLON",
"print : PRINT CHARCHAIN CLOSEPAREN SEMICOLON",
"print : PRINT OPENPAREN CHARCHAIN SEMICOLON",
"functionExecution : ID OPENPAREN varList CLOSEPAREN SEMICOLON",
"functionExecution : ID varList CLOSEPAREN SEMICOLON",
"functionExecution : ID OPENPAREN varList SEMICOLON",
"iteration : FOR OPENPAREN ID ASSIGN expression SEMICOLON ID COMPARATOR expression CLOSEPAREN declarationList executions SEMICOLON",
"iteration : FOR ID ASSIGN expression SEMICOLON ID COMPARATOR expression CLOSEPAREN declarationList executions SEMICOLON",
"iteration : FOR OPENPAREN ID ASSIGN expression SEMICOLON ID COMPARATOR expression declarationList executions SEMICOLON",
"selection : IF OPENPAREN condition CLOSEPAREN THEN block ELSE block SEMICOLON",
"selection : IF condition CLOSEPAREN THEN block ELSE block SEMICOLON",
"selection : IF OPENPAREN condition THEN block ELSE block SEMICOLON",
"selection : IF OPENPAREN condition block ELSE block SEMICOLON",
"selection : IF OPENPAREN condition CLOSEPAREN THEN block SEMICOLON",
"selection : IF condition CLOSEPAREN THEN block SEMICOLON",
"selection : IF OPENPAREN condition THEN block SEMICOLON",
"selection : IF OPENPAREN condition CLOSEPAREN block SEMICOLON",
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
"assign : ID ASSIGN expression SEMICOLON",
"expression : expression PLUS term",
"expression : expression MINUS term",
"expression : term",
"term : term PRODUCT factor",
"term : term DIVISION factor",
"term : factor",
"factor : CTE",
"factor : ID",
};

//#line 260 "./grammar.txt"

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
//#line 474 "Parser.java"
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
//#line 50 "./grammar.txt"
{
	System.out.println("startiiing");
}
break;
case 11:
//#line 71 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Variable declaration ", parserUtils.lexical.getLine());
		SymbolTable symbolTable = SymbolTable.getInstance();
		String literalTypeValue = ((Token)val_peek(2).obj).getLiteralValue();
		parserUtils.errorHandler.addError(error);
		/*add type to each symbol table entryof the varList*/
		for(String varName : parserUtils.variableList) {
			symbolTable.identify(varName).setVarType(literalTypeValue);
		}
		/*get the list ready for the following variable declarations*/
		parserUtils.variableList.clear();

	}
break;
case 12:
//#line 84 "./grammar.txt"
{
		/*add variable name to a list that will be consulted later*/
		/*to add type to each variable in the symbol table*/
		parserUtils.variableList.add(((Token)val_peek(0).obj).getLiteralValue());
	}
break;
case 13:
//#line 89 "./grammar.txt"
{
		parserUtils.variableList.add(((Token)val_peek(0).obj).getLiteralValue());
	}
break;
case 14:
//#line 93 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 15:
//#line 97 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 16:
//#line 101 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 17:
//#line 105 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 18:
//#line 109 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 30:
//#line 134 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 31:
//#line 138 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 32:
//#line 142 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 33:
//#line 147 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 34:
//#line 151 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 35:
//#line 155 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 36:
//#line 161 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 37:
//#line 165 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 38:
//#line 169 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 39:
//#line 174 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Iteration execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 40:
//#line 178 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in iteration execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 41:
//#line 182 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in iteration execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 42:
//#line 187 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Selection execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 43:
//#line 191 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 44:
//#line 195 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 45:
//#line 199 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 47:
//#line 204 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 48:
//#line 208 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 49:
//#line 212 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 53:
//#line 221 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 54:
//#line 225 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 56:
//#line 230 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 57:
//#line 234 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 60:
//#line 241 "./grammar.txt"
{
	Error error = new Error(Error.TYPE_NOTIFICATION,"Assign execution ", parserUtils.lexical.getLine());
	parserUtils.errorHandler.addError(error);
	}
break;
//#line 862 "Parser.java"
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
