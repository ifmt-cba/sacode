package br.com.edu.ifmt.sacode.domain.entities;

import br.com.edu.ifmt.sacode.domain.entities.vo.NomeCategoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import java.util.UUID;

public class Categoria {

    private UUID id;
    private UUID usuario;
    private NomeCategoria nome;
    private Descricao descricao;
    private UUID idCategoriaPai;

    public Categoria() {
        this.id = null;
        this.usuario = null;
        this.nome = new NomeCategoria(null);
        this.descricao = new Descricao(null);
        this.idCategoriaPai = null;
    }

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    public UUID getUsuario() {return usuario;}
    public void setUsuario(UUID usuario) {this.usuario = usuario;}

    public NomeCategoria getNome() {
        return nome;
    }

    public void setNome(NomeCategoria nome) {
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
                    "usuario":"%s"
                    "nome":"%s",
                    "descricao":"%s",
                    "idCategoriaPai":"%s"
                }
                """,
                id.toString(),
                usuario.toString(),
                nome.toString(),
                descricao.toString(),
                idCategoriaPai == null ? "null" : idCategoriaPai.toString());
    }
}
