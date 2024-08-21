package br.com.edu.ifmt.sacode.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;
import br.com.edu.ifmt.sacode.domain.entities.vo.TipoMoeda;
import br.com.edu.ifmt.sacode.infrastructure.adapters.DespesaRepositoryAdapter;
import br.com.edu.ifmt.sacode.infrastructure.adapters.LogAdapter;
import br.com.edu.ifmt.sacode.infrastructure.mappers.DespesaORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaRepository;
import br.com.edu.ifmt.sacode.infrastructure.persistence.DespesaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.DespesaRepository;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DespesaRepositoryAdapterTest {

    DespesaRepositoryAdapter despesaRepositoryAdapter;

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    DespesaORM despesa = new DespesaORM();
    DespesaORM despesa1 = new DespesaORM();
    DespesaORM despesa2 = new DespesaORM();
    DespesaORM despesa3 = new DespesaORM();
    DespesaORM despesa4 = new DespesaORM();

    UsuarioORM usuario = new UsuarioORM();

    CategoriaORM categoria1 = new CategoriaORM();
    CategoriaORM categoria2 = new CategoriaORM();

    @BeforeEach
    void setUp() {

        despesaRepositoryAdapter = new DespesaRepositoryAdapter(despesaRepository,
                new DespesaORMMapper(),
                new LogAdapter());

        usuario.setIdUsuario(UUID.randomUUID().toString());
        usuario.setEmail("mariasilva@gmail.com");
        usuario.setNome("Maria da Silva");
        usuario.setUsername("Maria");
        usuario.setPassword("44e0ce52a039177f58976cf227a50265e9ed4119f19b182ea08c40a75d3c1985");
        usuario.setSuperusuario(true);
//        usuario.setMembroFamiliar(List.of());

        categoria1.setIdCategoria(UUID.randomUUID().toString());
        categoria1.setNome("Despesas Mensal");
        categoria1.setDescricao("Grupo de despesas mensais recorrente");
        categoria1.setCategoriaSuperior(UUID.randomUUID().toString());
        categoria1.setUsuario(usuario);

        categoria2.setIdCategoria(UUID.randomUUID().toString());
        categoria2.setNome("Despesas Eventuais");
        categoria2.setDescricao("Grupo de despesas eventual");
        categoria2.setCategoriaSuperior(UUID.randomUUID().toString());
        categoria2.setUsuario(usuario);

        despesa1.setIdDespesa(UUID.randomUUID().toString());
        despesa1.setAutorDespesa("Jose da Silva");
        despesa1.setCorrelacaoParcelas(UUID.randomUUID().toString());
        despesa1.setData(LocalDate.now());
        despesa1.setDescricao("gasto com alimentação");
        despesa1.setFinanciadorDespesa("Jose da Silva");
        despesa1.setFixa(true);
        despesa1.setNumParcela(0);
        despesa1.setUsuario(usuario);
        despesa1.setValor("10.00");
        despesa1.setCategoria(categoria1);

        despesa2.setIdDespesa(UUID.randomUUID().toString());
        despesa2.setAutorDespesa("Maria da Silva");
        despesa2.setCorrelacaoParcelas(UUID.randomUUID().toString());
        despesa2.setData(LocalDate.now());
        despesa2.setDescricao("gasto com viagem");
        despesa2.setFinanciadorDespesa("Jose da Silva");
        despesa2.setFixa(false);
        despesa2.setNumParcela(3);
        despesa2.setUsuario(usuario);
        despesa2.setValor("10000.00");
        despesa2.setCategoria(categoria2);

        despesa3.setIdDespesa(UUID.randomUUID().toString());
        despesa3.setAutorDespesa("Joao da Silva");
        despesa3.setCorrelacaoParcelas(UUID.randomUUID().toString());
        despesa3.setDescricao("gasto com viagem");
        despesa3.setFinanciadorDespesa("Jao da Silva");
        despesa3.setFixa(false);
        despesa3.setNumParcela(3);
        despesa3.setUsuario(usuario);
        despesa3.setValor("1000.00");
        despesa3.setCategoria(categoria2);

        despesa4.setIdDespesa(UUID.randomUUID().toString());
        despesa4.setAutorDespesa("Joao da Silva");
        despesa4.setCorrelacaoParcelas(UUID.randomUUID().toString());
        despesa4.setDescricao("gasto com viagem");
        despesa4.setFinanciadorDespesa("Jao da Silva");
        despesa4.setFixa(false);
        despesa4.setNumParcela(3);
        despesa4.setUsuario(usuario);
        despesa4.setValor("1000.00");
        despesa4.setCategoria(categoria2);

    }

    @AfterEach
    void cleanUp() {
        despesaRepository.deleteAll();
    }

    @Test

    void criarDespesa() {

        usuarioRepository.save(usuario);

        categoriaRepository.save(categoria1);

        despesa.setIdDespesa(UUID.randomUUID().toString());
        despesa.setAutorDespesa("Jose da Silva");
        despesa.setCorrelacaoParcelas(UUID.randomUUID().toString());
        despesa.setData(LocalDate.now());
        despesa.setDescricao("gasto com alimentação");
        despesa.setFinanciadorDespesa("Jose da Silva");
        despesa.setFixa(true);
        despesa.setNumParcela(0);
        despesa.setUsuario(usuario);
        Moeda moeda = new Moeda(TipoMoeda.REAL, new BigDecimal("10.00"));
        despesa.setValor(moeda.toString());
        despesa.setCategoria(categoria1);
        despesaRepository.save(despesa);

        var despesaEncontrada = despesaRepositoryAdapter.buscarDespesa(UUID.fromString(despesa.getIdDespesa()));
        Assertions.assertEquals(despesa.getIdDespesa(), despesaEncontrada.getIdDespesa().toString());
        Assertions.assertEquals(despesa.getValor(), despesaEncontrada.getValor().toString());
        Assertions.assertEquals(despesa.getAutorDespesa(), despesaEncontrada.getAutorDespesa().toString());

    }

    @Test
    void atualizarDespesa() {

        usuarioRepository.save(usuario);
        categoriaRepository.save(categoria2);

        despesaRepository.save(despesa2);

        despesa2.setFinanciadorDespesa("Maria da Silva");
        despesa2.setFixa(false);
        despesa2.setNumParcela(4);
        despesa2.setUsuario(usuario);
        Moeda moeda = new Moeda(TipoMoeda.REAL, new BigDecimal("15000.00"));
        despesa2.setValor(moeda.toString());

        var despesaOrmParaDominio = new DespesaORMMapper();
        despesaRepositoryAdapter.atualizarDespesa(despesaOrmParaDominio.toDomainObj(despesa2));

        var despesaEncontrada = despesaRepositoryAdapter.buscarDespesa(UUID.fromString(despesa2.getIdDespesa()));
        Assertions.assertEquals(despesa2.getIdDespesa(), despesaEncontrada.getIdDespesa().toString());

        Assertions.assertEquals(despesa2.getValor(), despesaEncontrada.getValor().toString());

    }

    @Test
    void excluirDespesa() {
        usuarioRepository.save(usuario);
        categoriaRepository.save(categoria1);

        despesaRepository.save(despesa1);
        despesaRepositoryAdapter.excluirDespesa(UUID.fromString((despesa1.getIdDespesa())),
                UUID.fromString(despesa1.getUsuario().getIdUsuario()));

        Assertions.assertFalse(despesaRepository.existsById(despesa1.getIdDespesa()));

    }

    @Test
    void buscarDespesa() {
        usuarioRepository.save(usuario);
        categoriaRepository.save(categoria1);
        despesaRepository.save(despesa1);

        var despesaEncontrada = despesaRepositoryAdapter.buscarDespesa(UUID.fromString((despesa1.getIdDespesa())));
        Assertions.assertEquals(despesa1.getIdDespesa(), despesaEncontrada.getIdDespesa().toString());
        Assertions.assertEquals(despesa1.getAutorDespesa(), despesaEncontrada.getAutorDespesa().toString());
        Assertions.assertEquals(despesa1.getFinanciadorDespesa(), despesaEncontrada.getFinanciadorDespesa().toString());

    }

    @Test
    void excluirParcelas() {
        usuarioRepository.save(usuario);
        categoriaRepository.save(categoria2);
        despesaRepository.save(despesa2);

        Assertions.assertTrue(despesaRepository.existsById(despesa2.getIdDespesa()));

        var retorno = despesaRepositoryAdapter.excluirParcelas(UUID.fromString(despesa2.getUsuario().getIdUsuario()),
                UUID.fromString(despesa2.getCorrelacaoParcelas()));
        Assertions.assertEquals(1, retorno);

    }

    @Test
    void buscarDespesasPorAutor() {

        usuarioRepository.save(usuario);
        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);

        despesaRepository.saveAll(List.of(despesa1, despesa2, despesa3, despesa4));

        var despesasEncontradas = despesaRepositoryAdapter
                .buscarDespesasPorAutor(UUID.fromString(usuario.getIdUsuario()), despesa1.getAutorDespesa());

        var despesasPorAutor = despesasEncontradas.stream()
                .allMatch(d -> despesa1.getAutorDespesa().equals(d.getAutorDespesa().toString()));

        Assertions.assertTrue(despesasPorAutor);

        despesasEncontradas = despesaRepositoryAdapter
                .buscarDespesasPorAutor(UUID.fromString(usuario.getIdUsuario()), despesa3.getAutorDespesa());

        despesasPorAutor = despesasEncontradas.stream()
                .allMatch(d -> despesa3.getAutorDespesa().equals(d.getAutorDespesa().toString()));

        Assertions.assertTrue(despesasPorAutor);
    }

    @Test
    void buscarDespesasPorFinanciador() {

        usuarioRepository.save(usuario);
        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);

        despesaRepository.saveAll(List.of(despesa1, despesa2, despesa3, despesa4));

        var despesasEncontradas = despesaRepositoryAdapter
                .buscarDespesasPorFinanciador(UUID.fromString(usuario.getIdUsuario()),
                        despesa1.getFinanciadorDespesa());

        var despesasEsperada = despesasEncontradas.stream()
                .allMatch(d -> despesa1.getFinanciadorDespesa().equals(d.getFinanciadorDespesa().toString()));

        Assertions.assertTrue(despesasEsperada);

        despesasEncontradas = despesaRepositoryAdapter
                .buscarDespesasPorFinanciador(UUID.fromString(usuario.getIdUsuario()),
                        despesa3.getFinanciadorDespesa());

        despesasEsperada = despesasEncontradas.stream()
                .allMatch(d -> despesa3.getFinanciadorDespesa().equals(d.getFinanciadorDespesa().toString()));

        Assertions.assertTrue(despesasEsperada);

    }

    @Test
    void buscarDespesasPorPeriodo() {
        usuarioRepository.save(usuario);
        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);

        LocalDate hoje = LocalDate.now();
        LocalDate ontem = hoje.minusDays(1);
        LocalDate mesPassado = hoje.minusMonths(1);

        despesa1.setData(ontem);
        despesa2.setData(mesPassado);
        despesa3.setData(hoje);
        despesa4.setData(mesPassado);

        despesaRepository.saveAll(List.of(despesa1, despesa2, despesa3, despesa4));

        LocalDate inicioPeriodo = hoje.minusDays(40);
        LocalDate fimPeriodo = hoje;

        var despesasEncontradas = despesaRepositoryAdapter.buscarDespesasPorPeriodo(
                UUID.fromString(despesa1.getUsuario().getIdUsuario()), inicioPeriodo, fimPeriodo);

        var despesasEsperada = despesasEncontradas.stream()
                .anyMatch(d -> despesa1.getData().equals(d.getData()));

        Assertions.assertTrue(despesasEsperada);

        despesasEncontradas = despesaRepositoryAdapter.buscarDespesasPorPeriodo(
                UUID.fromString(despesa2.getUsuario().getIdUsuario()), inicioPeriodo, fimPeriodo);

        despesasEsperada = despesasEncontradas.stream()
                .anyMatch(d -> despesa2.getData().equals(d.getData()));

        Assertions.assertTrue(despesasEsperada);

    }

}
