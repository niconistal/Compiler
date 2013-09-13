package Sintactic;

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






//#line 2 "gram.txt"
import java.util.StringTokenizer;
import Lexical.LexicalAnalizer;
import Lexical.Token;
import Lexical.ErrorHandler;
import Lexical.Error;
//#line 23 "Parser.java"




public class Parser
{
	ErrorHandler errorHandler = ErrorHandler.getInstance();
	private LexicalAnalizer lexical;
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
    0,    0,    0,    1,    1,    3,    3,    6,    6,    4,
    7,    7,    5,    5,    5,    5,    5,    2,    2,    8,
    8,   10,   10,    9,    9,    9,    9,    9,   11,   11,
   11,   12,   12,   12,   13,   13,   13,   14,   14,   14,
   15,   15,   15,   15,   15,   15,   15,   15,   18,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   16,   17,
   17,   17,   20,   20,   20,   21,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    1,    1,    1,    2,    1,    3,
    3,    1,   10,    9,    9,    9,    9,    2,    1,    2,
    1,    1,    1,    1,    1,    1,    1,    1,    5,    4,
    4,    5,    4,    4,    5,    4,    4,   13,   12,   12,
    9,    8,    8,    7,    7,    6,    6,    6,    3,    4,
    4,    3,    3,    4,    3,    3,    1,    1,    4,    3,
    3,    1,    3,    3,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    5,
    6,    7,   19,   24,   25,   26,   27,   28,   12,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   67,   66,
    0,    0,    0,    0,   65,    0,    4,   18,   10,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   11,   33,   34,    0,
   59,   37,    0,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   63,   64,   32,   35,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    9,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   47,
    0,   48,   52,   55,    0,    0,   46,    0,    8,    0,
   22,   21,   23,    0,    0,    0,    0,    0,   50,   51,
   54,    0,    0,   45,   44,    0,    0,    0,    0,   20,
    0,    0,    0,    0,    0,   43,    0,   42,    0,    0,
   15,    0,   17,   16,   14,    0,    0,   41,   31,    0,
   30,   13,    0,    0,    0,   29,    0,    0,    0,   39,
    0,   40,   38,
};
final static short yydgoto[] = {                          7,
   72,   73,   74,   11,   12,  100,   20,  120,   75,  122,
  123,   14,   15,   16,   17,   18,   32,   33,   76,   34,
   35,
};
final static short yysindex[] = {                        64,
 -234, -254, -219, -230, -192, -201,    0,   64,  -26,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   27,
 -242, -211,   46, -234, -110, -243, -215, -184,    0,    0,
   46, -183, -186,   34,    0,  -26,    0,    0,    0, -160,
 -167, -229,  -92,   17, -161, -140, -152,   46, -143, -113,
   46,   46,   46, -136,   46,   46,    0,    0,    0, -132,
    0,    0, -114,    0, -175, -120,   65,   46,   64,   52,
   39, -148,  -26,    0,    0,  -76,   72,   34,   34,   52,
    0,    0,    0,    0, -140, -240, -140,  -44,   67,   64,
  -26,  -69,   52,  -36,  -18,    6,   52,  -48,    0, -246,
 -140, -246, -246,   -9,   19,   -5,   30,   63,   52,    0,
  -27,    0,    0,    0,   31,   52,    0, -198,    0, -146,
    0,    0,    0, -246, -129, -127,   46,   50,    0,    0,
    0,   59,   52,    0,    0,   66,   46,  -98,   68,    0,
 -223,   82,   83,   70,   46,    0,   84,    0,   58,   85,
    0,   86,    0,    0,    0, -148, -191,    0,    0,   87,
    0,    0,   64, -148,   64,    0, -111,   64,  -68,    0,
  -47,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  267,  324,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -70,    0,  336,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   14,   15,    0,   -7,  -49,  -28,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -142,   60,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    7,   23,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    3,    8,   18,  -22,    0,    9,   -3,  -64,    1,  -72,
    0,    0,    0,    0,    0,    0,  -21,  329,  -65,  298,
  297,
};
final static int YYTABLESIZE=360;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   13,   43,    8,   47,   92,   94,   21,    9,   13,   38,
    1,    2,    3,    1,   98,   36,    1,   10,    5,   22,
   44,    6,  101,   65,   19,   37,   67,  111,   26,   77,
   46,  115,   41,  118,    2,    3,   38,  125,  126,   19,
  152,    5,   59,  132,    6,   60,   89,  140,  153,   42,
  136,   23,  140,  140,   24,   48,  118,   29,   30,  141,
   29,   30,   99,   99,   99,    1,   27,  147,  140,   13,
    4,   90,   31,   96,   49,  137,   91,  119,   99,  119,
  119,   28,   51,  164,   52,   53,   10,   85,   54,   95,
   13,  108,   52,   53,  102,  103,  138,  106,   57,   86,
  121,  119,  121,  121,   58,  144,   38,  107,    1,  124,
   64,    2,    3,    4,    4,  149,    1,  139,    5,    4,
  121,    6,   66,  157,  121,  121,  121,   68,    2,    3,
    2,    3,   80,  118,  142,    5,  143,    5,    6,   83,
    6,  121,   87,    1,    2,    3,    2,    3,    4,   69,
  118,    5,  118,    5,    6,   70,    6,   84,  163,  165,
  170,   71,   40,   13,   45,   13,  168,   38,   13,   38,
  167,   38,  169,   10,   10,  171,  150,   52,   53,   61,
   37,   10,   37,   52,   53,   37,   62,   62,   62,    2,
    3,   62,   62,   97,   62,   62,    5,   62,   62,    6,
  109,   62,  110,  172,   62,   62,   62,   60,   60,   60,
    2,    3,   60,   60,  104,   60,   60,    5,   60,   60,
    6,  116,   60,  117,  173,   60,   60,   60,   61,   61,
   61,    2,    3,   61,   61,  112,   61,   61,    5,   61,
   61,    6,  133,   61,  134,  113,   61,   61,   61,   49,
   49,   49,    2,    3,   49,   49,  127,   49,  129,    5,
   49,   49,    6,    4,    4,    4,    1,   49,    4,  114,
    5,    4,   19,   19,    4,    5,   53,  128,   53,   19,
   18,   18,   19,   57,   58,   57,   58,   18,   62,   40,
   18,   63,   56,  130,   56,    1,    2,    3,   39,   40,
    4,   69,  135,    5,   29,   30,    6,   93,    1,    2,
    3,   55,   56,    4,   69,  145,    5,   18,   18,    6,
    1,    2,    3,    3,   18,    4,  131,   18,    5,  159,
  146,    6,  160,   52,   53,    2,   88,  148,  105,  151,
   52,   53,   52,   53,  156,   52,   53,   52,   53,   78,
   79,   81,   82,  154,  155,  158,  161,  162,  166,   50,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
    0,   23,    0,   26,   70,   71,  261,    0,    8,    9,
  257,  258,  259,  257,   80,    8,  257,    0,  265,  274,
   24,  268,  263,   46,  259,    8,   48,   93,  259,   51,
  274,   97,  275,  280,  258,  259,   36,  102,  103,  259,
  264,  265,  272,  109,  268,  275,   68,  120,  272,  261,
  116,  271,  125,  126,  274,  271,  280,  259,  260,  124,
  259,  260,   85,   86,   87,  257,  259,  133,  141,   69,
  262,   69,  274,   73,  259,  274,   69,  100,  101,  102,
  103,  274,  266,  275,  276,  277,   69,  263,  275,   72,
   90,   91,  276,  277,   86,   87,  118,   90,  259,  275,
  100,  124,  102,  103,  272,  127,  106,   90,  257,  101,
  272,  258,  259,  262,  257,  137,  257,  264,  265,  262,
  120,  268,  275,  145,  124,  125,  126,  271,  258,  259,
  258,  259,  269,  280,  264,  265,  264,  265,  268,  272,
  268,  141,  263,  257,  258,  259,  258,  259,  262,  263,
  280,  265,  280,  265,  268,  269,  268,  272,  156,  157,
  272,  275,  273,  163,  275,  165,  164,  167,  168,  169,
  163,  171,  165,  156,  157,  168,  275,  276,  277,  272,
  163,  164,  165,  276,  277,  168,  257,  258,  259,  258,
  259,  262,  263,  270,  265,  266,  265,  268,  269,  268,
  270,  272,  272,  272,  275,  276,  277,  257,  258,  259,
  258,  259,  262,  263,  259,  265,  266,  265,  268,  269,
  268,  270,  272,  272,  272,  275,  276,  277,  257,  258,
  259,  258,  259,  262,  263,  272,  265,  266,  265,  268,
  269,  268,  270,  272,  272,  264,  275,  276,  277,  257,
  258,  259,  258,  259,  262,  263,  266,  265,  264,  265,
  268,  269,  268,  257,  258,  259,    0,  275,  262,  264,
  257,  265,  258,  259,  268,  262,  270,  259,  272,  265,
  258,  259,  268,  270,  270,  272,  272,  265,  272,  273,
  268,  275,  270,  264,  272,  257,  258,  259,  272,  273,
  262,  263,  272,  265,  259,  260,  268,  269,  257,  258,
  259,  278,  279,  262,  263,  266,  265,  258,  259,  268,
  257,  258,  259,    0,  265,  262,  264,  268,  265,  272,
  272,  268,  275,  276,  277,    0,  272,  272,  272,  272,
  276,  277,  276,  277,  275,  276,  277,  276,  277,   52,
   53,   55,   56,  272,  272,  272,  272,  272,  272,   31,
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
"$accept : program",
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

