package br.ufscar.dc.compiladores.brffmpeg;

import java.io.PrintWriter;
import java.util.BitSet;
import org.antlr.v4.runtime.ANTLRErrorListener; // cuidado para importar a versão 4
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token; // Vamos também precisar de Token
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

// Outros imports vão ser necessários aqui. O NetBeans ou IntelliJ fazem isso automaticamente

public class ErrorListener implements ANTLRErrorListener {
    PrintWriter pw;
    public ErrorListener(PrintWriter pw){
        this.pw = pw;
    }
    @Override
    public void	reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }
    
    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }

    @Override
    public void	syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        // Tratamos erros como EOF, comentário não fechado e simbolos não identificados.
        // No caso default, verificamos se é uma cadeia não fechada usando como base começar com " e não terminar com " 
        // Ou se é um erro sintatico próximo a um token
            
        Token t = (Token) offendingSymbol;
        String tokenText = t.getText();
        pw.println(tokenText);
        if(tokenText.startsWith("\'") && !tokenText.endsWith("\'")){
            pw.println("Linha "+line+": cadeia nao fechada");
        }
        else if(tokenText.startsWith("'")){
            pw.println("Linha "+line+": Apenas um arquivo de entrada/saida ou legenda");
        }    
        else {
          pw.println("Linha "+line+": erro sintatico proximo a " + tokenText);  
        }
               
        // Para interromper a execução do programa (além de indicar o erro), criamos um RuntimeException.
        throw new RuntimeException();
    }
}