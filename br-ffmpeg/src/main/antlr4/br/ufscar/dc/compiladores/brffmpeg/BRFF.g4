grammar BRFF;

WS: ( ' ' | '\t' | '\r' | '\n' ) -> skip;

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

programa: in out modify* EOF;
in: ENTRADA':' CADEIA;
out: SAIDA':' CADEIA;
modify: ( cut | speed | subtitle);

cut: CORTAR':' corpo_cortar;
corpo_cortar: INICIO TEMPO FIM TEMPO;

speed: ACELERAR':' corpo_acelerar;
corpo_acelerar: VELOCIDADE NUM_INT AUDIO ('Y' | 'N');

subtitle: LEGENDA':' CADEIA;