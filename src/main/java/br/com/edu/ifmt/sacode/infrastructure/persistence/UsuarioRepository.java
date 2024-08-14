package br.com.edu.ifmt.sacode.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioORM, String> {
    UsuarioORM findByIdUsuario(String id);
    Optional<UsuarioORM> findByEmail(String email);
}
