/*
* Declarations for a improved calculator ic
*/
/* interface to the lexer */
extern int yylineno; /* from lexer */
void yyerror( char *s, ... );

/* nodes in the abstract syntax tree */
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
