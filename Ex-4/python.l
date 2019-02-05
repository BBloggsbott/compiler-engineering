%{
#include<stdio.h>
%}
%%
(from" "([a-zA-Z]+.?)+" ")?import" "[a-zA-Z]+ printf("Import statement: %s\n", yytext);
as" "[a-zA-Z]+   printf("Alias: %s\n", yytext);
$\t(\t)*  printf("Inside block: %s\n", yytext);
(in)|(del)|(lambda)|(as)   printf("Keyword: %s\n", yytext);
class printf("Class definition: %s\n", yytext);
def printf("Function Definition: %s\n", yytext);
(if)|(for)|(else)|(while)|(elif)   printf("Control statement: %s\n", yytext);
"("    printf("Bracket starts: %s\n", yytext);
")"    printf("Bracket ends: %s\n", yytext);
["[""\]"]    printf("List or slicing: %s\n", yytext);
["+""-""//""**"".""/""*"] printf("Operator: %s\n", yytext);
"," printf("Comma: %s\n", yytext);
=   printf("Assignment: %s\n", yytext);
[a-zA-Z]([a-zA-Z]|[0-9])*   printf("Object or function: %s\n", yytext);
[0-9]+  printf("Number: %s\n", yytext);
";" printf("Statement Ends: %s\n", yytext);
":" printf("Block starts or Slicing: %s\n", yytext);
%%
int yywrap(void){}

int main(){
	yylex();
	return 0;
}