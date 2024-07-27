package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public interface DespesaPort {
    Despesa criarDespesa(Despesa despesa);

    Despesa atualizarDespesa(Despesa despesa);

    int excluirDespesa(UUID idDespesa, UUID usuario);

    Despesa buscarDespesa(UUID despesaId);

    int excluirParcelas(UUID usuario, UUID correlacaoParcleas);

    List<Despesa> buscarDespesasPorPeriodo(UUID usuario, LocalDate primeiroDia, LocalDate ultimoDia);
    List<Despesa> buscarDespesasPorAutor(UUID usuario, String autor);
    List<Despesa> buscarDespesasPorFinanciador(UUID usuario, String financiador);
    List<Despesa> buscarDespesasPorUsuario(UUID usuario);


}