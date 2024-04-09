package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.User;

import java.util.UUID;

public interface UserPort {
    User salvarUsuario(User user);

    void deletarUsuario(UUID id, User user);

    User buscaPorIdUsuario(UUID userId);
}