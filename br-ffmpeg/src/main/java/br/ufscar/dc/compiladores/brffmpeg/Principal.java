/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.brffmpeg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author donde
 */
public class Principal {
     public static void main(String args[]) throws IOException {
        
        CharStream cs = CharStreams.fromFileName(args[0]);
        
        BRFF lex = new BRFF(cs);
        
        Token token = null;
        
        try {
            File out = new File(args[1]);
        } catch (Exception e) {
            System.out.println("Arquivo de saída nao pode ser criado");
        }
        
        try {
            FileWriter fw = new FileWriter(args[1]);
            SAIDA:
            while ((token = lex.nextToken()).getType() != Token.EOF) {
                String tipo = BRFF.VOCABULARY.getDisplayName(token.getType());
                String valor = token.getText();

                if(tipo.equals("ERRO")){
                    fw.write("Linha " + token.getLine() + ": " + valor + " - simbolo nao identificado\n");
                    break;
                }
                else if(tipo.equals("CADEIA_NAO_FECHADA")){
                    fw.write("Linha " + token.getLine() + ": cadeia literal nao fechada\n");
                    break;
                }
                else{
                    fw.write("<'" + valor + "'," + tipo + ">\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Erro de escrita no arquivo de saída");
        }
    }
}
