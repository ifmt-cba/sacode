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

import br.com.edu.ifmt.sacode.infrastructure.adapters.LogAdapter;
import br.com.edu.ifmt.sacode.infrastructure.adapters.UsuarioRepositoryAdapter;
import br.com.edu.ifmt.sacode.infrastructure.mappers.UsuarioORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioRepository;

@SpringBootTest
public class UsuarioRepositoryAdapterTest {

    UsuarioRepositoryAdapter userRepositoryAdapter;
    @Autowired
    UsuarioRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepositoryAdapter = new UsuarioRepositoryAdapter(userRepository, new UsuarioORMMapper(), new LogAdapter());

    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();

    }

    @Test
    void test1() {
        var usuarioId = UUID.randomUUID();
        var usuario = new UsuarioORM();
        usuario.setId(usuarioId.toString());
        usuario.setEmail(null);
        usuario.setNome("Jose da Silva");
        usuario.setPassword("44e0ce52a039177f58976cf227a50265e9ed4119f19b182ea08c40a75d3c1985");
        usuario.setSuperusuario(false);
        usuario.setMembroFamiliar(List.of());
        userRepository.save(usuario);

        var persistenceUser = userRepositoryAdapter.buscaPorIdUsuario(usuarioId);

        Assertions.assertEquals(usuario.getId(), persistenceUser.getIdUsuario().toString());
        Assertions.assertEquals(usuario.getNome(), persistenceUser.getName().toString());
        Assertions.assertEquals(usuario.getPassword(), persistenceUser.getSenha().toString());
        Assertions.assertEquals(usuario.getMembroFamiliar().size(), persistenceUser.getMembroFamiliar().size());

    }

}
