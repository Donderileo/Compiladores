/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.brffmpeg;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
/**
 *
 * @author donde
 */
public class Principal {
    static PrintWriter pw;
    static CharStream cs;
    static BRFFLexer lexer;
    static BRFFParser parser;
    
    
    public static void main(String args[]) throws IOException {
        pw = new PrintWriter(new File(args[1]));
        if(lexico(args[0])){
            pw.println("Lexico OK");
            if(sintatico(args[0])){
               pw.println("Sintatico OK");
            }
            else{
               pw.println("Erro Sintatico");
            }
        }
        else{
            pw.println("Erro Léxico");
        }
        pw.close();
    }
    
     static boolean lexico(String file) throws IOException{
        cs = CharStreams.fromFileName(file);
        lexer = new BRFFLexer(cs);

        Token token = null;

        try {
            File out = new File(file);
        } catch (Exception e) {
            System.out.println("Arquivo de saída nao pode ser criado");
        }

        SAIDA:
        while ((token = lexer.nextToken()).getType() != Token.EOF) {
            String tipo = BRFFLexer.VOCABULARY.getDisplayName(token.getType());
            String valor = token.getText();

            if(tipo.equals("ERRO")){
                return false;
            }
            else if(tipo.equals("CADEIA_NAO_FECHADA")){
                return false;
            }
            else{
                pw.println("<'" + valor + "'," + tipo + ">\n");
            }
        }
        return true;
     }
     
     static boolean sintatico(String file) throws IOException{
        cs = CharStreams.fromFileName(file);
        lexer = new BRFFLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new BRFFParser(tokens);

        parser.removeErrorListeners();
        ErrorListener error_listener = new ErrorListener(pw);
        parser.addErrorListener(error_listener);

        try{
           parser.programa(); 
           return true;
        } catch(Exception e) {
            return false;
        }
     }
}

