package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.util.regex.Pattern;

/*
 * Um nome válido deve atender a seguinte regex : 
 *  ^[a-zA-ZÀ-ÿ\\s'-]{8,80}$
 *  ^ Tipifica que a string deve corresponder a regex do inicio ao final
 *  [a-zA-ZÀ-ÿ\\s'-] Um conjunto de caracteres que inclui letras de A a Z (maiúsculas e minúsculas),
 *  letras acentuadas (como À-ÿ), espaços, hifens e apóstrofos.
 *  {8,80} restrição de comprimento
 *  $ Fim da string
 */
public class Name {

    private String name;
    private static final String regexName = "^[a-zA-ZÀ-ÿ\\s'-]{7,79}$";

    public Name(String name) {
        if (name != null && Pattern.matches(regexName, name)) {
            this.name = name;
        } else {
            this.name = null;
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
