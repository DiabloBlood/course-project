/* calculator with AST */
%{
# include <stdio.h>
# include <stdlib.h>
# include <stdarg.h>
int lineNum = 1;

struct ast {
	char *name;
	struct ast *left;
	struct ast *right;
};

struct numval {
	int nodetype; /* type K for constant */
	double number;
};

/* build an AST */
struct ast *newast( char *name, struct ast *left, struct ast *right );
struct ast *newnum( double d );

/* evaluate an AST */
double eval( struct ast * );
/* delete and free an AST */
void treefree( struct ast * );
void treeTrav( struct ast *a, int layer );

extern int yylineno; /* from lexer */
void yyerror( char *s, ... );
%}

%union {
	struct ast *a;
	double d;
}

/* declare tokens */
%token <a>NUMBER
%token EOL
%type <a> exp factor term '+'


%%

calclist
	: /* nothing */
	| calclist exp EOL 
		{
			printf("exp\n"); /*evaluate and print the AST*/
			treeTrav($2, lineNum);
			printf( "> " );
		}
		
	| calclist EOL { printf("> "); } /* blank line or a comment */
	;
	
exp
	: factor {
				$$ = newast("exp", NULL, NULL);
				$$->left = $1;
			 }
	| exp '+' factor { 
						$$ = newast("exp", NULL, NULL);
						$$->left = $1;
						$2 = newast("+", NULL, NULL); $1->right = $2;
						$2->right = $3;
					 }
	| exp '-' factor /*{ $$ = newast("-", $1,$3); }*/
	;
	
factor
	: term {
				$$ = newast("factor", NULL, NULL);
				$$->left = $1;
		   }
	;
	
term
	: NUMBER { 
				$1 = newast("NUMBER", NULL, NULL);
				$$ = newast("term", NULL, NULL);
				$$->left = $1;				 
			 }
	;
%%

struct ast *newast( char *name, struct ast *left, struct ast *right )
{
	struct ast *a = malloc( sizeof( struct ast ) );
	if(!a) 
	{
		yyerror( "out of space" );
		exit(0);
	}
	
	a->name = name;
	a->left = left;
	a->right = right;
	return a;
}

struct ast *newnum( double d )
{
	struct numval *a = malloc( sizeof( struct numval ) );
	if(!a) 
	{
		yyerror( "out of space" );
		exit(0);
	}
	a->nodetype = 'K';
	a->number = d;
	return (struct ast *)a;
}

void treeTrav( struct ast *a, int layer)
{
	if(a == NULL)
		return;
	int i = 0;
	for(i = 0; i<layer; i++)
	{
		printf("|");
	}
	layer++;
	printf("%s\n", a->name);
	treeTrav( a->left, layer );
	treeTrav( a->right, layer-1 );	
}
 
void yyerror(char *s, ...)
{
	va_list ap;
	va_start(ap, s);
	fprintf(stderr, "%d: error: ", yylineno);
	vfprintf(stderr, s, ap);
	fprintf(stderr, "\n");
}

int main()
{
	printf("> ");
	yyparse();
	return 0;
}
























