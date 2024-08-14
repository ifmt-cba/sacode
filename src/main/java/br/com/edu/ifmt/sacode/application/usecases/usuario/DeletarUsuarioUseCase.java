package br.com.edu.ifmt.sacode.application.usecases.usuario;

import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.UsuarioService;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeletarUsuarioUseCase {

    private final UsuarioService service;
    private final LogPort logPort;

    public DeletarUsuarioUseCase(UsuarioService service, LogPort logPort) {
        this.service = service;
        this.logPort = logPort;
    }

    public void deletarUsuario(String id, Usuario usuario) throws UsuarioException {
        logPort.trace("-> DeletarUsuarioUseCase.deletarUsuario()");

        service.deletarUsuario(UUID.fromString(id), usuario);

        logPort.info("Usuario deletado com Sucesso.");
        logPort.trace("<- DeletarUsuarioUseCase.deletarUsuario()");

    }

}
