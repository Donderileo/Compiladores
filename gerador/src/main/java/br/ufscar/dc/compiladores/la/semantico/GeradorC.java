/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.la.semantico;

import br.ufscar.dc.compiladores.la.semantico.TabelaDeSimbolos.TipoEta;
import br.ufscar.dc.compiladores.la.semantico.TabelaDeSimbolos.TipoLa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author donde
 */
public class GeradorC extends LaBaseVisitor<Void> {
    public StringBuilder saida;
    TabelaDeSimbolos tabela;
    Escopos escopos = new Escopos();
    VerificaTipo verificador = new VerificaTipo();
   
   public GeradorC(){
       saida = new StringBuilder();
       this.tabela = new TabelaDeSimbolos();
   }

    //Função auxiliar para facilitar comparações e switches.
    public String converteTipoDeVolta(TipoLa tipo){
        String tipoString = "";
        
        if(tipo.equals(TipoLa.INTEIRO)){
            tipoString = "inteiro";
        }
        else if(tipo.equals(TipoLa.REAL)){
            tipoString = "real";
        }
        else if(tipo.equals(TipoLa.LOGICO)){
            tipoString = "logico";
        }
        else if(tipo.equals(TipoLa.LITERAL)){
            tipoString = "literal";
        }
        else{
            tipoString = "vazio";
        }
        return tipoString;
    }
   
   @Override
   public Void visitPrograma(LaParser.ProgramaContext ctx){

        /*
            O programa começa com o mesmo cabeçalho de sempre e divide-se em duas partes
            visitDeclaracoes = Declarações de funções/procedimentos
            visitCorpo = tudo que vai dentro da main()
        
        */

        saida.append("#include <stdio.h>\n");
        saida.append("#include <stdlib.h>\n");
        saida.append("#include <string.h>\n");
        saida.append("\n");
        visitDeclaracoes(ctx.declaracoes());
        saida.append("\n");
        saida.append("int main() {\n");
        visitCorpo(ctx.corpo());
        saida.append("return 0;\n");
        saida.append("}\n");
        
        
        return null;

   }
   
   @Override
   public Void visitDeclaracao_local(LaParser.Declaracao_localContext ctx){
       if(ctx.variavel() != null){
           visitVariavel(ctx.variavel());
       }
       else if(ctx.tipo_basico() != null){
           saida.append("#define " + ctx.identConstante.getText() + " " + ctx.valor_constante().getText() + "\n");
       }
       else{
           //Registro.
           String nomeReg = ctx.identTipo.getText();
           saida.append("typedef ");
           
           if(ctx.tipo().tipo_estendido() == null){
               saida.append("struct {\n");
               
               //Passa pelas variáveis do registro
               ctx.tipo().registro().variavel().forEach(variavel_ctx -> {
                   if(variavel_ctx.tipo().tipo_estendido() != null){
                       boolean e_ponteiro = variavel_ctx.tipo().tipo_estendido().ponteiro != null;
                       if(variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico()!= null){
                           String tipoIDENT = variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText();
                           
                           for(LaParser.IdentificadorContext ident_ctx: variavel_ctx.identificador()){
                                   //Adapta os tipos obtidos para os tipos da linguagem C.
                                   switch(tipoIDENT){
                                        case "inteiro":
                                            saida.append("int " + ident_ctx.getText() + ";\n");
                                            break;
                                        case "real":
                                             saida.append("float " + ident_ctx.getText() + ";\n");
                                             break;
                                        case "literal":
                                             saida.append("char " + ident_ctx.getText() + "[80];\n");
                                             break;  
                                        case "logico":
                                             saida.append("int " + ident_ctx.getText() + ";\n");
                                             break;  
                                   }

                               }
                           }
                       }
               });
               saida.append("} " + nomeReg + ";\n");
           }
       }
       return null;
   }
   
