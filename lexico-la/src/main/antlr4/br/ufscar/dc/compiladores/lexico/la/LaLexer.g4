lexer grammar LaLexer;

/* 
Definição da gramática: Mais específico para mais gerais 
Evita-se fazer termos gerais que serão tratados posteriormente,
Aqui todas as divisões foram feitas até a forma mínima, sem generalizações.


*/


/* Palavras Reservadas - Funções*/ 

INI_ALGORITMO: 'algoritmo';
FIM_ALGORITMO: 'fim_algoritmo';
ATE: 'ate';
ESCREVA: 'escreva';
LEIA: 'leia';
FACA: 'faca';
SEJA: 'seja';
LOGICO: 'logico';
RETORNE: 'retorne';
ENTAO: 'entao';
ENQUANTO: 'enquanto';
FIM_ENQUANTO: 'fim_enquanto';
PARA: 'para';
FIM_PARA: 'fim_para';
CASO: 'caso';
FIM_CASO: 'fim_caso';
SE: 'se';
SENAO: 'senao';
FIM_SE: 'fim_se';
FUNCAO: 'funcao';
FIM_FUNCAO: 'fim_funcao';
REGISTRO: 'registro';
FIM_REGISTRO: 'fim_registro';
PROCEDIMENTO: 'procedimento';
FIM_PROCEDIMENTO: 'fim_procedimento';


/* Operadores Lógicos */
E: 'e';
OU: 'ou';
NAO: 'nao';
VERDADEIRO: 'verdadeiro';
FALSO: 'falso';
IGUAL: '=';
DIF: '<>';
MENOR: '<';
MENOR_IGUAL: '<=';
MAIOR: '>';
MAIOR_IGUAL: '>=';
ATRIBUICAO: '<-';

/* Declarações e Tipos */
DECLARE: 'declare';
TIPO: 'tipo';
VAR: 'var';
LITERAL: 'literal';
CONSTANTE: 'constante';
INTEIRO: 'inteiro';
REAL: 'real';

/* Operadores Matemáticos */
SOM: '+';
SUB: '-';
DIV: '/';
RES: '%';
MUL: '*';
ELEVADO: '^';
ECOMERCIAL: '&';


/* Caracteres especiais */
PONTO: '.';
INTERVALO: '..';
DOIS_PONTOS: ':';
VIRGULA: ',';

ABREPAR: '(';
FECHAPAR: ')';
ABRECOL: '[';
FECHACOL: ']';

/* Gerais */
IDENT: ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;
CADEIA: '"' (~('"'|'\n'))* '"';

NUM_INT: ('0'..'9')+;
NUM_REAL: ('0'..'9')+ '.' ('0'..'9')+;


/* Não devem gerar token */
COMENTARIO: '{' ~('\n'|'}')* '}' -> skip;
WS: ( ' ' | '\t' | '\r' | '\n' ) -> skip;

/* Erros que devem ser identificados (casos de teste) */
COMENTARIO_NAO_FECHADO: '{' .;
CADEIA_NAO_FECHADA: '"' (~('"'))* '\n';
ERRO: .; 