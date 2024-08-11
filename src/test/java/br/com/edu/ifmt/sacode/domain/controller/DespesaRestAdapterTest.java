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

import br.com.edu.ifmt.sacode.application.DespesaRestAdapter;
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
public class DespesaRestAdapterTest {
    @Mock
    private DespesaPort despesaPortMock;

    @Mock
    private LogPort logPortMock;

    @Mock
    static private DespesaRestAdapter despesaController;

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
}
