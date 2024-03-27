package br.com.edu.ifmt.sacode.domain.entities.vo;


import java.util.regex.Pattern;

/**
 * Passwords accepted just as hash SHA-256
 */
public class Password {

    private static final String regex = "[A-Fa-f0-9]{64}";

    private String password;

    public Password(String password) {
        if (password != null && Pattern.matches(regex, password)) {
            this.password = password;
        } else {
            this.password = null;
        }
    }

    @Override
    public String toString() {
        return password;
    }

}
