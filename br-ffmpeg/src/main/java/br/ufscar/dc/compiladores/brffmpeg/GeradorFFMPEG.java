/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.brffmpeg;

import java.io.File;

/**
 *
 * @author donde
 */
public class GeradorFFMPEG extends BRFFBaseVisitor<Void>{
    public StringBuilder saida;
    TabelaDeSimbolos tabela; 
    boolean tem_cut = false;
    boolean tem_speed = false;
    boolean tem_sub = false;
    int modify_count = 0;
    
    public GeradorFFMPEG(){
       saida = new StringBuilder();
       this.tabela = new TabelaDeSimbolos();
   }
   
    public String tiraAspas(String str){
        String path = str.substring(1, str.length() - 1);
        return path;
    }

    public double converteSpeed(String velocidade){
        int number = Integer.parseInt(velocidade);
        return 1.0/number;
    }  
    
    
    
    @Override 
    public Void visitPrograma(BRFFParser.ProgramaContext ctx) { 
        
        //Percorre a árvore salvando as informações.
        super.visitPrograma(ctx);
        String saida_orig = tabela.getCadeia("SAIDA");
        String arquivo_temp = "temp.mp4";

        
        if(tem_cut){
            saida.append("ffmpeg");
            saida.append(" -ss " + tabela.getValor("TEMPO_INICIO"));
            saida.append(" -i " + tabela.getCadeia("ENTRADA"));
            saida.append(" -to " + tabela.getValor("TEMPO_FIM"));
            if(tem_speed || tem_sub){
                saida.append(" -c copy " + arquivo_temp);  
            }
            else{
                saida.append(" -c copy " + saida_orig);  
            }
        }
        
        if(tem_speed){
            if(tem_cut){
               saida.append(" && ");
            } 
            
            saida.append("ffmpeg");
            
            if(tem_cut){
                saida.append(" -i " + arquivo_temp);  
            }
            else{
               saida.append(" -i " + tabela.getCadeia("ENTRADA"));
            }

            saida.append(" -vf \"setpts=");
            saida.append(converteSpeed(tabela.getValor("VELOCIDADE")));
            saida.append("*PTS\"");

            System.out.println(tabela.getValor("OPCAO"));
            if("Y".equals(tabela.getValor("OPCAO"))){
                saida.append(" -af \"atempo=");
                saida.append(tabela.getValor("VELOCIDADE") +"\" ");
            }
            else{
                saida.append(" -an ");
            }
            
            if(tem_sub){
                saida.append(arquivo_temp);
            }
            else{
                saida.append(saida_orig);
            }
        }
        
        if(tem_sub){
             if(tem_cut || tem_speed){
               saida.append(" && ");
            } 
            saida.append("ffmpeg");
            if(tem_cut || tem_speed){
                saida.append(" -i " + arquivo_temp);  
            }
            else{
                saida.append(" -i " + tabela.getCadeia("ENTRADA"));
            }
            
            saida.append(" -i " + tabela.getCadeia("LEGENDA"));
            saida.append(" -map 0 -map 1 ");
            saida.append(" -c copy -c:s mov_text" + tabela.getCadeia("SAIDA")); 
        }
        
        
        if(modify_count > 1){
            saida.append(" && del " + arquivo_temp);
        }
        return null;
        
        
    }
    
    @Override 
    public Void visitIn(BRFFParser.InContext ctx) { 
        tabela.add_cmd_cadeia("ENTRADA", tiraAspas(ctx.path.getText()));
        return super.visitIn(ctx);
    }
    
    @Override
    public Void visitOut(BRFFParser.OutContext ctx) { 
        tabela.add_cmd_cadeia("SAIDA", tiraAspas(ctx.path.getText()));
        return super.visitOut(ctx);
    }

    @Override 
    public Void visitCorpo_cortar(BRFFParser.Corpo_cortarContext ctx) { 
       
        tem_cut = true;
        modify_count += 1;
        
        tabela.add_cmd_valor("TEMPO_INICIO", ctx.start_time.getText());
        tabela.add_cmd_valor("TEMPO_FIM", ctx.finish_time.getText());

        

    
        return visitChildren(ctx); 
    }   

    @Override 
    public Void visitCorpo_acelerar(BRFFParser.Corpo_acelerarContext ctx) { 
        tem_speed = true;
        modify_count += 1;

        tabela.add_cmd_valor("VELOCIDADE", ctx.vel.getText());
        tabela.add_cmd_valor("OPCAO", ctx.option.getText());

        
           /*
        saida.append("ffmpeg");
        saida.append(" -i " + tabela.getCadeia("ENTRADA"));
        saida.append(" -vf \"setpts=");
        saida.append(converteSpeed(ctx.vel.getText()));
        saida.append("*PTS\"");
        
        if(ctx.option.getText().equals('Y') || ctx.option.getText().equals('y')){
            saida.append(" -af \"atempo=");
            saida.append(ctx.vel.getText() +"\"");
        }
        else{
            saida.append(" -an ");
        }
        saida.append(tabela.getCadeia("SAIDA"));

        
            */
        return visitChildren(ctx); 
    }

      
    @Override 
    public Void visitSubtitle(BRFFParser.SubtitleContext ctx) { 
        tem_sub = true;
        modify_count += 1;

        tabela.add_cmd_cadeia("LEGENDA", ctx.path.getText());
        
//        saida.append("ffmpeg");
//        saida.append(" -i " + tabela.getCadeia("ENTRADA"));
//        saida.append(" -i " + ctx.path.getText());
//        saida.append(" -map 0 -map 1 ");
//        saida.append(" -c copy -c:s mov_text" + tabela.getCadeia("SAIDA"));
        return visitChildren(ctx); 
    }
    
}
   


