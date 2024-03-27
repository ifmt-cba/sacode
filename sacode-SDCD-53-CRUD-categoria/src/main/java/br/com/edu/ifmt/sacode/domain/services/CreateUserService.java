package br.com.edu.ifmt.sacode.domain.services;


import br.com.edu.ifmt.sacode.domain.entities.User;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UserPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UserException;

import java.util.Locale;
import java.util.ResourceBundle;

public class CreateUserService {
    private ResourceBundle excRB;
    private final UserPort userPort;
    private final LogPort logPort;

    public CreateUserService(UserPort createUser, LogPort logPort) {
        this.userPort = createUser;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt","BR"));
    }

    public User createUser(User user) throws UserException {
        logPort.trace("-> CreateUserService.createUser()");
        StringBuilder exc = new StringBuilder();

        if (user.getUsername().toString() == null)
            exc.append(excRB.getString("user.username.invalid").concat(" "));

        if (user.getEmail().toString() == null)
            exc.append(excRB.getString("user.email.invalid").concat(" "));

        if (user.getPassword().toString() == null)
            exc.append(excRB.getString("user.password.invalid").concat(" "));

        if (!exc.isEmpty()){
            logPort.warn(exc.toString());
            throw new UserException(exc.toString());
        }
        User userResponse = userPort.createUser(user);
        logPort.info("User successfully created.");
        logPort.debug(user.toString());
        logPort.trace("<- CreateUserService.createUser()");
        return userResponse;
    }
}

