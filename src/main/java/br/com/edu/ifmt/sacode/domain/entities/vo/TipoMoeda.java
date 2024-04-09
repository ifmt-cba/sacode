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

    @Override
    public String toString(){
        return codigo;
    }

    public static TipoMoeda match(String codigo){
        switch (codigo) {
            case "BRL":
            return TipoMoeda.REAL;
            case "USD":
            return TipoMoeda.DOLAR;
            case "EUR":
            return TipoMoeda.EURO;
            case "JPY":
            return TipoMoeda.IENE;
            case "ARS":
            return TipoMoeda.PESO_ARGENTINO;
            case "MXN":
            return TipoMoeda.PESO_MEXICANO;
            default:
                return null;
        }
    }
}
