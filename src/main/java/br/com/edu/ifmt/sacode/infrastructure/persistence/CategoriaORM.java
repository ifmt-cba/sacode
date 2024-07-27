package br.com.edu.ifmt.sacode.infrastructure.persistence;

import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "categoria")
public class CategoriaORM {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private String categoriaSuperior;
    private String usuario;

    public CategoriaORM() {
    }

    public CategoriaORM(UUID id, Nome nomeCategoria, Descricao descricao,
                        UUID categoriaSuperior, String usuario) {
        this.id = id.toString();
        this.nome = nomeCategoria.toString();
        this.descricao = descricao.toString();
        this.categoriaSuperior = categoriaSuperior.toString();
        this.usuario = usuario;
    }

    public String getIdCategoria() {
        return id;
    }

    public void setIdCategoria(String idCategoria) {
        this.id = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoriaSuperior() {
        return categoriaSuperior;
    }

    public void setCategoriaSuperior(String categoriaSuperior) {
        this.categoriaSuperior = categoriaSuperior;
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
                categoriaSuperior != null ? categoriaSuperior : "null");
    }
}
