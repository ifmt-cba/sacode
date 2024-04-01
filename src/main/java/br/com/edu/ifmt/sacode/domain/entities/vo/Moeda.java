package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.math.BigDecimal;

public class Moeda {
    private TipoMoeda tipoMoeda;
    private BigDecimal valor;

    public Moeda(TipoMoeda tipoMoeda, BigDecimal valor) {
        this.tipoMoeda = tipoMoeda;
        this.valor = valor;
    }

    public String getCodigo() {
        return this.tipoMoeda.getCodigo();
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor){
        this.valor = valor;
    }

    public Moeda mockMoeda()
    {
        return new Moeda(TipoMoeda.REAL, new BigDecimal(100));
    }
}
