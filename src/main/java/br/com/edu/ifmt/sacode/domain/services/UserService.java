package br.com.edu.ifmt.sacode.domain.services;

import br.com.edu.ifmt.sacode.domain.entities.User;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UserPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UserException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import static java.util.Objects.isNull;

public class UserService {

    private ResourceBundle excRB;
    private final UserPort userPort;
    private final LogPort logPort;

    public UserService(UserPort createUser, LogPort logPort) {
        this.userPort = createUser;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt","BR"));
    }

    public User createUser(User user) throws UserException {
        logPort.trace("-> UserService.createUser()");
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
        logPort.trace("<- UserService.createUser()");
        return userResponse;
    }

    public void deleteUser(UUID id, User user) throws UserException {
        logPort.trace("-> UserService.deleteUser()");

        // permissoes
        //categoriaPort.deleteCategoriaByUserId(user.userId);

        //despesaPort.deleteDespesaByUserId(user.userId);

        userPort.deleteUser(id,user);

        logPort.info("User successfully deleted.");
        logPort.trace("<- UserService.deleteUser()");

    }


    public User getByIdUser(UUID userId) throws UserException {
        logPort.trace("-> UserService.getByIdUser()");
        StringBuilder exc = new StringBuilder();

        User userResponse = userPort.getByIdUser(userId);

        if(isNull(userResponse)){
            logPort.info(excRB.getString("user.not.found").concat(" "));
            throw new UserException(exc.toString());
        }


        logPort.info("Search User successfully.");
        logPort.debug(userResponse.toString());
        logPort.trace("<- UserService.getByIdUser()");
        return userResponse;

    }


    public User updateUser(User user) throws UserException {
        logPort.trace("-> UserService.updateUser()");
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
        User userResponse = userPort.updateUser(user);
        logPort.info("User successfully updated.");
        logPort.debug(user.toString());
        logPort.trace("<- UserService.updateUser()");
        return userResponse;
    }
}
