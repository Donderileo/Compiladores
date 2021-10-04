
grammar La;

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

/* ------------------ Sintatico --------------------- */

/* Adicionado EOF para evitar erros de continuidade */
programa: declaracoes 'algoritmo' corpo 'fim_algoritmo';

declaracoes: decl_local_global*;

decl_local_global: declaracao_local | declaracao_global;

declaracao_local: 'declare' variavel 
                | 'constante' identConstante=IDENT ':' tipo_basico '=' valor_constante
                | 'tipo' identTipo=IDENT ':' tipo;

variavel: identificador (',' identificador)* ':' tipo;

identificador: ident1=IDENT ('.' ident2+=IDENT)* dimensao;

dimensao: ('[' exp_aritmetica ']')*;

tipo: registro | tipo_estendido;

tipo_basico: 'literal' | 'inteiro' | 'real' | 'logico';

tipo_basico_ident: tipo_basico | IDENT;

tipo_estendido: ponteiro='^'? tipo_basico_ident;

valor_constante: CADEIA | NUM_INT | NUM_REAL | 'verdadeiro' | 'falso';

registro: 'registro' variavel* 'fim_registro';

declaracao_global: 'procedimento' IDENT '(' parametros? ')' declaracao_local* 
                    cmd* 'fim_procedimento'
                |   'funcao' IDENT '(' parametros? ')' ':' tipo_estendido declaracao_local*
                    cmd* 'fim_funcao';

parametro: 'var'? identificador (',' identificador)* ':' tipo_estendido;

parametros: parametro (',' parametro)*;

corpo: declaracao_local* cmd*;

cmd: cmdLeia | cmdEscreva | cmdSe | cmdCaso | cmdPara | cmdEnquanto
    | cmdFaca | cmdAtribuicao | cmdChamada | cmdRetorne;

cmdLeia: 'leia' '(' '^'? identificador (',' '^'? identificador)* ')';

cmdEscreva: 'escreva' '(' expressao (',' expressao)* ')';

cmdSe: 'se' expressao 'entao' cmdIf+=cmd* ('senao' cmdElse+=cmd*)? 'fim_se';

cmdCaso: 'caso' exp_aritmetica 'seja' selecao ('senao' cmd*)? 'fim_caso';

cmdPara: 'para' IDENT '<-' exp_aritmetica 'ate' exp_aritmetica 'faca' cmd* 'fim_para';

cmdEnquanto: 'enquanto' expressao 'faca' cmd* 'fim_enquanto';

cmdFaca: 'faca' cmd* 'ate' expressao;

cmdAtribuicao: '^'? identificador '<-' expressao;

cmdChamada: IDENT '(' expressao (',' expressao)* ')';

cmdRetorne: 'retorne' expressao;

selecao: item_selecao*;

item_selecao: constantes ':' cmd*;

constantes: constantesIntervalo=numero_intervalo (',' constantesIntervalo2=numero_intervalo)*;

numero_intervalo: op_unario? intervaloInicio=NUM_INT ('..' op_unario? intervaloFim=NUM_INT)?;

op_unario: '-';

exp_aritmetica: termo (op1 termo)*;

termo: fator (op2 fator)*;

fator: parcela (op3 parcela)*;

op1: '+' | '-';

op2: '*' | '/';

op3: '%';

parcela: op_unario? parcela_unario | parcela_nao_unario;

parcela_unario: '^'? identificador
                | IDENT '(' expressao (',' expressao)* ')'
                | NUM_INT
                | NUM_REAL
                | '(' parcelaUnarioExp=expressao ')';

parcela_nao_unario: '&' identificador | CADEIA;

exp_relacional: exp_aritmetica (op_relacional exp_aritmetica)?;

op_relacional: '=' | '<>' | '>=' | '<=' | '>' | '<';

expressao: termo_logico (op_logico_1 termo_logico)*;

termo_logico: fator_logico (op_logico_2 fator_logico)*;

fator_logico: 'nao'? parcela_logica;

parcela_logica: ('verdadeiro' | 'falso')
                | exp_relacional;

op_logico_1: 'ou';

op_logico_2: 'e';