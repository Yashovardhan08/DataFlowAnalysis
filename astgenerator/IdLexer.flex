import java_cup.runtime.Symbol;
import java.io.FileInputStream;
import java.io.InputStream;

%%

%class IdLexer
%line
%column

%cup
%{

%}

//%type Symbol
digit = [0-9]
letter = [a-zA-Z]
id = {letter}({letter}|{digit})*
num = {digit}+
type = "int" | "bool" | "void"
boolconst = "true" | "false"
LineTerminator = [\r|\n|\r\n]
InputCharacter = [^\r\n]
whitespace     = {LineTerminator} | [ \t\f]
%%

{type} {
  if(yytext().equals("int")) {
    return new Symbol(sym.TYPE, yyline, yycolumn, new IntType());
	}
	else if(yytext().equals("bool")) {
    return new Symbol(sym.TYPE, yyline, yycolumn, new BoolType());
	}
  else if(yytext().equals("void")) {
    return new Symbol(sym.TYPE,yyline,yycolumn, new VoidType());
  }

}
{boolconst} { return new Symbol(sym.BOOLCONST, yyline, yycolumn, Boolean.parseBoolean(yytext())); }
"if" { return new Symbol(sym.IF, yyline, yycolumn, null); }
"else" { return new Symbol(sym.ELSE, yyline, yycolumn, null); }
"while" { return new Symbol(sym.WHILE, yyline, yycolumn ,null); }
"return" { return new Symbol(sym.RETURN, yyline, yycolumn ,null); }
{id} { return new Symbol(sym.ID, yyline, yycolumn, yytext()); }
{num} { return new Symbol(sym.NUMBER, yyline, yycolumn, Integer.parseInt(yytext())); }
"%" { return new Symbol(sym.MOD, yyline, yycolumn, null); }
";" { return new Symbol(sym.SEMI, yyline, yycolumn, null); }
"==" { return new Symbol(sym.EEQUALS, yyline, yycolumn, null); }
"<=" { return new Symbol(sym.LEQUALS, yyline, yycolumn, null); }
">=" { return new Symbol(sym.GEQUALS, yyline, yycolumn, null); }
">" { return new Symbol(sym.GREATER, yyline, yycolumn, null); }
"<" { return new Symbol(sym.LESSER, yyline, yycolumn, null); }
"=" { return new Symbol(sym.EQUALS, yyline, yycolumn, null); }
"+" { return new Symbol(sym.PLUS, yyline, yycolumn, null); }
"-" { return new Symbol(sym.MINUS, yyline, yycolumn, null); }
"*" { return new Symbol(sym.TIMES, yyline, yycolumn, null); }
"{" { return new Symbol(sym.LBRACE, yyline, yycolumn, null); }
"}" { return new Symbol(sym.RBRACE, yyline, yycolumn, null); }
"(" { return new Symbol(sym.LPAREN, yyline, yycolumn, null); }
")" { return new Symbol(sym.RPAREN, yyline, yycolumn, null); }
"," { return new Symbol(sym.COMMA, yyline, yycolumn, null); }
{whitespace}+ { /* skip white spaces */ }
[^]              { throw new Error("Illegal character <"+yytext()+">"); }
