package br.com.edu.ifmt.sacode.application.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecuperaJwTokenDto {

    private String idUsuario;
    private String nome;
    private String email;
    private boolean superUsuario;
    private String token;
}
