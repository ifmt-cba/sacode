package br.com.edu.ifmt.sacode.infrastructure.persistence;

import java.util.UUID;

import br.com.edu.ifmt.sacode.domain.entities.vo.NomeCategoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class CategoriaORM {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private String categoriaPai;

    // @OneToMany(mappedBy = "categoriaPai", cascade = CascadeType.ALL,
    // orphanRemoval = true)
    // private List<Categoria> subCategorias;

    public CategoriaORM() {
    }

    public CategoriaORM(UUID id, NomeCategoria nome, Descricao descricao,
            UUID idCategoriaPai) {

        this.id = id.toString();
        this.nome = nome.getNomeCategoria();
        this.descricao = descricao.getDescricao();
        this.categoriaPai = idCategoriaPai.toString();

    }

    @Override
    public String toString() {
        return String.format("""
                {
                    "id":"%s",
                    "nome":"%s",
                    "descricao":"%s",
                    "idCategoriaPai":"%s",
                    "subCategorias":[%s]
                }
                """,
                id.toString(),
                nome.toString(),
                descricao.toString(),
                categoriaPai == null ? null : categoriaPai.toString());
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

}
