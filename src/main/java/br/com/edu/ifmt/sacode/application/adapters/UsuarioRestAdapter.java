package br.com.edu.ifmt.sacode.application.adapters;

import br.com.edu.ifmt.sacode.application.io.AutenticarUsuarioRequest;
import br.com.edu.ifmt.sacode.application.io.CriarUsuarioRequest;
import br.com.edu.ifmt.sacode.application.io.CriarUsuarioResponse;
import br.com.edu.ifmt.sacode.application.io.RecuperaJwTokenDto;
import br.com.edu.ifmt.sacode.application.usecases.usuario.AutenticarUsuarioUseCase;
import br.com.edu.ifmt.sacode.application.usecases.usuario.BuscarUsuarioPorIdUseCase;
import br.com.edu.ifmt.sacode.application.usecases.usuario.CriarUsuarioUseCase;
import br.com.edu.ifmt.sacode.application.usecases.usuario.DeletarUsuarioUseCase;
import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("usuarios")
public class UsuarioRestAdapter {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final LogPort logPort;

    UsuarioRestAdapter(CriarUsuarioUseCase criarUsuarioUseCase, LogPort logPort,
                       BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase, DeletarUsuarioUseCase deletarUsuarioUseCase,
                       AutenticarUsuarioUseCase autenticarUsuarioUseCase){
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.logPort = logPort;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<RecuperaJwTokenDto> autenticarUsuario(@RequestBody AutenticarUsuarioRequest request){
        RecuperaJwTokenDto token = autenticarUsuarioUseCase.autenticar(request);

        return ResponseEntity.status(200).body(token);
    }

    @GetMapping("/{id}")
    public CriarUsuarioResponse buscarUsuario(@PathVariable("id") String id) throws Exception{
        return buscarUsuarioPorIdUseCase.buscarUsuario(id);
    }


    @PostMapping
    public CriarUsuarioResponse criarUsuario(@RequestHeader Map<String, String> headers, @RequestBody CriarUsuarioRequest request) {
        logPort.trace("-> POST /users");
        logPort.debug(headers.toString());
        logPort.debug(request.toString());
        try {
            CriarUsuarioResponse reply = criarUsuarioUseCase.criarUsuario(request);
            logPort.debug(reply.toString());
            logPort.trace("<- POST /users");
            return reply;
        } catch (NoSuchAlgorithmException e) {
            logPort.error(e.getMessage());
            return new CriarUsuarioResponse(request.nomeUsuario(), request.email(), e.getMessage());
        } catch (UsuarioException e) {
            return new CriarUsuarioResponse(request.nomeUsuario(), request.email(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") String id, @RequestBody Usuario usuario) throws UsuarioException {
        deletarUsuarioUseCase.deletarUsuario(id, usuario);

        return ResponseEntity.noContent().build();
    }

}
