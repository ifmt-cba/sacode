package br.com.edu.ifmt.sacode.application.mappers;

import br.com.edu.ifmt.sacode.application.configs.SecurityConfiguration;
import br.com.edu.ifmt.sacode.application.io.AtualizarUsuarioResponse;
import br.com.edu.ifmt.sacode.application.io.CriarUsuarioRequest;
import br.com.edu.ifmt.sacode.application.io.CriarUsuarioResponse;
import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.UUID;

@Component
public class UsuarioDTOMapper {

    private final SecurityConfiguration securityConfiguration;

    public UsuarioDTOMapper(SecurityConfiguration securityConfiguration){
        this.securityConfiguration = securityConfiguration;
    }

    public CriarUsuarioResponse toResponse(Usuario user) {
        return new CriarUsuarioResponse(user.getIdUsuario().toString(), user.getName().toString() ,user.getNomeUsuario().toString(),
                user.getEmail().toString(), user.isSuperUsuario(), null);
    }

    public AtualizarUsuarioResponse toAtualizarResponse(Usuario user) {
        return new AtualizarUsuarioResponse(user.getIdUsuario().toString(), user.getName().toString() ,user.getNomeUsuario().toString(),
                user.getEmail().toString(), user.isSuperUsuario(), null);
    }

    public Usuario toUser(CriarUsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(UUID.randomUUID());
        usuario.setNomeUsuario(new Username(isNull(request.nomeUsuario()) ? "" : request.nomeUsuario()));
        usuario.setName(new Nome(isNull(request.nome()) ? "" : request.nome() ));
        if (nonNull(request.senha())) {
            String encodedPassword = securityConfiguration.passwordEncoder().encode(request.senha());
            usuario.setSenha(new Password(encodedPassword));
        } else
            usuario.setSenha(new Password(null));
        usuario.setEmail(new Email(isNull(request.email()) ? "" :request.email()));
        return usuario;
    }
}
