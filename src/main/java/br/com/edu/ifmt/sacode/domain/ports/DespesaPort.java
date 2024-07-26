package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DespesaPort {
    Despesa criarDespesa(Despesa despesa);

    Despesa atualizarDespesa(Despesa despesa);

    int excluirDespesa(UUID idDespesa, UUID usuario);

    Despesa buscarDespesa(UUID despesaId);

    int excluirParcelas(UUID usuario, UUID correlacaoParcleas);

    List<Despesa> buscarDespesaPorID(UUID usuario);
    List<Despesa> buscarDespesaPorPeriodo(UUID usuario, LocalDate primeiroDia, LocalDate ultimoDia);
    List<Despesa> buscarDespesaPorAutor(UUID usuario, String autor);
    List<Despesa> buscarDespesaPorFinanciador(UUID usuario, String financiador);
    List<Despesa> buscarDespesaPorUsuario(UUID usuario);

}