// Generated from br\u005Cufscar\dc\compiladores\la\semantico\La.g4 by ANTLR 4.9.1
package br.ufscar.dc.compiladores.la.semantico;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INI_ALGORITMO=1, FIM_ALGORITMO=2, ATE=3, ESCREVA=4, LEIA=5, FACA=6, SEJA=7, 
		LOGICO=8, RETORNE=9, ENTAO=10, ENQUANTO=11, FIM_ENQUANTO=12, PARA=13, 
		FIM_PARA=14, CASO=15, FIM_CASO=16, SE=17, SENAO=18, FIM_SE=19, FUNCAO=20, 
		FIM_FUNCAO=21, REGISTRO=22, FIM_REGISTRO=23, PROCEDIMENTO=24, FIM_PROCEDIMENTO=25, 
		E=26, OU=27, NAO=28, VERDADEIRO=29, FALSO=30, IGUAL=31, DIF=32, MENOR=33, 
		MENOR_IGUAL=34, MAIOR=35, MAIOR_IGUAL=36, ATRIBUICAO=37, DECLARE=38, TIPO=39, 
		VAR=40, LITERAL=41, CONSTANTE=42, INTEIRO=43, REAL=44, SOM=45, SUB=46, 
		DIV=47, RES=48, MUL=49, ELEVADO=50, ECOMERCIAL=51, PONTO=52, INTERVALO=53, 
		DOIS_PONTOS=54, VIRGULA=55, ABREPAR=56, FECHAPAR=57, ABRECOL=58, FECHACOL=59, 
		IDENT=60, CADEIA=61, NUM_INT=62, NUM_REAL=63, COMENTARIO=64, WS=65, COMENTARIO_NAO_FECHADO=66, 
		CADEIA_NAO_FECHADA=67, ERRO=68;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INI_ALGORITMO", "FIM_ALGORITMO", "ATE", "ESCREVA", "LEIA", "FACA", "SEJA", 
			"LOGICO", "RETORNE", "ENTAO", "ENQUANTO", "FIM_ENQUANTO", "PARA", "FIM_PARA", 
			"CASO", "FIM_CASO", "SE", "SENAO", "FIM_SE", "FUNCAO", "FIM_FUNCAO", 
			"REGISTRO", "FIM_REGISTRO", "PROCEDIMENTO", "FIM_PROCEDIMENTO", "E", 
			"OU", "NAO", "VERDADEIRO", "FALSO", "IGUAL", "DIF", "MENOR", "MENOR_IGUAL", 
			"MAIOR", "MAIOR_IGUAL", "ATRIBUICAO", "DECLARE", "TIPO", "VAR", "LITERAL", 
			"CONSTANTE", "INTEIRO", "REAL", "SOM", "SUB", "DIV", "RES", "MUL", "ELEVADO", 
			"ECOMERCIAL", "PONTO", "INTERVALO", "DOIS_PONTOS", "VIRGULA", "ABREPAR", 
			"FECHAPAR", "ABRECOL", "FECHACOL", "IDENT", "CADEIA", "NUM_INT", "NUM_REAL", 
			"COMENTARIO", "WS", "COMENTARIO_NAO_FECHADO", "CADEIA_NAO_FECHADA", "ERRO"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'algoritmo'", "'fim_algoritmo'", "'ate'", "'escreva'", "'leia'", 
			"'faca'", "'seja'", "'logico'", "'retorne'", "'entao'", "'enquanto'", 
			"'fim_enquanto'", "'para'", "'fim_para'", "'caso'", "'fim_caso'", "'se'", 
			"'senao'", "'fim_se'", "'funcao'", "'fim_funcao'", "'registro'", "'fim_registro'", 
			"'procedimento'", "'fim_procedimento'", "'e'", "'ou'", "'nao'", "'verdadeiro'", 
			"'falso'", "'='", "'<>'", "'<'", "'<='", "'>'", "'>='", "'<-'", "'declare'", 
			"'tipo'", "'var'", "'literal'", "'constante'", "'inteiro'", "'real'", 
			"'+'", "'-'", "'/'", "'%'", "'*'", "'^'", "'&'", "'.'", "'..'", "':'", 
			"','", "'('", "')'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INI_ALGORITMO", "FIM_ALGORITMO", "ATE", "ESCREVA", "LEIA", "FACA", 
			"SEJA", "LOGICO", "RETORNE", "ENTAO", "ENQUANTO", "FIM_ENQUANTO", "PARA", 
			"FIM_PARA", "CASO", "FIM_CASO", "SE", "SENAO", "FIM_SE", "FUNCAO", "FIM_FUNCAO", 
			"REGISTRO", "FIM_REGISTRO", "PROCEDIMENTO", "FIM_PROCEDIMENTO", "E", 
			"OU", "NAO", "VERDADEIRO", "FALSO", "IGUAL", "DIF", "MENOR", "MENOR_IGUAL", 
			"MAIOR", "MAIOR_IGUAL", "ATRIBUICAO", "DECLARE", "TIPO", "VAR", "LITERAL", 
			"CONSTANTE", "INTEIRO", "REAL", "SOM", "SUB", "DIV", "RES", "MUL", "ELEVADO", 
			"ECOMERCIAL", "PONTO", "INTERVALO", "DOIS_PONTOS", "VIRGULA", "ABREPAR", 
			"FECHAPAR", "ABRECOL", "FECHACOL", "IDENT", "CADEIA", "NUM_INT", "NUM_REAL", 
			"COMENTARIO", "WS", "COMENTARIO_NAO_FECHADO", "CADEIA_NAO_FECHADA", "ERRO"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "La.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2F\u0213\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3 \3 \3!\3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3%\3&\3&\3&\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3,\3-\3"+
		"-\3-\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3"+
		"\64\3\65\3\65\3\66\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3"+
		"=\3=\7=\u01d9\n=\f=\16=\u01dc\13=\3>\3>\7>\u01e0\n>\f>\16>\u01e3\13>\3"+
		">\3>\3?\6?\u01e8\n?\r?\16?\u01e9\3@\6@\u01ed\n@\r@\16@\u01ee\3@\3@\6@"+
		"\u01f3\n@\r@\16@\u01f4\3A\3A\7A\u01f9\nA\fA\16A\u01fc\13A\3A\3A\3A\3A"+
		"\3B\3B\3B\3B\3C\3C\3C\3D\3D\7D\u020b\nD\fD\16D\u020e\13D\3D\3D\3E\3E\2"+
		"\2F\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67"+
		"m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F\3\2\b\5\2C"+
		"\\aac|\6\2\62;C\\aac|\4\2\f\f$$\4\2\f\f\177\177\5\2\13\f\17\17\"\"\3\2"+
		"$$\2\u0219\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S"+
		"\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2"+
		"\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2"+
		"\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y"+
		"\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3"+
		"\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\3\u008b\3\2\2\2"+
		"\5\u0095\3\2\2\2\7\u00a3\3\2\2\2\t\u00a7\3\2\2\2\13\u00af\3\2\2\2\r\u00b4"+
		"\3\2\2\2\17\u00b9\3\2\2\2\21\u00be\3\2\2\2\23\u00c5\3\2\2\2\25\u00cd\3"+
		"\2\2\2\27\u00d3\3\2\2\2\31\u00dc\3\2\2\2\33\u00e9\3\2\2\2\35\u00ee\3\2"+
		"\2\2\37\u00f7\3\2\2\2!\u00fc\3\2\2\2#\u0105\3\2\2\2%\u0108\3\2\2\2\'\u010e"+
		"\3\2\2\2)\u0115\3\2\2\2+\u011c\3\2\2\2-\u0127\3\2\2\2/\u0130\3\2\2\2\61"+
		"\u013d\3\2\2\2\63\u014a\3\2\2\2\65\u015b\3\2\2\2\67\u015d\3\2\2\29\u0160"+
		"\3\2\2\2;\u0164\3\2\2\2=\u016f\3\2\2\2?\u0175\3\2\2\2A\u0177\3\2\2\2C"+
		"\u017a\3\2\2\2E\u017c\3\2\2\2G\u017f\3\2\2\2I\u0181\3\2\2\2K\u0184\3\2"+
		"\2\2M\u0187\3\2\2\2O\u018f\3\2\2\2Q\u0194\3\2\2\2S\u0198\3\2\2\2U\u01a0"+
		"\3\2\2\2W\u01aa\3\2\2\2Y\u01b2\3\2\2\2[\u01b7\3\2\2\2]\u01b9\3\2\2\2_"+
		"\u01bb\3\2\2\2a\u01bd\3\2\2\2c\u01bf\3\2\2\2e\u01c1\3\2\2\2g\u01c3\3\2"+
		"\2\2i\u01c5\3\2\2\2k\u01c7\3\2\2\2m\u01ca\3\2\2\2o\u01cc\3\2\2\2q\u01ce"+
		"\3\2\2\2s\u01d0\3\2\2\2u\u01d2\3\2\2\2w\u01d4\3\2\2\2y\u01d6\3\2\2\2{"+
		"\u01dd\3\2\2\2}\u01e7\3\2\2\2\177\u01ec\3\2\2\2\u0081\u01f6\3\2\2\2\u0083"+
		"\u0201\3\2\2\2\u0085\u0205\3\2\2\2\u0087\u0208\3\2\2\2\u0089\u0211\3\2"+
		"\2\2\u008b\u008c\7c\2\2\u008c\u008d\7n\2\2\u008d\u008e\7i\2\2\u008e\u008f"+
		"\7q\2\2\u008f\u0090\7t\2\2\u0090\u0091\7k\2\2\u0091\u0092\7v\2\2\u0092"+
		"\u0093\7o\2\2\u0093\u0094\7q\2\2\u0094\4\3\2\2\2\u0095\u0096\7h\2\2\u0096"+
		"\u0097\7k\2\2\u0097\u0098\7o\2\2\u0098\u0099\7a\2\2\u0099\u009a\7c\2\2"+
		"\u009a\u009b\7n\2\2\u009b\u009c\7i\2\2\u009c\u009d\7q\2\2\u009d\u009e"+
		"\7t\2\2\u009e\u009f\7k\2\2\u009f\u00a0\7v\2\2\u00a0\u00a1\7o\2\2\u00a1"+
		"\u00a2\7q\2\2\u00a2\6\3\2\2\2\u00a3\u00a4\7c\2\2\u00a4\u00a5\7v\2\2\u00a5"+
		"\u00a6\7g\2\2\u00a6\b\3\2\2\2\u00a7\u00a8\7g\2\2\u00a8\u00a9\7u\2\2\u00a9"+
		"\u00aa\7e\2\2\u00aa\u00ab\7t\2\2\u00ab\u00ac\7g\2\2\u00ac\u00ad\7x\2\2"+
		"\u00ad\u00ae\7c\2\2\u00ae\n\3\2\2\2\u00af\u00b0\7n\2\2\u00b0\u00b1\7g"+
		"\2\2\u00b1\u00b2\7k\2\2\u00b2\u00b3\7c\2\2\u00b3\f\3\2\2\2\u00b4\u00b5"+
		"\7h\2\2\u00b5\u00b6\7c\2\2\u00b6\u00b7\7e\2\2\u00b7\u00b8\7c\2\2\u00b8"+
		"\16\3\2\2\2\u00b9\u00ba\7u\2\2\u00ba\u00bb\7g\2\2\u00bb\u00bc\7l\2\2\u00bc"+
		"\u00bd\7c\2\2\u00bd\20\3\2\2\2\u00be\u00bf\7n\2\2\u00bf\u00c0\7q\2\2\u00c0"+
		"\u00c1\7i\2\2\u00c1\u00c2\7k\2\2\u00c2\u00c3\7e\2\2\u00c3\u00c4\7q\2\2"+
		"\u00c4\22\3\2\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8\7"+
		"v\2\2\u00c8\u00c9\7q\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb\7p\2\2\u00cb\u00cc"+
		"\7g\2\2\u00cc\24\3\2\2\2\u00cd\u00ce\7g\2\2\u00ce\u00cf\7p\2\2\u00cf\u00d0"+
		"\7v\2\2\u00d0\u00d1\7c\2\2\u00d1\u00d2\7q\2\2\u00d2\26\3\2\2\2\u00d3\u00d4"+
		"\7g\2\2\u00d4\u00d5\7p\2\2\u00d5\u00d6\7s\2\2\u00d6\u00d7\7w\2\2\u00d7"+
		"\u00d8\7c\2\2\u00d8\u00d9\7p\2\2\u00d9\u00da\7v\2\2\u00da\u00db\7q\2\2"+
		"\u00db\30\3\2\2\2\u00dc\u00dd\7h\2\2\u00dd\u00de\7k\2\2\u00de\u00df\7"+
		"o\2\2\u00df\u00e0\7a\2\2\u00e0\u00e1\7g\2\2\u00e1\u00e2\7p\2\2\u00e2\u00e3"+
		"\7s\2\2\u00e3\u00e4\7w\2\2\u00e4\u00e5\7c\2\2\u00e5\u00e6\7p\2\2\u00e6"+
		"\u00e7\7v\2\2\u00e7\u00e8\7q\2\2\u00e8\32\3\2\2\2\u00e9\u00ea\7r\2\2\u00ea"+
		"\u00eb\7c\2\2\u00eb\u00ec\7t\2\2\u00ec\u00ed\7c\2\2\u00ed\34\3\2\2\2\u00ee"+
		"\u00ef\7h\2\2\u00ef\u00f0\7k\2\2\u00f0\u00f1\7o\2\2\u00f1\u00f2\7a\2\2"+
		"\u00f2\u00f3\7r\2\2\u00f3\u00f4\7c\2\2\u00f4\u00f5\7t\2\2\u00f5\u00f6"+
		"\7c\2\2\u00f6\36\3\2\2\2\u00f7\u00f8\7e\2\2\u00f8\u00f9\7c\2\2\u00f9\u00fa"+
		"\7u\2\2\u00fa\u00fb\7q\2\2\u00fb \3\2\2\2\u00fc\u00fd\7h\2\2\u00fd\u00fe"+
		"\7k\2\2\u00fe\u00ff\7o\2\2\u00ff\u0100\7a\2\2\u0100\u0101\7e\2\2\u0101"+
		"\u0102\7c\2\2\u0102\u0103\7u\2\2\u0103\u0104\7q\2\2\u0104\"\3\2\2\2\u0105"+
		"\u0106\7u\2\2\u0106\u0107\7g\2\2\u0107$\3\2\2\2\u0108\u0109\7u\2\2\u0109"+
		"\u010a\7g\2\2\u010a\u010b\7p\2\2\u010b\u010c\7c\2\2\u010c\u010d\7q\2\2"+
		"\u010d&\3\2\2\2\u010e\u010f\7h\2\2\u010f\u0110\7k\2\2\u0110\u0111\7o\2"+
		"\2\u0111\u0112\7a\2\2\u0112\u0113\7u\2\2\u0113\u0114\7g\2\2\u0114(\3\2"+
		"\2\2\u0115\u0116\7h\2\2\u0116\u0117\7w\2\2\u0117\u0118\7p\2\2\u0118\u0119"+
		"\7e\2\2\u0119\u011a\7c\2\2\u011a\u011b\7q\2\2\u011b*\3\2\2\2\u011c\u011d"+
		"\7h\2\2\u011d\u011e\7k\2\2\u011e\u011f\7o\2\2\u011f\u0120\7a\2\2\u0120"+
		"\u0121\7h\2\2\u0121\u0122\7w\2\2\u0122\u0123\7p\2\2\u0123\u0124\7e\2\2"+
		"\u0124\u0125\7c\2\2\u0125\u0126\7q\2\2\u0126,\3\2\2\2\u0127\u0128\7t\2"+
		"\2\u0128\u0129\7g\2\2\u0129\u012a\7i\2\2\u012a\u012b\7k\2\2\u012b\u012c"+
		"\7u\2\2\u012c\u012d\7v\2\2\u012d\u012e\7t\2\2\u012e\u012f\7q\2\2\u012f"+
		".\3\2\2\2\u0130\u0131\7h\2\2\u0131\u0132\7k\2\2\u0132\u0133\7o\2\2\u0133"+
		"\u0134\7a\2\2\u0134\u0135\7t\2\2\u0135\u0136\7g\2\2\u0136\u0137\7i\2\2"+
		"\u0137\u0138\7k\2\2\u0138\u0139\7u\2\2\u0139\u013a\7v\2\2\u013a\u013b"+
		"\7t\2\2\u013b\u013c\7q\2\2\u013c\60\3\2\2\2\u013d\u013e\7r\2\2\u013e\u013f"+
		"\7t\2\2\u013f\u0140\7q\2\2\u0140\u0141\7e\2\2\u0141\u0142\7g\2\2\u0142"+
		"\u0143\7f\2\2\u0143\u0144\7k\2\2\u0144\u0145\7o\2\2\u0145\u0146\7g\2\2"+
		"\u0146\u0147\7p\2\2\u0147\u0148\7v\2\2\u0148\u0149\7q\2\2\u0149\62\3\2"+
		"\2\2\u014a\u014b\7h\2\2\u014b\u014c\7k\2\2\u014c\u014d\7o\2\2\u014d\u014e"+
		"\7a\2\2\u014e\u014f\7r\2\2\u014f\u0150\7t\2\2\u0150\u0151\7q\2\2\u0151"+
		"\u0152\7e\2\2\u0152\u0153\7g\2\2\u0153\u0154\7f\2\2\u0154\u0155\7k\2\2"+
		"\u0155\u0156\7o\2\2\u0156\u0157\7g\2\2\u0157\u0158\7p\2\2\u0158\u0159"+
		"\7v\2\2\u0159\u015a\7q\2\2\u015a\64\3\2\2\2\u015b\u015c\7g\2\2\u015c\66"+
		"\3\2\2\2\u015d\u015e\7q\2\2\u015e\u015f\7w\2\2\u015f8\3\2\2\2\u0160\u0161"+
		"\7p\2\2\u0161\u0162\7c\2\2\u0162\u0163\7q\2\2\u0163:\3\2\2\2\u0164\u0165"+
		"\7x\2\2\u0165\u0166\7g\2\2\u0166\u0167\7t\2\2\u0167\u0168\7f\2\2\u0168"+
		"\u0169\7c\2\2\u0169\u016a\7f\2\2\u016a\u016b\7g\2\2\u016b\u016c\7k\2\2"+
		"\u016c\u016d\7t\2\2\u016d\u016e\7q\2\2\u016e<\3\2\2\2\u016f\u0170\7h\2"+
		"\2\u0170\u0171\7c\2\2\u0171\u0172\7n\2\2\u0172\u0173\7u\2\2\u0173\u0174"+
		"\7q\2\2\u0174>\3\2\2\2\u0175\u0176\7?\2\2\u0176@\3\2\2\2\u0177\u0178\7"+
		">\2\2\u0178\u0179\7@\2\2\u0179B\3\2\2\2\u017a\u017b\7>\2\2\u017bD\3\2"+
		"\2\2\u017c\u017d\7>\2\2\u017d\u017e\7?\2\2\u017eF\3\2\2\2\u017f\u0180"+
		"\7@\2\2\u0180H\3\2\2\2\u0181\u0182\7@\2\2\u0182\u0183\7?\2\2\u0183J\3"+
		"\2\2\2\u0184\u0185\7>\2\2\u0185\u0186\7/\2\2\u0186L\3\2\2\2\u0187\u0188"+
		"\7f\2\2\u0188\u0189\7g\2\2\u0189\u018a\7e\2\2\u018a\u018b\7n\2\2\u018b"+
		"\u018c\7c\2\2\u018c\u018d\7t\2\2\u018d\u018e\7g\2\2\u018eN\3\2\2\2\u018f"+
		"\u0190\7v\2\2\u0190\u0191\7k\2\2\u0191\u0192\7r\2\2\u0192\u0193\7q\2\2"+
		"\u0193P\3\2\2\2\u0194\u0195\7x\2\2\u0195\u0196\7c\2\2\u0196\u0197\7t\2"+
		"\2\u0197R\3\2\2\2\u0198\u0199\7n\2\2\u0199\u019a\7k\2\2\u019a\u019b\7"+
		"v\2\2\u019b\u019c\7g\2\2\u019c\u019d\7t\2\2\u019d\u019e\7c\2\2\u019e\u019f"+
		"\7n\2\2\u019fT\3\2\2\2\u01a0\u01a1\7e\2\2\u01a1\u01a2\7q\2\2\u01a2\u01a3"+
		"\7p\2\2\u01a3\u01a4\7u\2\2\u01a4\u01a5\7v\2\2\u01a5\u01a6\7c\2\2\u01a6"+
		"\u01a7\7p\2\2\u01a7\u01a8\7v\2\2\u01a8\u01a9\7g\2\2\u01a9V\3\2\2\2\u01aa"+
		"\u01ab\7k\2\2\u01ab\u01ac\7p\2\2\u01ac\u01ad\7v\2\2\u01ad\u01ae\7g\2\2"+
		"\u01ae\u01af\7k\2\2\u01af\u01b0\7t\2\2\u01b0\u01b1\7q\2\2\u01b1X\3\2\2"+
		"\2\u01b2\u01b3\7t\2\2\u01b3\u01b4\7g\2\2\u01b4\u01b5\7c\2\2\u01b5\u01b6"+
		"\7n\2\2\u01b6Z\3\2\2\2\u01b7\u01b8\7-\2\2\u01b8\\\3\2\2\2\u01b9\u01ba"+
		"\7/\2\2\u01ba^\3\2\2\2\u01bb\u01bc\7\61\2\2\u01bc`\3\2\2\2\u01bd\u01be"+
		"\7\'\2\2\u01beb\3\2\2\2\u01bf\u01c0\7,\2\2\u01c0d\3\2\2\2\u01c1\u01c2"+
		"\7`\2\2\u01c2f\3\2\2\2\u01c3\u01c4\7(\2\2\u01c4h\3\2\2\2\u01c5\u01c6\7"+
		"\60\2\2\u01c6j\3\2\2\2\u01c7\u01c8\7\60\2\2\u01c8\u01c9\7\60\2\2\u01c9"+
		"l\3\2\2\2\u01ca\u01cb\7<\2\2\u01cbn\3\2\2\2\u01cc\u01cd\7.\2\2\u01cdp"+
		"\3\2\2\2\u01ce\u01cf\7*\2\2\u01cfr\3\2\2\2\u01d0\u01d1\7+\2\2\u01d1t\3"+
		"\2\2\2\u01d2\u01d3\7]\2\2\u01d3v\3\2\2\2\u01d4\u01d5\7_\2\2\u01d5x\3\2"+
		"\2\2\u01d6\u01da\t\2\2\2\u01d7\u01d9\t\3\2\2\u01d8\u01d7\3\2\2\2\u01d9"+
		"\u01dc\3\2\2\2\u01da\u01d8\3\2\2\2\u01da\u01db\3\2\2\2\u01dbz\3\2\2\2"+
		"\u01dc\u01da\3\2\2\2\u01dd\u01e1\7$\2\2\u01de\u01e0\n\4\2\2\u01df\u01de"+
		"\3\2\2\2\u01e0\u01e3\3\2\2\2\u01e1\u01df\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2"+
		"\u01e4\3\2\2\2\u01e3\u01e1\3\2\2\2\u01e4\u01e5\7$\2\2\u01e5|\3\2\2\2\u01e6"+
		"\u01e8\4\62;\2\u01e7\u01e6\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01e7\3\2"+
		"\2\2\u01e9\u01ea\3\2\2\2\u01ea~\3\2\2\2\u01eb\u01ed\4\62;\2\u01ec\u01eb"+
		"\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee\u01ec\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef"+
		"\u01f0\3\2\2\2\u01f0\u01f2\7\60\2\2\u01f1\u01f3\4\62;\2\u01f2\u01f1\3"+
		"\2\2\2\u01f3\u01f4\3\2\2\2\u01f4\u01f2\3\2\2\2\u01f4\u01f5\3\2\2\2\u01f5"+
		"\u0080\3\2\2\2\u01f6\u01fa\7}\2\2\u01f7\u01f9\n\5\2\2\u01f8\u01f7\3\2"+
		"\2\2\u01f9\u01fc\3\2\2\2\u01fa\u01f8\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb"+
		"\u01fd\3\2\2\2\u01fc\u01fa\3\2\2\2\u01fd\u01fe\7\177\2\2\u01fe\u01ff\3"+
		"\2\2\2\u01ff\u0200\bA\2\2\u0200\u0082\3\2\2\2\u0201\u0202\t\6\2\2\u0202"+
		"\u0203\3\2\2\2\u0203\u0204\bB\2\2\u0204\u0084\3\2\2\2\u0205\u0206\7}\2"+
		"\2\u0206\u0207\13\2\2\2\u0207\u0086\3\2\2\2\u0208\u020c\7$\2\2\u0209\u020b"+
		"\n\7\2\2\u020a\u0209\3\2\2\2\u020b\u020e\3\2\2\2\u020c\u020a\3\2\2\2\u020c"+
		"\u020d\3\2\2\2\u020d\u020f\3\2\2\2\u020e\u020c\3\2\2\2\u020f\u0210\7\f"+
		"\2\2\u0210\u0088\3\2\2\2\u0211\u0212\13\2\2\2\u0212\u008a\3\2\2\2\n\2"+
		"\u01da\u01e1\u01e9\u01ee\u01f4\u01fa\u020c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}