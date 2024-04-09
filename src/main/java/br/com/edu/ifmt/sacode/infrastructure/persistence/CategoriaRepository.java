package br.com.edu.ifmt.sacode.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<CategoriaORM, String>{
    List<CategoriaORM> findByNome(String categoriaNome);

//    List<CategoriaORM> findByUsuarioId(String usuarioId);

//    @Query("SELECT c FROM CategoriaORM c WHERE c.categoriaPai = :idCategoriaPai")
//    List<CategoriaORM> findByCategoriaPaiAndIdCategoria(String idCategoriaPai);

    
}
