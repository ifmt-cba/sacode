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
    private Name nome;
    private Username nomeUsuario;
    private Password senha;
    private Email email;
    private boolean superUsuario;
    private List<Name> membroFamiliar;

    public User() {
        this.idUsuario = new UUID(0, 0);
        this.nome = new Name(null);
        this.nomeUsuario = new Username(null);
        this.senha = new Password(null);
        this.email = new Email(null);
        this.superUsuario = false;
        this.membroFamiliar = new ArrayList<>();
    }

    public User(UUID idUsuario, Name nome, Username nomeUsuario, Password senha, Email email, boolean superUsuario, List<Name> membroFamiliar) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.email = email;
        this.superUsuario = superUsuario;
        this.membroFamiliar = membroFamiliar;
    }

    public Username getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(Username nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public Password getSenha() {
        return senha;
    }

    public void setSenha(Password senha) {
        this.senha = senha;
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
        return nome;
    }

    public void setName(Name name) {
        this.nome = name;
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
                nome.toString(),
                nomeUsuario.toString(),
                email.toString(),
                Boolean.toString(superUsuario),
                senha.toString());
    }

}
