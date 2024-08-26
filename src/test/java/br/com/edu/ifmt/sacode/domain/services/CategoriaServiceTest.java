package br.com.edu.ifmt.sacode.domain.services;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.ports.CategoriaPort;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UsuarioPort;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoriaServiceTest {

    static CategoriaService categoriaService;

    private static ResourceBundle excRB;

    @Mock
    private CategoriaPort categoriaPort;

    @Mock
    private UsuarioPort usuarioPort;

    @Mock
    private LogPort logPort;

    @BeforeEach
    public void prepararAmbienteDeTesteCorretamente() {
        MockitoAnnotations.openMocks(this);
        ResourceBundle.clearCache();
        categoriaService = new CategoriaService(categoriaPort, logPort, usuarioPort);
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt", "BR"));
    }

    @Test
    void testarCriacaoCategoriaComSucesso() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setUsuario(UUID.randomUUID());
        categoria.setNome(new Nome("nome teste"));
        categoria.setDescricao(new Descricao("descricao teste"));

        when(categoriaPort.criarCategoria(any(Categoria.class))).thenReturn(categoria);
        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(true);

        Categoria resultado = categoriaService.criarCategoria(categoria);

        assertNotNull(resultado);
        verify(categoriaPort).criarCategoria(categoria);
        verify(logPort).info("Categoria criada com sucesso.");
    }

    @Test
    @Disabled
    void testarCriacaoCategoriaLancaExcecao() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(null);
        categoria.setNome(null);
        categoria.setDescricao(null);
        categoria.setUsuario(null);

        when(categoriaPort.criarCategoria(any(Categoria.class))).thenThrow(new CategoriaException(
                "erro: O id da categoria não pode ser nulo nem vazio erro: O nome da categoria não pode ser nulo nem vazio erro: A descrição da categoria não pode ser nula nem vazia"));
        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(false);

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.criarCategoria(categoria);
        });

        assertTrue(excecao.getMessage().trim().contains(excRB.getString("usuario.not.found")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("usuario.id.nulo")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.id.nulo")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.nome.nulo")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.descricao.nula")));
    }

    @Test
    void testarExclusaoCategoriaComSucesso() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());

        doNothing().when(categoriaPort).excluirCategoria(any(UUID.class));

        categoriaService.excluirCategoria(categoria);

        verify(categoriaPort).excluirCategoria(categoria.getId());
        verify(logPort).info("Categoria excluida com sucesso.");
    }

    @Test
    void testarExclusaoCategoriaLancaExcecao() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(null);

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.excluirCategoria(categoria);
        });
        assertTrue(excecao.getMessage().contains("erro"));
    }

    @Test
    void testarValidacaoCamposObrigatoriosComSucesso() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setNome(new Nome("Categoria Teste"));
        categoria.setDescricao(new Descricao("Descrição Teste"));
        categoria.setUsuario(UUID.randomUUID());

        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(true);

        boolean resultado = categoriaService.validarCamposObrigatorios(categoria);

        assertTrue(resultado);
    }

    @Test
    @Disabled
    void testarValidacaoCamposObrigatoriosLancaExcecao() {
        Categoria categoria = new Categoria();
        categoria.setId(null);
        categoria.setNome(null);
        categoria.setDescricao(null);
        categoria.setUsuario(null);

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.validarCamposObrigatorios(categoria);
        });

        // Verifica se a mensagem da exceção contém informações esperadas
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("usuario.not.found")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("usuario.id.nulo")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.id.nulo")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.nome.nulo")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.descricao.nula")));

    }

    @Test
    void testarAtualizacaoCategoriaComSucesso() throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setUsuario(UUID.randomUUID());
        categoria.setNome(new Nome("nome teste"));
        categoria.setDescricao(new Descricao("descricao teste"));

        when(categoriaPort.buscarCategoria(categoria.getId())).thenReturn(categoria);
        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(true);
        when(categoriaPort.atualizarCategoria(any(Categoria.class))).thenReturn(categoria);

        Categoria categoriaAtualizada = categoriaService.atualizarCategoria(categoria);
        verify(categoriaPort).atualizarCategoria(categoria);
        assertNotNull(categoriaAtualizada);
    }

    @Test
    void testarAtualizacaoCategoriaNaoEncontrada() {
        Categoria categoria = new Categoria();
        categoria.setUsuario(UUID.randomUUID());

        when(categoriaPort.buscarCategoria(categoria.getId()))
                .thenThrow(new RuntimeException("Categoria nao encontrada"));
        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(true);
        CategoriaException thrownException = assertThrows(CategoriaException.class, () -> {
            categoriaService.atualizarCategoria(categoria);
        });

        String mensagemErro = thrownException.getMessage().trim();
        assertTrue(mensagemErro.contains("Categoria nao encontrada"));
    }

    @Test
    @Disabled
    void testarAtualizacaoCategoriaComCamposObrigatoriosNulos() {
        Categoria categoria = new Categoria();
        categoria.setId(null);
        categoria.setNome(null);
        categoria.setDescricao(null);
        categoria.setUsuario(null);

        when(categoriaPort.buscarCategoria(any(UUID.class))).thenReturn(categoria);
        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(false);

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.atualizarCategoria(categoria);
        });

        // Verifica se a mensagem da exceção contém informações esperadas

        assertTrue(excecao.getMessage().trim().contains(excRB.getString("usuario.not.found")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.id.nulo")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.nome.nulo")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.descricao.nula")));
    }

    @Test
    void testarAtualizacaoCategoriaLancaExcecaoGenerica() {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setUsuario(UUID.randomUUID());
        categoria.setNome(new Nome("nome teste"));
        categoria.setDescricao(new Descricao("descricao teste"));

        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(true);
        when(categoriaPort.buscarCategoria(any(UUID.class))).thenThrow(new RuntimeException("Erro genérico"));

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.atualizarCategoria(categoria);
        });

        assertTrue(excecao.getMessage().contains("Erro genérico"));
    }

    @Test
    void testarBuscaCategoriasPorNomeComNomeCategoriaNulo() {
        Nome nomeCategoria = null;

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.buscarCategoriasPorNome(nomeCategoria);
        });

        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.nome.nulo")));

    }

    @Test
    void testarBuscaCategoriasPorNomeComSucesso() throws CategoriaException {

        Nome nomeCategoria = new Nome("categoriaTeste");

        List<Categoria> categoriasEsperadas = Arrays.asList(new Categoria(), new Categoria());

        when(categoriaPort.buscarCategoriasPorNome(nomeCategoria)).thenReturn(categoriasEsperadas);

        List<Categoria> categoriasEncontradas = categoriaService.buscarCategoriasPorNome(nomeCategoria);

        assertEquals(categoriasEsperadas, categoriasEncontradas);
        verify(categoriaPort).buscarCategoriasPorNome(nomeCategoria);
    }

    @Test
    void testarBuscaCategoriasPorNomeLancaExcecao() {
        Nome nomeCategoria = new Nome("categoriaTeste");

        when(categoriaPort.buscarCategoriasPorNome(nomeCategoria))
                .thenThrow(new RuntimeException("Erro ao buscar categorias"));

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.buscarCategoriasPorNome(nomeCategoria);
        });

        assertTrue(excecao.getMessage().contains("Erro ao buscar categorias"));
    }

    @Test
    void testarBuscaSubCategoriasComCategoriaSuperiorNula() {
        UUID categoriaIdPai = null;

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.buscarSubCategorias(categoriaIdPai);
        });

        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.idSuperior.invalido")));

    }

    @Test
    void testarBuscaSubCategoriasComSucesso() throws CategoriaException {
        UUID categoriaIdPai = UUID.randomUUID();
        List<Categoria> subCategoriasEsperadas = Arrays.asList(new Categoria(), new Categoria());

        when(categoriaPort.buscarSubCategorias(categoriaIdPai)).thenReturn(subCategoriasEsperadas);

        List<Categoria> subCategoriasEncontradas = categoriaService.buscarSubCategorias(categoriaIdPai);

        assertEquals(subCategoriasEsperadas, subCategoriasEncontradas);
        verify(categoriaPort).buscarSubCategorias(categoriaIdPai);
    }

    @Test
    void testarBuscaSubCategoriasLancaExcecao() {
        UUID categoriaIdPai = UUID.randomUUID();

        when(categoriaPort.buscarSubCategorias(categoriaIdPai))
                .thenThrow(new RuntimeException("Erro ao buscar subcategorias"));

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.buscarSubCategorias(categoriaIdPai);
        });

        assertTrue(excecao.getMessage().contains("Erro ao buscar subcategorias"));
    }

    @Test
    void testarBuscaCategoriasPorUsuarioComIdNulo() {
        UUID usuarioId = null;

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.buscarCategoriasPorUsuario(usuarioId);
        });

        assertTrue(excecao.getMessage().trim().contains(excRB.getString("usuario.not.found")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("usuario.id.nulo")));
    }

    @Test
    void testarBuscaCategoriasPorUsuarioComSucesso() throws CategoriaException {
        UUID usuarioId = UUID.randomUUID();

        List<Categoria> categoriasEsperadas = Arrays.asList(new Categoria(), new Categoria());

        when(categoriaPort.buscarCategoriasPorUsuario(usuarioId)).thenReturn(categoriasEsperadas);
        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(true);

        List<Categoria> categoriasEncontradas = categoriaService.buscarCategoriasPorUsuario(usuarioId);

        assertEquals(categoriasEsperadas, categoriasEncontradas);
        verify(categoriaPort).buscarCategoriasPorUsuario(usuarioId);
    }

    @Test
    void testarBuscaCategoriasPorUsuarioLancaExcecao() {
        UUID usuarioId = UUID.randomUUID();

        when(categoriaPort.buscarCategoriasPorUsuario(usuarioId))
                .thenThrow(new RuntimeException("Erro ao buscar categorias"));
        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(true);
        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.buscarCategoriasPorUsuario(usuarioId);
        });

        assertTrue(excecao.getMessage().contains("Erro ao buscar categorias"));
    }

    @Test
    void testarBuscaCategoriaComIdNulo() {
        UUID categoriaId = null;

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.buscarCategoria(categoriaId);
        });

        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.id.nulo")));

    }

    @Test
    void testarBuscaCategoriaComSucesso() throws CategoriaException {
        UUID categoriaId = UUID.randomUUID();

        Categoria categoriaEsperada = new Categoria();
        categoriaEsperada.setId(categoriaId);

        when(categoriaPort.buscarCategoria(categoriaId)).thenReturn(categoriaEsperada);
        when(usuarioPort.checarUsuarioExistente(any(String.class))).thenReturn(true);

        Categoria categoriaEncontrada = categoriaService.buscarCategoria(categoriaId);

        assertEquals(categoriaEsperada, categoriaEncontrada);
        verify(categoriaPort).buscarCategoria(categoriaId);
    }

    @Test
    void testarBuscaCategoriaLancaExcecao() {
        UUID categoriaId = UUID.randomUUID();

        when(categoriaPort.buscarCategoria(categoriaId)).thenThrow(new RuntimeException("Erro ao buscar categoria"));

        CategoriaException thrownException = assertThrows(CategoriaException.class, () -> {
            categoriaService.buscarCategoria(categoriaId);
        });

        assertTrue(thrownException.getMessage().contains("Erro ao buscar categoria"));
    }

    @Test
    void testarAdicaoCategoriaSuperiorComIdNulo() {
        UUID idCategoriaSuperior = null;
        UUID idCategoriaInferior = null;

        CategoriaException exception = assertThrows(CategoriaException.class, () -> {
            categoriaService.adicionarCategoriaSuperior(idCategoriaSuperior, idCategoriaInferior);
        });

        assertTrue(exception.getMessage().trim().contains(excRB.getString("categoria.idSuperior.invalido")));
        assertTrue(exception.getMessage().trim().contains(excRB.getString("categoria.idInferior.invalido")));

    }

    @Test
    void testarAdicaoCategoriaSuperiorComSucesso() throws CategoriaException {
        UUID idCategoriaSuperior = UUID.randomUUID();
        UUID idCategoriaInferior = UUID.randomUUID();

        Categoria categoriaSuperior = new Categoria();
        categoriaSuperior.setId(idCategoriaSuperior);

        Categoria categoriaInferior = new Categoria();
        categoriaInferior.setId(idCategoriaInferior);

        when(categoriaPort.buscarCategoria(idCategoriaSuperior)).thenReturn(categoriaSuperior);
        when(categoriaPort.buscarCategoria(idCategoriaInferior)).thenReturn(categoriaInferior);

        when(categoriaPort.atualizarCategoria(any(Categoria.class))).thenReturn(categoriaInferior);

        categoriaService.adicionarCategoriaSuperior(idCategoriaSuperior, idCategoriaInferior);

        verify(categoriaPort).atualizarCategoria(any(Categoria.class));
    }

    @Test
    void testarAdicaoCategoriaSuperiorLancaExcecao() {
        UUID idCategoriaSuperior = UUID.randomUUID();
        UUID idCategoriaInferior = UUID.randomUUID();

        when(categoriaPort.buscarCategoria(idCategoriaSuperior))
                .thenThrow(new RuntimeException("Erro ao buscar categoria"));

        CategoriaException thrownException = assertThrows(CategoriaException.class, () -> {
            categoriaService.adicionarCategoriaSuperior(idCategoriaSuperior, idCategoriaInferior);
        });

        assertTrue(thrownException.getMessage().contains("Erro ao buscar categoria"));
    }

    @Test
    void testarRemocaoCategoriaSuperiorComIdNulo() {
        UUID idCategoriaSuperior = null;
        UUID idCategoriaInferior = null;

        CategoriaException excecao = assertThrows(CategoriaException.class, () -> {
            categoriaService.removerCategoriaSuperior(idCategoriaSuperior, idCategoriaInferior);
        });

        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.idSuperior.invalido")));
        assertTrue(excecao.getMessage().trim().contains(excRB.getString("categoria.idInferior.invalido")));

    }

    @Test
    void testarRemocaoCategoriaSuperiorComSucesso() throws CategoriaException {
        UUID idCategoriaSuperior = UUID.randomUUID();
        UUID idCategoriaInferior = UUID.randomUUID();

        Categoria categoriaSuperior = new Categoria();
        categoriaSuperior.setId(idCategoriaSuperior);

        Categoria categoriaInferior = new Categoria();
        categoriaInferior.setId(idCategoriaInferior);

        when(categoriaPort.buscarCategoria(idCategoriaSuperior)).thenReturn(categoriaSuperior);
        when(categoriaPort.buscarCategoria(idCategoriaInferior)).thenReturn(categoriaInferior);
        when(categoriaPort.atualizarCategoria(any(Categoria.class))).thenReturn(categoriaInferior);

        categoriaService.removerCategoriaSuperior(idCategoriaSuperior, idCategoriaInferior);

        verify(categoriaPort).atualizarCategoria(categoriaInferior);
        assertNull(categoriaInferior.getIdCategoriaSuperior());
    }

    @Test
    void testarVerificacaoSubCategoriasComSubcategorias() throws CategoriaException {
        Categoria categoriaSuperior = new Categoria();
        categoriaSuperior.setId(UUID.randomUUID());

        List<Categoria> subCategorias = new ArrayList<>();

        when(categoriaPort.buscarSubCategorias(categoriaSuperior.getId())).thenReturn(subCategorias);

        categoriaService.verificarSubCategorias(categoriaSuperior);

        verify(categoriaPort, times(1)).buscarSubCategorias(categoriaSuperior.getId());

    }

}