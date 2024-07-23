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
    private String categoriaPai;
    private String usuario;

    // @OneToMany(mappedBy = "categoriaPai", cascade = CascadeType.ALL,
    // orphanRemoval = true)
    // private List<Categoria> subCategorias;

    public CategoriaORM() {
    }

    public CategoriaORM(UUID id, Nome nomeCategoria, Descricao descricao,
                        UUID idCategoriaPai, String usuario) {

        this.id = id.toString();
        this.nome = nomeCategoria.toString();
        this.descricao = descricao.getDescricao();
        this.categoriaPai = idCategoriaPai.toString();
        this.usuario = usuario;

    }

    @Override
    public String toString() {
        return String.format("""
                {
                    "id":"%s",
                    "nome":"%s",
                    "descricao":"%s",
                    "idCategoriaPai":"%s",
                    "usuario":"%s",
                  
                }
                """,
                id.toString(),
                nome.toString(),
                descricao.toString(),
                categoriaPai == null ? null : categoriaPai.toString(),
                usuario
                );
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

    public String getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(String categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

    public void setUsuario(String usuario) {this.usuario = usuario; }

    public String getUsuario() {return usuario; }
}
