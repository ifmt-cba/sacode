package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.util.regex.Pattern;

/**
 * O nome do MembroFamiliar deve ser:
 * - ser nome seguido de sobrenome,
 * - começando por letra maiuscula
 * - o sobrnome é opcional
 * - e o tamanho mínimo é 30 e mínimo 3.
 * - sendo ^[A-Z][a-z]+( [A-Z][a-z]?).{3,29}$
 */
public class Nome {
    private static final String regex = "^[A-Z][a-z]+( [A-Z][a-z]?).{3,29}$";

    private String nome;

    public Nome(){this.nome = new String();}
    public Nome(String nome) {
        if (nome != null && Pattern.matches(regex, nome)) {
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
