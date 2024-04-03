package br.com.edu.ifmt.sacode.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<CategoriaORM, String>{
    CategoriaORM findByIdCategoria(String idCategoria);

    List<CategoriaORM> findByNome(String categoriaNome);

    List<CategoriaORM> findByUsuarioId(String usuarioId);

    @Query("SELECT c FROM categoria c WHERE c.idCategoriaPai = :idCategoriaPai")
    List<CategoriaORM> findByCategoriaPaiByIdCategoria(String idCategoriaPai);
}
