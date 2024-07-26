package br.com.edu.ifmt.sacode.domain.services;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.ports.CategoriaPort;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ResourceBundle;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoriaServiceTest {

    static CategoriaService categoriaService;

    @Mock
    private CategoriaPort categoriaPort;

    @Mock
    private LogPort  logPort;

    @BeforeEach
    public void prepararAmbienteDeTesteCorretamente() {
        MockitoAnnotations.openMocks(this);
        ResourceBundle.clearCache();
        categoriaService = new CategoriaService(categoriaPort, logPort);
    }

    @Test
    void testCriarCategoriaSuccess() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setUsuario(UUID.randomUUID());
        categoria.setNome(new Nome("nome teste"));
        categoria.setDescricao(new Descricao("descricao teste"));
        when(categoriaPort.criarCategoria(any(Categoria.class))).thenReturn(categoria);

        Categoria result = categoriaService.criarCategoria(categoria);

        assertNotNull(result);
        verify(categoriaPort).criarCategoria(categoria);
        verify(logPort).info("Categoria criada com sucesso.");
    }

    @Test
    void testCriarCategoriaThrowsException() throws CategoriaException {
        Categoria categoria = new Categoria();
        when(categoriaPort.criarCategoria(any(Categoria.class))).thenThrow(new CategoriaException("erro"));

        CategoriaException exception = assertThrows(CategoriaException.class, () -> {
            categoriaService.criarCategoria(categoria);
        });

        assertTrue(exception.getMessage().contains("erro"));
    }

    @Test
    void testExcluirCategoriaSuccess() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        doNothing().when(categoriaPort).excluirCategoria(any(UUID.class), any(Categoria.class));

        categoriaService.excluirCategoria(categoria);

        verify(categoriaPort).excluirCategoria(categoria.getId(), categoria);
        verify(logPort).info("Categoria excluida com sucesso.");
    }

    @Test
    void testValidarCamposObrigatorios() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setNome(new Nome("Categoria Teste"));
        categoria.setDescricao(new Descricao("Descrição Teste"));

        boolean resultado = categoriaService.validarCamposObrigatorios(categoria);

        assertTrue(resultado);
    }

    @Test
    void testBuscaCategoriasPorNome() throws CategoriaException {
        Nome nome = new Nome("Categoria Teste");
        List<Categoria> categorias = List.of(new Categoria());
        when(categoriaPort.buscaCategoriasPorNome(nome)).thenReturn(categorias);

        List<Categoria> result = categoriaService.buscaCategoriasPorNome(nome);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }


}
