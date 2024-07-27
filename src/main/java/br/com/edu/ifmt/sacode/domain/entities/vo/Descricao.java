package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.util.regex.Pattern;

/**
 * Uma descrição válida deve:
 * - começar com um caractere alfabético, ou seja, [A-Za-z].
 * - Todos os outros caracteres podem ser alfabéticos, números ou um sublinhado, ou seja, [A-Za-z0-9_].
 * - A restrição de comprimento é 0-500.
 * - A descrição pode ser vazia.
 */
public class Descricao {
    private static final String regexDescricao = "^[A-Za-zÀ-ÿ\\s0-9_'-]{0,500}$";
    private static final Pattern patternDescricao = Pattern.compile(regexDescricao);

    private String descricao;

    public Descricao() {
        this.descricao = "";
    }

    public Descricao(String descricao) {
        if (descricao != null && patternDescricao.matcher(descricao).matches()) {
            this.descricao = descricao;
        } else {
            this.descricao = ""; // ou talvez lançar uma exceção para indicar erro
        }
    }

    @Override
    public String toString() {
        return descricao;
    }
}
