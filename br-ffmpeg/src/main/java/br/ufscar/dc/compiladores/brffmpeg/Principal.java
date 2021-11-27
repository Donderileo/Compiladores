/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.brffmpeg;
import br.ufscar.dc.compiladores.brffmpeg.BRFFParser.ProgramaContext;
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
    static BRFFSemantico semantico;
    static GeradorFFMPEG gerador;
    static CommonTokenStream tokens;
    
    public static void main(String args[]) throws IOException {
        pw = new PrintWriter(new File(args[1]));
        if(lexico(args[0])){
            if(sintatico(args[0])){
               if(semantico(args[0])){
                   geradorFFMPEG(args[0]);
               }
            }
        }
        else{
            pw.println("Erro Lexico");
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
     
     static boolean semantico(String file) throws IOException{
        cs = CharStreams.fromFileName(file);
        lexer = new BRFFLexer(cs);
        tokens = new CommonTokenStream(lexer);
        parser = new BRFFParser(tokens);
        ProgramaContext arvore = parser.programa();
        semantico = new BRFFSemantico();
        semantico.visitPrograma(arvore);
        if(BRFFSemanticoUtils.errosSemanticos.isEmpty()){
            return true; 
        }
        BRFFSemanticoUtils.errosSemanticos.forEach((erro) -> pw.write(erro + '\n'));
        pw.write("Fim da compilacao\n");
        pw.close();
        
        return false;
             
    }
     
      static void geradorFFMPEG(String file) throws IOException{
        gerador = new GeradorFFMPEG();
        cs = CharStreams.fromFileName(file);
        lexer = new BRFFLexer(cs);
        tokens = new CommonTokenStream(lexer);
        parser = new BRFFParser(tokens);
        ProgramaContext arvore = parser.programa();
        gerador.visitPrograma(arvore);
        pw.write(gerador.saida.toString()); 
    }
}

