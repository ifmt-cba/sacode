package br.com.edu.ifmt.sacode.application.io;

public record CriarUsuarioResponse(String idUsuario, String nome, String nomeUsuario, String email, Boolean superUsuario, String error) {
}
