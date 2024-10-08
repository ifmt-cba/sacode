package br.com.edu.ifmt.sacode.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.DespesaPort;
import br.com.edu.ifmt.sacode.domain.services.exception.DespesaException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

@SpringBootTest
public class DespesaServiceTest {
    @Mock
    private DespesaPort despesaPortMock;

    @Mock
    private LogPort logPortMock;
    @Mock
    private ResourceBundle resourceBundleMock;

    static private DespesaService despesaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        despesaService = new DespesaService(logPortMock, despesaPortMock);
        when(resourceBundleMock.getString(anyString())).thenReturn("Mensagem de erro mockada");
        ReflectionTestUtils.setField(despesaService, "excRB", resourceBundleMock, ResourceBundle.class);
    }

    @Test
    void shouldThrowException() {
        assertThrows(DespesaException.class, () -> {
            despesaService.criarDespesa(new Despesa());
        });
    }

    @Test
    public void testCriarDespesa_Null() {
        assertThrows(DespesaException.class, () -> {
            despesaService.criarDespesa(null);
        });
    }

    @Test
    public void testCriarDespesa_VerificarCampos() {
        Despesa despesa = mock(Despesa.class);
        assertThrows(DespesaException.class, () -> {
            despesaService.criarDespesa(despesa);
        });
    }

    // Outros testes podem ser adicionados de forma semelhante

    @Test
    public void testPorMes() throws DespesaException {
        UUID usuario = UUID.randomUUID();
        List<Despesa> despesas = new ArrayList<>();
        when(despesaPortMock.buscarDespesasPorPeriodo(usuario, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 30))).thenReturn(despesas);

        List<Despesa> result = despesaService.buscarDespesasPorMes(usuario, 2024, 4);

        assertEquals(despesas, result);
    }

    @Test
    public void testPorMes_UserInvalido() {
        assertThrows(DespesaException.class, () -> {
            despesaService.buscarDespesasPorMes(null, 2024, 4);
        });
    }

    // Outros testes podem ser adicionados de forma semelhante
}
