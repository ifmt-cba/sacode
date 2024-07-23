package br.com.edu.ifmt.sacode.infrastructure.adapters;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UsuarioPort;
import br.com.edu.ifmt.sacode.infrastructure.mappers.UsuarioORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioRepository;

public class UsuarioRepositoryAdapter implements UsuarioPort{
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioORMMapper usuarioORMMapper;

    private final LogPort logPort;

    
    public UsuarioRepositoryAdapter(UsuarioRepository usuarioRepository, UsuarioORMMapper usuarioORMMapper, LogPort logPort) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioORMMapper = usuarioORMMapper;
        this.logPort = logPort;
    }

    @Override
    public Usuario buscaPorIdUsuario(UUID id)
    {
        logPort.trace("-> UsuarioRepositoryAdapter.buscarPorIdUsuario");
        UsuarioORM usuarioEncontrado = usuarioRepository.findById(id.toString());
        logPort.trace("<- UsuarioRepositoryAdapter.buscarPorIdUsuario");
        return usuarioORMMapper.toDomainObj(usuarioEncontrado);
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        logPort.trace(" -> UsuarioRepositoryAdapter.salvarUsuario");
        UsuarioORM usuarioSalvar = usuarioORMMapper.toORM(usuario);
        logPort.info(" Usuario mapeado para o banco de dados");
        usuarioRepository.save(usuarioSalvar);
        logPort.debug(" Salvo com sucesso");
        return usuarioORMMapper.toDomainObj(usuarioSalvar);
    }

    @Override
    public void deletarUsuario(UUID id, Usuario usuario) {
        logPort.trace(" -> UsuarioRepositoryAdapter.deletarUsuario");
        UsuarioORM usuarioDeletar = usuarioORMMapper.toORM(usuario);
        logPort.info(" Usuario mapeado para o banco de dados");
        usuarioRepository.delete(usuarioDeletar);
        logPort.debug(" Deletado com sucesso");
    }
}