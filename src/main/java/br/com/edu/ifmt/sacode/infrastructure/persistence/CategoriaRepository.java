package br.com.edu.ifmt.sacode.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends CrudRepository<CategoriaORM, String>{
    List<CategoriaORM> findByNomeContainingIgnoreCase(String categoriaNome);
    List<CategoriaORM> findByCategoriaSuperior(String categoriaSuperior);
    List<CategoriaORM> findByUsuarioIdUsuario(String usuarioId);

}
