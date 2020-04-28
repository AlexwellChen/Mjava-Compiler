# Mjava-Compiler

Lexier and parser

### Lexical

* 语言的关键字（19个):

class, public, static, void, main, String, extends, return, int, boolean, if, else, while, System.out.println , length, true, false, this, new

注：关键字是保留字，并且区分大小写。

* 语言的专用符号16个：

| [ | ] | ( | ) | { | } | , | ; | = | && | < | + |  - | * | . | ! |

* 标示符Identifier和整数IntegerLitera的词法规则通过下列正则表达式定义：

Identifier = identifier_letter (underline?letter_or_digit)*

identifier_letter=a|..|z|A|..|Z|

digit = 0|..|9

underline=_

letter_or_digit = identifier_letter | digit

IntegerLiteral = digit (digit)*



### Grammer

Goal-> MainClass { ClassDeclaration } EOF



MainClass->**"class"** Identifier **"{"** **"public"** **"static"** **"void"** **"main"** **"("** **"String"** **"["** **"]"** Identifier **")"** **"{"** Statement **"}"** **"}"**



ClassDeclaration->**"class"** Identifier [ **"extends"** Identifier ] **"{"** { VarDeclaration } { MethodDeclaration } **"}"**



VarDeclaration ->Type Identifier **";"**



MethodDeclaration->**"public"** Type Identifier **"("** [ Type Identifier { **","** Type Identifier } ] **")"** **"{"** { VarDeclaration } { Statement } **"return"** Expression **";"** **"}"**



Type->**"int"** **"["** **"]"**|**"boolean"**|**"int"**| Identifier



Statement->**"{"** { Statement } **"}"**|**"if"** **"("** Expression **")"** Statement **"else"** Statement |**"while"** **"("** Expression **")"** Statement |**"System.out.println"** **"("** Expression **")"** **";"**| Identifier **"="** Expression **";"**| Identifier **"["** Expression **"]"** **"="** Expression **";"**



Expression-> Expression ( "&&" | "<" | "+" | "-" | "*" ) Expression | Expression **"["** Expression **"]"**| Expression **"."** **"length"**| Expression **"."** Identifier **"("** [ Expression { **","** Expression } ] **")"**| IntegerLiteral |**"true"**|**"false"**| Identifier |**"this"**|**"new"** **"int"** **"["** Expression **"]"**|**"new"** Identifier "(" ")"| **"!"** Expression |**"("** Expression **")"**