
%option reentrant noyywrap bison-bridge bison-locations
%option extra-type="struct Extra *"

%{
 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lexer.h"
#include "parser.tab.h"

#define YY_USER_ACTION                                      \
    {                                                       \
        int i;                                              \
        struct Extra *extra = yyextra;                      \
        if (! extra->continued) {                           \
            yylloc->first_line = extra->cur_line;           \
            yylloc->first_column = extra->cur_column;       \
        }                                                   \
        extra->continued = 0;                               \
        for (i = 0; i < yyleng; i++)                        \
        {                                                   \
            if (yytext[i] == '\n')                          \
            {                                               \
                extra->cur_line++;                          \
                extra->cur_column = 1;                      \
            }                                               \
            else                                            \
                extra->cur_column++;                        \
            }                                               \
                                                            \
        yylloc->last_line = extra->cur_line;                \
        yylloc->last_column = extra->cur_column;            \
    }

void yyerror(YYLTYPE *loc, yyscan_t scanner, char *msg)
{
    printf("Error (%d,%d): %s\n", loc->first_line, loc->first_column, msg);
}

%}

IDENT 		[a-zA-Z][a-zA-Z0-9]*
NUMBER    [0-9]+

%%

[\n\t ]+

var       return VAR;
integer 	return INT_TYPE;
real      return REAL_TYPE;
string    return STRING_TYPE;
array     return ARRAY;
of        return OF;
record    return RECORD;
end       return END;
\,        return COMMA;
\;        return SEMICOLON;
\[        return LBRACKET;
\]        return RBRACKET;
\.\.       return TWO_POINTS;
\:        return COLON;

{IDENT} 	{
						yylval->string = yytext;
						return IDENT;
					}

{NUMBER}  {
						yylval->num = atoi(yytext);
						return NUMBER;
					}

%%

void init_scanner(char *program, yyscan_t *scanner, struct Extra *extra)
{
    extra->continued = 0;
    extra->cur_line = 1;
    extra->cur_column = 1;
    yylex_init(scanner);
    yylex_init_extra(extra, scanner);
    yy_scan_string(program, *scanner);
}
 
void destroy_scanner(yyscan_t scanner)
{
    yylex_destroy(scanner);
}
