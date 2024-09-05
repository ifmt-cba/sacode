package br.com.edu.ifmt.sacode.application.io;

import java.time.LocalDate;

public record DespesaResponse(String idDespesa, String descricao, String valor, LocalDate data, String usuario, String autorDespesa,
                              Boolean fixa, String financiadorDespesa, String correlacaoParcelas, Integer numParcela,
                              CategoriaResponse categoriaResponse) {
}
