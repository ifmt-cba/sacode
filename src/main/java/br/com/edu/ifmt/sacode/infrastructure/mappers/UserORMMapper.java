package br.com.edu.ifmt.sacode.infrastructure.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.edu.ifmt.sacode.domain.entities.User;
import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Name;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UserORM;

public class UserORMMapper {

    public UserORM toORM(User user){
        return new UserORM(
            user.getIdUsuario(),
            user.getEmail(),
            user.getNomeUsuario(),
            user.getSenha(),
            user.getName(),
            user.isSuperUsuario(),
            user.getMembroFamiliar()
        );
    }

    public User toDomainObj(UserORM userORM){
        User user = new User();
        user.setIdUsuario(UUID.fromString(userORM.getId()));
        user.setEmail(new Email(userORM.getEmail()));
        user.setNomeUsuario(new Username(userORM.getUsername()));
        user.setSenha(new Password(userORM.getPassword()));
        user.setName(new Name(userORM.getNome()));
        user.setSuperUsuario(userORM.isSuperusuario());
        user.setMembroFamiliar(userORM.getMembroFamiliar());
        return user;
    }

    public List<User> toDomainList(List<UserORM> usersORM) {
        List<User> users = new ArrayList<>();
        for (UserORM userORM : usersORM) {
            users.add(toDomainObj(userORM));
        }
        return users;
    }
}
