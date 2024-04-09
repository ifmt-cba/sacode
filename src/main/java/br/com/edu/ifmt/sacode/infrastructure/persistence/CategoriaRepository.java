package br.com.edu.ifmt.sacode.infrastructure.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<CategoriaORM, String>{
    List<CategoriaORM> findByNome(String categoriaNome);
    List<CategoriaORM> findByCategoriaPai(String categoriaPai);    
}
