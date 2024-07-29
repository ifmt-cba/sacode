package br.com.edu.ifmt.sacode.infrastructure;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.infrastructure.adapters.CategoriaRepositoryAdapter;
import br.com.edu.ifmt.sacode.infrastructure.adapters.LogAdapter;
import br.com.edu.ifmt.sacode.infrastructure.mappers.CategoriaORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaRepository;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriaRepositoryAdapterTest {

    CategoriaRepositoryAdapter categoriaRepositoryAdapter;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    UsuarioORM usuario = new UsuarioORM();

    CategoriaORM categoria;

    UUID categoriaId;

    UUID categoriaSuperior;

    @BeforeEach
    void setUp() {
        categoriaRepositoryAdapter = new CategoriaRepositoryAdapter(categoriaRepository, new CategoriaORMMapper(),
                new LogAdapter());

        var usuarioId = UUID.randomUUID();
        usuario.setIdUsuario(usuarioId.toString());
        usuario.setEmail("mariasilva@gmail.com");
        usuario.setNome("Maria da Silva");
        usuario.setUsername("Maria");
        usuario.setPassword("44e0ce52a039177f58976cf227a50265e9ed4119f19b182ea08c40a75d3c1985");
        usuario.setSuperusuario(true);
        usuario.setMembroFamiliar(List.of());
        usuarioRepository.save(usuario);

        categoria = new CategoriaORM();
        categoriaId = UUID.randomUUID();
        categoriaSuperior = UUID.randomUUID();

    }

    @AfterEach
    void cleanUp() {
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();

    }

    @Test
    void test1() {
        categoria.setIdCategoria(categoriaId.toString());
        categoria.setNome("Despesas Mensal");
        categoria.setDescricao("Grupo de despesas mensais recorrente");
        categoria.setCategoriaSuperior(categoriaSuperior.toString());
        categoria.setUsuario(usuario);
        categoriaRepository.save(categoria);

        var persistenceCategoria = categoriaRepositoryAdapter.buscarCategoria(categoriaId);

        Assertions.assertEquals(categoria.getIdCategoria(), persistenceCategoria.getId().toString());
    }

    @Test
    void test2() {

        categoria.setIdCategoria(categoriaId.toString());
        categoria.setNome("Despesas Anual");
        categoria.setDescricao("Grupo de despesas anuais");
        categoria.setCategoriaSuperior(categoriaSuperior.toString());
        categoria.setUsuario(usuario);
        var categoriaConvertida = new CategoriaORMMapper();
        categoriaRepositoryAdapter.atualizarCategoria(categoriaConvertida.ormParaDominio(categoria));

        var categoriaEncontrada = categoriaRepositoryAdapter.buscarCategoria(categoriaId);
        Assertions.assertEquals(categoria.getNome(), categoriaEncontrada.getNome().toString());
        Assertions.assertEquals(categoria.getDescricao(), categoriaEncontrada.getDescricao().toString());

    }

    @Test
    void test3() {

        categoria.setIdCategoria(categoriaId.toString());
        categoria.setNome("Despesas Semanal");
        categoria.setDescricao("Grupo de despesas semanais");
        categoria.setCategoriaSuperior(categoriaSuperior.toString());
        categoria.setUsuario(usuario);

        var converteCategoria = new CategoriaORMMapper();
        var categoriaDomain = categoriaRepositoryAdapter
                .atualizarCategoria(converteCategoria.ormParaDominio(categoria));

        var categoriaEncontrada = categoriaRepositoryAdapter.buscarCategoria(categoriaDomain.getId());
        Assertions.assertEquals(categoria.getIdCategoria(), categoriaEncontrada.getId().toString());

        var nomeEncontrado = categoriaRepositoryAdapter.buscarCategoriasPorNome(categoriaDomain.getNome());
        boolean categoriaComNomeEsperado = nomeEncontrado.stream()
                .anyMatch(c -> categoria.getNome().equals(c.getNome().toString()));
        Assertions.assertTrue(categoriaComNomeEsperado);

        // var subCategoriaEncontrada =
        // categoriaRepositoryAdapter.buscarSubCategorias(categoriaSuperior);
        // boolean subCategoriaEsperada = subCategoriaEncontrada.stream()
        // .anyMatch(c ->
        // categoria.getCategoriaSuperior().equals(c.getIdCategoriaSuperior().toString()));
        // Assertions.assertTrue(subCategoriaEsperada);

        // var UsuarioEncontrado =
        // categoriaRepository.findByUsuario_IdUsuario(categoria.getUsuario().getIdUsuario());

        var usuarioDomain = converteCategoria.ormParaDominio(categoria);

        categoriaRepository.delete(categoria);

    }

    @Test
    void test4() {
        categoria.setIdCategoria(categoriaId.toString());
        categoria.setNome("Despesas Semanal");
        categoria.setDescricao("Grupo de despesas semanal");
        categoria.setCategoriaSuperior(categoriaSuperior.toString());
        categoria.setUsuario(usuario);

        var categoria2 = new CategoriaORM();

        categoria2.setIdCategoria(UUID.randomUUID().toString());
        categoria2.setNome("Despesas Casa");
        categoria2.setDescricao("Grupo de despesas casa");
        categoria2.setCategoriaSuperior(categoriaSuperior.toString());
        categoria2.setUsuario(usuario);

        var categoria3 = new CategoriaORM();

        categoria3.setIdCategoria(UUID.randomUUID().toString());
        categoria3.setNome("Despesas Mensal");
        categoria3.setDescricao("Grupo de despesas mensal");
        categoria3.setCategoriaSuperior(categoriaSuperior.toString());
        categoria3.setUsuario(usuario);

        categoriaRepository.saveAll(List.of(categoria, categoria2, categoria3));

        var categoriasEncontradas = categoriaRepositoryAdapter
                .buscarCategoriasPorUsuario(UUID.fromString(usuario.getIdUsuario()));
        boolean usuarioEsperado = categoriasEncontradas.stream()
                .allMatch(c -> usuario.getIdUsuario().equals(c.getUsuario().toString()));
        Assertions.assertTrue(usuarioEsperado);

    }

    @Test
    void test5() {

        var categoriasEncontradas = categoriaRepositoryAdapter
                .buscarCategoriasPorUsuario(UUID.fromString(usuario.getIdUsuario()));
        boolean possuiCategoriaRelacionada = categoriasEncontradas.stream()
                .noneMatch(c -> usuario.getIdUsuario().equals(c.getUsuario().toString()));
        Assertions.assertTrue(possuiCategoriaRelacionada);

    }

}
