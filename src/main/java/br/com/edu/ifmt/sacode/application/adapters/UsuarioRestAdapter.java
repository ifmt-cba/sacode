package br.com.edu.ifmt.sacode.application.adapters;

import br.com.edu.ifmt.sacode.application.io.*;
import br.com.edu.ifmt.sacode.application.usecases.usuario.*;
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
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final LogPort logPort;

    UsuarioRestAdapter(CriarUsuarioUseCase criarUsuarioUseCase, LogPort logPort,
                       BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase, DeletarUsuarioUseCase deletarUsuarioUseCase,
                       AutenticarUsuarioUseCase autenticarUsuarioUseCase, AtualizarUsuarioUseCase atualizarUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.logPort = logPort;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<RecuperaJwTokenDto> autenticarUsuario(@RequestBody AutenticarUsuarioRequest request){
        RecuperaJwTokenDto token = autenticarUsuarioUseCase.autenticar(request);

        return ResponseEntity.status(200).body(token);
    }

    @GetMapping("/{id}")
    public CriarUsuarioResponse buscarUsuario(@PathVariable("id") String id) throws UsuarioException {
        return buscarUsuarioPorIdUseCase.buscarUsuario(id);
    }


    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestHeader Map<String, String> headers, @RequestBody CriarUsuarioRequest request) {
        logPort.trace("-> POST /users");
        logPort.debug(headers.toString());
        logPort.debug(request.toString());
        try {
            CriarUsuarioResponse reply = criarUsuarioUseCase.criarUsuario(request);
            logPort.debug(reply.toString());
            logPort.trace("<- POST /users");
            return ResponseEntity.status(201).body(reply);
        } catch (NoSuchAlgorithmException e) {
            logPort.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (UsuarioException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") String id, @RequestBody Usuario usuario) throws UsuarioException {
        deletarUsuarioUseCase.deletarUsuario(id, usuario);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtualizarUsuarioResponse> atualizarUsuario(@PathVariable("id") String id, @RequestBody CriarUsuarioRequest atualizarUsuarioRequest) throws UsuarioException, NoSuchAlgorithmException {
        logPort.trace("-> PUT /usuarios/{id}");
        logPort.debug(atualizarUsuarioRequest.toString());
        AtualizarUsuarioResponse reply = atualizarUsuarioUseCase.atualizarUsuario(atualizarUsuarioRequest);
        logPort.debug(reply.toString());
        logPort.trace("<- PUT /usuarios/{id}");
        return ResponseEntity.status(200).body(reply);
    }

}
