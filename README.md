# Compiladores-Fase-2

# 1 Problema
Nesta fase será realizada a análise léxica, sintática e semântica para a linguagem descrita pela
gramática da Seção 2. Esta gramática está parcialmente representada e será completada
na próxima etapa do trabalho, podendo sofrer alterações em certas regras de produção.

# 2 Gramática
Essa seção define a gramática a ser implementada. As palavras reservadas e símbolos da
linguagem são exibidos entre aspas simples. Elementos da notação da gramática:<br />
• Uma sequência de símbolos entre { e } pode ser repetida zero ou mais vezes;<br />
• Uma sequência de símbolos entre [ e ] é opcional;<br />
• Regras de produção alternativas são separadas por |.<br />
• Comentários devem ser iniciados por ’#’ e terminam com ’\n’.<br />
• Palavras reservadas não podem ser utilizadas como nomes de identificadores.<br />
• . indica qualquer caracter.<br />
• ^ representa a operação de potenciação.<br />
• Esta linguagem é case sensitive.<br />

# GRAMATICA
Program ::= ’program’ Name ’:’ Body ’end’<br />
Name ::= Letter{Letter | Digit}<br />
Letter ::= ’a’ | ... | ’z’ | ’A’ | ... | ’Z’<br />
Signal ::= ’+’ | ’-’<br />
Digit ::= ’0’ | ... | ’9’<br />
Body ::= [Declaration] {Stmt}<br />
Declaration ::= Type IdList {’;’ Type IdList} ’;’<br />
Type ::= ’int’ | ’float’ | ’string’ | ’boolean’<br />
IdList ::= Name [ ‘[’Number‘]’ ] {’,’ Name [ ‘[’Number‘]’ ]}<br />
Stmt ::= SimpleStmt | CompoundStmt<br />
SimpleStmt ::= ExprStmt | PrintStmt | BreakStmt<br />
ExprStmt ::= Name[ ‘[’Number‘]’ ] ’=’ (OrTest | ’[’ ExprList ’]’) ’;’<br />
OrTest ::= AndTest {’or’ AndTest}<br />
AndTest ::= NotTest {’and’ NotTest}<br />
NotTest ::= [’not’] Comparison<br />
Comparison ::= Expr [CompOp Expr]<br />
Expr ::= Term {(’+’ | ’-’) Term}<br />
Term ::= Factor {(’*’ | ’/’) Factor}<br />
Factor ::= [’+’|’-’] Atom {^ Factor}<br />
Atom ::= Name[ ‘[’(Number | Name)‘]’ ] | Number | String | ’True’ | ’False’<br />
Number ::= [Signal] Digit{Digit} [’.’ Digit{Digit}]<br />
String ::= ”’ . ”’<br />
CompOp ::= ’<’ | ’>’ | ’==’ | ’>=’ | ’<=’ | ’<>’<br />
ExprList ::= Expr {, Expr}<br />
PrintStmt ::= ’print’ OrTest {’,’ OrTest}’;’<br />
BreakStmt ::= ’break’ ’;’<br />
CompoundStmt ::= IfStmt | WhileStmt | ForStmt<br />
IfStmt ::= ’if’ OrTest ’{’ {Stmt} ’}’ [’else’ ’{’ {Stmt} ’}’]<br />
WhileStmt ::= ’while’ OrTest ’{’ {Stmt} ’}’<br />
ForStmt ::= ’for’ Name ’inrange’ ’(’ Number ’,’ Number ’)’ ’{’ {Stmt} ’}’<br />

# 3 Detalhes da linguagem

• Loop for : inrange(x, y) possui incremento de +1 quando x < y e decremento de −1 quando x > y. Exemplo de acordo com a gramática e quivalência em c:<br />
for x inrange(0, 3) → for(x = 0; x < 3; x + +)<br />
for x inrange(3, 0) → for(x = 3; x > 0; x − −)<br />
• Arrays válidos na linguagem:<br />
int a[3]; a = [1, 2, 3];<br />
int b[3]; b[0] = 1;<br />

# 4 Análise Léxica

O compilador produzido nesta fase deve ser capaz de analisar tokens mais complexos, por
exemplo, a palavra reservada ‘print’, ao invés de ‘R’. Para isso é importante atenção com as
palavras reservadas que devem ser identificadas pelo seu analisador léxico. São elas:<br />
and boolean break elif
else end False float
for if in inrange
int not notin or
print program string True
while<br />
Os identificadores são compostos por um conjunto de letras e números iniciando com
uma letra. Exemplos de identificadores válidos: var1, x213, myVar, Num. Exemplos de
identificadores inválidos: 123a, var#, Var$, num_1. Outrossim, palavras reservadas são
inválidas como nome de identificadores.

# 5 Análise Semântica

• Equivalência entre tipos: Cada variável deve estar associada a um tipo básico específico
e só deve receber valores deste tipo. Não deve ser permitido que uma variável do tipo
int receba um valor do tipo float ou que um vetor seja atribuído a uma variável simples.<br />
• Atribuição: Os dois lados da atribuição devem ter o mesmo tipo. Sintaticamente
é possível uma atribuição do tipo: var = ‘string’ ˆ 2; porém esta é uma expressão
inválida semanticamente e deve gerar um erro. Outra regra possível sintaticamente é
possuir um break fora de um loop, isto também deve gerar um erro semântico.<br />
• Variáveis: Devem ser declaradas antes de serem usadas no código e, por ser uma
linguagem case sensitive, deve haver diferenciação entre maiúsculas e minúsculas.<br />
• Vetores: Possuem tamanho definido na declaração e, apesar de sintaticamente possível,
números negativos devem ser proibidos.<br />
• For loop: Deve aceitar somente números inteiros no inrange, mesmo sendo sintaticamente
possível o emprego de valores reais.<br />

# 6 Mensagens de Erro

As mensagens de erro devem ser escritas de forma a identificar o erro identificado pelo
programa. Deve obedecer o seguinte formato:<br />
<Nome do arquivo>, <Linha com erro>, <Mensagem>\n<br />
Exemplo:<br />
"erroSintatico3.in, 2, Falta o nome do programa.\n¨<br />
Favor seguir o padrão descrito, pois a correção será automatizada. A mensagem
de erro deve ser sucinta, porém o formato fica a critério da dupla.