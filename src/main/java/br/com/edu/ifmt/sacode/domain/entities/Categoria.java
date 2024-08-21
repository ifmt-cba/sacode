package br.com.edu.ifmt.sacode.domain.entities;

import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import java.util.UUID;

public class Categoria {

    private UUID id;
    private UUID usuario;
    private Nome nome;
    private Descricao descricao;
    private UUID idCategoriaSuperior;

    public Categoria() {
        this.id = UUID.randomUUID();
        this.usuario = null;
        this.nome = new Nome();
        this.descricao = new Descricao();
        this.idCategoriaSuperior = null;
    }

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    public Nome getNome() {
        return nome;
    }

    public void setNome(Nome nome) {
        this.nome = nome;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    public UUID getIdCategoriaSuperior() {
        return idCategoriaSuperior;
    }

    public void setIdCategoriaSuperior(UUID idCategoriaSuperior) {
        this.idCategoriaSuperior = idCategoriaSuperior;
    }

    public UUID getUsuario() {
        return usuario;
    }

    public void setUsuario(UUID usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    "id":"%s",
                    "nome":"%s",
                    "descricao":"%s",
                    "usuario":"%s",
                    "idCategoriaSuperior":"%s"
                }
                """,
                id != null ? id : "null",
                nome != null ? nome : "null",
                descricao != null ? descricao : "null",
                usuario != null ? usuario : "null",
                idCategoriaSuperior != null ? idCategoriaSuperior : "null");
    }

}
