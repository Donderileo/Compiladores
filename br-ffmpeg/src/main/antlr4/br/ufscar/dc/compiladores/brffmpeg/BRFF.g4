grammar BRFF;

WS: ( ' ' | '\t' | '\r' | '\n' ) -> skip;
/* Aqui definimos todos os comandos */
ENTRADA: 'ENTRADA';
SAIDA: 'SAIDA';
CORTAR: 'CORTAR';
ACELERAR: 'ACELERAR'; 
LEGENDA: 'LEGENDA';
INICIO: 'INICIO' ;
FIM: 'FIM';
VELOCIDADE: 'VELOCIDADE';
AUDIO: 'AUDIO';
TEMPO: NUM_INT ':' NUM_INT ':' NUM_INT;
SEPARADOR: ':';

IDENT: ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;
CADEIA: '\'' (~('"'|'\n'))* '\'';
NUM_INT: ('0'..'9')+;
NUM_REAL: ('0'..'9')+ '.' ('0'..'9')+;
COMENTARIO: '//' ( ~('\n'|'\r') )*? ('\n'|'\r') -> skip;
CADEIA_NAO_FECHADA: ('\'') ~('\'')*? ('\n'|'\r');
ERRO: .; 

/* Sintatico */

programa: in out modify+ EOF;
in: cmd=ENTRADA':' path=CADEIA;
out: cmd=SAIDA':' path=CADEIA;
modify: ( cut | speed | subtitle);

cut: CORTAR':' corpo_cortar;
corpo_cortar: INICIO start_time=TEMPO FIM finish_time=TEMPO;

speed: ACELERAR':' corpo_acelerar;
corpo_acelerar: VELOCIDADE vel=NUM_INT AUDIO option=('Y' | 'N');

subtitle: LEGENDA':' path=CADEIA;