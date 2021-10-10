
package br.ufscar.dc.compiladores.la.semantico;

import java.util.HashMap;
import java.util.List;

public class TabelaDeSimbolos {
    
    public enum TipoLa {
        INTEIRO,
        REAL,
        INVALIDO,
        LITERAL,
        LOGICO,
        VAZIO,
        TIPOESTENDIDO,
        REGISTRO,
        NULL,
    }
    
    public enum TipoEta{
        VARIAVEL,
        FUNCAO,
        PROCEDIMENTO,
        TIPO,
        CONSTANTE,
        PARAMETRO,
    }
    
    class EntradaTabelaDeSimbolos{
        String nome;
        String valor;
        TipoLa tipo;
        TipoEta tipoEta;
        String nomeReg;
        List<TipoLa> paramsTipos;
        boolean pointer;
        TabelaDeSimbolos subTabela;
        
    }
    
    private final HashMap<String, EntradaTabelaDeSimbolos> tabela; 
    
    public TabelaDeSimbolos(){
        this.tabela = new HashMap<>();
    }
    
    public void add(String nome,TipoLa tipo, boolean pointer){
        //System.out.println("chamou add1");

        EntradaTabelaDeSimbolos etds = new EntradaTabelaDeSimbolos();
        etds.nome = nome;
        etds.tipo = tipo;
        etds.pointer = pointer;
        tabela.put(nome, etds);

    }
    
    public void add(String nome, TipoLa tipo, String valor){
        //System.out.println("chamou add2");

        EntradaTabelaDeSimbolos etds = new EntradaTabelaDeSimbolos();
        etds.nome = nome;
        etds.tipo = tipo;
        etds.valor = valor;
        tabela.put(nome,etds);
    }
    
    public void add(String nome, TipoLa tipo, String nomeReg, TabelaDeSimbolos subTabela ){
        //System.out.println("chamou add3");

        EntradaTabelaDeSimbolos etds = new EntradaTabelaDeSimbolos();
        etds.nome = nome;
        etds.tipo = tipo;
        etds.nomeReg = nomeReg;
        etds.subTabela = subTabela;
        tabela.put(nome,etds);
    }
    
     public void add(String nome, TipoLa tipo, TipoEta tipoEta, List<TipoLa> paramsTipos ){
        //System.out.println("chamou add4");
        EntradaTabelaDeSimbolos etds = new EntradaTabelaDeSimbolos();
        etds.nome = nome;
        etds.tipo = tipo;
        etds.tipoEta = tipoEta;
        etds.paramsTipos = paramsTipos;
        tabela.put(nome,etds);
    }
    public void add(String nome){
        //System.out.println("chamou add5 - regsitro");
        EntradaTabelaDeSimbolos etds = new EntradaTabelaDeSimbolos();
        etds.nome = nome;
        tabela.put(nome,etds);
    }
    
    
    public boolean existe(String nome){
        return tabela.containsKey(nome);
    }
    
    public TipoLa verificarTipo(String nome){
        return tabela.get(nome).tipo;
    }
    
    public TabelaDeSimbolos getSubTabela(String nome){
        return tabela.get(nome).subTabela;
    }
    
    public List<TipoLa> getParamsTipos(String nome){
        return tabela.get(nome).paramsTipos;
    }
    
    public TipoLa getTipo(String tipo){
        // Verificação dos tipos básicos.
        
        if("literal".equals(tipo)){
            return TipoLa.LITERAL;
        }
        else if("inteiro".equals(tipo)){
            return TipoLa.INTEIRO;
        }
        else if("real".equals(tipo)){
            return TipoLa.REAL;
        }
        else if("logico".equals(tipo)){
            return TipoLa.LOGICO;
        }
        else{
            return TipoLa.INVALIDO;
        }
    }
    
    public boolean getPointer(String nome){
        return tabela.get(nome).pointer;
    }
    
    public TipoEta getTipoEta(String nome){
        return tabela.get(nome).tipoEta;
    }
    
     public boolean isPointer(String nome){
        return tabela.get(nome).pointer;
    }
     
     public String getRegistro(String nome){
        return tabela.get(nome).nomeReg;
    }
    
}
