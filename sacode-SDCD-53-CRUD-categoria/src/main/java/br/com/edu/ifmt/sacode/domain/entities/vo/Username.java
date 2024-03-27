package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.util.regex.Pattern;

/**
 * A valid username should:
 * - start with an alphabet so, [A-Za-z].
 * - All other characters can be alphabets, numbers or an underscore so, [A-Za-z0-9_].
 * - Length constraint is 8-30.
 */
public class Username {

    private static final String regex = "^[A-Za-z][A-Za-z0-9_]{7,29}$";

    private String username;

    public Username(String username) {
        if (username != null && Pattern.matches(regex, username)) {
            this.username = username;
        } else {
            this.username = null;
        }
    }

    @Override
    public String toString() {
        return username;
    }

}
