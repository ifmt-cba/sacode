package br.com.edu.ifmt.sacode.application.io;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;

import java.time.LocalDate;

public record DespesaRequest(String idDespesa, String descricao, String valor, LocalDate data, String usuario,
                             String autorDespesa, Boolean fixa, String financiadorDespesa, String correlacaoParcelas,
                             Integer numParcela, String categoriaId) {
}
