package br.com.edu.ifmt.sacode.infrastructure.persistence;

import br.com.edu.ifmt.sacode.application.io.DespesaMensalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaORM, String> {

    DespesaORM findByIdDespesa(String idDespesa);

    int deleteByIdDespesaAndUsuario_IdUsuario(String idDespesa, String usuarioId);

    int deleteByUsuario_IdUsuarioAndCorrelacaoParcelas(String usuarioId, String correlacaoParcelas);

    List<DespesaORM> findByUsuario_IdUsuarioAndFixa(String usuarioId, Boolean fixa);

    List<DespesaORM> findByUsuario_IdUsuarioAndDataBetween(String usuarioId, LocalDate startData, LocalDate endData);

    List<DespesaORM> findByUsuario_IdUsuarioAndAutor(String usuarioId, String autor);

    List<DespesaORM> findByUsuario_IdUsuarioAndFinanciador(String usuarioId, String financiador);

    List<DespesaORM> findByUsuario_IdUsuario(String usuarioId);

    @Query("SELECT new br.com.edu.ifmt.sacode.application.io.DespesaMensalDTO(MONTH(d.data), c.nome, SUM(CAST(SUBSTRING(d.valor, 4) AS BigDecimal))) " +
            "FROM DespesaORM d " +
            "JOIN d.categoria c " +
            "WHERE YEAR(d.data) = :ano AND d.usuario.idUsuario = :idUsuario " +
            "GROUP BY MONTH(d.data), c.nome")
    List<DespesaMensalDTO> findDespesasPorAnoEUsuario(@Param("ano") int ano, @Param("idUsuario") String idUsuario);



}
