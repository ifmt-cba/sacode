package br.com.edu.ifmt.sacode.infrastructure.persistence;

import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "categoria")
public class CategoriaORM extends  AbstractEntity<CategoriaORM> {

    @Id
    private String idCategoria;
    private String nome;
    private String descricao;
    private String categoriaSuperior;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioORM usuario;

    public CategoriaORM() {
    }

    public CategoriaORM(UUID idCategoria, Nome nomeCategoria, Descricao descricao,
                        UUID categoriaSuperior, UsuarioORM usuario) {
        this.idCategoria = idCategoria.toString();
        this.nome = nomeCategoria.toString();
        this.descricao = descricao.toString();
        this.categoriaSuperior = categoriaSuperior.toString();
        this.usuario = usuario;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
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

    public String getCategoriaSuperior() {
        return categoriaSuperior;
    }

    public void setCategoriaSuperior(String categoriaSuperior) {
        this.categoriaSuperior = categoriaSuperior;
    }

    public UsuarioORM getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioORM usuario) {
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
                idCategoria != null ? idCategoria : "null",
                nome != null ? nome : "null",
                descricao != null ? descricao : "null",
                usuario != null ? usuario.getNome() : "null",
                categoriaSuperior != null ? categoriaSuperior : "null");
    }
}
