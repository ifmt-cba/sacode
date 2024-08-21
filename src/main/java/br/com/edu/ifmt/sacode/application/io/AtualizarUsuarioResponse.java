package br.com.edu.ifmt.sacode.application.io;

public record AtualizarUsuarioResponse(String idUsuario, String nome, String nomeUsuario, String email, Boolean superUsuario, String error) {
    
}
