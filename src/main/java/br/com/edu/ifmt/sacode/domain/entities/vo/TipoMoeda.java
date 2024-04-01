package br.com.edu.ifmt.sacode.domain.entities.vo;

public enum TipoMoeda {
    REAL("BRL", "Real brasileiro", "R$"),
    DOLAR("USD", "Dólar americano", "$"),
    EURO("EUR", "Euro", "€"),
    IENE("JPY", "Iene japonês", "¥"),
    PESO_ARGENTINO("ARS", "Peso argentino", "$"),
    PESO_MEXICANO("MXN", "Peso mexicano", "$");

    private final String codigo;
    private final String descricao;
    private final String simbolo;

    TipoMoeda(String codigo, String descricao, String simbolo) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.simbolo = simbolo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
