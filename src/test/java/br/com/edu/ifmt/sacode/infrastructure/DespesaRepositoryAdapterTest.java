// package br.com.edu.ifmt.sacode.infrastructure;

// import static org.mockito.Mockito.verify;

// import java.time.LocalDate;
// import java.util.UUID;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.ActiveProfiles;

// import
// br.com.edu.ifmt.sacode.infrastructure.adapters.DespesaRepositoryAdapter;
// import br.com.edu.ifmt.sacode.infrastructure.adapters.LogAdapter;
// import br.com.edu.ifmt.sacode.infrastructure.mappers.DespesaORMMapper;
// import br.com.edu.ifmt.sacode.infrastructure.persistence.DespesaORM;
// import br.com.edu.ifmt.sacode.infrastructure.persistence.DespesaRepository;

// @DataJpaTest(showSql = false)
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @ActiveProfiles("test")
// public class DespesaRepositoryAdapterTest {

// DespesaRepositoryAdapter despesaRepositoryAdapter;

// @Autowired
// DespesaRepository despesaRepository;

// @BeforeEach
// void setUp() {

// despesaRepositoryAdapter = new DespesaRepositoryAdapter(despesaRepository,
// new DespesaORMMapper(),
// new LogAdapter());

// }

// @AfterEach
// void cleanUp() {
// despesaRepository.deleteAll();
// }

// @Test
// void criarDespesa() {
// var despesaId = UUID.randomUUID();
// var usuarioId = UUID.randomUUID();
// var parcelaId = UUID.randomUUID();

// var despesa = new DespesaORM();
// despesa.setIdDespesa(despesaId.toString());
// despesa.setAutorDespesa("Jose");
// despesa.setCorrelacaoParcelas(parcelaId.toString());
// LocalDate dataAtual = LocalDate.now();
// despesa.setData(dataAtual);
// despesa.setDescricao("gasto com alimentação");
// despesa.setFinanciadorDespesa(null);
// despesa.setFixa(true);
// despesa.setNumParcela(3);
// // despesa.setUsuario(usuarioId.toString());
// despesa.setValor("10.00");
// // despesaRepository.save(despesa);

// var persistenceDespesa = despesaRepositoryAdapter.buscarDespesa(despesaId);
// //
// Assertions.assertEquals(despesa.getIdDespesa(),persistenceDespesa.getIdDespesa().toString());

// // despesaRepositoryAdapter.buscarDespesasPorAutor(usuarioId,
// // despesa.getAutorDespesa().toString());

// var despesaAtualizada = new DespesaORM();
// despesaAtualizada.setIdDespesa(despesaId.toString());
// despesaAtualizada.setAutorDespesa("Jose");
// despesaAtualizada.setCorrelacaoParcelas(parcelaId.toString());
// despesaAtualizada.setData(dataAtual);
// despesaAtualizada.setDescricao("gasto com lazer");
// despesaAtualizada.setFinanciadorDespesa(null);
// despesaAtualizada.setFixa(true);
// despesaAtualizada.setNumParcela(0);
// // despesaAtualizada.setUsuario(usuarioId.toString());
// despesaAtualizada.setValor("100.00");

// // despesaRepositoryAdapter.atualizarDespesa(despesaAtualizada);

// // despesaRepositoryAdapter.excluirDespesa(despesaId, usuarioId);
// }

// }
