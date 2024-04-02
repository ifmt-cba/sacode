package br.com.edu.ifmt.sacode.infrastructure.persistence.Categoria.CategoriaRepository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.infrastructure.persistence.Categoria.CategoriaORM.CategoriaORM;

public interface CategoriaRepository extends CrudRepository<Categoria, String>{
    CategoriaORM findByIdCategoria(String idCategoria);

    List<CategoriaORM> findByName(String categoriaNome);

    List<CategoriaORM> findByUsuarioId(String usuarioId);

    @Query("SELECT c FROM CATEGORIA c WHERE c.idCategoriaPai = :idCategoriaPai")
    List<CategoriaORM> findByCategoriaPaiByIdCategoria(String idCategoriaPai);

    
}
