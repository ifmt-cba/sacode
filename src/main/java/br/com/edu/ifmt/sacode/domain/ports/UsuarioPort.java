package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.Usuario;

import java.util.UUID;

public interface UsuarioPort {
    Usuario salvarUsuario(Usuario user);

    void deletarUsuario(UUID id, Usuario user);

    Usuario buscaPorIdUsuario(UUID userId);
}