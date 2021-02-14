/* recognize tokens for the calculator */
%{
# include "icparser.tab.h"
extern int yylineno; /* from lexer */
void yyerror( char *s, ... );
%}

/* float exponent */
EXP ([Ee][-+]?[0-9]+)

%%
"+" |
"-" |
"*" |
"/" |
"|" |
"(" |
")" { return yytext[0]; }

[0-9]+"."[0-9]*{EXP}? |
"."?[0-9]+{EXP}? { yylval.d = atof(yytext); return NUMBER; }

\n { return EOL; }

"//".*

[ \t] { /* ignore whitespace */ }

. { yyerror("Mystery character %c\n", *yytext); }
%%

int yywrap( void )
{
	return 1;
}
