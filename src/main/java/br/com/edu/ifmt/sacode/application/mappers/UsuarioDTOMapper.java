package br.com.edu.ifmt.sacode.application.mappers;

import br.com.edu.ifmt.sacode.application.configs.SecurityConfiguration;
import br.com.edu.ifmt.sacode.application.io.CriarUsuarioRequest;
import br.com.edu.ifmt.sacode.application.io.CriarUsuarioResponse;
import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
        return new CriarUsuarioResponse(user.getNomeUsuario().toString(), user.getEmail().toString(),null);
    }

    public Usuario toUser(CriarUsuarioRequest request) throws NoSuchAlgorithmException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(UUID.randomUUID());
        usuario.setNomeUsuario(new Username(isNull(request.nomeUsuario()) ? "" : request.nomeUsuario()));
        usuario.setName(new Nome(isNull(request.nome()) ? "" : request.nome() ));
        if (nonNull(request.senha())) {
//            byte[] sha256 = MessageDigest.getInstance("SHA-256").digest(request.senha().getBytes(StandardCharsets.UTF_8));
            String encodedPassword = securityConfiguration.passwordEncoder().encode(request.senha());
            usuario.setSenha(new Password(encodedPassword));
        } else
            usuario.setSenha(new Password(null));
        usuario.setEmail(new Email(isNull(request.email()) ? "" :request.email()));
        return usuario;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
