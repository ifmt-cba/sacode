package br.com.edu.ifmt.sacode.infrastructure.persistence;
import org.springframework.data.repository.CrudRepository;


public interface UsuarioRepository extends CrudRepository<UsuarioORM, Long> {
    UsuarioORM findById(String id);
}
