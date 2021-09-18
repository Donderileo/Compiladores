package br.ufscar.dc.compiladores.lexico.la;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;


public class Principal {
    public static void main(String args[]) throws IOException {
        /* Objeto antlr CharStream */
        
        CharStream cs = CharStreams.fromFileName(args[0]);
        
        /* Inicialização da gramática */
        LaLexer lex = new LaLexer(cs);
        
        /* Token para percorrer o arquivo */ 
        Token token = null;
        
        // Cria o arquivo de saida passado em args[1] (segundo argumento)
        try {
            File out = new File(args[1]);
        } catch (Exception e) {
            System.out.println("Arquivo de saída nao pode ser criado");
        }
        
        // Faz a análise Léxica, escrevendo os tokens e error
        try {
            FileWriter fw = new FileWriter(args[1]);
            SAIDA:
            while ((token = lex.nextToken()).getType() != Token.EOF) {
                String tipo = LaLexer.VOCABULARY.getDisplayName(token.getType());
                String valor = token.getText();
                
                if(tipo.equals("ERRO")){
                    fw.write("Linha " + token.getLine() + ": " + valor + " - simbolo nao identificado\n");
                    break;
                }
                else if(tipo.equals("COMENTARIO_NAO_FECHADO")){
                    fw.write("Linha " + token.getLine() + ": comentario nao fechado\n");
                    break;
                }
                else if(tipo.equals("CADEIA_NAO_FECHADA")){
                    fw.write("Linha " + token.getLine() + ": cadeia literal nao fechada\n");
                    break;
                }
                /* Caso comum, escreve o token */
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