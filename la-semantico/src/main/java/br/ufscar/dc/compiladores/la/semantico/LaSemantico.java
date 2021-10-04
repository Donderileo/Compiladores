package br.ufscar.dc.compiladores.la.semantico;

import br.ufscar.dc.compiladores.la.semantico.TabelaDeSimbolos.TipoLa;
import br.ufscar.dc.compiladores.la.semantico.TabelaDeSimbolos.TipoEta;
import java.util.ArrayList;
import java.util.List;

public class LaSemantico extends LaBaseVisitor<Void> {

    Escopos escopos = new Escopos();

    @Override
    public Void visitPrograma(LaParser.ProgramaContext ctx) {
        
        if (ctx.corpo() != null) {
            if (ctx.corpo().cmd() != null) {
                // Comando de retorno permitido somente em funções. Se existir retorna erro
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
        
        
        // Diferença entre eles.
        if(ctx.tipo_estendido() == null){ //Caso não tenha o tipo estendido, procedimento.
            procedimento = true;
        }
        else{ // caso tenha, procedimento
            procedimento = false;
        }
        
        if(procedimento){
           //Tratamento do procedimento 
           escopoAtual.add(ctx.IDENT().getText(), TipoLa.VAZIO, TipoEta.PROCEDIMENTO, params);
           ctx.cmd().stream().filter(comand_ctx -> (comand_ctx.cmdRetorne() != null)).forEachOrdered(comand_ctx -> {
                // Erro do caso de teste 15
                LaSemanticoUtils.adicionarErroSemantico(comand_ctx.cmdRetorne().getStart(), "comando retorne nao permitido nesse escopo");
            });
        }
        else{
          // Tratamento da função
          escopoAtual.add(ctx.IDENT().getText(), TipoLa.VAZIO, TipoEta.FUNCAO, params);
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
                   String tipo_basico_ident = ctx.tipo().tipo_estendido().tipo_basico_ident().getText();
                   TipoLa tipo_ident = escopoAtual.getTipo(tipo_basico_ident);
                   // Necessário verificar se é ponteiro para diferenciação na tabela.
                   if(e_ponteiro){
                       tipo_basico_ident = "^" + tipo_basico_ident;
                   }
                   
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

                        if(variavel_ctx.tipo().tipo_estendido().tipo_basico_ident() != null){
                            String tipo_basico_ident = variavel_ctx.tipo().tipo_estendido().tipo_basico_ident().getText();
                            TipoLa tipo_ident = escopoAtual.getTipo(tipo_basico_ident);
                            // Necessário verificar se é ponteiro para diferenciação na tabela.
                            if(e_ponteiro){
                                tipo_basico_ident = "^" + tipo_basico_ident;
                            }
                          
                          //Porém agora verificamos a pré existência na tabelaRegistro.
                          for(LaParser.IdentificadorContext identificador: variavel_ctx.identificador()){
                              
                              if(tabelaRegistro.existe(identificador.getText())){
                                // Nenhum caso de teste nesse erro.
                                LaSemanticoUtils.adicionarErroSemantico(variavel_ctx.getStart(), "identificador " + identificador.getText() + " ja declarado anteriormente");  
                              }
                              else{
                                  tabelaRegistro.add(identificador.getText());
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
                System.out.println(IDENTtipo);
               for (LaParser.IdentificadorContext ident_ctx: ctx.identificador()){
                   String nomeIDENT = ident_ctx.ident1.getText();
                   System.out.println(nomeIDENT);
                   if(escopoAtual.existe(nomeIDENT)){
                      System.out.println(nomeIDENT +  " Deu erro");

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
               TabelaDeSimbolos subTabela = new TabelaDeSimbolos();


               if(escopoAtual.existe(tipoIDENT)){
                  ctx.identificador().stream().map(ictx -> {
                      String nomeIDENT = ictx.ident1.getText();
                      if(escopoAtual.existe(nomeIDENT)){
                          LaSemanticoUtils.adicionarErroSemantico(ictx.getStart(), "identificador " + tipoIDENT + " ja declarado anteriormente"); 
                      }
                      else{
                          
                      }
                      return nomeIDENT;
                  }).forEachOrdered(nomeIDENT -> {
                      escopoAtual.add(nomeIDENT, TipoLa.REGISTRO, tipoIDENT, subTabela);
                  });
               }
               else {
                   escopoAtual.add(ctx.identificador(0).getText(), TipoLa.INVALIDO, e_ponteiro);
                   LaSemanticoUtils.adicionarErroSemantico(ctx.getStart(), "tipo " + tipoIDENT + " nao declarado" );
               }
           }
       }
       else {
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
                           if(subTabela.existe(nomeIDENT)){
                               LaSemanticoUtils.adicionarErroSemantico(ident_ctx.getStart(), "identificador " + nomeIDENT + " ja declarado anteriormente ");
                           }
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
               escopoAtual.add(ident_ctx.getText(), TipoLa.REGISTRO, ident_ctx.ident1.getText(), subTabela);
           });
       }
       return null;
    }
    
    
}
