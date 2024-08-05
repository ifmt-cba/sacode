package br.com.edu.ifmt.sacode.domain.services;

import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UsuarioPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class UsuarioService {

    private ResourceBundle excRB;
    private final UsuarioPort usuarioPort;
    private final LogPort logPort;

    @Autowired
    public UsuarioService(UsuarioPort createUsuario, LogPort logPort) {
        this.usuarioPort = createUsuario;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt","BR"));
    }

    public Usuario salvarUsuario(Usuario usuario) throws UsuarioException {
        logPort.trace("-> UsuarioService.createUsuario()");
        StringBuilder exc = new StringBuilder();

        if (usuario.getNomeUsuario().toString() == null)
            exc.append(excRB.getString("usuario.username.invalid").concat(" "));

        if (usuario.getEmail().toString() == null)
            exc.append(excRB.getString("usuario.email.invalid").concat(" "));

        if (usuario.getSenha().toString() == null)
            exc.append(excRB.getString("usuario.password.invalid").concat(" "));

        if (!exc.isEmpty()){
            logPort.warn(exc.toString());
            throw new UsuarioException(exc.toString());
        }
        Usuario usuarioResponse = usuarioPort.salvarUsuario(usuario);
        logPort.info("Usuario successfully created.");
        logPort.debug(usuario.toString());
        logPort.trace("<- UsuarioService.createUsuario()");
        return usuarioResponse;
    }

    public void deletarUsuario(UUID id, Usuario usuario) throws UsuarioException {
        logPort.trace("-> UsuarioService.deleteUsuario()");

        //categoriaPort.deleteCategoriaByUsuarioId(usuario.usuarioId);

        //despesaPort.deleteDespesaByUsuarioId(usuario.usuarioId);

        usuarioPort.deletarUsuario(id,usuario);

        logPort.info("Usuario successfully deleted.");
        logPort.trace("<- UsuarioService.deleteUsuario()");

    }


    public Usuario buscaPorIdUsuario(UUID usuarioId) throws UsuarioException {
        logPort.trace("-> UsuarioService.getByIdUsuario()");
        StringBuilder exc = new StringBuilder();

        Usuario usuarioResponse = usuarioPort.buscarPorIdUsuario(usuarioId);

        if(isNull(usuarioResponse)){
            logPort.info(excRB.getString("usuario.not.found").concat(" "));
            throw new UsuarioException(exc.toString());
        }

        logPort.info("Search Usuario successfully.");
        logPort.debug(usuarioResponse.toString());
        logPort.trace("<- UsuarioService.getByIdUsuario()");
        return usuarioResponse;

    }

}
