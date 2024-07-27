package br.com.edu.ifmt.sacode.infrastructure.persistence;

import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class UsuarioORM {
    
    @Id
    private String idUsuario;
    private String email;
    private String username;
    private String password;
    private String nome;
    private boolean superUsuario;

    @ElementCollection
    private List<String> membroFamiliar;

    public UsuarioORM(
        UUID idUsuario,
        Email email, 
        Username username, 
        Password password, 
        Nome nome,
        boolean superUsuario, 
        List<String> membroFamiliar
    )
    {
        this.idUsuario = idUsuario.toString();
        this.email = email.toString();
        this.username = username.toString();
        this.password = password.toString();
        this.nome = nome.toString();
        this.superUsuario = superUsuario;
        this.membroFamiliar = membroFamiliar;
    }

    public UsuarioORM() {

    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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
        return superUsuario;
    }
    public void setSuperusuario(boolean superUsuario) {
        this.superUsuario = superUsuario;
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
                    "superUsuario" : "%s",
                    "membrofamiliar" : {
                        "%s"
                    }
                }
                """,
                this.idUsuario,
                this.email,
                this.username,
                this.password,
                this.nome,
                this.superUsuario,
                this.membroFamiliar
            );
    }


}
