package br.com.edu.ifmt.sacode.application.io;

public record CategoriaRequest(String id, String usuarioId, String nome, String descricao, String idCategoriaSuperior) {
}
