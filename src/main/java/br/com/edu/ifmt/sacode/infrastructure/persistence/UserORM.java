package br.com.edu.ifmt.sacode.infrastructure.persistence;

import java.util.UUID;

import java.util.List;

import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserORM {

    @Id
    private String id;
    private String email;
    private String username;
    private String password;
    private String nome;
    private boolean superusuario;

    @ElementCollection
    private List<String> membroFamiliar;

    public UserORM(
            UUID id,
            Email email,
            Username username,
            Password password,
            Nome nome,
            boolean superusuario,
            List<String> membroFamiliar) {
        this.id = id.toString();
        this.email = email.toString();
        this.username = username.toString();
        this.password = password.toString();
        this.nome = nome.toString();
        this.superusuario = superusuario;
        this.membroFamiliar = membroFamiliar;
    }

    public UserORM() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isSuperusuario() {
        return superusuario;
    }

    public void setSuperusuario(boolean superusuario) {
        this.superusuario = superusuario;
    }

    public List<String> getMembroFamiliar() {
        return membroFamiliar;
    }

    public void setMembroFamiliar(List<String> membroFamiliar) {
        this.membroFamiliar = membroFamiliar;
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    "id" : "%s",
                    "email" : "%s",
                    "username" : "%s",
                    "password" : "%s",
                    "nome" : "%s",
                    "superusuario" : "%s",
                    "membrofamiliar" : {
                        "%s"
                    }
                }
                """,
                this.id,
                this.email,
                this.username,
                this.password,
                this.nome,
                this.superusuario,
                this.membroFamiliar);
    }

}
