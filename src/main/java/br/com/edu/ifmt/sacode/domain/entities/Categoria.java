package br.com.edu.ifmt.sacode.domain.entities;

import br.com.edu.ifmt.sacode.domain.entities.vo.CategoryName;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Categoria {

    private UUID id;
    private CategoryName nome;
    private Descricao descricao;
    private UUID idCategoriaPai;
    private List<Categoria> subCategorias;

    public Categoria() {
        this.nome = new CategoryName(null);
        this.descricao = new Descricao(null);
        this.subCategorias = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CategoryName getNome() {
        return nome;
    }

    public void setNome(CategoryName nome) {
        this.nome = nome;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    public UUID getIdCategoriaPai() {
        return idCategoriaPai;
    }

    public void setIdCategoriaPai(UUID idCategoriaPai) {
        this.idCategoriaPai = idCategoriaPai;
    }

    public List<Categoria> getSubCategorias() {
        return subCategorias;
    }

    public void setSubCategorias(List<Categoria> subCategorias) {
        this.subCategorias = subCategorias;
    }

    public void addSubCategoria(Categoria subCategoria) {
        subCategoria.setIdCategoriaPai(this.id);
        this.subCategorias.add(subCategoria);
    }

    public void removeSubCategoria(Categoria subCategoria) {
        this.subCategorias.remove(subCategoria);
        if (subCategoria.getIdCategoriaPai() != null) {
            subCategoria.setIdCategoriaPai(null);
        }
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
            idCategoriaPai == null ? null : idCategoriaPai.toString(),
            subCategorias.stream()
                .map(subCategoria -> String.format("{\"nome\":\"%s\", \"descricao\":\"%s\"}", subCategoria.getNome(), subCategoria.getDescricao()))
                .collect(Collectors.joining(", ")));
}
}
