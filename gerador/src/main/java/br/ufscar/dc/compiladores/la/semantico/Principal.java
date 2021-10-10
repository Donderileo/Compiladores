package br.ufscar.dc.compiladores.la.semantico;

import br.ufscar.dc.compiladores.la.semantico.LaParser.ProgramaContext;
import java.io.File;
import java.io.FileWriter;
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
    static CharStream cs;
    static LaLexer lexer;
    static CommonTokenStream tokens;
    static LaParser parser;
    static PrintWriter pw;
    static GeradorC gerador;
    static LaSemantico las;

    public static void main(String args[]) throws IOException {
        pw = new PrintWriter(new File(args[1]));
        if(lexica(args[0])){
           if(sintatico(args[0])){
               if(semantico(args[0])){
                    geradorC(args[0]);
                    pw.close();
                }
           } 
       }
        
    }
    
    static void geradorC(String saida) throws IOException{
        gerador = new GeradorC();
        cs = CharStreams.fromFileName(saida);
        lexer = new LaLexer(cs);
        tokens = new CommonTokenStream(lexer);
        parser = new LaParser(tokens);
        ProgramaContext arvore = parser.programa();
        gerador.visitPrograma(arvore);
        pw.write(gerador.saida.toString()); 
    }
    
    static boolean semantico(String saida) throws IOException{
        cs = CharStreams.fromFileName(saida);
        lexer = new LaLexer(cs);
        tokens = new CommonTokenStream(lexer);
        parser = new LaParser(tokens);
        ProgramaContext arvore = parser.programa();
        las = new LaSemantico();
        las.visitPrograma(arvore);
        if(LaSemanticoUtils.errosSemanticos.isEmpty()){
            return true; 
        }
        LaSemanticoUtils.errosSemanticos.forEach((erro) -> pw.write(erro + '\n'));
        pw.write("Fim da compilacao\n");
        pw.close();
        
        return false;
             
    }
    
    static boolean sintatico(String saida) throws IOException{
            CharStream cs = CharStreams.fromFileName(saida);
            LaLexer lexer = new LaLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LaParser parser = new LaParser(tokens);

            // Removendo Listener de erros do parser para dicionar o listener customizado
            parser.removeErrorListeners();
            ErroListener error_listener = new ErroListener(pw);
            parser.addErrorListener(error_listener);
            try{
               parser.programa();
               return true;
            } catch(Exception e) {
                pw.println("Fim da compilacao");
                return false;
            }

            //Ao fim da analise sintatica, exibe a mensagem padrão "Fim da compilacao"
            
            
    }
    
    static boolean lexica(String file) throws IOException{
        /* Objeto antlr CharStream */
        
        cs = CharStreams.fromFileName(file);
        
        /* Inicialização da gramática */
        lexer = new LaLexer(cs);
        
        /* Token para percorrer o arquivo */ 
        Token token = null;
        
        // Faz a análise Léxica, escrevendo os tokens e error
        
       //Para verificar os erros, atribuiremos a uma variável, se ele passar do loop e continuar false,
       boolean error = false;
            
        SAIDA:
        while ((token = lexer.nextToken()).getType() != Token.EOF) {
            String tipo = LaLexer.VOCABULARY.getDisplayName(token.getType());


            if(tipo.equals("ERRO")){
                //fw.write("Linha " + token.getLine() + ": " + valor + " - simbolo nao identificado\n");
                return false;
            }
            else if(tipo.equals("COMENTARIO_NAO_FECHADO")){
                //fw.write("Linha " + token.getLine() + ": comentario nao fechado\n");
                return false;
            }
            else if(tipo.equals("CADEIA_NAO_FECHADA")){
                //fw.write("Linha " + token.getLine() + ": cadeia literal nao fechada\n");
                return false;
            }
            
        }
        
        return true;
    }
    
    
    
}
