package br.com.edu.ifmt.sacode.domain.entities;
import java.util.Date;

import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.MembroFamiliar;


public class Despesa {
    
    private Descricao descricao;
    private float valor;
    private Date date;
    private User usuario;
    private MembroFamiliar autorDespesa;
    private boolean fixa;
    private MembroFamiliar financiadorDespesa;
    private Despesa despesaOriginal;
    private int numParcela;


    public Despesa() {
        this.descricao = null;
        this.valor = 0;
        this.date = null;
        this.usuario = null;
        this.autorDespesa = new MembroFamiliar(null);
        this.fixa = false;
        this.financiadorDespesa = new MembroFamiliar(null);
        this.despesaOriginal = new Despesa();
        this.numParcela = 0;
    }
  
    public Descricao getDescricao() {return descricao;}
    
    public void setDescricao(Descricao descricao) {this.descricao = descricao;}

    public float getValor() {return valor;}

    public void setValor(float valor) {this.valor = valor;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public User getUsuario() {return usuario;}

    public void setUsuario(User usuario) {this.usuario = usuario; }

    public MembroFamiliar getAutorDespesa() {return autorDespesa;}

    public void setAutorDespesa(MembroFamiliar autorDespesa) {this.autorDespesa = autorDespesa;}

    public boolean isFixa() {return fixa;}

    public void setFixa(boolean fixa) {this.fixa = fixa;}

    public MembroFamiliar getFinanciadorDespesa() {return financiadorDespesa;}

    public void setFinanciadorDespesa(MembroFamiliar financiadorDespesa) {this.financiadorDespesa = financiadorDespesa;}

    public Despesa getDespesaOriginal() {return despesaOriginal;}

    public void setDespesaOriginal(Despesa despesaOriginal) {this.despesaOriginal = despesaOriginal;}

    public int getNumParcela() {return numParcela;}

    public void setNumParcela(int numParcela) {this.numParcela = numParcela;}   

    
    @Override
    public String toString() {
        return String.format( """
                {
                    "descricao":"%s",                   
                    "valor":%s,
                    "date":"%s",
                    "usuario":"%s",
                    "autoDespesa":"%s",
                    "fixa": %s,
                    "financiadorDespesa":"%s",
                    "despesaOriginal":%s,
                    "numParcela":%s
                }
                """,
            this.descricao.toString(),
            Float.toString(this.valor),
            this.date.toString(),
            this.usuario.toString(),
            this.autorDespesa.toString(),
            Boolean.toString(this.fixa),
            this.financiadorDespesa.toString(),
            this.despesaOriginal.toString(),
            Integer.toString(this.numParcela)
        );
    }
}