package br.com.edu.ifmt.sacode.infrastructure;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.edu.ifmt.sacode.infrastructure.adapters.LogAdapter;
import br.com.edu.ifmt.sacode.infrastructure.adapters.UsuarioRepositoryAdapter;
import br.com.edu.ifmt.sacode.infrastructure.mappers.UsuarioORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryAdapterTest {

    UsuarioRepositoryAdapter usuarioRepositoryAdapter;

    @Autowired
    UsuarioRepository usuarioRepository;

    @BeforeAll
    void setUp() {
        usuarioRepositoryAdapter = new UsuarioRepositoryAdapter(usuarioRepository,
                new UsuarioORMMapper(),
                new LogAdapter());

    }

    @AfterAll
    void cleanUp() {
        usuarioRepository.deleteAll();

    }

    @Test
    void test1() {
        var usuarioId = UUID.randomUUID();
        var usuario = new UsuarioORM();
        usuario.setIdUsuario(usuarioId.toString());
        usuario.setEmail("josesilva@gmail.com");
        usuario.setNome("Jose da Silva");
        usuario.setUsername("Jose");
        usuario.setPassword("44e0ce52a039177f58976cf227a50265e9ed4119f19b182ea08c40a75d3c1985");
        usuario.setSuperusuario(false);
        usuario.setMembroFamiliar(List.of());
        usuarioRepository.save(usuario);

        var persistenceUsuario = usuarioRepositoryAdapter.buscaPorIdUsuario(usuarioId);

        Assertions.assertEquals(usuario.getIdUsuario(), persistenceUsuario.getIdUsuario().toString());
        Assertions.assertEquals(usuario.getNome(),
                persistenceUsuario.getName().toString());
        Assertions.assertEquals(usuario.getPassword(),
                persistenceUsuario.getSenha().toString());
        Assertions.assertEquals(usuario.getMembroFamiliar().size(),
                persistenceUsuario.getMembroFamiliar().size());

        usuarioRepository.delete(usuario);

    }

}
