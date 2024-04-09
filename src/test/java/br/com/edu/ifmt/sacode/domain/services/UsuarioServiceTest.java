package br.com.edu.ifmt.sacode.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UsuarioPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UsuarioServiceTest {

    private UsuarioService userService;

    @Mock
    private UsuarioPort userPort;

    @Mock
    private LogPort logPort;

    private static final UUID ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UsuarioService(userPort, logPort);
    }

    @Test
    void deveSalvarUsuario() throws UsuarioException {
        Usuario usuario = new Usuario(UUID.randomUUID(), new Nome("PedrinhoJogado"), new Username("pedro001"),
                new Password("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"),
                new Email("pedro@gmail.com"), false, List.of());

        when(userPort.salvarUsuario(usuario)).thenReturn(usuario);

        Usuario resultado = userService.salvarUsuario(usuario);

        assertEquals(usuario, resultado);

        verify(logPort).info("Usuario successfully created.");
    }

    @Test
    void deveLancarExcecaoAoSalvarUsuarioComDadosInvalidos() {
        Usuario usuarioComDadosInvalidos = new Usuario();

        assertThrows(UsuarioException.class, () -> {
            userService.salvarUsuario(usuarioComDadosInvalidos);
        });

        verify(logPort).warn(anyString());
    }

    @Test
    void deveDeletarUsuario() throws UsuarioException {
        Usuario usuario = new Usuario();

        doNothing().when(userPort).deletarUsuario(ID, usuario);

        userService.deletarUsuario(ID, usuario);

        verify(userPort).deletarUsuario(ID, usuario);

        verify(logPort).info(anyString());
    }

    @Test
    void DeveRetornarUsuarioQuandoBuscarPorId() throws UsuarioException {
        Usuario usuarioExistente = new Usuario();

        when(userPort.buscaPorIdUsuario(ID)).thenReturn(usuarioExistente);

        Usuario resultado = userService.buscaPorIdUsuario(ID);

        assertEquals(usuarioExistente, resultado);

        verify(logPort).info(anyString());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarUsuarioNaoExistente() {

        when(userPort.buscaPorIdUsuario(ID)).thenReturn(null);

        assertThrows(UsuarioException.class, () -> {
            userService.buscaPorIdUsuario(ID);
        });

        verify(logPort).info(anyString());
    }

}