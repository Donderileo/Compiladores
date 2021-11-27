/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.brffmpeg;

import java.util.HashMap;

/**
 *
 * @author donde
 */
public class TabelaDeSimbolos {
    class EntradaTabelaDeSimbolos{
        String comando;
        String cadeia;
        String valor;
    }
    
    private final HashMap<String, EntradaTabelaDeSimbolos> tabela; 
    
     public TabelaDeSimbolos(){
        this.tabela = new HashMap<>();
    }
     
     public void add_cmd_cadeia(String comando, String cadeia){
         EntradaTabelaDeSimbolos eta = new EntradaTabelaDeSimbolos();
         eta.comando = comando;
         eta.cadeia = cadeia;
        tabela.put(comando, eta);

     }
     
     
     public void add_cmd_valor(String comando, String valor){
         EntradaTabelaDeSimbolos eta = new EntradaTabelaDeSimbolos();
         eta.comando = comando;
         eta.valor = valor;
         tabela.put(comando, eta);
     }
     
     public boolean existe(String nome){
        return tabela.containsKey(nome);
    }
     
    public String getCadeia(String nome){
        return tabela.get(nome).cadeia;
    }
    
     public String getValor(String nome){
        return tabela.get(nome).valor;
    }
}
