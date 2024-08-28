package br.com.edu.ifmt.sacode.application.usecases.usuario;

import br.com.edu.ifmt.sacode.application.io.CriarUsuarioRequest;
import br.com.edu.ifmt.sacode.application.io.CriarUsuarioResponse;
import br.com.edu.ifmt.sacode.application.mappers.UsuarioDTOMapper;
import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.UsuarioService;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import org.springframework.stereotype.Component;

@Component
public class CriarUsuarioUseCase {

    private final UsuarioService service;
    private final UsuarioDTOMapper usuarioDTOMapper;
    private final LogPort logPort;

    public CriarUsuarioUseCase(UsuarioService service, UsuarioDTOMapper usuarioDTOMapper, LogPort logPort) {
        this.service = service;
        this.usuarioDTOMapper = usuarioDTOMapper;
        this.logPort = logPort;
    }

    public CriarUsuarioResponse criarUsuario(CriarUsuarioRequest request) throws UsuarioException {
        logPort.trace("-> CriarUsuarioUseCase.criarUsuario");
        Usuario usuarioObj = usuarioDTOMapper.toUser(request);
        logPort.debug(usuarioObj.toString());
        Usuario usuario = service.salvarUsuario(usuarioObj);
        logPort.trace("<- CriarUsuarioUseCase.criarUsuario");
        return usuarioDTOMapper.toResponse(usuario);
    }
}
