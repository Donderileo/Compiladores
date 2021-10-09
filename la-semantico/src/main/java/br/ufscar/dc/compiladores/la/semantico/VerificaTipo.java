package br.ufscar.dc.compiladores.la.semantico;

import br.ufscar.dc.compiladores.la.semantico.TabelaDeSimbolos.TipoLa;
import br.ufscar.dc.compiladores.la.semantico.TabelaDeSimbolos.TipoEta;


/**
 *
 * @author donde
 */
public class VerificaTipo {

    public TipoLa verificaTipo(LaParser.ExpressaoContext ctx, Escopos escopo){
        
        TipoLa tipo = null;
        
        for (LaParser.Termo_logicoContext termo_ctx : ctx.termo_logico()) {
            TipoLa tipoLogico = verificaTipo(termo_ctx, escopo);
            if (tipo == null){         
                tipo = tipoLogico;
            }
            
        }

        return tipo;
    }
    
    public TipoLa verificaTipo(LaParser.Termo_logicoContext ctx, Escopos escopo){
                

        TipoLa tipo = null;
        
        for (LaParser.Fator_logicoContext fator_ctx : ctx.fator_logico()) {
            TipoLa tipoFator = verificaTipo(fator_ctx, escopo);
            if (tipo == null) {
                tipo = tipoFator;
            }
        }

        return tipo;
    }
    
    public TipoLa verificaTipo(LaParser.Fator_logicoContext ctx, Escopos escopo){
        TipoLa tipo = null;
        TipoLa tipoParcela = verificaTipo(ctx.parcela_logica(),escopo);
        if(tipo == null){
            tipo = tipoParcela;
        }
        
        return tipo;
    }
    
    public TipoLa verificaTipo(LaParser.Parcela_logicaContext ctx, Escopos escopo){
            
        TipoLa tipo = null;

        if(ctx.exp_relacional() != null){  
            TipoLa tipoRelacional = verificaTipo(ctx.exp_relacional(), escopo);
            if(tipo == null){
                tipo = tipoRelacional;
            }
               
        } 
        else{
            tipo = TipoLa.LOGICO;
        }
        return tipo;
    }
    
    public TipoLa verificaTipo(LaParser.Exp_relacionalContext ctx, Escopos escopo){
        
        TipoLa tipo = null;

        if(ctx.op_relacional() != null) {     
            tipo = TipoLa.LOGICO;
        }
        else{
            TipoLa tipoExpArit = verificaTipo(ctx.exp_aritmetica(0), escopo);
            if(tipo == null){
                tipo = tipoExpArit;
            }
            
        }
        return tipo;
    }
    
    public TipoLa verificaTipo(LaParser.Exp_aritmeticaContext ctx, Escopos escopo){
                
        
        TipoLa tipo = null; 

        for (LaParser.TermoContext termo_ctx : ctx.termo()) {
            TipoLa tipoTermo = verificaTipo(termo_ctx, escopo);
            if (tipo == null){
                tipo = tipoTermo;
            }
            else{
                if(!tipoTermo.equals(tipo)){
                    tipo = TipoLa.INVALIDO;
                }
            }
        } 
        return tipo;
    }
    
    public TipoLa verificaTipo(LaParser.TermoContext ctx, Escopos escopo){
        TipoLa tipo = null;

        for(LaParser.FatorContext fator_ctx: ctx.fator()){
            TipoLa tipoFator = verificaTipo(fator_ctx, escopo);
            if(tipo == null){
                tipo = tipoFator;
            }
            
        }

        return tipo;  
    }
    
    public TipoLa verificaTipo(LaParser.FatorContext ctx, Escopos escopo){
                
        
        TipoLa tipo = null;

        for (LaParser.ParcelaContext parcela_ctx : ctx.parcela()) {
            TipoLa tipoParcela = verificaTipo(parcela_ctx, escopo);
            if (tipo == null){         
                tipo = tipoParcela;
            }
        } 
        return tipo;
    }
    
    public TipoLa verificaTipo(LaParser.ParcelaContext ctx, Escopos escopo){
                
        TipoLa tipo = null;
        
        if(ctx.parcela_unario() != null){     
            TipoLa tipoParUnario = verificaTipo(ctx.parcela_unario(),escopo);
            if(tipo == null){
                tipo = tipoParUnario;
            }
        }
        else{
            TipoLa tipoParNaoUnario = verificaTipo(ctx.parcela_nao_unario(),escopo);
            if(tipo == null){
                tipo = tipoParNaoUnario;
            }
        }

        return tipo;
    }

    public TipoLa verificaTipo(LaParser.Parcela_unarioContext ctx, Escopos escopo) {

        TipoLa tipo = null;

        if(ctx.identificador() != null) {     
            String IDENT = ctx.identificador().ident1.getText();
            
            //Recupera o tipo dentro da subtabela.
            if(ctx.identificador().ident2.size() > 0){
                for(TabelaDeSimbolos tabela: escopo.percorrerEscoposAninhados()){
                    if(tabela.existe(IDENT)){
                        //Recupera o tipo do IDENT para os casos basicos
                        if(!tabela.verificarTipo(IDENT).equals(TipoLa.REGISTRO) && tabela.getSubTabela(IDENT) == null){
                            return tabela.verificarTipo(IDENT);
                        }
                        //Recupera o nome do IDENT para o caso de um registro
                        else if(tabela.verificarTipo(IDENT).equals(TipoLa.REGISTRO)){
                            return tabela.getSubTabela(IDENT).verificarTipo(ctx.identificador().ident2.get(0).getText());
                        }
                    }
                }
            }
            
             else{
                //Pega o tipo base.
                tipo = escopo.obterEscopoAtual().verificarTipo(IDENT);
                if(tipo.equals(TipoLa.REGISTRO)){
                    tipo = escopo.obterEscopoAtual().getTipo(escopo.obterEscopoAtual().getRegistro(IDENT));
                }
            }
        }
        //Para o caso de função, recupera o tipo de retorno
        else if(!ctx.expressao().isEmpty() && ctx.IDENT() != null){
            String internoIDENT = ctx.IDENT().getText();
            for(TabelaDeSimbolos tabela: escopo.percorrerEscoposAninhados()){
                if(tabela.existe(internoIDENT)){
                    if(tabela.getTipoEta(internoIDENT).equals(TipoEta.FUNCAO)){
                        tipo = tabela.verificarTipo(internoIDENT);
                    }
                }
            }
        }
        //Se for um inteiro, retorna o tipo inteiro
        else if(ctx.NUM_INT() != null){
            tipo = TipoLa.INTEIRO;
        }
        
        //Se for um real, retorna o tipo real
        else if(ctx.NUM_REAL() != null){
            tipo = TipoLa.REAL;
        }
        //Caso não for nenhum dos acima, verifica a expressao
        else{
            tipo = verificaTipo(ctx.parcelaUnarioExp,escopo);
        }

        return tipo;
       
    }

    public TipoLa verificaTipo(LaParser.Parcela_nao_unarioContext ctx, Escopos escopo) {

        TipoLa tipo = null;

        if(ctx.identificador() != null){
            if(ctx.endereco != null){
                tipo = TipoLa.INVALIDO;
            }
            else{
                tipo = escopo.obterEscopoAtual().verificarTipo(ctx.identificador().getText());
            }
        }
        else{
            tipo = TipoLa.LITERAL;
        }   

        return tipo; 
    }
}
  