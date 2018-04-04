%option noyywrap bison-bridge bison-locations

%{

#include <stdio.h>
#include <stdlib.h>

#define TAG_EOF     0
#define TAG_IDENT   1
#define TAG_NUMBER  2
#define TAG_KEYWORD 3
#define TAG_ERROR   4

char *tag_names[] = {
    "END_OF_PROGRAM", "IDENT", "NUMBER", "KEYWORD", "ERROR"
};

struct Position {
    int line, pos, index;
};


void print_pos(struct Position *p) {
    printf("(%d, %d)", p->line, p->pos);
}

struct Fragment {
    struct Position starting, following;
};

typedef struct Fragment YYLTYPE;

void print_frag(struct Fragment *f) {
    print_pos(&(f->starting));
    printf(" - ");
    print_pos(&(f->following));
}

union Token {
    char *tokenName;
};

typedef union Token YYSTYPE;

int continued;
struct Position cur;

#define YY_USER_ACTION                  \
{                                       \
    int i;                              \
    if (!continued)                     \
        yylloc->starting = cur;         \
    continued = 0;                      \
    for (i = 0; i < yyleng; i++) {      \
        if ('\n' == yytext[i]) {        \
            cur.line++;                 \
            cur.pos = 1;                \
        }                               \
        else                            \
            cur.pos++;                  \
        cur.index++;                    \
    }                                   \
    yylloc->following = cur;            \
}                                       \

//void init_scanner(char *program) {
//    continued = 0;
//    cur.line = 1;
//    cur.pos = 1;
//    cur.index = 0;
//    yy_scan_string(program);
//}

void init_scanner(FILE * program) {
	char * buffer = 0;
	long length;
	fseek(program, 0, SEEK_END);
	length = ftell(program);
	fseek(program, 0, SEEK_SET);
	buffer = malloc(length);
	fread(buffer, 1, length, program);
  continued = 0;
  cur.line = 1;
  cur.pos = 1;
  cur.index = 0;
  yy_scan_string(buffer);
}

void err(char *msg) {
    printf("Error ");
    print_pos(&cur);
    printf(": %s\n", msg);
}

%}

IDENT   [a-zA-Z][a-zA-Z0-9]*
NUMBER  [0-9]+|[0-9][0-9A-Fa-f]+h
KEYWORD mov|eax


%%

[\n\t ]+

{KEYWORD}  {
		       yylval->tokenName = yytext;
		       return TAG_KEYWORD;
	        }

{IDENT}    {
               yylval->tokenName = yytext;
               return TAG_IDENT;
           }

{NUMBER}   {
               yylval->tokenName = yytext;
               return TAG_NUMBER;
           }

<<EOF>>    return TAG_EOF;

.          {
		err("Unexpected character");
		return TAG_ERROR;
	   }

%%


#define PROGRAM "123 ident321 0123AAAh mov eax @@"

int main(int argc, char *argv[])
{
    int tag;
    YYSTYPE value;
    YYLTYPE coords;

    //FILE * f = fopen("/home/trishlex/program.txt", "rb");

    FILE * f = fopen(argv[1], "rb");

    //init_scanner(PROGRAM);

    init_scanner(f);

    do {
        tag = yylex(&value, &coords);
        if (0 != tag && TAG_ERROR != tag) {
          printf("%s ", tag_names[tag]);
            print_frag(&coords);
            printf(": %s\n", value.tokenName);
        }
    } while (0 != tag);

    return 0;
}
