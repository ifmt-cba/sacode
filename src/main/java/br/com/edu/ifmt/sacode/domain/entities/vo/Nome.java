package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.util.regex.Pattern;

/**
 * Um nome válido deve atender a seguinte regex :
 * ^[a-zA-ZÀ-ÿ\\s'-]{2,79}$
 * ^ Tipifica que a string deve corresponder a regex do inicio ao final
 * [a-zA-ZÀ-ÿ\\s'-] Um conjunto de caracteres que inclui letras de A a Z
 * (maiúsculas e minúsculas),
 * letras acentuadas (como À-ÿ), espaços, hifens e apóstrofos.
 * {2,79} restrição de comprimento
 * $ Fim da string
 */
public class Nome {
    private static final String regexNome = "^[a-zA-ZÀ-ÿ\\s'-]{2,79}$";

    private String nome;

    public Nome() {
        this.nome = new String();
    }

    public Nome(String nome) {
        if (nome != null && Pattern.matches(regexNome, nome)) {
            this.nome = nome;
        } else {
            this.nome = null;
        }
    }

    @Override
    public String toString() {
        return nome;
    }

}
