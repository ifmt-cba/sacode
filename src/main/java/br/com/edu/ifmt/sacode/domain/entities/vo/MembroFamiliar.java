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
public class MembroFamiliar {
    private static final String regex = "^[A-Z][a-z]+( [A-Z][a-z]?).{3,29}$";

    private String membroFamiliar;

    public MembroFamiliar(String membroFamiliar) {
        if (membroFamiliar != null && Pattern.matches(regex, membroFamiliar)) {
            this.membroFamiliar = membroFamiliar;
        } else {
            this.membroFamiliar = null;
        }
    }

    @Override
    public String toString() {
        return membroFamiliar;
    }

}