   //Declarações globais = funções e procedimentos.
   @Override
   public Void visitDeclaracao_global(LaParser.Declaracao_globalContext ctx){

        List<TipoLa> parametros = new ArrayList<>();
        String nome;   
       //Função
       if(ctx.tipo_estendido() != null){
           switch(ctx.tipo_estendido().getText()){
               case "inteiro":
                    saida.append("int ");
                    break;
               case "real":
                    saida.append("float ");
                    break;
               case "literal":
                    saida.append("char ");
                    break;
           }
       }
       //Procedimento
       else{
           saida.append("void ");
       }
       
       //Insere o nome da função/procedimento.
       saida.append(ctx.IDENT().getText() + " (");
       
       //Para funções/procedimentos com parâmetros.
       if(ctx.parametros() != null){
           for(LaParser.ParametroContext param_ctx: ctx.parametros().parametro()){
              
               switch(param_ctx.tipo_estendido().getText()){
                    case "inteiro":
                        saida.append("int ");
                        break;
                    case "real":
                        saida.append("float ");
                        break;
                    case "literal":
                        saida.append("char* ");
                        break;
                }
               nome = "";
               for(LaParser.IdentificadorContext ident_ctx: param_ctx.identificador()){
                   nome += ident_ctx.getText();
               }
               saida.append(nome + " ");
           }
       }
       saida.append(" ){\n");
       
      
        for (LaParser.CmdContext cmd_ctx : ctx.cmd()) {
            visitCmd(cmd_ctx);
        }
        saida.append("}\n");
         
       return null;
   }
   
