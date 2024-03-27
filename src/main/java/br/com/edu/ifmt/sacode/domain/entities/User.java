package br.com.edu.ifmt.sacode.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private UUID idUsuario;
    private Username username;
    private Password password;
    private Email email;
    private boolean superUsuario;
    private List<String> membroFamiliar;

    public User() {
        this.idUsuario = new UUID(0, 0);
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

    public List<String> getMembroFamiliar() {
        return membroFamiliar;
    }

    public void setMembroFamiliar(List<String> membroFamiliar) {
        this.membroFamiliar = membroFamiliar;
    }

    public void addMembroFamiliar(String membro) {
        if (membroFamiliar == null) {
            membroFamiliar = new ArrayList<>();
        }
        String newMembro = membro.toLowerCase();
        membroFamiliar.add(newMembro);
    }

    public void removeMembroFamiliar(String membro) {
        if (membroFamiliar != null) {
            for (int i = 0; i < membroFamiliar.size(); i++) {
                if (membroFamiliar.get(i).toLowerCase().equals(membro)) {
                    membroFamiliar.remove(i);
                    break;
                }
            }
        }
    }

    public void alterMembroFamiliar(String membro, String newMembro) {
        if (membroFamiliar != null) {
            int aux = -1;
            for (int i = 0; i < membroFamiliar.size(); i++) {
                if (membroFamiliar.get(i).toLowerCase().equals(newMembro)) {
                    aux = i;
                    break;
                }
            }
            if (aux != -1) {
                membroFamiliar.set(aux, newMembro);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    "idUsuario":"%s",
                    "username":"%s",
                    "email":"%s",
                    "superUsuario":"%s",
                    "password":"%s"
                }
                """,
                idUsuario.toString(),
                username.toString(),
                email.toString(),
                Boolean.toString(superUsuario),
                password.toString());
    }

}
