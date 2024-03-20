package br.com.edu.ifmt.sacode.domain.entities.vo;


import java.util.regex.Pattern;

/**
 * The following restrictions are imposed in the email address’ local part by using this regex:
 * - It allows numeric values from 0 to 9.
 * - Both uppercase and lowercase letters from a to z are allowed.
 * - Allowed are underscore “_”, hyphen “-“, and dot “.”
 * - Dot isn’t allowed at the start and end of the local part.
 * - Consecutive dots aren’t allowed.
 * - For the local part, a maximum of 64 characters are allowed.
 * Restrictions for the domain part in this regular expression include:
 * - It allows numeric values from 0 to 9.
 * - We allow both uppercase and lowercase letters from a to z.
 * - Hyphen “-” and dot “.” aren’t allowed at the start and end of the domain part.
 * - No consecutive dots.
 */
public class Email {
    private static final String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private String email;

    public Email(String email) {
        if (email != null && Pattern.matches(regex, email)) {
            this.email = email;
        } else {
            this.email = null;
        }
    }

    public String getLocalPart() {
        return this.email.split("@")[0];
    }

    public String getDomain() {
        return this.email.split("@")[1];
    }

    @Override
    public String toString() {
        return email;
    }
}
