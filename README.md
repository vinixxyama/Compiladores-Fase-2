# Compiladores-Fase-2

# 1 Problema
Nesta fase será realizada a análise léxica, sintática e semântica para a linguagem descrita pela
gramática da Seção 2. Esta gramática está parcialmente representada e será completada
na próxima etapa do trabalho, podendo sofrer alterações em certas regras de produção.

# 2 Gramática
Essa seção define a gramática a ser implementada. As palavras reservadas e símbolos da
linguagem são exibidos entre aspas simples. Elementos da notação da gramática:<br />
• Uma sequência de símbolos entre { e } pode ser repetida zero ou mais vezes;_
• Uma sequência de símbolos entre [ e ] é opcional;
• Regras de produção alternativas são separadas por |.
• Comentários devem ser iniciados por ’#’ e terminam com ’\n’.
• Palavras reservadas não podem ser utilizadas como nomes de identificadores.
• . indica qualquer caracter.
• ^ representa a operação de potenciação.
• Esta linguagem é case sensitive.

# GRAMATICA
Program ::= ’program’ Name ’:’ Body ’end’
Name ::= Letter{Letter | Digit}
Letter ::= ’a’ | ... | ’z’ | ’A’ | ... | ’Z’
Signal ::= ’+’ | ’-’
Digit ::= ’0’ | ... | ’9’
Body ::= [Declaration] {Stmt}
Declaration ::= Type IdList {’;’ Type IdList} ’;’
Type ::= ’int’ | ’float’ | ’string’ | ’boolean’
IdList ::= Name [ ‘[’Number‘]’ ] {’,’ Name [ ‘[’Number‘]’ ]}
Stmt ::= SimpleStmt | CompoundStmt
SimpleStmt ::= ExprStmt | PrintStmt | BreakStmt
ExprStmt ::= Name[ ‘[’Number‘]’ ] ’=’ (OrTest | ’[’ ExprList ’]’) ’;’
OrTest ::= AndTest {’or’ AndTest}
AndTest ::= NotTest {’and’ NotTest}
NotTest ::= [’not’] Comparison
Comparison ::= Expr [CompOp Expr]
Expr ::= Term {(’+’ | ’-’) Term}
Term ::= Factor {(’*’ | ’/’) Factor}
Factor ::= [’+’|’-’] Atom {^ Factor}
Atom ::= Name[ ‘[’(Number | Name)‘]’ ] | Number | String | ’True’ | ’False’
Number ::= [Signal] Digit{Digit} [’.’ Digit{Digit}]
String ::= ”’ . ”’
CompOp ::= ’<’ | ’>’ | ’==’ | ’>=’ | ’<=’ | ’<>’
ExprList ::= Expr {, Expr}
PrintStmt ::= ’print’ OrTest {’,’ OrTest}’;’
BreakStmt ::= ’break’ ’;’
CompoundStmt ::= IfStmt | WhileStmt | ForStmt
IfStmt ::= ’if’ OrTest ’{’ {Stmt} ’}’ [’else’ ’{’ {Stmt} ’}’]
WhileStmt ::= ’while’ OrTest ’{’ {Stmt} ’}’
ForStmt ::= ’for’ Name ’inrange’ ’(’ Number ’,’ Number ’)’ ’{’ {Stmt} ’}’

# 3 Detalhes da linguagem

• Loop for : inrange(x, y) possui incremento de +1 quando x < y e decremento de −1
quando x > y. Exemplo de acordo com a gramática e equivalência em c:
for x inrange(0, 3) → for(x = 0; x < 3; x + +)
for x inrange(3, 0) → for(x = 3; x > 0; x − −)
• Arrays válidos na linguagem:
int a[3]; a = [1, 2, 3];
int b[3]; b[0] = 1;

# 4 Análise Léxica

O compilador produzido nesta fase deve ser capaz de analisar tokens mais complexos, por
exemplo, a palavra reservada ‘print’, ao invés de ‘R’. Para isso é importante atenção com as
palavras reservadas que devem ser identificadas pelo seu analisador léxico. São elas:
and boolean break elif
else end False float
for if in inrange
int not notin or
print program string True
while
Os identificadores são compostos por um conjunto de letras e números iniciando com
uma letra. Exemplos de identificadores válidos: var1, x213, myVar, Num. Exemplos de
identificadores inválidos: 123a, var#, Var$, num_1. Outrossim, palavras reservadas são
inválidas como nome de identificadores.

# 5 Análise Semântica

• Equivalência entre tipos: Cada variável deve estar associada a um tipo básico específico
e só deve receber valores deste tipo. Não deve ser permitido que uma variável do tipo
int receba um valor do tipo float ou que um vetor seja atribuído a uma variável simples.
• Atribuição: Os dois lados da atribuição devem ter o mesmo tipo. Sintaticamente
é possível uma atribuição do tipo: var = ‘string’ ˆ 2; porém esta é uma expressão
inválida semanticamente e deve gerar um erro. Outra regra possível sintaticamente é
possuir um break fora de um loop, isto também deve gerar um erro semântico.
• Variáveis: Devem ser declaradas antes de serem usadas no código e, por ser uma
linguagem case sensitive, deve haver diferenciação entre maiúsculas e minúsculas.
• Vetores: Possuem tamanho definido na declaração e, apesar de sintaticamente possível,
números negativos devem ser proibidos.
• For loop: Deve aceitar somente números inteiros no inrange, mesmo sendo sintaticamente
possível o emprego de valores reais.

# 6 Mensagens de Erro

As mensagens de erro devem ser escritas de forma a identificar o erro identificado pelo
programa. Deve obedecer o seguinte formato:
<Nome do arquivo>, <Linha com erro>, <Mensagem>\n
Exemplo:
"erroSintatico3.in, 2, Falta o nome do programa.\n¨
Favor seguir o padrão descrito, pois a correção será automatizada. A mensagem
de erro deve ser sucinta, porém o formato fica a critério da dupla.