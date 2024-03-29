package br.com.edu.ifmt.sacode.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Name;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;

public class User {
    private UUID idUsuario;
    private Name name;
    private Username username;
    private Password password;
    private Email email;
    private boolean superUsuario;
    private List<Name> membroFamiliar;

    public User() {
        this.idUsuario = new UUID(0, 0);
        this.name = new Name(null);
        this.username = new Username(null);
        this.password = new Password(null);
        this.email = new Email(null);
        this.superUsuario = false;
        this.membroFamiliar = new ArrayList<>();
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isSuperUsuario() {
        return superUsuario;
    }

    public void setSuperUsuario(boolean superUsuario) {
        this.superUsuario = superUsuario;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public List<Name> getMembroFamiliar() {
        return membroFamiliar;
    }

    public void setMembroFamiliar(List<Name> membroFamiliar) {
        this.membroFamiliar = membroFamiliar;
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    "idUsuario":"%s",
                    "name":"%s",
                    "username":"%s",
                    "email":"%s",
                    "superUsuario":"%s",
                    "password":"%s"
                }
                """,
                idUsuario.toString(),
                name.toString(),
                username.toString(),
                email.toString(),
                Boolean.toString(superUsuario),
                password.toString());
    }

}
