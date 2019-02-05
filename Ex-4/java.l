%{
#include<stdio.h>
%}
%%
import" "[a-z]+ printf("Import statement: %s\n", yytext);
import" "[A-Z][a-zA-Z]* printf("Invalid import statement: %s\n", yytext);
(public)|(private)|(default)|(static)   printf("Keywords: %s\n", yytext);
class printf("Class definition: %s\n", yytext);
(int)|(double)|(float)|(long) printf("Number based data types: %s\n", yytext);
(char)|(String) printf("Character based data type: %s\n", yytext);
(if)|(for)|(else)|(while)   printf("Control statements: %s\n", yytext);
"{"    printf("Block Starts: %s\n", yytext);
"}"    printf("Block Ends: %s\n", yytext);
"("    printf("Brackets starts: %s\n", yytext);
")"    printf("Brackets end: %s\n", yytext);
["[""\]"]    printf("Array: %s\n", yytext);
["+""-""/""*""."] printf("Operators: %s\n", yytext);
=   printf("Assignment: %s\n", yytext);
[a-zA-Z]([a-zA-Z]|[0-9])*   printf("Data member or Method or Class: %s\n", yytext);
[0-9]+  printf("Number: %s\n", yytext);
";" printf("End of statement: %s\n", yytext);
%%
int yywrap(void){}

int main(){
	yylex();
	return 0;
}