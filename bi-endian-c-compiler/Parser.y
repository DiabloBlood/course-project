%{
	
#include <stdio.h>
#include <stdlib.h> /* For malloc in symbol table */
#include <malloc.h> /* For malloc in symbol table */
#include <string.h> /* For strcmp in symbol table */

int lineNum = 1;
void yyerror(char *ps) { /* need this to avoid
link problem */
printf("ERROR:\t%s at line %d\n", ps, lineNum);
}
/*declare tree node*/
struct ast {
	char *name;
	char *content;
	int nodetype; /* 0: non-terminal, 1 terminal, 2: punction*/
	struct ast *left;
	struct ast *right;
};

struct ast *newast( char *name, struct ast *left, struct ast *right );
struct ast *newTerm( char *content, struct ast *left, struct ast *right );
struct ast *newPunc( char *name, struct ast *left, struct ast *right );
void treeTrav( struct ast *a, int layer );


int yylex();
%}

/* declare the union of yyval*/
%union 
{
	struct ast *a;
}

/*all the tokens*/
%token	<a> IDENTIFIER 
%token	<a> I_CONSTANT F_CONSTANT FUNC_NAME SIZEOF STRING_LITERAL

%token	<a> PTR_OP INC_OP DEC_OP LEFT_OP RIGHT_OP LE_OP GE_OP EQ_OP NE_OP
%token	<a> AND_OP OR_OP MUL_ASSIGN DIV_ASSIGN MOD_ASSIGN ADD_ASSIGN
%token	<a> SUB_ASSIGN LEFT_ASSIGN RIGHT_ASSIGN AND_ASSIGN
%token	<a> XOR_ASSIGN OR_ASSIGN
%token	<a> TYPEDEF_NAME ENUMERATION_CONSTANT

%token	<a> TYPEDEF EXTERN STATIC AUTO REGISTER INLINE
%token	<a> CONST RESTRICT VOLATILE
%token	<a> BOOL CHAR SHORT INT LONG SIGNED UNSIGNED FLOAT DOUBLE VOID
%token	<a> COMPLEX IMAGINARY 
%token	<a> STRUCT UNION ENUM ELLIPSIS

%token	<a> CASE DEFAULT IF ELSE SWITCH WHILE DO FOR GOTO CONTINUE BREAK RETURN

%token	<a> ALIGNAS ALIGNOF ATOMIC GENERIC NORETURN STATIC_ASSERT THREAD_LOCAL

%token	<a> ATTRIBUTE

%token  <a> ';' '{' '}' ',' ':' '=' '(' ')' '[' ']' '.' '&' '!' '~' '-' '+' '*' '/' '%' '<' '>' '^' '|' '?'

/* declaration of nonterminal */
%type <a> primary_expression constant enumeration_constant string generic_selection
%type <a> generic_assoc_list generic_association postfix_expression argument_expression_list unary_expression
%type <a> unary_operator cast_expression multiplicative_expression additive_expression shift_expression
%type <a> relational_expression equality_expression and_expression exclusive_or_expression inclusive_or_expression
%type <a> logical_and_expression logical_or_expression conditional_expression assignment_expression assignment_operator
%type <a> expression constant_expression declaration declaration_specifiers init_declarator_list
%type <a> init_declarator storage_class_specifier type_specifier struct_or_union_specifier struct_or_union
%type <a> struct_declaration_list struct_declaration specifier_qualifier_list struct_declarator_list struct_declarator
%type <a> enum_specifier enumerator_list enumerator atomic_type_specifier type_qualifier
%type <a> function_specifier alignment_specifier declarator direct_declarator pointer
%type <a> type_qualifier_list parameter_type_list parameter_list parameter_declaration identifier_list
%type <a> type_name abstract_declarator direct_abstract_declarator initializer initializer_list
%type <a> designation designator_list designator static_assert_declaration statement
%type <a> labeled_statement compound_statement block_item_list block_item expression_statement 
%type <a> selection_statement iteration_statement jump_statement translation_unit external_declaration
%type <a> function_definition declaration_list


%start translation_unit

%%

primary_expression
	: IDENTIFIER
		{
			$$ = newast("primary_expression", NULL, NULL);
			$1 = newast("IDENTIFIER", NULL, NULL);
			$$->left = $1; 
		}
	| constant
		{
			$$ = newast("primary_expression", NULL, NULL);
			$$->left = $1;
		}
	| string
		{
			$$ = newast("primary_expression", NULL, NULL);
			$$->left = $1;
		}
	| '(' expression ')'
		{
			$$ = newast("primary_expression", NULL, NULL);
			$1 = newast("(", NULL, NULL); $$->left = $1;
			$1->right = $2; 
			$3 = newast(")", NULL, NULL); $2->right = $3;
		}
	| generic_selection
		{
			$$ = newast("primary_expression", NULL, NULL);
			$$->left = $1;
		}
	;

constant
	: I_CONSTANT		/* includes character_constant */
		{
			$$ = newast("constant", NULL, NULL);
			$1 = newast("I_CONSTANT", NULL, NULL);
			$$->left = $1;
		}
	| F_CONSTANT
		{
			$$ = newast("constant", NULL, NULL);
			$1 = newast("I_CONSTANT", NULL, NULL);
			$$->left = $1;
		}
	| ENUMERATION_CONSTANT	/* after it has been defined as such */
		{
			$$ = newast("constant", NULL, NULL);
			$1 = newast("ENUMERATION_CONSTANT", NULL, NULL);
			$$->left = $1;
		}
	;

enumeration_constant		/* before it has been defined as such */
	: IDENTIFIER
		{
			$$ = newast("enumeration_constant", NULL, NULL);
			$1 = newast("IDENTIFIER", NULL, NULL);
			$$->left = $1; 
		}
	;

string
	: STRING_LITERAL
		{
			$$ = newast("string", NULL, NULL);
			$1 = newast("STRING_LITERAL", NULL, NULL);
			$$->left = $1;
		}
	| FUNC_NAME
		{
			$$ = newast("string", NULL, NULL);
			$1 = newast("__func__", NULL, NULL);
			$$->left = $1;
		}
	;

