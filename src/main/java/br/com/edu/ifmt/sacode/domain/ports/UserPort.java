package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.User;

import java.util.UUID;

public interface UserPort {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(UUID id, User user);

    User getByIdUser(UUID userId);
}