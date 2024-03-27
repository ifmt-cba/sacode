package br.com.edu.ifmt.sacode.domain.services;

import br.com.edu.ifmt.sacode.domain.entities.User;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UserPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UserException;

import java.util.Locale;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import java.util.UUID;

public class GetByIdUserService {

    private ResourceBundle excRB;
    private final UserPort userPort;
    private final LogPort logPort;

    public GetByIdUserService(UserPort createUser, LogPort logPort) {
        this.userPort = createUser;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt","BR"));
    }

    public User getByIdUser(UUID userId) throws UserException {
        logPort.trace("-> GetByIdUserService.getByIdUser()");
        StringBuilder exc = new StringBuilder();

        User userResponse = userPort.getByIdUser(userId);

        if(isNull(userResponse)){
            logPort.info(excRB.getString("user.not.found").concat(" "));
            throw new UserException(exc.toString());
        }


        logPort.info("Search User successfully.");
        logPort.debug(userResponse.toString());
        logPort.trace("<- GetByIdUserService.getByIdUser()");
        return userResponse;

    }
}
