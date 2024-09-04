package br.com.edu.ifmt.sacode.application.io;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DespesaMensalDTO {
    private int mes;
    private String categoria;
    private BigDecimal total;

    public DespesaMensalDTO(int mes, String categoria, BigDecimal total) {
        this.mes = mes;
        this.categoria = categoria;
        this.total = total;
    }

}