//#line 158 "gram.txt"

String ins;
StringTokenizer st;

void yyerror(String s)
{
 System.out.println("par:"+s);
}

boolean newline;

int yylex()
{
	Token token = this.lexical.getToken();
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
//#line 494 "Parser.java"
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
case 10:
//#line 62 "gram.txt"
{Error error = new Error(Error.TYPE_NOTIFICATION,"Variable declaration ", lexical.getLine()); errorHandler.addError(error);}
break;
case 13:
//#line 69 "gram.txt"
{Error error = new Error(Error.TYPE_NOTIFICATION,"Function declaration ", lexical.getLine()); errorHandler.addError(error);}
break;
case 14:
//#line 70 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function declaration ", lexical.getLine()); errorHandler.addError(error);}
break;
case 15:
//#line 71 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function declaration ", lexical.getLine()); errorHandler.addError(error);}
break;
case 16:
//#line 72 "gram.txt"
{Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in function declaration ", lexical.getLine()); errorHandler.addError(error);}
break;
case 17:
//#line 73 "gram.txt"
{Error error = new Error(Error.TYPE_FATAL,"Missing END in function declaration ", lexical.getLine()); errorHandler.addError(error);}
break;
case 29:
//#line 96 "gram.txt"
{Error error = new Error(Error.TYPE_NOTIFICATION,"Return execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 30:
//#line 97 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in return execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 31:
//#line 98 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in return execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 32:
//#line 101 "gram.txt"
{Error error = new Error(Error.TYPE_NOTIFICATION,"Print execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 33:
//#line 102 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in print execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 34:
//#line 103 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in print execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 35:
//#line 106 "gram.txt"
{Error error = new Error(Error.TYPE_NOTIFICATION,"Function execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 36:
//#line 107 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 37:
//#line 108 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 38:
//#line 111 "gram.txt"
{Error error = new Error(Error.TYPE_NOTIFICATION,"Iteration execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 39:
//#line 112 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in iteration execution  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 40:
//#line 113 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in iteration execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 41:
//#line 115 "gram.txt"
{Error error = new Error(Error.TYPE_NOTIFICATION,"Selection execution ", lexical.getLine()); errorHandler.addError(error);}
break;
case 42:
//#line 116 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in selection execution  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 43:
//#line 117 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in selection execution  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 44:
//#line 118 "gram.txt"
{Error error = new Error(Error.TYPE_FATAL,"Missing THEN in selection execution  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 46:
//#line 120 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in selection execution  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 47:
//#line 121 "gram.txt"
{Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in selection execution  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 48:
//#line 122 "gram.txt"
{Error error = new Error(Error.TYPE_FATAL,"Missing THEN in selection execution  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 52:
//#line 131 "gram.txt"
{Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 53:
//#line 132 "gram.txt"
{Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 55:
//#line 134 "gram.txt"
{Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 56:
//#line 135 "gram.txt"
{Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", lexical.getLine()); errorHandler.addError(error);}
break;
case 59:
//#line 140 "gram.txt"
{Error error = new Error(Error.TYPE_NOTIFICATION,"Assign execution ", lexical.getLine()); errorHandler.addError(error);}
break;
//#line 763 "Parser.java"
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
	lexical = new LexicalAnalizer("test.txt");
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
