package br.com.edu.ifmt.sacode.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends CrudRepository<DespesaORM, Long> {
    DespesaORM findByIdDespesa(String idDespesa);
    int deleteByIdDespesaAndUsuario(String idDespesa, String usuario);
    int deleteByUsuarioAndCorrelacaoParcelas(String usuario, String correlacaoParcelas);
    List<DespesaORM> findByUsuarioAndFixa(String usuario, Boolean fixa);
    List<DespesaORM> findByUsuarioAndDataBetween(String usuario, LocalDate startData, LocalDate endData);
    List<DespesaORM> findByUsuarioAndAutor(String usuario, String autor);
    List<DespesaORM> findByUsuarioAndFinanciador(String usuario, String financiador);
}
