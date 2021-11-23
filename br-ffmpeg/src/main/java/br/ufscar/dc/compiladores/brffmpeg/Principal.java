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
/**
 *
 * @author donde
 */
public class Principal {
    
     public static void main(String args[]) throws IOException {
         try(PrintWriter pw = new PrintWriter(new File(args[1]))){
            
            CharStream cs = CharStreams.fromFileName(args[0]);
            BRFFLexer lexer = new BRFFLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            BRFFParser parser = new BRFFParser(tokens);
            
            parser.removeErrorListeners();
            ErrorListener error_listener = new ErrorListener(pw);
            parser.addErrorListener(error_listener);
            // Removendo Listener de erros do parser para dicionar o listener customizado
            try{
               parser.programa();                  
            } catch(Exception e) {}

            //Ao fim da analise sintatica, exibe a mensagem padrão "Fim da compilacao"
            pw.println("Fim da compilacao");
            
        }
    }       
}

