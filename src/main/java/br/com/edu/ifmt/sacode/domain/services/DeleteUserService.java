package br.com.edu.ifmt.sacode.domain.services;

import br.com.edu.ifmt.sacode.domain.entities.User;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UserPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UserException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

public class DeleteUserService {

    private ResourceBundle excRB;
    private final UserPort userPort;
    private final LogPort logPort;


    public DeleteUserService(UserPort createUser, LogPort logPort, GetByIdUserService getByIdUserService) {
        this.userPort = createUser;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt","BR"));
    }

    public void deleteUser(UUID id, User user) throws UserException {
        logPort.trace("-> DeleteUserService.deleteUser()");

        // permissoes
        //categoriaPort.deleteCategoriaByUserId(user.userId);

        //despesaPort.deleteDespesaByUserId(user.userId);

        userPort.deleteUser(id,user);

        logPort.info("User successfully deleted.");
        logPort.trace("<- DeleteUserService.deleteUser()");

    }

}