package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface UsuarioPort {
    Usuario salvarUsuario(Usuario user);

    void deletarUsuario(UUID id, Usuario user);

    Usuario buscarPorIdUsuario(UUID userId);

    Boolean checarUsuarioExistente(String idUsuario);

}