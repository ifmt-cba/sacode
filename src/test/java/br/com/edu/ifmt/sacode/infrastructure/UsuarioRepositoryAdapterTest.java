package br.com.edu.ifmt.sacode.infrastructure;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;

import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import br.com.edu.ifmt.sacode.infrastructure.adapters.LogAdapter;
import br.com.edu.ifmt.sacode.infrastructure.adapters.UsuarioRepositoryAdapter;
import br.com.edu.ifmt.sacode.infrastructure.mappers.UsuarioORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryAdapterTest {

        UsuarioRepositoryAdapter usuarioRepositoryAdapter;

        @Autowired
        UsuarioRepository usuarioRepository;

        UsuarioORM usuario = new UsuarioORM();
        UsuarioORM usuario2 = new UsuarioORM();
        UsuarioORM usuario3 = new UsuarioORM();

        UUID usuarioId, usuarioId2, usuarioId3;

        @BeforeEach
        void setUp() {
                usuarioRepositoryAdapter = new UsuarioRepositoryAdapter(usuarioRepository,
                                new UsuarioORMMapper(),
                                new LogAdapter());

                usuario.setEmail("josesilva@gmail.com");
                usuario.setNome("Jose da Silva");
                usuario.setUsername("Jose");
                usuario.setPassword("44e0ce52a039177f58976cf227a50265e9ed4119f19b182ea08c40a75d3c1985");
                usuario.setSuperusuario(false);
//                usuario.setMembroFamiliar(List.of());
                usuarioId = UUID.randomUUID();
                usuario.setIdUsuario(usuarioId.toString());

                usuario2.setEmail("joaosouza@gmail.com");
                usuario2.setNome("Joao da Souza");
                usuario2.setUsername("Jao");
                usuario2.setPassword("44e0ce74a039177f58976cf227a54085e9ed4119f19b182ea08c40a75d3c8547");
                usuario2.setSuperusuario(true);
//                usuario2.setMembroFamiliar(List.of());
                usuarioId2 = UUID.randomUUID();
                usuario2.setIdUsuario(usuarioId2.toString());

                usuario3.setEmail("joanaouza@gmail.com");
                usuario3.setNome("Joana da Souza");
                usuario3.setUsername("Jojo");
                usuario3.setPassword("487e0ce74a39177f58976cf426a54085e9ed4119f19t782ea08c40a75d3c8547");
                usuario3.setSuperusuario(false);
//                usuario3.setMembroFamiliar(List.of());
                usuarioId3 = UUID.randomUUID();
                usuario3.setIdUsuario(usuarioId3.toString());

        }

        @AfterEach
        void cleanUp() {
                usuarioRepository.deleteAll();

        }

        @Test
        void criarUsuario() {
                usuarioRepository.save(usuario);

                var persistenceUsuario = usuarioRepositoryAdapter.buscarPorIdUsuario(usuarioId);

                Assertions.assertEquals(usuario.getIdUsuario(), persistenceUsuario.getIdUsuario().toString());
                Assertions.assertEquals(usuario.getNome(),
                                persistenceUsuario.getName().toString());
                Assertions.assertEquals(usuario.getPassword(),
                                persistenceUsuario.getSenha().toString());
//                Assertions.assertEquals(usuario.getMembroFamiliar().size(),
//                                persistenceUsuario.getMembroFamiliar().size());

        }

        @Test
        void buscarPorIdUsuario() {
                usuarioRepository.saveAll(List.of(usuario, usuario2, usuario3));

                var persistenceUsuario = usuarioRepositoryAdapter.buscarPorIdUsuario(usuarioId2);

                Assertions.assertEquals(usuario2.getIdUsuario(), persistenceUsuario.getIdUsuario().toString());
                Assertions.assertEquals(usuario2.getNome(),
                                persistenceUsuario.getName().toString());
                Assertions.assertEquals(usuario2.getPassword(),
                                persistenceUsuario.getSenha().toString());
//                Assertions.assertEquals(usuario2.getMembroFamiliar().size(),
//                                persistenceUsuario.getMembroFamiliar().size());

        }

        @Test
        void DeletarUsuario() {
                usuarioRepository.save(usuario3);
                usuarioRepository.delete(usuario3);

                Assertions.assertFalse(usuarioRepository.existsById(usuario3.getIdUsuario()));
        }

}
