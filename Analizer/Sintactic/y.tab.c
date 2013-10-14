#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 2 "./grammar.txt"
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

class parserUtils {
	static ArrayList<String> variableList = new ArrayList<String>(); 
	static ErrorHandler errorHandler = ErrorHandler.getInstance();
	static LexicalAnalizer lexical = new LexicalAnalizer(PathContainer.getPath());	
}
#line 23 "y.tab.c"
#define VARTYPE 257
#define PRINT 258
#define ID 259
#define CTE 260
#define CHARCHAIN 261
#define FUNCTION 262
#define BEGIN 263
#define END 264
#define FOR 265
#define COMPARATOR 266
#define ERROR 267
#define IF 268
#define THEN 269
#define ELSE 270
#define ASSIGN 271
#define SEMICOLON 272
#define COMMA 273
#define OPENPAREN 274
#define CLOSEPAREN 275
#define PLUS 276
#define MINUS 277
#define PRODUCT 278
#define DIVISION 279
#define RETURN 280
#define IT 281
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    0,    0,    1,    1,    3,    3,    6,    6,    4,
    7,    7,    8,    5,    5,    5,    5,    5,    2,    2,
    9,    9,   11,   11,   10,   10,   10,   10,   10,   12,
   12,   12,   13,   13,   13,   14,   14,   14,   15,   15,
   15,   16,   16,   16,   16,   16,   16,   16,   16,   19,
   20,   20,   20,   20,   20,   20,   20,   20,   20,   17,
   18,   18,   18,   21,   21,   21,   22,   22,
};
short yylen[] = {                                         2,
    1,    2,    1,    2,    1,    1,    1,    2,    1,    3,
    3,    1,    5,    6,    9,    9,    9,    9,    2,    1,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    5,
    4,    4,    5,    4,    4,    5,    4,    4,   13,   12,
   12,    9,    8,    8,    7,    7,    6,    6,    6,    3,
    4,    4,    3,    3,    4,    3,    3,    1,    1,    4,
    3,    3,    1,    3,    3,    1,    1,    1,
};
short yydefred[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    5,
    6,    7,    0,   20,   25,   26,   27,   28,   29,   12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   68,
   67,    0,    0,    0,    0,   66,    0,    4,   19,    0,
   10,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    9,    0,
   11,   34,   35,    0,   60,   38,    0,   37,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   64,   65,    0,    8,    0,   23,
   22,   24,   33,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   21,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   48,    0,   49,   53,   56,    0,    0,   47,
    0,    0,   14,    0,    0,    0,    0,    0,    0,   51,
   52,   55,    0,    0,   46,   45,    0,   32,    0,   31,
    0,    0,    0,    0,    0,    0,   44,    0,   43,   30,
   16,   18,   17,   15,    0,    0,   42,    0,    0,    0,
    0,    0,    0,   40,    0,   41,   39,
};
short yydgoto[] = {                                       7,
   76,   77,   78,   11,   12,   60,   21,   13,   89,   90,
   91,   92,   15,   16,   17,   18,   19,   33,   34,   80,
   35,   36,
};
short yysindex[] = {                                     97,
 -240, -250, -230, -216, -210, -167,    0,   97, -122,    0,
    0,    0, -203,    0,    0,    0,    0,    0,    0,    0,
 -121, -219, -191,  -92, -240, -192, -254, -181, -163,    0,
    0,  -92, -138, -172,  -84,    0, -122,    0,    0, -147,
    0, -135, -139, -131, -155,  -39, -132, -147, -114,  -92,
  -87,  -56,  -92,  -92,  -92,  -78,  -92,  -92,    0, -243,
    0,    0,    0,  -72,    0,    0,  -64,    0, -204,  -36,
 -112,  -92,   97,   85,   72, -227, -122,    0,    0,  -90,
  -53,  -84,  -84,   85,    0,    0, -165,    0, -207,    0,
    0,    0,    0,    0, -147, -104, -147,  -29,   98,   97,
 -122,  -95,   85,  -24,   12,   14,   85,  -83,  -92,   96,
   -3,    0, -243, -147, -243, -243,   24,   33,  -54,   45,
   47,   85,    0,  -15,    0,    0,    0,   53,   85,    0,
   91,   55,    0, -153, -243, -133, -110,  -92,   51,    0,
    0,    0,   60,   85,    0,    0,   61,    0,   64,    0,
   66, -226,   67,   73,  101,  -92,    0,   74,    0,    0,
    0,    0,    0,    0, -227, -178,    0,   97, -227,   97,
  -14,   97,    7,    0,   28,    0,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,  319,  349,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -37,    0,  351,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -190,   48,    0,
   26,  -16,    5,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -143, -102,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   40,
   56,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
short yygindex[] = {                                      0,
   27,   18,   13,  102,    0,  -91,    9,    0,  -47,    1,
  -89,    0,    0,    0,    0,    0,    0,  -22,  320,  -67,
  249,  265,
};
#define YYTABLESIZE 378
short yytable[] = {                                     112,
   14,   45,    1,  113,  115,  116,  102,  104,   14,   39,
   22,   26,   10,    1,    2,    3,  108,    9,   20,   48,
   38,    5,  135,   23,    6,   37,    8,   71,   20,    1,
   81,    2,    3,   46,    4,  124,   87,   39,    5,  128,
   24,    6,   27,   25,  112,  162,  112,  112,   28,   99,
    2,    3,   79,   87,  143,   43,  111,    5,   95,   40,
    6,  147,  112,   29,  110,  134,    5,  136,  137,   44,
   96,    5,   87,   14,   79,   79,  158,  106,    1,   58,
   42,   58,   47,    4,   79,   10,  131,  152,  105,   50,
  101,   30,   31,   30,   31,   51,  169,   54,   55,  100,
   14,  121,   56,   79,    2,    3,   32,   79,  109,    1,
  151,    5,  120,    4,    6,  155,   65,  119,    4,   39,
   54,   55,   79,   61,    2,    3,   87,   53,   49,   79,
  153,    5,   62,  166,    6,    2,    3,   54,   55,   68,
   63,   59,    5,   64,   79,    6,   87,    2,    3,   69,
   41,   42,    1,  154,    5,   19,   19,    6,  114,   98,
   70,   88,   19,   54,   55,   19,   30,   31,   14,   87,
   14,   39,   14,   39,  122,   39,  123,   10,   10,  107,
   38,   10,   38,   72,   38,  171,  129,  173,  130,  175,
   84,  168,  170,   57,   58,  172,   59,   59,   59,   93,
    1,    2,    3,    2,    3,    4,   73,   94,    5,  140,
    5,    6,   74,    6,   88,   59,   88,   88,   75,   63,
   63,   63,   54,   55,   63,   63,   97,   63,   63,  117,
   63,   63,   66,   42,   63,   67,   88,   63,   63,   63,
   61,   61,   61,    2,    3,   61,   61,  125,   61,   61,
    5,   61,   61,    6,  144,   61,  145,  174,   61,   61,
   61,   62,   62,   62,    2,    3,   62,   62,  133,   62,
   62,    5,   62,   62,    6,  126,   62,  127,  176,   62,
   62,   62,   50,   50,   50,    2,    3,   50,   50,  138,
   50,  139,    5,   50,   50,    6,    4,    4,    4,  177,
   50,    4,   82,   83,    4,   20,   20,    4,  141,   54,
  142,   54,   20,   19,   19,   20,  156,   59,    1,   59,
   19,   85,   86,   19,  146,   57,  150,   57,    1,    2,
    3,  157,  159,    4,   73,  160,    5,  161,  163,    6,
  103,    1,    2,    3,  164,  167,    4,   73,    3,    5,
    2,   52,    6,    1,    2,    3,    0,    0,    4,    0,
    0,    5,  148,    0,    6,  149,   54,   55,    0,  118,
  132,   54,   55,   54,   55,  165,   54,   55,
};
short yycheck[] = {                                      89,
    0,   24,  257,   95,   96,   97,   74,   75,    8,    9,
  261,    3,    0,  257,  258,  259,   84,    0,  259,  274,
    8,  265,  114,  274,  268,    8,    0,   50,  259,  257,
   53,  258,  259,   25,  262,  103,  280,   37,  265,  107,
  271,  268,  259,  274,  134,  272,  136,  137,  259,   72,
  258,  259,   52,  280,  122,  275,  264,  265,  263,  263,
  268,  129,  152,  274,   87,  113,  257,  115,  116,  261,
  275,  262,  280,   73,   74,   75,  144,   77,  257,  270,
  273,  272,  275,  262,   84,   73,  109,  135,   76,  271,
   73,  259,  260,  259,  260,  259,  275,  276,  277,   73,
  100,  101,  275,  103,  258,  259,  274,  107,  274,  257,
  264,  265,  100,  257,  268,  138,  272,  100,  262,  119,
  276,  277,  122,  259,  258,  259,  280,  266,   27,  129,
  264,  265,  272,  156,  268,  258,  259,  276,  277,  272,
  272,   40,  265,  275,  144,  268,  280,  258,  259,   48,
  272,  273,  257,  264,  265,  258,  259,  268,  263,  272,
  275,   60,  265,  276,  277,  268,  259,  260,  168,  280,
  170,  171,  172,  173,  270,  175,  272,  165,  166,  270,
  168,  169,  170,  271,  172,  168,  270,  170,  272,  172,
  269,  165,  166,  278,  279,  169,   95,   96,   97,  272,
  257,  258,  259,  258,  259,  262,  263,  272,  265,  264,
  265,  268,  269,  268,  113,  114,  115,  116,  275,  257,
  258,  259,  276,  277,  262,  263,  263,  265,  266,  259,
  268,  269,  272,  273,  272,  275,  135,  275,  276,  277,
  257,  258,  259,  258,  259,  262,  263,  272,  265,  266,
  265,  268,  269,  268,  270,  272,  272,  272,  275,  276,
  277,  257,  258,  259,  258,  259,  262,  263,  272,  265,
  266,  265,  268,  269,  268,  264,  272,  264,  272,  275,
  276,  277,  257,  258,  259,  258,  259,  262,  263,  266,
  265,  259,  265,  268,  269,  268,  257,  258,  259,  272,
  275,  262,   54,   55,  265,  258,  259,  268,  264,  270,
  264,  272,  265,  258,  259,  268,  266,  270,    0,  272,
  265,   57,   58,  268,  272,  270,  272,  272,  257,  258,
  259,  272,  272,  262,  263,  272,  265,  272,  272,  268,
  269,  257,  258,  259,  272,  272,  262,  263,    0,  265,
    0,   32,  268,  257,  258,  259,   -1,   -1,  262,   -1,
   -1,  265,  272,   -1,  268,  275,  276,  277,   -1,  272,
  275,  276,  277,  276,  277,  275,  276,  277,
};
#define YYFINAL 7
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 281
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"VARTYPE","PRINT","ID","CTE",
"CHARCHAIN","FUNCTION","BEGIN","END","FOR","COMPARATOR","ERROR","IF","THEN",
"ELSE","ASSIGN","SEMICOLON","COMMA","OPENPAREN","CLOSEPAREN","PLUS","MINUS",
"PRODUCT","DIVISION","RETURN","IT",
};
char *yyrule[] = {
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
"functionHeader : FUNCTION ID OPENPAREN varDeclaration CLOSEPAREN",
"functionDeclaration : functionHeader BEGIN varDeclarationList executionWReturnList END SEMICOLON",
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
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 274 "./grammar.txt"

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
#line 414 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 6:
#line 63 "./grammar.txt"
{
		/*get the list ready for the following variable declarations*/
		parserUtils.variableList.clear();
	}
break;
case 10:
#line 72 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Variable declaration ", parserUtils.lexical.getLine());
		SymbolTable symbolTable = SymbolTable.getInstance();
		SymbolElement element = new SymbolElement();
		String literalTypeValue = ((Token)yyvsp[-2].obj).getLiteralValue();

		parserUtils.errorHandler.addError(error);
		/*add type to each symbol table entryof the varList*/
		for(String varName : parserUtils.variableList) {
			element = symbolTable.identify(varName);
			element.setVarType(literalTypeValue);
			element.setUse("VAR");
		}

	}
