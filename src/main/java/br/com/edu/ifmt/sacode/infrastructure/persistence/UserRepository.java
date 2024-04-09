package br.com.edu.ifmt.sacode.infrastructure.persistence;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserORM, Long> {
    UserORM findById(String id);
}
