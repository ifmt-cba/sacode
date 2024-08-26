package br.com.edu.ifmt.sacode.infrastructure;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

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
public class CategoriaRepositoryAdapterTest {

    CategoriaRepositoryAdapter categoriaRepositoryAdapter;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    UsuarioORM usuario = new UsuarioORM();

    CategoriaORM categoria = new CategoriaORM();

    CategoriaORM categoria1 = new CategoriaORM();
    CategoriaORM categoria2 = new CategoriaORM();
    CategoriaORM categoria3 = new CategoriaORM();

    @BeforeEach
    void setUp() {
        categoriaRepositoryAdapter = new CategoriaRepositoryAdapter(categoriaRepository, new LogAdapter());

        usuario.setIdUsuario(UUID.randomUUID().toString());
        usuario.setEmail("mariasilva@gmail.com");
        usuario.setNome("Maria da Silva");
        usuario.setUsername("Maria");
        usuario.setPassword("44e0ce52a039177f58976cf227a50265e9ed4119f19b182ea08c40a75d3c1985");
        usuario.setSuperusuario(true);
//        usuario.setMembroFamiliar(List.of());
        usuarioRepository.save(usuario);

        categoria1.setIdCategoria(UUID.randomUUID().toString());
        categoria1.setNome("Despesas Semanal");
        categoria1.setDescricao("Grupo de despesas semanal");
        categoria1.setCategoriaSuperior(UUID.randomUUID().toString());
        categoria1.setUsuario(usuario);

        categoria2.setIdCategoria(UUID.randomUUID().toString());
        categoria2.setNome("Despesas Casa");
        categoria2.setDescricao("Grupo de despesas casa");
        categoria2.setCategoriaSuperior(UUID.randomUUID().toString());
        categoria2.setUsuario(usuario);

        categoria3.setIdCategoria(UUID.randomUUID().toString());
        categoria3.setNome("Despesas Mensal");
        categoria3.setDescricao("Grupo de despesas mensal");
        categoria3.setCategoriaSuperior(UUID.randomUUID().toString());
        categoria3.setUsuario(usuario);

    }

    @AfterEach
    void cleanUp() {
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();

    }

    @Test
    void criarCategoria() {
        categoria.setIdCategoria(UUID.randomUUID().toString());
        categoria.setNome("Despesas Mensal");
        categoria.setDescricao("Grupo de despesas mensais recorrente");
        categoria.setCategoriaSuperior(UUID.randomUUID().toString());
        categoria.setUsuario(usuario);
        categoriaRepository.save(categoria);

        var persistenceCategoria = categoriaRepositoryAdapter
                .buscarCategoria(UUID.fromString(categoria.getIdCategoria()));

        Assertions.assertEquals(categoria.getIdCategoria(), persistenceCategoria.getId().toString());
    }

    @Test
    @Disabled
    void excluirCategoria() {
        categoriaRepository.save(categoria1);
        categoriaRepositoryAdapter.excluirCategoria(UUID.fromString(categoria1.getIdCategoria()));

        var categoriaBuscada = categoriaRepositoryAdapter.buscarCategoria(UUID.fromString(categoria1.getIdCategoria()));

        Assertions.assertNull(categoriaBuscada);
    }

    @Test
    void buscarCategoria() {

        categoriaRepository.save(categoria2);

        var categoriaEncontrada = categoriaRepositoryAdapter
                .buscarCategoria(UUID.fromString(categoria2.getIdCategoria()));
        Assertions.assertEquals(categoria2.getIdCategoria(), categoriaEncontrada.getId().toString());

    }

    @Test
    void atualizarCategoria() {

        categoriaRepository.save(categoria1);

        categoria1.setNome("Despesas Anual");
        categoria1.setDescricao("Grupo de despesas anuais");

        var categoriaOrmParaDominio = new CategoriaORMMapper();
        categoriaRepositoryAdapter.atualizarCategoria(categoriaOrmParaDominio.ormParaDominio(categoria1));

        var categoriaEncontrada = categoriaRepositoryAdapter
                .buscarCategoria(UUID.fromString(categoria1.getIdCategoria()));
        Assertions.assertEquals(categoria1.getNome(), categoriaEncontrada.getNome().toString());
        Assertions.assertEquals(categoria1.getDescricao(), categoriaEncontrada.getDescricao().toString());

    }

    @Test
    void buscarCategoriasPorUsuario() {

        categoriaRepository.saveAll(List.of(categoria1, categoria2, categoria3));

        var categoriasEncontradas = categoriaRepositoryAdapter
                .buscarCategoriasPorUsuario(UUID.fromString(usuario.getIdUsuario()));
        boolean usuarioEsperado = categoriasEncontradas.stream()
                .allMatch(c -> usuario.getIdUsuario().equals(c.getUsuario().toString()));
        Assertions.assertTrue(usuarioEsperado);

    }

    @Test
    void buscarCategoriasPorNome() {
        categoria.setIdCategoria(UUID.randomUUID().toString());
        categoria.setNome("Despesas Mensal");
        categoria.setDescricao("Grupo de despesas mensais recorrente");
        categoria.setCategoriaSuperior(UUID.randomUUID().toString());
        categoria.setUsuario(usuario);
        categoriaRepository.save(categoria);

        categoriaRepository.saveAll(List.of(categoria, categoria1, categoria2, categoria3));

        var categoriaOrmParaDominio = new CategoriaORMMapper();
        var nomeCategoria = categoriaOrmParaDominio.ormParaDominio(categoria1);

        var categoriasEncontradas = categoriaRepositoryAdapter
                .buscarCategoriasPorNome(nomeCategoria.getNome());
        boolean categoriaEsperada = categoriasEncontradas.stream()
                .allMatch(c -> categoria1.getNome().equals(c.getNome().toString()));
        Assertions.assertTrue(categoriaEsperada);

    }

    @Test
    void buscarSubCategorias() {

        var categoria4 = new CategoriaORM();

        var categoria5 = new CategoriaORM();

        var categoria6 = new CategoriaORM();

        var idCategoriaSuperior = UUID.randomUUID();

        categoria4.setIdCategoria(UUID.randomUUID().toString());
        categoria4.setNome("Despesas Semanal");
        categoria4.setDescricao("Grupo de despesas semanal");
        categoria4.setCategoriaSuperior(idCategoriaSuperior.toString());
        categoria4.setUsuario(usuario);

        categoria5.setIdCategoria(UUID.randomUUID().toString());
        categoria5.setNome("Despesa Semanal 1");
        categoria5.setDescricao("Grupo de despesas semanal 1");
        categoria5.setCategoriaSuperior(idCategoriaSuperior.toString());
        categoria5.setUsuario(usuario);

        categoria6.setIdCategoria(UUID.randomUUID().toString());
        categoria6.setNome("Despesa Semanal 2");
        categoria6.setDescricao("Grupo de despesas semanal 2");
        categoria6.setCategoriaSuperior(idCategoriaSuperior.toString());
        categoria6.setUsuario(usuario);

        categoriaRepository.saveAll(List.of(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6));

        var categoriasEncontradas = categoriaRepositoryAdapter
                .buscarSubCategorias(UUID.fromString(categoria4.getCategoriaSuperior()));

        boolean subCategoriasEsperadas = categoriasEncontradas.stream()
                .allMatch(c -> categoria4.getCategoriaSuperior().equals(c.getIdCategoriaSuperior().toString()));
        Assertions.assertTrue(subCategoriasEsperadas);
    }

}
