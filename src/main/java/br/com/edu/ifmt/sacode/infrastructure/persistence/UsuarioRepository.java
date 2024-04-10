package br.com.edu.ifmt.sacode.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<UsuarioORM, String> {

}
