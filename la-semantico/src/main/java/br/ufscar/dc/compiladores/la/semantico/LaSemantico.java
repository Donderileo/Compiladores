package br.ufscar.dc.compiladores.la.semantico;

import br.ufscar.dc.compiladores.la.semantico.TabelaDeSimbolos.TipoLa;
import br.ufscar.dc.compiladores.la.semantico.TabelaDeSimbolos.TipoEta;
import java.util.ArrayList;
import java.util.List;

public class LaSemantico extends LaBaseVisitor<Void> {

    Escopos escopos = new Escopos();
    VerificaTipo verificador = new VerificaTipo();
    
    @Override
    public Void visitPrograma(LaParser.ProgramaContext ctx) {
        
        if (ctx.corpo() != null) {
            if (ctx.corpo().cmd() != null) {
                // Comando de retorno permitido somente em funções, caso exista
                for (LaParser.CmdContext cmd : ctx.corpo().cmd()) {
                    if (cmd.cmdRetorne() != null) {
                        // Erro do caso de teste 16
                        LaSemanticoUtils.adicionarErroSemantico(cmd.cmdRetorne().getStart(), "comando retorne nao permitido nesse escopo");
                    }
                }
            }
        }
        return super.visitPrograma(ctx);
    }
    
    /*
        declaracao_global: 'procedimento' IDENT '(' parametros? ')' declaracao_local* 
                    cmd* 'fim_procedimento'
                |   'funcao' IDENT '(' parametros? ')' ':' tipo_estendido declaracao_local*
                    cmd* 'fim_funcao';
     */
    @Override
    public Void visitDeclaracao_global(LaParser.Declaracao_globalContext ctx){
        List<TipoLa> params = new ArrayList<>();
        Boolean procedimento;
        TabelaDeSimbolos escopoAtual = escopos.obterEscopoAtual();
        TipoLa tipoLa;
        
        ctx.parametros().parametro().forEach(pctx -> {
            params.add(escopoAtual.getTipo(pctx.tipo_estendido().getText()));
        });
        // Diferença entre eles.
        if(ctx.tipo_estendido() == null){ //Caso não tenha o tipo estendido, procedimento.
            procedimento = true;
        }
        else{ // caso tenha, procedimento
            procedimento = false;
        }
        
        if(procedimento){
           
           escopoAtual.add(ctx.IDENT().getText(), TipoLa.VAZIO, TipoEta.PROCEDIMENTO, params);
           ctx.cmd().stream().filter(comand_ctx -> (comand_ctx.cmdRetorne() != null)).forEachOrdered(comand_ctx -> {
                // Erro do caso de teste 15
                LaSemanticoUtils.adicionarErroSemantico(comand_ctx.cmdRetorne().getStart(), "comando retorne nao permitido nesse escopo");
            });
        }
        else{
          // Tratamento da função
          escopoAtual.add(ctx.IDENT().getText(), TipoLa.REAL, TipoEta.FUNCAO, params);
        }
        
        //Cria novo escopo para os parâmetros da função/procedimento.
        
        escopos.criarNovoEscopo();
        
        // Trata parametros e desce na árvore da gramática.
        visitParametros(ctx.parametros());
        ctx.declaracao_local().forEach(local_ctx -> {
            visitDeclaracao_local(local_ctx);
        });
        
        // Em caso de comandos
        ctx.cmd().forEach(comand_ctx -> {
            visitCmd(comand_ctx);
        });
        
        //Independente de função ou procedimento, o escopo criado encerra ao término.
        //.pop() na pilha
        escopos.abandonarEscopo();
        
        return null;
    }
    
