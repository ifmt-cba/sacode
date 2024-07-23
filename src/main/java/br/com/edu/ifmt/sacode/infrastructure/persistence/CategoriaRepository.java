package br.com.edu.ifmt.sacode.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends CrudRepository<CategoriaORM, String>{
    List<CategoriaORM> findByNome(String categoriaNome);
    List<CategoriaORM> findByCategoriaPai(String categoriaPai);
    List<CategoriaORM> findByUsuario(String string);
}
