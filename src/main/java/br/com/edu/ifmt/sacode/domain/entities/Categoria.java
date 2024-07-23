package br.com.edu.ifmt.sacode.domain.entities;

import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;

import java.util.UUID;

public class Categoria {

    private UUID id;
    private UUID usuario;
    private Nome nome;
    private Descricao descricao;
    private UUID idCategoriaPai;

    public Categoria() {
        this.usuario = UUID.randomUUID();
        this.nome = new Nome();
        this.descricao = new Descricao(null);
        this.idCategoriaPai = null;
    }

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    public UUID getUsuario() {return usuario;}
    public void setUsuario(UUID usuario) {this.usuario = usuario;}

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

    public UUID getIdCategoriaPai() {
        return idCategoriaPai;
    }

    public void setIdCategoriaPai(UUID idCategoriaPai) {
        this.idCategoriaPai = idCategoriaPai;
    }


//    @Override
//    public String toString() {
//        return String.format("""
//                {
//                    "id":"%s",
//                    "usuario":"%s"
//                    "nome":"%s",
//                    "descricao":"%s",
//                    "idCategoriaPai":"%s"
//                }
//                """,
//                String.valueOf(id),
//                usuario.toString(),
//                nome.toString(),
//                descricao.toString(),
//                idCategoriaPai == null ? "null" : idCategoriaPai.toString());
//    }

//    @Override
//    public String toString() {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(this);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return "{}"; // Retorna um JSON vazio em caso de erro
//        }
//    }

    @Override
    public String toString() {
        return String.format(
                "{\"id\": \"%s\", \"nome\": \"%s\", \"descricao\": \"%s\", \"idCategoriaPai\": \"%s\"}",
                id != null ? id : "null",
                nome != null ? nome : "null",
                descricao != null ? descricao : "null",
                idCategoriaPai != null ? idCategoriaPai : "null"
        );
    }
}
