package br.com.edu.ifmt.sacode.infrastructure;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import br.com.edu.ifmt.sacode.domain.entities.User;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.infrastructure.adapters.UserRepositoryAdapter;
import br.com.edu.ifmt.sacode.infrastructure.mappers.UserORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UserORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UserRepository;

@SpringBootTest
public class UserRepositoryAdapterTest {

    UserRepositoryAdapter userRepositoryAdapter;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepositoryAdapter = new UserRepositoryAdapter(userRepository, new UserORMMapper(), new LogPortAdapter());
        var user1 = new UserORM();
        user1.setId(UUID.randomUUID().toString());
        user1.setEmail(null);
        user1.setNome("Jose");
        user1.setPassword("123");
        user1.setSuperusuario(false);
        user1.setMembroFamiliar(List.of("joao", "maria"));
        userRepository.save(user1);

    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();

    }

    @Test
    void test1() {
        var userID = UUID.randomUUID();
        var user1 = new UserORM();
        user1.setId(userID.toString());
        user1.setEmail(null);
        user1.setNome("Jose da Silva");
        user1.setPassword("44e0ce52a039177f58976cf227a50265e9ed4119f19b182ea08c40a75d3c1985");
        user1.setSuperusuario(false);
        user1.setMembroFamiliar(List.of());
        userRepository.save(user1);

        var persistenceUser = userRepositoryAdapter.buscaPorIdUsuario(userID);

        Assertions.assertEquals(user1.getId(), persistenceUser.getIdUsuario().toString());
        Assertions.assertEquals(user1.getNome(), persistenceUser.getName().toString());
        Assertions.assertEquals(user1.getPassword(), persistenceUser.getSenha().toString());
        Assertions.assertEquals(user1.getMembroFamiliar().size(), persistenceUser.getMembroFamiliar().size());

    }

    /**
     * LogPortAdapter
     */
    public class LogPortAdapter implements LogPort {

        @Override
        public void debug(String msg) {

        }

        @Override
        public void error(String msg) {

        }

        @Override
        public void info(String msg) {

        }

        @Override
        public void trace(String msg) {

        }

        @Override
        public void warn(String msg) {

        }

    }
}
