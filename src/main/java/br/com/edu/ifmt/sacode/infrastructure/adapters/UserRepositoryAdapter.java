package br.com.edu.ifmt.sacode.infrastructure.adapters;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.edu.ifmt.sacode.domain.entities.User;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UserPort;
import br.com.edu.ifmt.sacode.infrastructure.mappers.UserORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UserORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UserRepository;

public class UserRepositoryAdapter implements UserPort{
    
    private final UserRepository userRepository;
    private final UserORMMapper userORMMapper;
    @Autowired
    private final LogPort logPort = null;

    
    public UserRepositoryAdapter(UserRepository userRepository, UserORMMapper userORMMapper) {
        this.userRepository = userRepository;
        this.userORMMapper = userORMMapper;
    }

    @Override
    public User buscaPorIdUsuario(UUID id)
    {
        logPort.trace("-> UserRepositoryAdapter.buscarPorIdUsuario");
        UserORM usuarioEncontrado = userRepository.findById(id.toString());
        logPort.trace("<- UserRepositoryAdapter.buscarPorIdUsuario");
        return userORMMapper.toDomainObj(usuarioEncontrado);
    }

    @Override
    public User salvarUsuario(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvarUsuario'");
    }

    @Override
    public void deletarUsuario(UUID id, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletarUsuario'");
    }
}
