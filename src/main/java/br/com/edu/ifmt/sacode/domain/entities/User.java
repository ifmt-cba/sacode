package br.com.edu.ifmt.sacode.domain.entities;


import br.com.edu.ifmt.sacode.domain.entities.vo.Email;
import br.com.edu.ifmt.sacode.domain.entities.vo.Password;
import br.com.edu.ifmt.sacode.domain.entities.vo.Username;

public class User {

    private Username username;
    private Password password;
    private Email email;

    public User() {
        this.username = new Username(null);
        this.password = new Password(null);
        this.email = new Email(null);
    }

    public Username getUsername() { return username; }

    public void setUsername(Username username) { this.username = username; }

    public Password getPassword() { return password; }

    public void setPassword(Password password) { this.password = password; }

    public Email getEmail() { return email; }

    public void setEmail(Email email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("""
                {
                    "username":"%s",
                    "email":"%s",
                    "password":"%s"
                }
                """,
                username.toString(),
                email.toString(),
                password.toString());
    }

}

