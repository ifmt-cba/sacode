package br.com.edu.ifmt.sacode.domain.services;


import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.edu.ifmt.sacode.domain.entities.User;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UserPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UserException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
public class CreateUserServiceTest {
    static CreateUserService createUserService;

    @BeforeAll
    static void init() {
        createUserService = new CreateUserService(new UserPort() {

            @Override
            public User createUser(User user) {
                return user;
            }

            @Override
            public User updateUser(User user) {
                return user;
            }

            @Override
            public void deleteUser(UUID id, User user){ }

            @Override
            public User getByIdUser(UUID userId){ return new User();}


        },
                new LogPort() {

                    @Override
                    public void trace(String msg) { }

                    @Override
                    public void debug(String msg) { }

                    @Override
                    public void info(String msg) { }

                    @Override
                    public void warn(String msg) { }

                    @Override
                    public void error(String msg) { }

                });
    }

    @Test
    void shouldThrowException() {
        assertThrows(UserException.class, () -> {
            createUserService.createUser(new User());
        });
    }

}