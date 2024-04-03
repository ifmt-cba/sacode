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

    private static final String regex = "^[A-Za-z][A-Za-z0-9_]{0,499}$";

    private String descricao;

    public Descricao(){
        this.descricao = null;
    }

    public boolean equals(Descricao entrada){
        return this.descricao.equals(entrada.descricao);
    }

    public Descricao(String descricao) {
        if (descricao != null && Pattern.matches(regex, descricao)) {
            this.descricao = descricao;
        } else {
            this.descricao = null;
        }
    }

    @Override
    public String toString() {
        return descricao;
    }
        
    public String getDescricao() {
        return descricao;
    }

    
    

    public static Descricao mock()
    {
        return new Descricao("Roberto");
    }
}