O   [0-7]
D   [0-9]
NZ  [1-9]
L   [a-zA-Z_]
A   [a-zA-Z_0-9]
H   [a-fA-F0-9]
HP  (0[xX])
E   ([Ee][+-]?{D}+)
P   ([Pp][+-]?{D}+)
FS  (f|F|l|L)
IS  (((u|U)(l|L|ll|LL)?)|((l|L|ll|LL)(u|U)?))
CP  (u|U|L)
SP  (u8|u|U|L)
ES  (\\(['"\?\\abfnrtv]|[0-7]{1,3}|x[a-fA-F0-9]+))
WS  [ \t\v\n\f]

%{
#include <string.h>  /* for strdup */
#include <stdio.h> /* for atoi and atof */
#include "P2.tab.h"  /* for token definitions and yylval */
/* #define DEBUG */
 
extern void yyerror(const char *);  /* prints grammar violation message */
extern int sym_type(const char *);  /* returns type from symbol table */


static void comment(void);
void print();
int idType();

extern int lineNum; /* line number from cal.tab.c */

/* list node declaration */
typedef struct node {
	char *name;
	struct node *pNext;
}ListNode, *LinkList;

/*function declaration*/
void ListAdd( const char* name );
int isFind( char* name );
void printList( );
int lookListTop();

LinkList pHead = NULL; /* define LinKlist head node as a global variable */

%}

%%
[ \t\v\t]+ {}
[\n] { lineNum++; }

"/*"                                    { comment(); }
"//".*                                    { /* consume //-comment */ }
"#".*					{ print();}


"auto"					{ print(); return(AUTO); }
"break"					{ print(); return(BREAK); }
"case"					{ print(); return(CASE); }
"char"					{ print(); return(CHAR); }
"const"					{ print(); return(CONST); }
"continue"				{ print(); return(CONTINUE); }
"default"				{ print(); return(DEFAULT); }
"do"					{ print(); return(DO); }
"double"				{ print(); return(DOUBLE); }
"else"					{ print(); return(ELSE); }
"enum"					{ print(); return(ENUM); }
"extern"				{ print(); return(EXTERN); }
"float"					{ print(); return(FLOAT); }
"for"					{ print(); return(FOR); }
"goto"					{ print(); return(GOTO); }
"if"					{ print(); return(IF); }
"inline"				{ print(); return(INLINE); }
"int"					{ print(); return(INT); }
"long"					{ print(); return(LONG); }
"register"				{ print(); return(REGISTER); }
"restrict"				{ print(); return(RESTRICT); }
"return"				{ print(); return(RETURN); }
"short"					{ print(); return(SHORT); }
"signed"				{ print(); return(SIGNED); }
"sizeof"				{ print(); return(SIZEOF); }
"static"				{ print(); return(STATIC); }
"struct"				{ print(); if(lookListTop()) ListAdd( yytext ); return(STRUCT); }
"switch"				{ print(); return(SWITCH); }
"typedef"				{ print(); ListAdd( yytext ); return(TYPEDEF); }
"union"					{ print(); return(UNION); }
"unsigned"				{ print(); return(UNSIGNED); }
"void"					{ print(); return(VOID); }
"volatile"				{ print(); return(VOLATILE); }
"while"					{ print(); return(WHILE); }
"_Alignas"                              { print(); return ALIGNAS; }
"_Alignof"                              { print(); return ALIGNOF; }
"_Atomic"                               { print(); return ATOMIC; }
"_Bool"                                 { print(); return BOOL; }
"_Complex"                              { print(); return COMPLEX; }
"_Generic"                              { print(); return GENERIC; }
"_Imaginary"                            { print(); return IMAGINARY; }
"_Noreturn"                             { print(); return NORETURN; }
"_Static_assert"                        { print(); return STATIC_ASSERT; }
"_Thread_local"                         { print(); return THREAD_LOCAL; }
"__func__"                              { print(); return FUNC_NAME; }
"__attribute__"							{ print(); return ATTRIBUTE; }
"be_int" 								{ print(); return TYPEDEF_NAME;}
"le_int" 								{ print(); return TYPEDEF_NAME;}


{L}{A}*							{ print(); /* yylval.id = ( char * ) strdup(yytext); */ return idType(); } /* important id check */

{HP}{H}+{IS}?					{ print(); return I_CONSTANT; }
{NZ}{D}*{IS}?					{ print(); return I_CONSTANT; }
"0"{O}*{IS}?					{ print(); return I_CONSTANT; }
{CP}?"'"([^'\\\n]|{ES})+"'"		{ print(); return I_CONSTANT; }

{D}+{E}{FS}?					{ print(); return F_CONSTANT; }
{D}*"."{D}+{E}?{FS}?			{ print(); return F_CONSTANT; }
{D}+"."{E}?{FS}?				{ print(); return F_CONSTANT; }
{HP}{H}+{P}{FS}?				{ print(); return F_CONSTANT; }
{HP}{H}*"."{H}+{P}{FS}?			{ print(); return F_CONSTANT; }
{HP}{H}+"."{P}{FS}?				{ print(); return F_CONSTANT; }

({SP}?\"([^"\\\n]|{ES})*\"{WS}*)+	{ print(); return STRING_LITERAL; }

"..."					{ print(); return ELLIPSIS; }
">>="					{ print(); return RIGHT_ASSIGN; }
"<<="					{ print(); return LEFT_ASSIGN; }
"+="					{ print(); return ADD_ASSIGN; }
"-="					{ print(); return SUB_ASSIGN; }
"*="					{ print(); return MUL_ASSIGN; }
"/="					{ print();  return DIV_ASSIGN; }
"%="					{ print(); return MOD_ASSIGN; }
"&="					{ print(); return AND_ASSIGN; }
"^="					{ print(); return XOR_ASSIGN; }
"|="					{ print(); return OR_ASSIGN; }
">>"					{ print(); return RIGHT_OP; }
"<<"					{ print(); return LEFT_OP; }
"++"					{ print(); return INC_OP; }
"--"					{ print(); return DEC_OP; }
"->"					{ print(); return PTR_OP; }
"&&"					{ print(); return AND_OP; }
"||"					{ print(); return OR_OP; }
"<="					{ print(); return LE_OP; }
">="					{ print(); return GE_OP; }
"=="					{ print(); return EQ_OP; }
"!="					{ print(); return NE_OP; }
";"						{ print(); return ';'; }
("{"|"<%")				{ print(); return '{'; }
("}"|"%>")				{ print(); return '}'; }
","						{ print(); return ','; }
":"						{ print(); return ':'; }
"="						{ print(); return '='; }
"("						{ print(); return '('; }
")"						{ print(); return ')'; }
("["|"<:")				{ print(); return '['; }
("]"|":>")				{ print(); return ']'; }
"."						{ print(); return '.'; }
"&"						{ print(); return '&'; }
"!"						{ print(); return '!'; }
"~"						{ print(); return '~'; }
"-"						{ print(); return '-'; }
"+"						{ print(); return '+'; }
"*"						{ print(); return '*'; }
"/"						{ print(); return '/'; }
"%"						{ print(); return '%'; }
"<"						{ print(); return '<'; }
">"						{ print(); return '>'; }
"^"						{ print(); return '^'; }
"|"						{ print(); return '|'; }
"?"						{ print(); return '?'; }

.					{ /* discard bad characters */ }

%%

int yywrap(void)        /* called at end of input */
{
    return 1;           /* terminate now */
}

static void comment(void)
{
    int c;

    while ((c = input()) != 0)
        if (c == '*')
        {
            while ((c = input()) == '*')
                ;

            if (c == '/')
                return;

            if (c == 0)
                break;
        }
}

void print()
{
	#ifdef DEBUG
		printf("%s", yytext);
		printf("\t\t\t");
		printf("%d\n", lineNum);
	#endif
}

int idType()
{
	if( isFind( yytext ) == 0 ) 
	{	
		if(lookListTop() == 1)
		{
			//printf("looktop is 1!\n");
			ListAdd( yytext );
			return IDENTIFIER;
		}
		else
		{
			//printf("looktop is not 1!\n");
			return IDENTIFIER;
		}
	}	
	else
		return TYPEDEF_NAME;		
}

int lookListTop() /* look as a controller that if write a identifier to list */
{
	LinkList pTemp;
	if( pHead == NULL) 
	{
		return 0;
	}
	else
	{
		pTemp = pHead->pNext;
		if( strcmp( pTemp->name, "typedef") == 0 )
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
int AddTypedef()
{
	
}
void ListAdd( const char* name ) /* yytext pionted string cannot be change, we should copy a new*/
{
	if ( pHead == NULL )
	{
		pHead = malloc( sizeof( ListNode ) );
		pHead->pNext = NULL; 
		//printf("pHead was malloc!\n");
	}
	LinkList pTemp;
	LinkList newCell = malloc( sizeof( ListNode ) );
	pTemp = pHead->pNext;
	pHead->pNext = newCell;
	newCell->name = (char *) malloc ( strlen(name)+1 );
	strcpy(newCell->name, name);
	newCell->pNext =pTemp;	
}

int isFind ( char* name )
{
	LinkList pTemp;
	if( pHead == NULL )
	{
		// not find 
		//printf("not find because pHead is NULL!\n");
		return 0;
	}
	else 
	{
		pTemp = pHead->pNext;
		while(pTemp != NULL && strcmp( pTemp->name, name) )
		{
			pTemp = pTemp->pNext;
		}
	
		if( pTemp == NULL)
		{	
			// not find 
			//printf("not find!\n");
			return 0;
		}

		else
		{
			// the element is found! 
			//printf("yes, find!\n");
			return 1;
		}
	}

}

void printList ()
{
	if( pHead == NULL )
	{
		//printf("Elements number is 0!");
		return;
	}
	else
	{
		LinkList pTemp = pHead->pNext;
		while(pTemp != NULL)
		{
			//printf("Element: %s\n", pTemp->name );
			pTemp = pTemp->pNext;		
		}	
	}
}