   @Override
    @SuppressWarnings("empty-statement")
    public Void visitVariavel(LaParser.VariavelContext ctx) {
        
        if(ctx.tipo().tipo_estendido() != null){
            boolean e_ponteiro = ctx.tipo().tipo_estendido().ponteiro != null;
            
            if(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null){

                //Trata variáveis ponteiro ou não
                String tipoIDENT = ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText();
                for (LaParser.IdentificadorContext ident_ctx: ctx.identificador()){
                    String IDENT = ident_ctx.getText();
                    if(e_ponteiro){
                        switch(tipoIDENT){
                            case "inteiro":
                                saida.append("int " + "*" + ident_ctx.getText() + ";\n");
                                escopoAtual.add(IDENT, escopoAtual.getTipo(tipoIDENT), e_ponteiro);
                                break;
                            case "real":
                                saida.append("float " + "*") + ident_ctx.getText() + ";\n");
                                escopoAtual.add(IDENT, escopoAtual.getTipo(tipoIDENT), e_ponteiro);
                                break;
                            case "literal":
                                saida.append("char " + "*") + ident_ctx.getText() + "[80];\n");
                                escopoAtual.add(IDENT, escopoAtual.getTipo(tipoIDENT), e_ponteiro);
                                break;  
                            case "logico":
                                saida.append("int " + "*") + ident_ctx.getText() + ";\n");
                                escopoAtual.add(IDENT, escopoAtual.getTipo(tipoIDENT), e_ponteiro);
                                break;  
                        } 
                    }
                    else{
                        switch(tipoIDENT){
                            case "inteiro":
                                saida.append("int " + ident_ctx.getText() + ";\n");
                                escopoAtual.add(IDENT, escopoAtual.getTipo(tipoIDENT), e_ponteiro);
                                break;
                            case "real":
                                saida.append("float " + ident_ctx.getText() + ";\n");
                                escopoAtual.add(IDENT, escopoAtual.getTipo(tipoIDENT), e_ponteiro);
                                break;
                            case "literal":
                                saida.append("char " + ident_ctx.getText() + "[80];\n");
                                escopoAtual.add(IDENT, escopoAtual.getTipo(tipoIDENT), e_ponteiro);
                                break;  
                            case "logico":
                                saida.append("int " + ident_ctx.getText() + ";\n");
                                escopoAtual.add(IDENT, escopoAtual.getTipo(tipoIDENT), e_ponteiro);
                                break;  
                        }
                    }
                }
            }
        }
        else{
            //Tratamento de registros.
            String nomeReg = ctx.identificador(0).getText();
            TabelaDeSimbolos tabelaReg = new TabelaDeSimbolos();
            
            saida.append("struct {\n");
            ctx.tipo().registro().variavel().forEach(variavel_ctx -> {
               if(variavel_ctx.tipo().tipo_estendido() != null){
                    boolean e_ponteiro = variavel_ctx.tipo().tipo_estendido().ponteiro != null;
                    if(variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText() != null){
                        String tipoIDENT = variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText();
                        
                        for(LaParser.IdentificadorContext ident_ctx: variavel_ctx.identificador()){
                            if(!tabelaReg.existe(ident_ctx.getText())){
                                switch(tipoIDENT){
                                    case "literal":
                                        saida.append("char ").append(ident_ctx.getText()).append("[80];\n");
                                        break;
                                    case "inteiro":
                                        saida.append("int ").append(ident_ctx.getText()).append(";\n");
                                        break;
                                    case  "logico" :
                                        saida.append("int ").append(ident_ctx.getText()).append(";\n");
                                        break;
                                    case "real":
                                        saida.append("float ").append(ident_ctx.getText()).append(";\n");
                                        break;
                                }
                                tabelaReg.add(ident_ctx.getText(), escopoAtual.getTipo(tipoIDENT), e_ponteiro); 
                            }
                        }
                               
                    }
               }
            });
            saida.append("} " + nomeReg + ";\n");
            escopoAtual.add(nomeReg,TipoLa.REGISTRO,nomeReg,tabelaReg);
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdEscreva(LaParser.CmdEscrevaContext ctx){
        
        saida.append("printf(\"");
        for(LaParser.ExpressaoContext exp_ctx: ctx.expressao()){
            
            TipoLa tipo = verificador.verificaTipo(exp_ctx, escopos);
            
            switch(converteTipoDeVolta(tipo)){
                case "inteiro":
                    saida.append("%d");
                    break;
                case "real":
                    saida.append("%f");
                    break;
                case "logico":
                    saida.append("%d");
                    break;
                case "literal":
                    saida.append("%s");
                    break;
                default:
                    saida.append("%s");
                    break;
            }
            
        }
        
        saida.append("\",");
        for(int i = 0; i < ctx.expressao().size(); i++){
            saida.append(ctx.expressao(i).getText());
            //colocar , para separar menos no último
            if(i != ctx.expressao().size() - 1){
                saida.append(",");
            }
        }
        saida.append(");\n");
        
       return null;
    }
    
    @Override
    public Void visitCmdLeia(LaParser.CmdLeiaContext ctx){
        for(LaParser.IdentificadorContext ident_ctx: ctx.identificador()){
            String IDENT = ident_ctx.ident1.getText();
            TipoLa tipoIDENT = escopos.obterEscopoAtual().verificarTipo(IDENT);
            switch(converteTipoDeVolta(tipoIDENT)){
                case "inteiro":
                    saida.append("scanf(\"%d\", &" + IDENT + ");\n");
                    break;
                case "real":
                    saida.append("scanf(\"%f\", &" + IDENT + ");\n");
                    break;
                case "logico":
                    saida.append("scanf(\"%d\", &" + IDENT + ");\n");
                    break;
                case "literal":
                    saida.append("gets(" + IDENT + ");\n");
                    break;
            }   
        }
        
        return null;
    }
    
     private boolean existeIdentificador(LaParser.IdentificadorContext ctx) {
        for (TabelaDeSimbolos tabela : escopos.percorrerEscoposAninhados()) {
            if (tabela.existe(ctx.ident1.getText())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Void visitCmdAtribuicao(LaParser.CmdAtribuicaoContext ctx){

        //Atribuições de valor
        boolean e_ponteiro = ctx.ponteiro != null;
        
        if(e_ponteiro){
            saida.append("*" + ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
        }
         else if (ctx.identificador().getText().contains(".") && verificador.verificaTipo(ctx.expressao(),escopos) == TipoLa.LITERAL) {
            saida.append("strcpy(" + ctx.identificador().getText() + "," + ctx.expressao().getText() + ");\n");
        } else { //identificador é uma variável do tipo básico
            saida.append(ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
        }
        
        return null;
    }
   
     @Override
    public Void visitCmdSe(LaParser.CmdSeContext ctx) {
        
        //Começa o comando if com a expressão já substituindo e por && e = por == como necessário para os casos de teste.
        saida.append("if (" + ctx.expressao().getText().replace("e", "&&").replace("=","==") + ") {\n");
       
        //Visita os comandos internos do if
        for (LaParser.CmdContext cmd_ctx : ctx.cmdIf) {
            visitCmd(cmd_ctx);
        }
        //Fecha o if
        saida.append("}");
        
        //Caso tenha else, abre o comando else, visita seus comandos e fecha.
        if (ctx.SENAO() != null) {
            saida.append("else {\n");
            for (LaParser.CmdContext cmd_ctx : ctx.cmdElse) {
                visitCmd(cmd_ctx);
            }
            saida.append("\n}");
        }
        return null;
    }
    
    @Override
    public Void visitCmdEnquanto(LaParser.CmdEnquantoContext ctx) {

        //Pega a expressão condicional do while, substituindo os simbolos La pelos da linguagem C
        String exp = ctx.expressao().getText().replaceAll("<>", "!=").replaceAll(" e ", "&&").replaceAll(" ou ", "||");
        //Começa o comando while

        saida.append("while(" + exp + "){\n");

        
        //Procura os comandos internos do while e os escreve.
        for (LaParser.CmdContext cmd_ctx : ctx.cmd()) {
            visitCmd(cmd_ctx);
        }
        saida.append("}\n");

        return null;
    }
   
    @Override
    public Void visitCmdCaso(LaParser.CmdCasoContext ctx){
        
        //Começa o comando switch.
        saida.append("switch (" + ctx.exp_aritmetica().getText() + "){\n");
        
        //Itera sobre cada caso do switch e imprime os cases/cmds de cada
        for(LaParser.Item_selecaoContext item_ctx: ctx.selecao().item_selecao()){
            if(item_ctx.constantes().constantesIntervalo.intervaloFim != null){
                int inicio = Integer.parseInt(item_ctx.constantes().constantesIntervalo.intervaloInicio.getText());
                int fim = Integer.parseInt(item_ctx.constantes().constantesIntervalo.intervaloFim.getText());
                
                for(int i = inicio ; i<= fim; i++){
                    saida.append("case " + i + ":\n");
                }
            
            }
            else{
               saida.append("case " + item_ctx.constantes().getText() + ":\n"); 
            }
            //Visita os comandos de cada caso.
            if(!item_ctx.cmd().isEmpty()){
                for (LaParser.CmdContext cmd_ctx : item_ctx.cmd()) {
                    visitCmd(cmd_ctx);
                    //saida.append(cmd_ctx.getText());
                    saida.append("break;\n");
                }
            }
        }
        //Finaliza o switch
        saida.append("}\n");
        return null;
    }
    
     @Override
    public Void visitCmdPara(LaParser.CmdParaContext ctx) {
        // CmdPara como for
        String IDENT = ctx.IDENT().getText();

        String Inicio = ctx.exp_aritmetica(0).getText();
        String Fim = ctx.exp_aritmetica(1).getText();
        //Inicia o for com o começo e condição de fim.
        saida.append("for(").append(IDENT).append("=").append(Inicio).append(";").append(IDENT).append("<=").append(Fim).append(";").append(IDENT).append("++) {\n");
        // Gera comandos dentro do for
        for (LaParser.CmdContext cmd : ctx.cmd()) {
            visitCmd(cmd);
        }
        //Finaliza o for.
        saida.append("}\n");
        return null;
    }
    
    @Override
    public Void visitCmdFaca(LaParser.CmdFacaContext ctx) {
        
        
        // Traduz simbolos da expressao para a linguagem C.
        String exp = ctx.expressao().getText().replaceAll("<>", "!=").replaceAll("nao", "!").replaceAll("=", "==");
        //Começa o comando do/while
        saida.append("do {\n");
        
        // Gera comandos internos
        for (LaParser.CmdContext cmd_ctx : ctx.cmd()) {
            visitCmd(cmd_ctx);
        }
        // Forma a expressão condicional do while.
        saida.append("} while (").append(exp).append(");\n");
        return null;
    }

    @Override
    public Void visitCmdChamada(LaParser.CmdChamadaContext ctx) {
        
        // Gera a chamada da função/procedimento
        saida.append(ctx.IDENT().getText()).append("(");
        
        // Coloca os parametros a serem passados
        for (int i = 0; i < ctx.expressao().size(); i++) {
            //Visita a expressão do parâmetro
            visitExpressao(ctx.expressao(i));
            if (i != ctx.expressao().size() - 1) {
                saida.append(", ");
            }
        }
        saida.append(");\n");
        return null;
    }

    @Override
    public Void visitCmdRetorne(LaParser.CmdRetorneContext ctx) {
        
        // Monta o comando return
        saida.append("return ");
        
        //Visita a expressão que será retornada
        visitExpressao(ctx.expressao());
        saida.append(";\n");
        return null;
    }
}
