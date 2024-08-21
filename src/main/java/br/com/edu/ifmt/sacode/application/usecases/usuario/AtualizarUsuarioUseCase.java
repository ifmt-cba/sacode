package br.com.edu.ifmt.sacode.application.usecases.usuario;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import br.com.edu.ifmt.sacode.application.io.CriarUsuarioRequest;
import br.com.edu.ifmt.sacode.application.io.AtualizarUsuarioResponse;
import br.com.edu.ifmt.sacode.application.mappers.UsuarioDTOMapper;
import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.UsuarioService;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;

@Service
public class AtualizarUsuarioUseCase {
    private final UsuarioService service;
    private final UsuarioDTOMapper usuarioDTOMapper;
    private final LogPort logPort;

    public AtualizarUsuarioUseCase(UsuarioService service, UsuarioDTOMapper usuarioDTOMapper, LogPort logPort) {
        this.service = service;
        this.usuarioDTOMapper = usuarioDTOMapper;
        this.logPort = logPort;
    }

    public AtualizarUsuarioResponse atualizarUsuario(CriarUsuarioRequest request) throws NoSuchAlgorithmException, UsuarioException {
        logPort.trace("-> AtualizarUsuarioUseCase.atualizarUsuario");
        Usuario usuarioObj = usuarioDTOMapper.toUser(request);
        logPort.debug(usuarioObj.toString());
        Usuario usuario = service.atualizarUsuario(usuarioObj);
        logPort.trace("<- AtualizarUsuarioUseCase.atualizarUsuario");
        return usuarioDTOMapper.toAtualizarResponse(usuario);
    }
}
