package br.com.edu.ifmt.sacode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/helloworld")
public class SwaggerController {

    @Operation(summary = "Hello", description = "Retorna uma mensagem de saudação", tags = "Helloworld")
     @GetMapping
    public String helloWorld() {
        return "helloworld";
    }
}