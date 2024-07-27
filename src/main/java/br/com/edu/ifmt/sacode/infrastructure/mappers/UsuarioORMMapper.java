package br.com.edu.ifmt.sacode.infrastructure.mappers;

import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UsuarioORMMapper {

    public static UsuarioORM toORM(Usuario user) {
        return new UsuarioORM(
                user.getIdUsuario(),
                user.getEmail(),
                user.getNomeUsuario(),
                user.getSenha(),
                user.getName(),
                user.isSuperUsuario(),
                user.getMembroFamiliar().stream().map(Nome::toString)
                        .collect(Collectors.toList()));
    }

    public static Usuario toDomainObj(UsuarioORM userORM) {
        Usuario user = new Usuario();
        user.setIdUsuario(UUID.fromString(userORM.getIdUsuario()));
        user.setEmail(new Email(userORM.getEmail()));
        user.setNomeUsuario(new Username(userORM.getUsername()));
        user.setSenha(new Password(userORM.getPassword()));
        user.setName(new Nome(userORM.getNome()));
        user.setSuperUsuario(userORM.isSuperusuario());
        user.setMembroFamiliar(userORM.getMembroFamiliar().stream()
                .map(Nome::new)
                .collect(Collectors.toList()));
        return user;
    }

    public List<Usuario> toDomainList(List<UsuarioORM> usersORM) {
        List<Usuario> users = new ArrayList<>();
        for (UsuarioORM userORM : usersORM) {
            users.add(toDomainObj(userORM));
        }
        return users;
    }
}
