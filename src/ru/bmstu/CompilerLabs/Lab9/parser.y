
%{
#include <stdio.h>
#include <stdlib.h>
#include "lexer.h"	
#include <string.h>

char* print_offset(int offset) {
	char *res = (char *) malloc(offset + 1);
	for (int i = 0; i < offset; i++) {
		res[i] = ' ';
	}
	res[offset] = '\0';
	return res;
}

#define loc_char_mem(size)   ((char *)calloc(1, sizeof(char) * (size + 1)))

%}

%define api.pure
%locations
%lex-param {yyscan_t scanner}
%parse-param {yyscan_t scanner}
%parse-param {int offset}

%union {
	char *string;
	int   num;
}


%token <string> IDENT
%token <num>   NUMBER
%token COMMA LBRACKET RBRACKET TWO_POINTS INT_TYPE REAL_TYPE STRING_TYPE ARRAY OF COLON VAR RECORD END SEMICOLON

%type <string> type simple_type array_type record_type index_range var_decls var_decl ident_rule idents
%start var_decl_part

%{
int yylex(YYSTYPE *yylval_param, YYLTYPE *yylloc_param , yyscan_t scanner);
void yyerror(YYLTYPE *yylloc, yyscan_t scanner, int offset, char *msg);
%}

%%

var_decl_part:	VAR  var_decls                  { printf("var\n%s\n", $2); }
								;

inc: {++offset;}
dec: {--offset;}

var_decls: 
							  var_decls SEMICOLON											{
							    																				printf("1\n");
							    																				$$ = loc_char_mem(strlen($1) + 1); 
							    																				sprintf($$, "%s;", $1);
							    																				printf("2\n");
							    																			}
								| var_decls SEMICOLON inc var_decl dec  {
																													printf("3\n");
																													$$ = loc_char_mem(strlen($1) + 2 + strlen($4)); 
																													sprintf($$, "%s;\n%s", $1, $4);
																													printf("4\n");
																												}
								| inc var_decl  dec                     {
																													$$ = $2;
																													printf("5\n");
																												}
								;

var_decl:				idents COLON type     					{ 
																									printf("C\n");
																									char* empty = print_offset(offset);
																		 						 	$$ = loc_char_mem(strlen(empty) + strlen($1) + 3 + strlen($3)+1);
																		 						 	printf("P\n");
																		 						 	sprintf($$, "%s%s: %s", empty, $1, $3);
																		 						 	printf("B\n");
																		 						 	free(empty);
																								}
								;

ident_rule:     IDENT                           {
																									printf("ident\n");
																									$$ = loc_char_mem(strlen($1));
																									sprintf($$, "%s", $1);
																								}
								;

idents:					ident_rule 											{
																									printf("IDENT2\n");
																									$$ = loc_char_mem(strlen($1));
																									sprintf($$, "%s", $1);
																								}
								| ident_rule COMMA idents       {
																									printf("idents\n");
																									$$ = loc_char_mem(strlen($1) + 1 + 1 + strlen($3));
																								 	sprintf($$, "%s, %s", $1, $3);
																								}
								;

type:       		simple_type                     { $$ = $1; }
								| array_type										{ $$ = $1; }
								| record_type                   { $$ = $1; }
								;

simple_type: 		INT_TYPE                        {
																									printf("int");
																									$$ = loc_char_mem(strlen("integer"));
																									sprintf($$, "integer");
																								}
								| REAL_TYPE											{
																									$$ = loc_char_mem(strlen("real"));
																									sprintf($$, "real");
																								}
								| STRING_TYPE										{
																									$$ = loc_char_mem(strlen("string"));
																									sprintf($$, "string");
																								}
								;

array_type: 		ARRAY LBRACKET index_range RBRACKET OF type 			 	{ 
																																			$$ = loc_char_mem(5 + 1 + 1 + strlen($3) + 1 + 1 + 2 + 1 + strlen($6));
																																		 	sprintf($$, "array [%s] of %s", $3, $6);
																																		}
																																		;

index_range: 		NUMBER TWO_POINTS NUMBER														{ 
																																			$$ = (char*)malloc(sizeof(int) * 2 + 2 * sizeof(char) + 1);
																																			sprintf($$, "%i..%i", $1, $3);
																																		}
																																		;

record_type:		RECORD var_decls END                       					{
																																			char* empty = print_offset(offset);
																																			$$ = loc_char_mem(strlen(empty) + 6 + 1 + strlen($2) + 4);
																																			sprintf($$, "record\n%s\n%send", $2, empty);
																																			free(empty);
																																		}
																																		;

%%

int main()
{
	  char * buffer = 0;
    long length;
    FILE * f = fopen ("program.txt", "rb");
 
    if (f) {
    	fseek (f, 0, SEEK_END);
    	length = ftell (f);
    	fseek (f, 0, SEEK_SET);
    	buffer = malloc (length);
    	if (buffer)
    		fread (buffer, 1, length, f);
      fclose (f);
   	}


	yyscan_t scanner;
	struct Extra extra;
	init_scanner(buffer, &scanner, &extra);
	yyparse(scanner, 0);
	destroy_scanner(scanner);
	return 0;
}


