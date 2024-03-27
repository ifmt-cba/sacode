package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.User;

public interface UserPort {
    User createUser(User user);
}