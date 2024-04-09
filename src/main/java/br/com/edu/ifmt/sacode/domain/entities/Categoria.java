package br.com.edu.ifmt.sacode.domain.entities;

import br.com.edu.ifmt.sacode.domain.entities.vo.CategoryName;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import java.util.UUID;

public class Categoria {

    private UUID id;
    private CategoryName nome;
    private Descricao descricao;
    private UUID idCategoriaPai;

    public Categoria() {
        this.nome = new CategoryName(null);
        this.descricao = new Descricao(null);
        
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


    @Override
    public String toString() {
        return String.format("""
                {
                    "id":"%s",
                    "nome":"%s",
                    "descricao":"%s",
                    "idCategoriaPai":"%s"
                }
                """,
                id.toString(),
                nome.toString(),
                descricao.toString(),
                idCategoriaPai == null ? null : idCategoriaPai.toString());
    }
}
