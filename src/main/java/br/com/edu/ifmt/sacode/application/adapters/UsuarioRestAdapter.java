package br.com.edu.ifmt.sacode.application.adapters;

import br.com.edu.ifmt.sacode.application.io.*;
import br.com.edu.ifmt.sacode.application.usecases.usuario.*;
import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("usuarios")
@Tag(name = "Usuário", description = "Operações relacionadas a usuários")
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

    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário")
    @PostMapping("/login")
    public ResponseEntity<RecuperaJwTokenDto> autenticarUsuario(@RequestBody AutenticarUsuarioRequest request){
        RecuperaJwTokenDto token = autenticarUsuarioUseCase.autenticar(request);

        return ResponseEntity.status(200).body(token);
    }

    @Operation(summary = "Buscar usuário", description = "Busca um usuário pelo id")
    @GetMapping("/{id}")
    public CriarUsuarioResponse buscarUsuario(@PathVariable("id") String id) throws UsuarioException {
        return buscarUsuarioPorIdUseCase.buscarUsuario(id);
    }

    @Operation(summary = "Criar usuário", description = "Cria um usuário")
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestHeader Map<String, String> headers, @RequestBody CriarUsuarioRequest request) throws UsuarioException {
        logPort.trace("-> POST /users");
        logPort.debug(headers.toString());
        logPort.debug(request.toString());
        CriarUsuarioResponse reply = criarUsuarioUseCase.criarUsuario(request);
        logPort.debug(reply.toString());
        logPort.trace("<- POST /users");
        return ResponseEntity.status(201).body(reply);
    }

    @Operation(summary = "Deletar usuário", description = "Deleta um usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") String id, @RequestBody Usuario usuario) throws UsuarioException {
        deletarUsuarioUseCase.deletarUsuario(id, usuario);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário")
    @PutMapping("/{id}")
    public ResponseEntity<AtualizarUsuarioResponse> atualizarUsuario(@PathVariable("id") String id, @RequestBody CriarUsuarioRequest atualizarUsuarioRequest) throws UsuarioException {
        logPort.trace("-> PUT /usuarios/{id}");
        logPort.debug(atualizarUsuarioRequest.toString());
        AtualizarUsuarioResponse reply = atualizarUsuarioUseCase.atualizarUsuario(atualizarUsuarioRequest);
        logPort.debug(reply.toString());
        logPort.trace("<- PUT /usuarios/{id}");
        return ResponseEntity.status(200).body(reply);
    }

}