generic_selection
	: GENERIC '(' assignment_expression ',' generic_assoc_list ')'
		{
			$$ = newast("generic_selection", NULL, NULL);
			$1 = newast("_Generic", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(",", NULL, NULL); $3->right = $4;
			$4->right = $5; 
			$6 = newast(")", NULL, NULL); $5->right = $6;
		}
	;

generic_assoc_list
	: generic_association
		{
			$$ = newast("generic_assoc_list", NULL, NULL);
			$$->left = $1;
		}
	| generic_assoc_list ',' generic_association
		{
			$$ = newast("generic_assoc_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

generic_association
	: type_name ':' assignment_expression
		{
			$$ = newast("generic_association", NULL, NULL);
			$$->left = $1;
			$2 = newast(":", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| DEFAULT ':' assignment_expression
		{
			$$ = newast("generic_association", NULL, NULL);
			$1 = newast("default", NULL, NULL); $$->left = $1;
			$2 = newast(":", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

postfix_expression
	: primary_expression
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$$->left = $1;
		}
	| postfix_expression '[' expression ']'
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| postfix_expression '(' ')'
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$3 = newast(")", NULL, NULL); $2->right = $3;
		}
	| postfix_expression '(' argument_expression_list ')'
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	| postfix_expression '.' IDENTIFIER
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast(".", NULL, NULL); $1->right = $2;
			$3 = newast("IDENTIFIER", NULL, NULL); $2->right = $3;
			
		}
	| postfix_expression PTR_OP IDENTIFIER
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("->", NULL, NULL); $1->right = $2;
			$3 = newast("IDENTIFIER", NULL, NULL); $2->right = $3;
		}
	| postfix_expression INC_OP
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("++", NULL, NULL); $1->right = $2;
		}
	| postfix_expression DEC_OP
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("--", NULL, NULL); $1->right = $2;
		}
	| '(' type_name ')' '{' initializer_list '}'
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$1 = newast("(", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast(")", NULL, NULL); $2->right = $3;
			$4 = newast("{", NULL, NULL); $3->right = $4;
			$4->right = $5;
			$6 = newast("}", NULL, NULL); $5->right = $6;			
		}
	| '(' type_name ')' '{' initializer_list ',' '}'
		{
			$$ = newast("postfix_expression", NULL, NULL);
			$1 = newast("(", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast(")", NULL, NULL); $2->right = $3;
			$4 = newast("{", NULL, NULL); $3->right = $4;
			$4->right = $5;
			$6 = newast(",", NULL, NULL); $5->right = $6;
			$7 = newast("}", NULL, NULL); $6->right = $7;
		}
	;

argument_expression_list
	: assignment_expression
		{
			$$ = newast("argument_expression_list", NULL, NULL);
			$$->left = $1;
		}
	| argument_expression_list ',' assignment_expression
		{
			$$ = newast("argument_expression_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

unary_expression
	: postfix_expression
		{
			$$ = newast("unary_expression", NULL, NULL);
			$$->left = $1;
		}
	| INC_OP unary_expression
		{
			$$ = newast("unary_expression", NULL, NULL);
			$1 = newast("++", NULL, NULL); $$->left = $1; 
			$1->right = $2; 
		}
	| DEC_OP unary_expression
		{
			$$ = newast("unary_expression", NULL, NULL);
			$1 = newast("--", NULL, NULL); $$->left = $1; 
			$1->right = $2; 
		}
	| unary_operator cast_expression
		{
			$$ = newast("unary_expression", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| SIZEOF unary_expression
		{
			$$ = newast("unary_expression", NULL, NULL);
			$1 = newast("sizeof", NULL, NULL); $$->left = $1; 
			$1->right = $2; 
		}
	| SIZEOF '(' type_name ')'
		{
			$$ = newast("unary_expression", NULL, NULL);
			$1 = newast("sizeof", NULL, NULL); $$->left = $1; 
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	| ALIGNOF '(' type_name ')'
		{
			$$ = newast("unary_expression", NULL, NULL);
			$1 = newast("_Alignof", NULL, NULL); $$->left = $1; 
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	;

unary_operator
	: '&'
		{
			$$ = newast("unary_operator", NULL, NULL);
			$1 = newast("&", NULL, NULL);
			$$->left = $1; 
		}
	| '*'
		{
			$$ = newast("unary_operator", NULL, NULL);
			$1 = newast("*", NULL, NULL);
			$$->left = $1; 
		}
	| '+'
		{
			$$ = newast("unary_operator", NULL, NULL);
			$1 = newast("+", NULL, NULL);
			$$->left = $1; 
		}
	| '-'
		{
			$$ = newast("unary_operator", NULL, NULL);
			$1 = newast("-", NULL, NULL);
			$$->left = $1; 
		}
	| '~'
		{
			$$ = newast("unary_operator", NULL, NULL);
			$1 = newast("~", NULL, NULL);
			$$->left = $1; 
		}
	| '!'
		{
			$$ = newast("unary_operator", NULL, NULL);
			$1 = newast("!", NULL, NULL);
			$$->left = $1; 
		}
	;

cast_expression
	: unary_expression
		{
			$$ = newast("cast_expression", NULL, NULL);
			$$->left = $1;
		}
	| '(' type_name ')' cast_expression
		{
			$$ = newast("cast_expression", NULL, NULL);
			$1 = newast("(", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast(")", NULL, NULL); $2->right = $3;
			$3->right = $4;
		}
	;

multiplicative_expression
	: cast_expression
		{
			$$ = newast("multiplicative_expression", NULL, NULL);
			$$->left = $1;
		}
	| multiplicative_expression '*' cast_expression
		{
			$$ = newast("multiplicative_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("*", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| multiplicative_expression '/' cast_expression
		{
			$$ = newast("multiplicative_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("/", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| multiplicative_expression '%' cast_expression
		{
			$$ = newast("multiplicative_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("%", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

additive_expression
	: multiplicative_expression
		{
			$$ = newast("additive_expression", NULL, NULL);
			$$->left = $1;
		}
	| additive_expression '+' multiplicative_expression
		{
			$$ = newast("additive_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("+", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| additive_expression '-' multiplicative_expression
		{
			$$ = newast("additive_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("-", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

shift_expression
	: additive_expression
		{
			$$ = newast("shift_expression", NULL, NULL);
			$$->left = $1;
		}
	| shift_expression LEFT_OP additive_expression
		{
			$$ = newast("shift_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("<<", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| shift_expression RIGHT_OP additive_expression
		{
			$$ = newast("shift_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast(">>", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

relational_expression
	: shift_expression
		{
			$$ = newast("relational_expression", NULL, NULL);
			$$->left = $1;
		}
	| relational_expression '<' shift_expression
		{
			$$ = newast("relational_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("<", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| relational_expression '>' shift_expression
		{
			$$ = newast("relational_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast(">", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| relational_expression LE_OP shift_expression
		{
			$$ = newast("relational_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("<=", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| relational_expression GE_OP shift_expression
		{
			$$ = newast("relational_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast(">=", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

equality_expression
	: relational_expression
		{
			$$ = newast("equality_expression", NULL, NULL);
			$$->left = $1;
		}
	| equality_expression EQ_OP relational_expression
		{
			$$ = newast("equality_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("==", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| equality_expression NE_OP relational_expression
		{
			$$ = newast("equality_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("!=", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

and_expression
	: equality_expression
		{
			$$ = newast("and_expression", NULL, NULL);
			$$->left = $1;
		}
	| and_expression '&' equality_expression
		{
			$$ = newast("and_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("&", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

exclusive_or_expression
	: and_expression
		{
			$$ = newast("exclusive_or_expression", NULL, NULL);
			$$->left = $1;
		}
	| exclusive_or_expression '^' and_expression
		{
			$$ = newast("exclusive_or_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("^", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

inclusive_or_expression
	: exclusive_or_expression
		{
			$$ = newast("inclusive_or_expression", NULL, NULL);
			$$->left = $1;
		}
	| inclusive_or_expression '|' exclusive_or_expression
		{
			$$ = newast("inclusive_or_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("|", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

logical_and_expression
	: inclusive_or_expression
		{
			$$ = newast("logical_and_expression", NULL, NULL);
			$$->left = $1;
		}
	| logical_and_expression AND_OP inclusive_or_expression
		{
			$$ = newast("logical_and_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("&&", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

logical_or_expression
	: logical_and_expression
		{
			$$ = newast("logical_or_expression", NULL, NULL);
			$$->left = $1;
		}
	| logical_or_expression OR_OP logical_and_expression
		{
			$$ = newast("logical_or_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("||", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

conditional_expression
	: logical_or_expression
		{
			$$ = newast("conditional_expression", NULL, NULL);
			$$->left = $1;
		}
	| logical_or_expression '?' expression ':' conditional_expression
		{
			$$ = newast("conditional_expression", NULL, NULL);
			$$->left = $1;
			$2 = newast("?", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("?", NULL, NULL); $3->right = $4;
			$4->right = $5;			
		}
	;

assignment_expression
	: conditional_expression
		{
			$$ = newast("assignment_expression", NULL, NULL);
			$$->left = $1;
		}
	| unary_expression assignment_operator assignment_expression
		{
			$$ = newast("assignment_expression", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
			$2->right = $3;
		}
	;

assignment_operator
	: '='
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("=", NULL, NULL);
			$$->left = $1; 
		}
	| MUL_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("*=", NULL, NULL);
			$$->left = $1;
		}
	| DIV_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("/=", NULL, NULL);
			$$->left = $1;
		}
	| MOD_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("%=", NULL, NULL);
			$$->left = $1;
		}
	| ADD_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("+=", NULL, NULL);
			$$->left = $1;
		}
	| SUB_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("-=", NULL, NULL);
			$$->left = $1;
		}
	| LEFT_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("<<=", NULL, NULL);
			$$->left = $1;
		}
	| RIGHT_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast(">>=", NULL, NULL);
			$$->left = $1;
		}
	| AND_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("&=", NULL, NULL);
			$$->left = $1;
		}
	| XOR_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("^=", NULL, NULL);
			$$->left = $1;
		}
	| OR_ASSIGN
		{
			$$ = newast("assignment_operator", NULL, NULL);
			$1 = newast("|=", NULL, NULL);
			$$->left = $1;
		}
	;

expression
	: assignment_expression
		{
			$$ = newast("expression", NULL, NULL);
			$$->left = $1;
		}
	| expression ',' assignment_expression
		{
			$$ = newast("expression", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

constant_expression
	: conditional_expression	/* with constraints */
		{
			$$ = newast("constant_expression", NULL, NULL);
			$$->left = $1;
		}
	;

declaration
	: declaration_specifiers ';'
		{
			$$ = newast("declaration", NULL, NULL);
			$$->left = $1;
			$2 = newast(";", NULL, NULL); $1->right = $2;
		}
	| declaration_specifiers init_declarator_list ';'
		{
			$$ = newast("declaration", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
			$3 = newast(";", NULL, NULL); $2->right = $3;
		}
	| static_assert_declaration
		{
			$$ = newast("declaration", NULL, NULL);
			$$->left = $1;
		}
	;

declaration_specifiers
	: storage_class_specifier declaration_specifiers
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| storage_class_specifier
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1;
		}
	| type_specifier declaration_specifiers
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1; 
			$1->right = $2;
		}
	| type_specifier
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1;
		}
	| type_qualifier declaration_specifiers
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1; 
			$1->right = $2;
		}
	| type_qualifier
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1;
		}
	| function_specifier declaration_specifiers
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1; 
			$1->right = $2;
		}
	| function_specifier
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1;
		}
	| alignment_specifier declaration_specifiers
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1; 
			$1->right = $2;
		}
	| alignment_specifier
		{
			$$ = newast("declaration_specifiers", NULL, NULL);
			$$->left = $1;
		}
	;

init_declarator_list
	: init_declarator
		{
			$$ = newast("init_declarator_list", NULL, NULL);
			$$->left = $1;
		}
	| init_declarator_list ',' init_declarator
		{
			$$ = newast("init_declarator_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

init_declarator
	: declarator '=' initializer
		{
			$$ = newast("init_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("=", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| declarator
		{
			$$ = newast("init_declarator", NULL, NULL);
			$$->left = $1;
		}
	;

storage_class_specifier
	: TYPEDEF	/* identifiers must be flagged as TYPEDEF_NAME */
		{
			$$ = newast("storage_class_specifier", NULL, NULL);
			$1 = newast("typedef", NULL, NULL);
			$$->left = $1;
		}
	| EXTERN
		{
			$$ = newast("storage_class_specifier", NULL, NULL);
			$1 = newast("extern", NULL, NULL);
			$$->left = $1;
		}
	| STATIC
		{
			$$ = newast("storage_class_specifier", NULL, NULL);
			$1 = newast("static", NULL, NULL);
			$$->left = $1;
		}
	| THREAD_LOCAL
		{
			$$ = newast("storage_class_specifier", NULL, NULL);
			$1 = newast("_Thread_local", NULL, NULL);
			$$->left = $1;
		}
	| AUTO
		{
			$$ = newast("storage_class_specifier", NULL, NULL);
			$1 = newast("auto", NULL, NULL);
			$$->left = $1;
		}
	| REGISTER
		{
			$$ = newast("storage_class_specifier", NULL, NULL);
			$1 = newast("register", NULL, NULL);
			$$->left = $1;
		}
	| TYPEDEF ATTRIBUTE '(' '(' IDENTIFIER ')' ')' 
		{
			$$ = newast("storage_class_specifier", NULL, NULL);
			$1 = newast("typedef", NULL, NULL);
			$$->left = $1;
			$2 = newast("__attribute__", NULL, NULL); $1->right = $2;
			$3 = newast("(", NULL, NULL); $2->right = $3;
			$4 = newast("(", NULL, NULL); $3->right = $4;
			$5 = newast("IDENTIFIER", NULL, NULL); $4->right = $5;
			$6 = newast(")", NULL, NULL); $5->right = $6;
			$7 = newast(")", NULL, NULL); $6->right = $7;
		}
	;

type_specifier
	: VOID
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("void", NULL, NULL);	
			$$->left = $1;
		}
	| CHAR
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("char", NULL, NULL);	
			$$->left = $1;
		}
	| SHORT
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("short", NULL, NULL);	
			$$->left = $1;
		}
	| INT
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("int", NULL, NULL);	
			$$->left = $1;
		}
	| LONG
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("long", NULL, NULL);	
			$$->left = $1;
		}
	| FLOAT
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("float", NULL, NULL);	
			$$->left = $1;
		}
	| DOUBLE
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("double", NULL, NULL);	
			$$->left = $1;
		}
	| SIGNED
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("signed", NULL, NULL);	
			$$->left = $1;
		}
	| UNSIGNED
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("unsigned", NULL, NULL);	
			$$->left = $1;
		}
	| BOOL
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("_Bool", NULL, NULL);	
			$$->left = $1;
		}
	| COMPLEX
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("_Complex", NULL, NULL);	
			$$->left = $1;
		}
	| IMAGINARY	  	/* non-mandated extension */
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("_Imaginary", NULL, NULL);	
			$$->left = $1;
		}
	| atomic_type_specifier
		{
			$$ = newast("type_specifier", NULL, NULL);
			$$->left = $1;
		}
	| struct_or_union_specifier
		{
			$$ = newast("type_specifier", NULL, NULL);
			$$->left = $1;
		}
	| enum_specifier
		{
			$$ = newast("type_specifier", NULL, NULL);
			$$->left = $1;
		}
	| TYPEDEF_NAME		/* after it has been defined as such */
		{
			$$ = newast("type_specifier", NULL, NULL);
			$1 = newast("TYPEDEF_NAME", NULL, NULL);	
			$$->left = $1;
		}
	;

struct_or_union_specifier
	: struct_or_union '{' struct_declaration_list '}'
		{
			$$ = newast("struct_or_union_specifier", NULL, NULL);
			$$->left = $1;
			$2 = newast("{", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("}", NULL, NULL); $3->right = $4;
		}
	| struct_or_union IDENTIFIER '{' struct_declaration_list '}'
		{
			$$ = newast("struct_or_union_specifier", NULL, NULL);
			$$->left = $1;
			$2 = newast("IDENTIFIER", NULL, NULL); $1->right = $2;
			$3 = newast("{", NULL, NULL); $2->right = $3;
			$3->right = $4; 
			$5 = newast("}", NULL, NULL); $4->right = $5;
		}
	| struct_or_union IDENTIFIER
		{
			$$ = newast("struct_or_union_specifier", NULL, NULL);
			$$->left = $1;
			$2 = newast("IDENTIFIER", NULL, NULL); $1->right = $2;
		}
	;

struct_or_union
	: STRUCT
		{
			$$ = newast("struct_or_union", NULL, NULL);
			$1 = newast("struct", NULL, NULL);	
			$$->left = $1;
		}
	| UNION
		{
			$$ = newast("struct_or_union", NULL, NULL);
			$1 = newast("union", NULL, NULL);	
			$$->left = $1;
		}
	;

struct_declaration_list
	: struct_declaration
		{
			$$ = newast("struct_declaration_list", NULL, NULL);
			$$->left = $1;
		}
	| struct_declaration_list struct_declaration
		{
			$$ = newast("struct_declaration_list", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	;

struct_declaration
	: specifier_qualifier_list ';'	/* for anonymous struct/union */
		{
			$$ = newast("struct_declaration", NULL, NULL);
			$$->left = $1;
			$2 = newast(";", NULL, NULL); $1->right = $2;
		}
	| specifier_qualifier_list struct_declarator_list ';'
		{
			$$ = newast("struct_declaration", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
			$3 = newast(";", NULL, NULL); $2->right = $3;
		}
	| static_assert_declaration
		{
			$$ = newast("struct_declaration", NULL, NULL);
			$$->left = $1;
		}
	;

specifier_qualifier_list
	: type_specifier specifier_qualifier_list
		{
			$$ = newast("specifier_qualifier_list", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| type_specifier
		{
			$$ = newast("specifier_qualifier_list", NULL, NULL);
			$$->left = $1;
		}
	| type_qualifier specifier_qualifier_list
		{
			$$ = newast("specifier_qualifier_list", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| type_qualifier
		{
			$$ = newast("specifier_qualifier_list", NULL, NULL);
			$$->left = $1;
		}
	;

struct_declarator_list
	: struct_declarator
		{
			$$ = newast("struct_declarator_list", NULL, NULL);
			$$->left = $1;
		}
	| struct_declarator_list ',' struct_declarator
		{
			$$ = newast("struct_declarator_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

struct_declarator
	: ':' constant_expression
		{
			$$ = newast("struct_declarator", NULL, NULL);
			$1 = newast(":", NULL, NULL); $$->left = $1; 
			$1->right = $2; 
		}
	| declarator ':' constant_expression
		{
			$$ = newast("struct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast(":", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| declarator
		{
			$$ = newast("struct_declarator", NULL, NULL);
			$$->left = $1;
		}
	;

enum_specifier
	: ENUM '{' enumerator_list '}'
		{
			$$ = newast("enum_specifier", NULL, NULL);
			$1 = newast("enum", NULL, NULL); $$->left = $1;
			$2 = newast("{", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("}", NULL, NULL); $3->right = $4;
		}
	| ENUM '{' enumerator_list ',' '}'
		{
			$$ = newast("enum_specifier", NULL, NULL);
			$1 = newast("enum", NULL, NULL); $$->left = $1;
			$2 = newast("{", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(",", NULL, NULL); $3->right = $4;
			$5 = newast("}", NULL, NULL); $4->right = $5;
		}
	| ENUM IDENTIFIER '{' enumerator_list '}'
		{
			$$ = newast("enum_specifier", NULL, NULL);
			$1 = newast("enum", NULL, NULL); $$->left = $1;
			$2 = newast("IDENTIFIER", NULL, NULL); $1->right = $2;
			$3 = newast("{", NULL, NULL); $2->right = $3;
			$3->right = $4;
			$5 = newast("}", NULL, NULL); $4->right = $5;		
		}
	| ENUM IDENTIFIER '{' enumerator_list ',' '}'
		{
			$$ = newast("enum_specifier", NULL, NULL);
			$1 = newast("enum", NULL, NULL); $$->left = $1;
			$2 = newast("IDENTIFIER", NULL, NULL); $1->right = $2;
			$3 = newast("{", NULL, NULL); $2->right = $3;
			$3->right = $4;
			$5 = newast(",", NULL, NULL); $4->right = $5;
			$6 = newast("}", NULL, NULL); $5->right = $6;
		}
	| ENUM IDENTIFIER
		{
			$$ = newast("enum_specifier", NULL, NULL);
			$1 = newast("enum", NULL, NULL); $$->left = $1;
			$2 = newast("IDENTIFIER", NULL, NULL); $1->right = $2;
		}
	;

enumerator_list
	: enumerator
		{
			$$ = newast("enumerator_list", NULL, NULL);
			$$->left = $1;
		}
	| enumerator_list ',' enumerator
		{
			$$ = newast("enumerator_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

enumerator	/* identifiers must be flagged as ENUMERATION_CONSTANT */
	: enumeration_constant '=' constant_expression
		{
			$$ = newast("enumerator", NULL, NULL);
			$$->left = $1;
			$2 = newast("=", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| enumeration_constant
		{
			$$ = newast("enumerator", NULL, NULL);
			$$->left = $1;
		}
	;

atomic_type_specifier
	: ATOMIC '(' type_name ')'
		{
			$$ = newast("atomic_type_specifier", NULL, NULL);
			$1 = newast("_Atomic", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	;

type_qualifier
	: CONST
		{
			$$ = newast("type_qualifier", NULL, NULL);
			$1 = newast("const", NULL, NULL); 
			$$->left = $1;
		}
	| RESTRICT
		{
			$$ = newast("type_qualifier", NULL, NULL);
			$1 = newast("restrict", NULL, NULL); 
			$$->left = $1;
		}
	| VOLATILE
		{
			$$ = newast("type_qualifier", NULL, NULL);
			$1 = newast("volatile", NULL, NULL); 
			$$->left = $1;
		}
	| ATOMIC
		{
			$$ = newast("type_qualifier", NULL, NULL);
			$1 = newast("_Atomic", NULL, NULL); 
			$$->left = $1;
		}
	;

function_specifier
	: INLINE
		{
			$$ = newast("function_specifier", NULL, NULL);
			$1 = newast("inline", NULL, NULL); 
			$$->left = $1;
		}
	| NORETURN
		{
			$$ = newast("function_specifier", NULL, NULL);
			$1 = newast("_Noreturn", NULL, NULL); 
			$$->left = $1;
		}
	;

alignment_specifier
	: ALIGNAS '(' type_name ')'
		{
			$$ = newast("alignment_specifier", NULL, NULL);
			$1 = newast("_Atomic", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	| ALIGNAS '(' constant_expression ')'
		{
			$$ = newast("alignment_specifier", NULL, NULL);
			$1 = newast("_Atomic", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	;

declarator
	: pointer direct_declarator
		{
			$$ = newast("declarator", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| direct_declarator
		{
				$$ = newast("declarator", NULL, NULL);
				$$->left = $1;
		}
	;

direct_declarator
	: IDENTIFIER
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$1 = newast("IDENTIFIER", NULL, NULL);					
			$$->left = $1;
		}
	| '(' declarator ')'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$1 = newast("(", NULL, NULL); $$->left = $1;
			$1->right = $2; 
			$3 = newast(")", NULL, NULL); $2->right = $3;
		}
	| direct_declarator '[' ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$3 = newast("]", NULL, NULL); $2->right = $3;
		}
	| direct_declarator '[' '*' ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$3 = newast("*", NULL, NULL); $2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| direct_declarator '[' STATIC type_qualifier_list assignment_expression ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$3 = newast("static", NULL, NULL); $2->right = $3;
			$3->right = $4;
			$4->right = $5;
			$6 = newast("]", NULL, NULL); $5->right = $6;
		}
	| direct_declarator '[' STATIC assignment_expression ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$3 = newast("static", NULL, NULL); $2->right = $3;
			$3->right = $4;
			$5 = newast("]", NULL, NULL); $4->right = $5;
		}
	| direct_declarator '[' type_qualifier_list '*' ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("*", NULL, NULL); $3->right = $4;
			$5 = newast("]", NULL, NULL); $4->right = $5;
		}
	| direct_declarator '[' type_qualifier_list STATIC assignment_expression ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("static", NULL, NULL); $3->right = $4;
			$4->right = $5;
			$6 = newast("]", NULL, NULL); $5->right = $6;
		}
	| direct_declarator '[' type_qualifier_list assignment_expression ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$3->right = $4;
			$5 = newast("]", NULL, NULL); $4->right = $5;
		}
	| direct_declarator '[' type_qualifier_list ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| direct_declarator '[' assignment_expression ']'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| direct_declarator '(' parameter_type_list ')'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	| direct_declarator '(' ')'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$3 = newast(")", NULL, NULL); $2->right = $3;
		}
	| direct_declarator '(' identifier_list ')'
		{
			$$ = newast("direct_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	;

pointer
	: '*' type_qualifier_list pointer
		{
			$$ = newast("pointer", NULL, NULL);
			$1 = newast("*", NULL, NULL); 
			$$->left = $1;
			$1->right = $2;
			$2->right = $3;
		}
	| '*' type_qualifier_list
		{
			$$ = newast("pointer", NULL, NULL);
			$1 = newast("*", NULL, NULL); 
			$$->left = $1;
			$1->right = $2;
		}
	| '*' pointer
		{
			$$ = newast("pointer", NULL, NULL);
			$1 = newast("*", NULL, NULL); 
			$$->left = $1;
			$1->right = $2;
		}
	| '*'
		{
			$$ = newast("pointer", NULL, NULL);
			$1 = newast("*", NULL, NULL); 
			$$->left = $1;
		}
	;

type_qualifier_list
	: type_qualifier
		{
			$$ = newast("type_qualifier_list", NULL, NULL);
			$$->left = $1;
		}
	| type_qualifier_list type_qualifier
		{
			$$ = newast("type_qualifier_list", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	;

parameter_type_list
	: parameter_list ',' ELLIPSIS
		{
			$$ = newast("parameter_type_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$3 = newast("...", NULL, NULL);$2->right = $3;
		}
	| parameter_list
		{
			$$ = newast("parameter_type_list", NULL, NULL);
			$$->left = $1;
		}
	;

parameter_list
	: parameter_declaration
		{
			$$ = newast("parameter_list", NULL, NULL);
			$$->left = $1;
		}
	| parameter_list ',' parameter_declaration
		{
			$$ = newast("parameter_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

parameter_declaration
	: declaration_specifiers declarator
		{
			$$ = newast("parameter_declaration", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| declaration_specifiers abstract_declarator
		{
			$$ = newast("parameter_declaration", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| declaration_specifiers
		{
			$$ = newast("parameter_declaration", NULL, NULL);
			$$->left = $1;
		}
	;

identifier_list
	: IDENTIFIER
		{
			$$ = newast("identifier_list", NULL, NULL);
			$1 = newast("IDENTIFIER", NULL, NULL);					
			$$->left = $1;
		}
	| identifier_list ',' IDENTIFIER
		{
			$$ = newast("identifier_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$3 = newast("IDENTIFIER", NULL, NULL);$2->right = $3;
		}
	;

type_name
	: specifier_qualifier_list abstract_declarator
		{
			$$ = newast("type_name", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| specifier_qualifier_list
		{
			$$ = newast("type_name", NULL, NULL);
			$$->left = $1;
		}
	;

abstract_declarator
	: pointer direct_abstract_declarator
		{
			$$ = newast("abstract_declarator", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| pointer
		{
			$$ = newast("abstract_declarator", NULL, NULL);
			$$->left = $1;
		}
	| direct_abstract_declarator
		{
			$$ = newast("abstract_declarator", NULL, NULL);
			$$->left = $1;
		}
	;

direct_abstract_declarator
	: '(' abstract_declarator ')'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("(", NULL, NULL); $$->left = $1;
			$1->right = $2; 
			$3 = newast(")", NULL, NULL); $2->right = $3;
		}
	| '[' ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$2 = newast("]", NULL, NULL); $1->right = $2;
		}
	| '[' '*' ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$2 = newast("*", NULL, NULL); $1->right = $2;
			$3 = newast("]", NULL, NULL); $2->right = $3;
		}
	| '[' STATIC type_qualifier_list assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$2 = newast("static", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$3->right = $4;
			$5 = newast("]", NULL, NULL); $4->right = $5;
		}
	| '[' STATIC assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$2 = newast("static", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| '[' type_qualifier_list STATIC assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast("static", NULL, NULL); $2->right = $3;
			$3->right = $4;
			$5 = newast("]", NULL, NULL); $4->right = $5;
		}
	| '[' type_qualifier_list assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| '[' type_qualifier_list ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast("]", NULL, NULL); $2->right = $3;
		}
	| '[' assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast("]", NULL, NULL); $2->right = $3;
		}
	| direct_abstract_declarator '[' ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$3 = newast("]", NULL, NULL); $2->right = $3;
		}
	| direct_abstract_declarator '[' '*' ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$3 = newast("*", NULL, NULL); $2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| direct_abstract_declarator '[' STATIC type_qualifier_list assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$3 = newast("static", NULL, NULL); $2->right = $3;
			$3->right = $4;
			$4->right = $5;
			$6 = newast("]", NULL, NULL); $5->right = $6;
		}
	| direct_abstract_declarator '[' STATIC assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$3 = newast("static", NULL, NULL); $2->right = $3;
			$3->right = $4;
			$5 = newast("]", NULL, NULL); $4->right = $5;
		}
	| direct_abstract_declarator '[' type_qualifier_list assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$3->right = $4;
			$5 = newast("]", NULL, NULL); $4->right = $5;
		}
	| direct_abstract_declarator '[' type_qualifier_list STATIC assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("static", NULL, NULL); $3->right = $4;
			$4->right = $5;
			$6 = newast("]", NULL, NULL); $5->right = $6;
		}
	| direct_abstract_declarator '[' type_qualifier_list ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| direct_abstract_declarator '[' assignment_expression ']'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("[", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast("]", NULL, NULL); $3->right = $4;
		}
	| '(' ')'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("(", NULL, NULL); $$->left = $1;
			$2 = newast(")", NULL, NULL); $1->right = $2;
		}
	| '(' parameter_type_list ')'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$1 = newast("(", NULL, NULL); $$->left = $1;
			$1->right = $2; 
			$3 = newast(")", NULL, NULL); $2->right = $3;
		}
	| direct_abstract_declarator '(' ')'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$3 = newast(")", NULL, NULL); $2->right = $3;
		}
	| direct_abstract_declarator '(' parameter_type_list ')'
		{
			$$ = newast("direct_abstract_declarator", NULL, NULL);
			$$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
		}
	;

initializer
	: '{' initializer_list '}'
		{
			$$ = newast("initializer", NULL, NULL);
			$1 = newast("{", NULL, NULL); $$->left = $1;
			$1->right = $2; 
			$3 = newast("}", NULL, NULL); $2->right = $3;
		}
	| '{' initializer_list ',' '}'
		{
			$$ = newast("initializer", NULL, NULL);
			$1 = newast("{", NULL, NULL); $$->left = $1;
			$1->right = $2; 
			$3 = newast(",", NULL, NULL); $2->right = $3;
			$4 = newast("}", NULL, NULL); $3->right = $4;
		}
	| assignment_expression
		{
			$$ = newast("initializer", NULL, NULL);
			$$->left = $1;
		}
	;

initializer_list
	: designation initializer
		{
			$$ = newast("initializer_list", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	| initializer
		{
			$$ = newast("initializer_list", NULL, NULL);
			$$->left = $1;
		}
	| initializer_list ',' designation initializer
		{
			$$ = newast("initializer_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$3->right = $4;
		}
	| initializer_list ',' initializer
		{
			$$ = newast("initializer_list", NULL, NULL);
			$$->left = $1;
			$2 = newast(",", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

designation
	: designator_list '='
		{
			$$ = newast("designation", NULL, NULL);
			$$->left = $1;
			$2 = newast("=", NULL, NULL); $1->right = $2;
		}
	;

designator_list
	: designator
		{
			$$ = newast("designator_list", NULL, NULL);
			$$->left = $1;
		}
	| designator_list designator
		{
			$$ = newast("designator_list", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	;

designator
	: '[' constant_expression ']'
		{
			$$ = newast("designator", NULL, NULL);
			$1 = newast("[", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast("]", NULL, NULL); $2->right = $3;
		}
	| '.' IDENTIFIER
		{
			$$ = newast("designator", NULL, NULL);
			$1 = newast(".", NULL, NULL); $$->left = $1;
			$2 = newast("IDENTIFIER", NULL, NULL); $1->right = $2;
		}
	;

static_assert_declaration
	: STATIC_ASSERT '(' constant_expression ',' STRING_LITERAL ')' ';'
		{
			$$ = newast("storage_class_specifier", NULL, NULL);
			$1 = newast("_Static_assert", NULL, NULL);
			$$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(",", NULL, NULL); $3->right = $4;
			$5 = newast("STRING_LITERAL", NULL, NULL);
			$6 = newast(")", NULL, NULL); $5->right = $6;
			$7 = newast(";", NULL, NULL); $6->right = $7;
		}
	;

statement
	: labeled_statement
		{
			$$ = newast("statement", NULL, NULL);
			$$->left = $1;
		}
	| compound_statement
		{
			$$ = newast("statement", NULL, NULL);
			$$->left = $1;
		}
	| expression_statement
		{
			$$ = newast("statement", NULL, NULL);
			$$->left = $1;
		}
	| selection_statement
		{
			$$ = newast("statement", NULL, NULL);
			$$->left = $1;
		}
	| iteration_statement
		{
			$$ = newast("statement", NULL, NULL);
			$$->left = $1;
		}
	| jump_statement
		{
			$$ = newast("statement", NULL, NULL);
			$$->left = $1;
		}
	;

labeled_statement
	: IDENTIFIER ':' statement
		{
			$$ = newast("labeled_statement", NULL, NULL);
			$1 = newast("IDENTIFIER", NULL, NULL); $$->left = $1;
			$2 = newast(":", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	| CASE constant_expression ':' statement
		{
			$$ = newast("labeled_statement", NULL, NULL);
			$1 = newast("case", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast(":", NULL, NULL); $2->right = $3;
			$3->right = $4;
		}
	| DEFAULT ':' statement
		{
			$$ = newast("labeled_statement", NULL, NULL);
			$1 = newast("default", NULL, NULL); $$->left = $1;
			$2 = newast(":", NULL, NULL); $1->right = $2;
			$2->right = $3;
		}
	;

compound_statement
	: '{' '}'
		{
			$$ = newast("compound_statement", NULL, NULL);
			$1 = newast("{", NULL, NULL); $$->left = $1;
			$2 = newast("}", NULL, NULL); $1->right = $2;
		}
	| '{'  block_item_list '}'
		{
			$$ = newast("compound_statement", NULL, NULL);
			$1 = newast("{", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast("}", NULL, NULL); $2->right = $3;
		}
	;

block_item_list
	: block_item
		{
			$$ = newast("block_item_list", NULL, NULL);
			$$->left = $1;
		}
	| block_item_list block_item
		{
			$$ = newast("block_item_list", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
		}
	;

block_item
	: declaration
		{
			$$ = newast("block_item", NULL, NULL);
			$$->left = $1;
		}
	| statement
		{
			$$ = newast("block_item", NULL, NULL);
			$$->left = $1;
		}
	;

expression_statement
	: ';'
		{
			$$ = newast("expression_statement", NULL, NULL);
			$1 = newast(";", NULL, NULL); 
			$$->left = $1;
		}
	| expression ';'
		{
			$$ = newast("expression_statement", NULL, NULL);
			$$->left = $1;
			$2 = newast(";", NULL, NULL); $1->right = $2;
		}
	;

selection_statement
	: IF '(' expression ')' statement ELSE statement
		{
			$$ = newast("selection_statement", NULL, NULL);
			$1 = newast("if", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
			$4->right = $5;
			$6 = newast("else", NULL, NULL); $5->right = $6;
			$6->right = $7;
		}
	| IF '(' expression ')' statement
		{
			$$ = newast("selection_statement", NULL, NULL);
			$1 = newast("if", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
			$4->right = $5;
		}
	| SWITCH '(' expression ')' statement
		{
			$$ = newast("selection_statement", NULL, NULL);
			$1 = newast("switch", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
			$4->right = $5;
		}
	;

iteration_statement
	: WHILE '(' expression ')' statement
		{
			$$ = newast("iteration_statement", NULL, NULL);
			$1 = newast("while", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$4 = newast(")", NULL, NULL); $3->right = $4;
			$4->right = $5;
		}
	| DO statement WHILE '(' expression ')' ';'
		{
			$$ = newast("iteration_statement", NULL, NULL);
			$1 = newast("do", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast("while", NULL, NULL); $2->right = $3;
			$4 = newast("(", NULL, NULL); $3->right = $4;
			$4->right = $5;
			$6 = newast(")", NULL, NULL); $5->right = $6;
			$7 = newast(";", NULL, NULL); $6->right = $7;
		}
	| FOR '(' expression_statement expression_statement ')' statement
		{
			$$ = newast("iteration_statement", NULL, NULL);
			$1 = newast("for", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$3->right = $4;
			$5 = newast(")", NULL, NULL);$4->right = $5;
			$5->right = $6;
		}
	| FOR '(' expression_statement expression_statement expression ')' statement
		{
			$$ = newast("iteration_statement", NULL, NULL);
			$1 = newast("for", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$3->right = $4;
			$4->right = $5;
			$6 = newast(")", NULL, NULL);$5->right = $6;
			$6->right = $7;
		}
	| FOR '(' declaration expression_statement ')' statement
		{
			$$ = newast("iteration_statement", NULL, NULL);
			$1 = newast("for", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$3->right = $4;
			$5 = newast(")", NULL, NULL);$4->right = $5;
			$5->right = $6;
		}
	| FOR '(' declaration expression_statement expression ')' statement
		{
			$$ = newast("iteration_statement", NULL, NULL);
			$1 = newast("for", NULL, NULL); $$->left = $1;
			$2 = newast("(", NULL, NULL); $1->right = $2;
			$2->right = $3;
			$3->right = $4;
			$4->right = $5;
			$6 = newast(")", NULL, NULL);$5->right = $6;
			$6->right = $7;
		}
	;

jump_statement
	: GOTO IDENTIFIER ';'
		{
			$$ = newast("jump_statement", NULL, NULL);
			$1 = newast("goto", NULL, NULL); $$->left = $1;
			$2 = newast("IDENTIFIER", NULL, NULL); $1->right = $2;
			$3 = newast(";", NULL, NULL); $2->right = $3;
		}
	| CONTINUE ';'
		{
			$$ = newast("jump_statement", NULL, NULL);
			$1 = newast("continue", NULL, NULL); $$->left = $1;
			$2 = newast(";", NULL, NULL); $1->right = $2;
		}
	| BREAK ';'
		{
			$$ = newast("jump_statement", NULL, NULL);
			$1 = newast("break", NULL, NULL); $$->left = $1;
			$2 = newast(";", NULL, NULL); $1->right = $2;
		}
	| RETURN ';'
		{
			$$ = newast("jump_statement", NULL, NULL);
			$1 = newast("return", NULL, NULL); $$->left = $1;
			$2 = newast(";", NULL, NULL); $1->right = $2;
		}
	| RETURN expression ';'
		{
			$$ = newast("jump_statement", NULL, NULL);
			$1 = newast("return", NULL, NULL); $$->left = $1;
			$1->right = $2;
			$3 = newast(";", NULL, NULL); $2->right = $3;
		}
	;

translation_unit
	: external_declaration 
		{
			$$ = newast("translation_unit", NULL, NULL);
			$$->left = $1;
			printf("program_root\n"); int layer = 1;
			treeTrav($$, layer);
			
		}
	| translation_unit external_declaration
		{
			$$ = newast("translation_unit", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
			printf("program_root\n"); int layer = 1;
			treeTrav($$, layer);
		}
	;

external_declaration
	: function_definition
		{
			$$ = newast("external_declaration", NULL, NULL);
			$$->left = $1;
		}
	| declaration
		{
			$$ = newast("external_declaration", NULL, NULL);
			$$->left = $1;
		}
	;

function_definition
	: declaration_specifiers declarator declaration_list compound_statement
		{
			$$ = newast("function_definition", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
			$2->right = $3;
			$3->right = $4;
		}
	| declaration_specifiers declarator compound_statement
		{
			$$ = newast("function_definition", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
			$2->right = $3;
		}
	;

declaration_list
	: declaration
		{
			$$ = newast("declaration_list", NULL, NULL);
			$$->left = $1;
		}
	| declaration_list declaration
		{
			$$ = newast("declaration_list", NULL, NULL);
			$$->left = $1;
			$1->right = $2;
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
	
	a->content = NULL;
	a->name = name;
	a->nodetype = 0;
	a->left = left;
	a->right = right;
	return a;
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
	if(a->nodetype == 0)
	{
		printf("%s\n", a->name);
	}
	else if(a->nodetype == 1)
	{
		printf("%s\n", a->content);
	}
	else if(a->nodetype == 2)
	{
		printf("%s\n", a->name);
	}
	treeTrav( a->left, layer );
	treeTrav( a->right, layer - 1 );	
}
 
 
struct ast *newTerm( char *content, struct ast *left, struct ast *right )
{
	struct ast *a = malloc( sizeof( struct ast ) );
	if(!a) 
	{
		yyerror( "out of space" );
		exit(0);
	}
	
	a->name = NULL;
	a->nodetype = 1;
	a->content = content;
	a->left = left;
	a->right = right;
	return a;
}

int main() {
	yyparse();
	return 0;
}














