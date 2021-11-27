package br.ufscar.dc.compiladores.brffmpeg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

    
public class BRFFSemantico extends BRFFBaseVisitor<Void>{
    TabelaDeSimbolos tabela = new TabelaDeSimbolos();
    
    public boolean verificaArquivo(String path){
        path = path.substring(1, path.length() - 1);
        File tempFile = new File(path);
        return tempFile.exists() && !tempFile.isDirectory();
    }
    public double converteSpeed(int velocidade){
        
        return 1/velocidade;
    }  
    @Override 
    public Void visitIn(BRFFParser.InContext ctx) { 
        //Verifica se a entrada existe
        if(!verificaArquivo(ctx.path.getText())){
            BRFFSemanticoUtils.adicionarErroSemantico(ctx.path, "O arquivo nao existe");
        }
        //Caso negativo, insere na tabela de simbolos
        else{
            tabela.add_cmd_cadeia("ENTRADA", ctx.path.getText());
        }
        return super.visitIn(ctx);
    }
    
    @Override
    public Void visitOut(BRFFParser.OutContext ctx) {
        //Verifica se a saida existe
        if(verificaArquivo(ctx.path.getText())){
            BRFFSemanticoUtils.adicionarErroSemantico(ctx.path, "O arquivo ja existe, não e possivel sobrescrever");
        }
        //Caso negativo, insere na tabela de simbolos
        else{
            tabela.add_cmd_cadeia("SAIDA", ctx.path.getText());
        }
        return super.visitOut(ctx);
    }

    @Override 
    public Void visitCorpo_cortar(BRFFParser.Corpo_cortarContext ctx) { 
        // Verifica se já existe o cortar
        if(tabela.existe(ctx.INICIO().getText())){
            BRFFSemanticoUtils.adicionarErroSemantico("CORTAR ja declarado");
        }
        // Salva os tempos na tabela de simbolos
        else{
            tabela.add_cmd_valor("INICIO",ctx.start_time.getText());
            tabela.add_cmd_valor("FIM",ctx.finish_time.getText());
        }
        return visitChildren(ctx); 
    }   

    @Override 
    public Void visitCorpo_acelerar(BRFFParser.Corpo_acelerarContext ctx) { 
        //Dispara erro quando a velocidade é 0
        if("0".equals(ctx.vel.getText())){
            BRFFSemanticoUtils.adicionarErroSemantico(ctx.vel,"ACELERAR nao pode ser 0");
            return visitChildren(ctx); 
        }
        //Verifica se o comando já foi declarado
        if(tabela.existe(ctx.VELOCIDADE().getText())){
            BRFFSemanticoUtils.adicionarErroSemantico("ACELERAR ja declarado");
        }
        //Caso não, preenche a tabela de simbolos
        else{
            tabela.add_cmd_valor("VELOCIDADE",ctx.vel.getText());
            tabela.add_cmd_valor("AUDIO",ctx.option.getText());
        }
        return visitChildren(ctx); 
    }

    @Override 
    public Void visitSubtitle(BRFFParser.SubtitleContext ctx) { 
        //Verifica se o comando já foi declarado

        if(tabela.existe(ctx.LEGENDA().getText())){
            BRFFSemanticoUtils.adicionarErroSemantico("LEGENDA ja declarado");
        }
        //Caso não, preenche a tabela
        else{
           tabela.add_cmd_cadeia("LEGENDA", ctx.path.getText());
        }
        
        return visitChildren(ctx); }
    
}
   
