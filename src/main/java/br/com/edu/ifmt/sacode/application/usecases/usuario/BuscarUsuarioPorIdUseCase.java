package br.com.edu.ifmt.sacode.application.usecases.usuario;

import br.com.edu.ifmt.sacode.application.io.CriarUsuarioResponse;
import br.com.edu.ifmt.sacode.application.mappers.UsuarioDTOMapper;
import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.UsuarioService;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class BuscarUsuarioPorIdUseCase {
    private final UsuarioService service;
    private final UsuarioDTOMapper usuarioDTOMapper;
    private final LogPort logPort;


    public BuscarUsuarioPorIdUseCase(UsuarioService service, UsuarioDTOMapper usuarioDTOMapper, LogPort logPort) {
        this.service = service;
        this.usuarioDTOMapper = usuarioDTOMapper;
        this.logPort = logPort;
    }

    public CriarUsuarioResponse buscarUsuario(String id) throws UsuarioException {
        logPort.trace("-> BuscarUsuarioUseCase.buscarUsuario");
        Usuario usuario = service.buscaPorIdUsuario(UUID.fromString(id));
        logPort.debug(usuario.toString());
        logPort.trace("<- BuscarUsuarioUseCase.buscarUsuario");
        return usuarioDTOMapper.toResponse(usuario);
    }

}
