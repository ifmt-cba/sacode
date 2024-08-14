package br.com.edu.ifmt.sacode.application.io;

import lombok.Getter;

@Getter
public class RecuperaJwTokenDto {

    String token;
    public RecuperaJwTokenDto(String token) {
        this.token = token;
    }
}
