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
        logPort.trace(" -> UserRepositoryAdapter.salvarUsuario");
        UserORM usuarioSalvar = userORMMapper.toORM(user);
        logPort.info(" Usuario mapeado para o banco de dados");
        userRepository.save(usuarioSalvar);
        logPort.debug(" Salvo com sucesso");
        return userORMMapper.toDomainObj(usuarioSalvar);
    }

    @Override
    public void deletarUsuario(UUID id, User user) {
        logPort.trace(" -> UserRepositoryAdapter.deletarUsuario");
        UserORM usuarioDeletar = userORMMapper.toORM(user);
        logPort.info(" Usuario mapeado para o banco de dados");
        userRepository.delete(usuarioDeletar);
        logPort.debug(" Deletado com sucesso");
    }
}