break;
case 11:
#line 87 "./grammar.txt"
{
		/*add variable name to a list that will be consulted later*/
		/*to add type to each variable in the symbol table*/
		String varName = ((Token)yyvsp[0].obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 12:
#line 93 "./grammar.txt"
{
		String varName = ((Token)yyvsp[0].obj).getLiteralValue();
		parserUtils.variableList.add(varName);
	}
break;
case 13:
#line 98 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Function declaration ", parserUtils.lexical.getLine());
		SymbolElement element = new SymbolElement();
		SymbolTable symbolTable = SymbolTable.getInstance();
		String functionName = ((Token)yyvsp[-3].obj).getLiteralValue();

		parserUtils.errorHandler.addError(error);
		
		/*add FUNCTION use to the function identifier*/
		element = symbolTable.identify(functionName);
		element.setUse("FUNC");
	}
break;
case 15:
#line 111 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 16:
#line 115 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 17:
#line 119 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 18:
#line 123 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in function declaration ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 30:
#line 148 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 31:
#line 152 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 32:
#line 156 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in return execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 33:
#line 161 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 34:
#line 165 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 35:
#line 169 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in print execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 36:
#line 175 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 37:
#line 179 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 38:
#line 183 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in function execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 39:
#line 188 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Iteration execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 40:
#line 192 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in iteration execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 41:
#line 196 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in iteration execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 42:
#line 201 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_NOTIFICATION,"Selection execution ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 43:
#line 205 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 44:
#line 209 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 45:
#line 213 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 47:
#line 218 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing left paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 48:
#line 222 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_WARNING,"Missing right paretheses in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 49:
#line 226 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing THEN in selection execution  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 53:
#line 235 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 54:
#line 239 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 56:
#line 244 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing BEGIN in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 57:
#line 248 "./grammar.txt"
{
		Error error = new Error(Error.TYPE_FATAL,"Missing END in block  ", parserUtils.lexical.getLine());
		parserUtils.errorHandler.addError(error);
	}
break;
case 60:
#line 255 "./grammar.txt"
{
	Error error = new Error(Error.TYPE_NOTIFICATION,"Assign execution ", parserUtils.lexical.getLine());
	parserUtils.errorHandler.addError(error);
	}
break;
#line 806 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
