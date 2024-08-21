package br.com.edu.ifmt.sacode.domain.entities;

import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;

import java.time.LocalDate;
import java.util.UUID;


public class Despesa {
    private UUID idDespesa;
    private Descricao descricao;
    private Moeda valor;
    private LocalDate data;
//    private Usuario usuario;
    private UUID usuario;
    private Nome autorDespesa;
    private Boolean fixa;
    private Nome financiadorDespesa;
    private UUID correlacaoParcelas;
    private Integer numParcela;
    private  Categoria categoria;

    // criação do obe
    public Despesa() {
        this.idDespesa = new UUID(0, 0);
        this.descricao = new Descricao();
        this.valor = Moeda.mockMoeda();
        this.data = null;
        this.usuario = null;
        this.autorDespesa = new Nome();
        this.fixa = null;
        this.financiadorDespesa = new Nome();
        this.correlacaoParcelas = null;
        this.numParcela = null;
        this.categoria = null;
    }

    // getters e setters padrão
    public UUID getIdDespesa(){return idDespesa;}

    public void setIdDespesa(UUID idDespesa) {this.idDespesa = idDespesa;}

    public Descricao getDescricao() {return descricao;}
    
    public void setDescricao(Descricao descricao) {this.descricao = descricao;}

    public Moeda getValor() {return valor;}

    public void setValor(Moeda valor) {this.valor = valor;}

    public LocalDate getData() {return data;}

    public void setData( LocalDate data) {this.data = data;}

    public Nome getAutorDespesa() {return autorDespesa;}

    public void setAutorDespesa(Nome autorDespesa) {this.autorDespesa = autorDespesa;}

    public Boolean isFixa() {return fixa;}

    public void setFixa(Boolean fixa) {this.fixa = fixa;}

    public Nome getFinanciadorDespesa() {return financiadorDespesa;}

    public void setFinanciadorDespesa(Nome financiadorDespesa) {this.financiadorDespesa = financiadorDespesa;}

    public UUID getCorrelacaoParcelas() {return correlacaoParcelas;}

    public void setCorrelacaoParcelas(UUID correlacaoParcelas) {this.correlacaoParcelas = correlacaoParcelas;}

    public Integer getNumParcela() {return numParcela;}

    public void setNumParcela(Integer numParcela) {this.numParcela = numParcela;}

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public UUID getUsuario() {
        return usuario;
    }

    public void setUsuario(UUID usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return String.format( """
                {
                    "idDespesa":"%s",                   
                    "descricao":"%s",                   
                    "valor":%s,
                    "data":"%s",
                    "usuario":"%s",
                    "autoDespesa":"%s",
                    "fixa": %s,
                    "financiadorDespesa":"%s",
                    "correlacaoParcelas":%s,
                    "numParcela":%s
                }
                """,
            this.idDespesa.toString(),
            this.descricao.toString(),
            this.valor.getValor(),
            this.data.toString(),
            this.usuario.toString(),
            this.autorDespesa.toString(),
            this.fixa.toString(),
            this.financiadorDespesa.toString(),
            this.correlacaoParcelas.toString(),
            this.numParcela.toString()
        );
    }
}