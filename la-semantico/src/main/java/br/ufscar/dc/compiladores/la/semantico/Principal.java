package br.ufscar.dc.compiladores.la.semantico;

import br.ufscar.dc.compiladores.la.semantico.LaParser.ProgramaContext;
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
    public static PrintWriter pw;

    public static void main(String args[]) throws IOException {
        
        pw = new PrintWriter(new File(args[1]));
        
        CharStream cs = CharStreams.fromFileName(args[0]);
        LaLexer lexer = new LaLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LaParser parser = new LaParser(tokens);
        ProgramaContext arvore = parser.programa();
        LaSemantico las = new LaSemantico();
        las.visitPrograma(arvore);
        LaSemanticoUtils.errosSemanticos.forEach((erro) -> pw.write(erro + '\n'));
        
        pw.write("Fim da compilacao\n");
        pw.close();
    }
}
