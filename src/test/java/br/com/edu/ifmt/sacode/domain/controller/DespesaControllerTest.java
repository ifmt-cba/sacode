package br.com.edu.ifmt.sacode.domain.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.com.edu.ifmt.sacode.Controller.DespesaController;
import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.DespesaPort;
import br.com.edu.ifmt.sacode.domain.services.DespesaService;
import br.com.edu.ifmt.sacode.domain.services.exception.DespesaException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

@SpringBootTest
@AutoConfigureMockMvc
public class DespesaControllerTest {
    @Mock
    private DespesaPort despesaPortMock;

    @Mock
    private LogPort logPortMock;

    @Mock
    static private DespesaController despesaController;

    @Autowired
    private MockMvc mockMvc;

    // Procurar código HTTP
    @Test
    void criarDespesaVazia() throws Exception {
        mockMvc.perform(post("/despesas/")).andExpect(status().isBadRequest());
    }

    @Test
    void atualizarDespesaVazia() throws Exception {
        mockMvc.perform(put("/despesas/")).andExpect(status().isBadRequest());
    }

    @Test
    void excluirDespesaVazia() throws Exception {
        mockMvc.perform(delete("/despesas/")).andExpect(status().isBadRequest());
    }
    
    @Test
    void buscarDespesasPorPeriodoInvalido() throws Exception {
        mockMvc.perform(get("/despesas/2024-5-23/2023-9-20")).andExpect(status().isBadRequest());
    }
    
    @Test
    void buscarDespesasPorMesInvalido() throws Exception {
        mockMvc.perform(get("/despesas/2024-14")).andExpect(status().isBadRequest());
    }

    /*
     * @Test
     * void shouldThrowException() {
     * assertThrows(Exception.class, () -> {
     * despesaController.buscarDespesasPorMes(null, 0, 0);
     * });
     * }
     */

    /*
     * @Test
     * public void testCriarDespesa_VerificarCampos() {
     * Despesa despesa = mock(Despesa.class);
     * assertThrows(DespesaException.class, () -> {
     * despesaService.criarDespesa(despesa);
     * });
     * }
     */
    /*
     * @Test
     * public void testPorMes() throws DespesaException {
     * UUID usuario = UUID.randomUUID();
     * List<Despesa> despesas = new ArrayList<>();
     * when(despesaPortMock.buscarDespesasPorPeriodo(usuario, LocalDate.of(2024, 4,
     * 1), LocalDate.of(2024, 4, 30))).thenReturn(despesas);
     * List<Despesa> result = despesaService.buscarDespesasPorMes(usuario, 2024, 4);
     * assertEquals(despesas, result);
     * }
     */
    /*
     * @Test
     * public void testPorMes_UserInvalido() {
     * assertThrows(DespesaException.class, () -> {
     * despesaService.buscarDespesasPorMes(null, 2024, 4);
     * });
     * }
     */
}
