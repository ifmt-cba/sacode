package br.com.edu.ifmt.sacode.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends CrudRepository<DespesaORM, String> {

    DespesaORM findByIdDespesa(String idDespesa);

    int deleteByIdDespesaAndUsuario_IdUsuario(String idDespesa, String usuarioId);

    int deleteByUsuario_IdUsuarioAndCorrelacaoParcelas(String usuarioId, String correlacaoParcelas);

    List<DespesaORM> findByUsuario_IdUsuarioAndFixa(String usuarioId, Boolean fixa);

    List<DespesaORM> findByUsuario_IdUsuarioAndDataBetween(String usuarioId, LocalDate startData, LocalDate endData);

    List<DespesaORM> findByUsuario_IdUsuarioAndAutor(String usuarioId, String autor);

    List<DespesaORM> findByUsuario_IdUsuarioAndFinanciador(String usuarioId, String financiador);

    List<DespesaORM> findByUsuario_IdUsuario(String usuarioId);
}
