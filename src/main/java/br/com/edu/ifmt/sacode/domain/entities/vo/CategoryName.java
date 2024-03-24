package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.util.regex.Pattern;

/**
 * Um nome de categoria válido deve:
 * - começar com um caractere alfabético, ou seja, [A-Za-z].
 * - Todos os outros caracteres podem ser alfabéticos, sublinhados ou hífen, ou seja, [A-Za-z_-].
 * - Restrição de comprimento é 3-30.
 */
public class CategoryName {

    private static final String regex = "^[A-Za-z][A-Za-z_-]{2,29}$";

    private String categoryName;

    public CategoryName(String categoryName) {
        if (categoryName != null && Pattern.matches(regex, categoryName)) {
            this.categoryName = categoryName;
        } else {
            this.categoryName = null;
        }
    }

    @Override
    public String toString() {
        return categoryName;
    }

}