    /*
        declaracao_local: 'declare' variavel 
            | 'constante' identConstante=IDENT ':' tipo_basico '=' valor_constante
            | 'tipo' identTipo=IDENT ':' tipo;
    */
    @Override
    public Void visitDeclaracao_local(LaParser.Declaracao_localContext ctx){
        TabelaDeSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        // Caso 1: 'declare' variavel
        if(ctx.variavel() != null){
            visitVariavel(ctx.variavel());
        }
        
        // Caso 2: contante identConstante=IDENT: tipo(baseLa) = valor
        else if(ctx.tipo_basico() != null){
            String IDENT = ctx.identConstante.getText();
            TipoLa tipo = escopoAtual.getTipo(ctx.tipo_basico().getText());
            String valor = ctx.valor_constante().getText();
            
            escopoAtual.add(IDENT, tipo, valor);
        }
        
        //Caso 3: 'tipo' identTipo=IDENT: tipo 
        else{
            String IDENT = ctx.identTipo.getText();
            
            
            if(ctx.tipo().tipo_estendido() != null){
                
               /* tipo_estendido: ponteiro='^'? tipo_basico_ident; */
               boolean e_ponteiro = ctx.tipo().tipo_estendido().ponteiro != null;
               
               if(ctx.tipo().tipo_estendido().tipo_basico_ident() != null){
                   String tipo_basico_ident = ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText();
                   TipoLa tipo_ident = escopoAtual.getTipo(tipo_basico_ident);
                   // Necessário verificar se é ponteiro para diferenciação na tabela.
                  
                   
                   //Verifica se o identificador (nome variavel) já foi declarado.
                   if(escopoAtual.existe(IDENT)){
                      LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "identificador " + IDENT + " ja declarado anteriormente");
                   }
                   //Caso negativo, adiciona ao escopoAtual.
                   else{
                       escopoAtual.add(IDENT,tipo_ident,e_ponteiro);
                   }
               }
            }
            //Caso seja registro.
            /* registro: 'registro' variavel* 'fim_registro'; */
            else{
                
                TabelaDeSimbolos tabelaRegistro = new TabelaDeSimbolos();
                
                ctx.tipo().registro().variavel().forEach(variavel_ctx -> {
                  
                  //Igual na declaração comun, verifica ponteiro.
                    if(variavel_ctx.tipo().tipo_estendido() != null){
                       boolean e_ponteiro = variavel_ctx.tipo().tipo_estendido().ponteiro != null;

                        if(variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null){
                            String tipo_basico_ident = variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText();
                            TipoLa tipo_ident = escopoAtual.getTipo(tipo_basico_ident);
                            // Necessário verificar se é ponteiro para diferenciação na tabela.
                            
                          
                          //Porém agora verificamos a pré existência na tabelaRegistro.
                          for(LaParser.IdentificadorContext ident_ctx: variavel_ctx.identificador()){
                              
                              if(tabelaRegistro.existe(ident_ctx.getText())){
                                // Nenhum caso de teste nesse erro.
                                LaSemanticoUtils.adicionarErroSemantico(variavel_ctx.getStart(), "identificador " + ident_ctx.getText() + " ja declarado anteriormente");  
                              }
                              else{
                                  tabelaRegistro.add(ident_ctx.getText(),tipo_ident, e_ponteiro);
                              }
                          }
                        }
                    }
                });
                //Adiciona o registro e a sua tabela de variáveis no escopo atual.
               
                escopoAtual.add(IDENT, TipoLa.REGISTRO, IDENT, tabelaRegistro);
            }
                
        }
        return null;
    }
    
    
    /*
        variavel: identificador (',' identificador)* ':' tipo;
        identificador: ident1=IDENT ('.' ident2+=IDENT)* dimensao;
    */
    @Override
    @SuppressWarnings("empty-statement")

    public Void visitVariavel(LaParser.VariavelContext ctx){
       TabelaDeSimbolos escopoAtual = escopos.obterEscopoAtual();
       if(ctx.tipo().tipo_estendido() != null){
           boolean e_ponteiro = ctx.tipo().tipo_estendido().ponteiro != null;
           
           //Primeiro verifica o tipo e se ele é ponteiro
           if(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null){
               String IDENTtipo = ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText();
               if(e_ponteiro){
                   IDENTtipo = "^" + IDENTtipo;
               }
               for (LaParser.IdentificadorContext ident_ctx: ctx.identificador()){
                   String nomeIDENT = ident_ctx.ident1.getText();
                   if(escopoAtual.existe(nomeIDENT)){

                      //Casos de teste 5,17,18 
                      LaSemanticoUtils.adicionarErroSemantico(ident_ctx.getStart(), "identificador " + nomeIDENT + " ja declarado anteriormente"); 
                   }
                   else{
                       TipoLa tipo;
                       tipo = escopoAtual.getTipo(IDENTtipo);
                       escopoAtual.add(nomeIDENT, tipo, e_ponteiro);
                       
                   }
                   
               }
           }
           // Não é tipo estendido.
           else{
               String tipoIDENT = ctx.tipo().getText();
               TabelaDeSimbolos subTabela;

               //Recupera a subtabela caso o identificador exista no escopo.
               if(escopoAtual.existe(tipoIDENT)){
                   subTabela = escopoAtual.getSubTabela(tipoIDENT);

                  ctx.identificador().stream().map(ident_ctx -> {
                      String IDENT = ident_ctx.ident1.getText();
                      //Se o identificador atual já existe no escopo atual, trata-se de uma declaração duplicada.
                      if(escopoAtual.existe(IDENT)){
                          LaSemanticoUtils.adicionarErroSemantico(ident_ctx.getStart(), "identificador " + tipoIDENT + " ja declarado anteriormente"); 
                      }
                      return IDENT;
                  }).forEachOrdered(IDENT -> {
                      //Caso não ocorra erro, adiciona o identificador ao escopo
                      escopoAtual.add(IDENT, TipoLa.REGISTRO, tipoIDENT, subTabela);
                  });
               }
               else {
                   //Caso não exista o identificador no contexto atual, retorna um erro.
                   escopoAtual.add(ctx.identificador(0).getText(), TipoLa.INVALIDO, e_ponteiro);
                   LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "tipo " + tipoIDENT + " nao declarado" );
               }
           }
       }
       else {
           //Cria a subtabela para o novo registro.
           TabelaDeSimbolos subTabela = new TabelaDeSimbolos();
           ctx.tipo().registro().variavel().forEach(variavel_ctx -> {
               if(variavel_ctx.tipo().tipo_estendido() != null){
                   boolean e_ponteiro = variavel_ctx.tipo().tipo_estendido().ponteiro != null;
                   if(variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null){
                       String tipoIDENT = variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText();
                       if(e_ponteiro){
                           tipoIDENT = "^" + tipoIDENT;
                       }
                       for(LaParser.IdentificadorContext ident_ctx: variavel_ctx.identificador()){
                           String nomeIDENT = ident_ctx.ident1.getText();
                           //Se no contexto da tabela esse identificador já existe, temos um erro.
                           if(subTabela.existe(nomeIDENT)){
                               LaSemanticoUtils.adicionarErroSemantico(ident_ctx.getStart(), "identificador " + nomeIDENT + " ja declarado anteriormente ");
                           }
                           //Caso não exista, adicionamos ele a subtabela.
                           else{
                               subTabela.add(nomeIDENT, subTabela.getTipo(tipoIDENT), e_ponteiro);
                           }
                       }
                   }
               }
           });
           ctx.identificador().stream().map(ident_ctx -> {
               return ident_ctx;
           }).forEachOrdered(ident_ctx -> {
               escopoAtual.add(ident_ctx.ident1.getText(), TipoLa.REGISTRO, ident_ctx.ident1.getText(), subTabela);
           });
       }
       return null;
    }
    
   /*
        parcela_unario: '^'? identificador
                | IDENT '(' expressao (',' expressao)* ')'
                | NUM_INT
                | NUM_REAL
                | '(' parcelaUnarioExp=expressao ')';
    */
    @Override
    public Void visitParcela_unario(LaParser.Parcela_unarioContext ctx){
        
      if(ctx.IDENT()!= null){
        String IDENT = ctx.IDENT().getText();
        for(TabelaDeSimbolos tabela: escopos.percorrerEscoposAninhados()){
            if(tabela.existe(IDENT)){
                if(tabela.getParamsTipos(IDENT).size() != ctx.expressao().size()){
                    //Caso de teste 13
                    LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "incompatibilidade de parametros na chamada de " + IDENT);
                    return super.visitParcela_unario(ctx); 
                }
                else{
                    for (int i = 0; i < tabela.getParamsTipos(IDENT).size(); i++) {
                            // Tipo dos parametros incompatíveis
                            TipoLa params = verificador.verificaTipo(ctx.expressao(i), escopos);
                            //System.out.println("Chamada: " + IDENT );
                            //System.out.println("VEIO: " + params );
                            //System.out.println( "ESPERADO: " + tabela.getParamsTipos(IDENT).get(i));
                            if (!tabela.getParamsTipos(IDENT).get(i).equals(params)) {
                                //Caso de teste 13
                                LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "incompatibilidade de parametros na chamada de " + IDENT);
                                return super.visitParcela_unario(ctx);
                            }
                    }
                }
                return super.visitParcela_unario(ctx);
            }
        }
        LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "identificador " + IDENT + " nao declarado");  
      }
      return super.visitParcela_unario(ctx);
    } 
    
    @Override
    public Void visitParametro(LaParser.ParametroContext ctx){
        boolean e_ponteiro = ctx.tipo_estendido().ponteiro != null;
        
        if(ctx.tipo_estendido().tipo_basico_ident().tipo_basico() != null){
            String tipoIDENT = ctx.tipo_estendido().getText();
            TipoLa tipoLaIDENT = escopos.obterEscopoAtual().getTipo(tipoIDENT);
            
            //Para cada parametro, adiciona no escopo atual.
            for(LaParser.IdentificadorContext ident_ctx: ctx.identificador()){
                escopos.obterEscopoAtual().add(ident_ctx.getText(), tipoLaIDENT, e_ponteiro);
            }
            
        }
        else{
            String tipoRegistro = ctx.tipo_estendido().tipo_basico_ident().IDENT().getText();
            TabelaDeSimbolos subTabela = new TabelaDeSimbolos();
            
            //Pega a subtabela
            for(TabelaDeSimbolos tabela: escopos.percorrerEscoposAninhados()){
                if(tabela.existe(tipoRegistro)){
                    subTabela = tabela.getSubTabela(tipoRegistro);
                    break;
                }
            }
            //Adiciona o registro com a sua subtabela.
            for(LaParser.IdentificadorContext ident_ctx: ctx.identificador()){
                escopos.obterEscopoAtual().add(ident_ctx.getText(), TipoLa.REGISTRO, tipoRegistro, subTabela);
            }
        }
        
        return super.visitParametro(ctx);
    }
    
    @Override
    public Void visitIdentificador(LaParser.IdentificadorContext ctx){
        String IDENT = ctx.ident1.getText();
        if(escopos.obterEscopoAtual().existe(IDENT)){
            if(ctx.ident2.size() > 0){
                TabelaDeSimbolos subTabela = escopos.obterEscopoAtual().getSubTabela(IDENT);
                ctx.ident2.stream().filter(t -> (!subTabela.existe(t.getText()))).forEachOrdered(t -> {
                    LaSemanticoUtils.adicionarErroSemantico(t, "identificador " + ctx.getText() + " nao declarado");
                });
            }
        }
        else{
            if(ctx.ident2.size() > 0){
                LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "identificador " + IDENT + "." + ctx.ident2.get(0).getText() + " nao declarado");
            }
            else{
                LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "identificador " + IDENT + " nao declarado");
            }
        }
        
       return null;
    }
    
    @Override
    public Void visitCmdAtribuicao(LaParser.CmdAtribuicaoContext ctx){
        TabelaDeSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        if(escopoAtual.existe(ctx.identificador().ident1.getText())){
            
            //Caso em que é um registro
            if(ctx.identificador().ident2.size() > 0){
                
                //Montagem dos dados do registro.
                String reg = ctx.identificador().ident1.getText();
                String IDENT = ctx.identificador().ident2.get(0).getText();
                TabelaDeSimbolos subTabela = escopoAtual.getSubTabela(reg);
                TipoLa tipoIDENT = subTabela.verificarTipo(IDENT);
                TipoLa tipoExpressao = verificador.verificaTipo(ctx.expressao(),escopos);
                if(tipoIDENT.equals(TipoLa.REAL) && tipoExpressao.equals(TipoLa.INTEIRO)){
                    //Atribuição de um inteiro em um real não deve dar erro.   
                }
                else if(!tipoIDENT.equals(tipoExpressao)){
                    //Outros casos que não sejam o anterior geram erros.
                     LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "atribuicao nao compativel para "+ (subTabela.isPointer(IDENT) ? "^" : "") + reg + "." + IDENT);
                }
            }
            //Atribuição de variável (~registro)
            else{
                String IDENT = ctx.identificador().ident1.getText();
                TipoLa tipoIDENT = escopos.obterEscopoAtual().verificarTipo(IDENT);
                Boolean e_ponteiro = false;
                if(tipoIDENT.equals(TipoLa.REGISTRO)){
                    tipoIDENT = escopoAtual.getTipo(escopoAtual.getRegistro(IDENT));
                }
                if(ctx.ponteiro != null){
                    e_ponteiro = true;
                }
                
                TipoLa tipoExpressao = verificador.verificaTipo(ctx.expressao(),escopos);
                
                
                System.out.println("IDENT:" + IDENT);
                System.out.println("Esperado:" + tipoIDENT +"\nObtido: " + tipoExpressao);
                if(tipoIDENT.equals(TipoLa.REAL) && tipoExpressao.equals(TipoLa.INTEIRO)){
                    //Atribuição de um inteiro em um real não deve dar erro.   
                }
                else if(!tipoIDENT.equals(tipoExpressao)){
                    //Outros casos que não sejam o anterior geram erros.
                     LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "atribuicao nao compativel para "+ (e_ponteiro ? "^" : "") + ctx.identificador().getText());
                }
            
            }
        }
        
        return super.visitCmdAtribuicao(ctx);
    }
}
