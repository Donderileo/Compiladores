/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.brffmpeg;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author donde
 */
public class BRFFSemanticoUtils {
    public static List<String> errosSemanticos = new ArrayList<>();
   
   public static void adicionarErroSemantico(Token t, String mensagem){
       int linha = t.getLine();
       //int coluna = t.getCharPositionInLine();
       
       errosSemanticos.add(String.format("Linha %d: %s", linha, mensagem));
   }
    public static void adicionarErroSemantico(String mensagem){

       errosSemanticos.add(String.format(mensagem));
   }
    
}

