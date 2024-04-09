package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.util.regex.Pattern;

/**
 * Um nome de categoria válido deve:
 * - começar com um caractere alfabético, ou seja, [A-Za-z].
 * - Todos os outros caracteres podem ser alfabéticos, sublinhados ou hífen, ou seja, [A-Za-z_-].
 * - Restrição de comprimento é 3-30.
 */
public class NomeCategoria {

    private static final String regex = "^[A-Za-z][A-Za-z_-]{3,30}$";

    private String nomeCategoria;

    public NomeCategoria(String nomeCategoria) {
        if (nomeCategoria != null && Pattern.matches(regex, nomeCategoria)) {
            this.nomeCategoria = nomeCategoria;
        } else {
            this.nomeCategoria = null;
        }
    }

    @Override
    public String toString() {
        return nomeCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        if (nomeCategoria != null && Pattern.matches(regex, nomeCategoria)) {
            this.nomeCategoria = nomeCategoria;
        } else {
            this.nomeCategoria = null;
        }
    }

    public boolean isValid() {
        if (nomeCategoria != null || !nomeCategoria.isEmpty()){
            return true;
        }

        return false;
    }

    

}