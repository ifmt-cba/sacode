package br.com.edu.ifmt.sacode.application.adapters;

import br.com.edu.ifmt.sacode.application.io.*;
import br.com.edu.ifmt.sacode.application.usecases.usuario.*;
import br.com.edu.ifmt.sacode.domain.entities.Usuario;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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

    @Operation(
            summary = "Autenticar usuário",
            description = "Autentica um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso", content = @Content(schema = @Schema(implementation = RecuperaJwTokenDto.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<RecuperaJwTokenDto> autenticarUsuario(@RequestBody AutenticarUsuarioRequest request) {
        RecuperaJwTokenDto token = autenticarUsuarioUseCase.autenticar(request);
        return ResponseEntity.status(200).body(token);
    }

    @Operation(
            summary = "Buscar usuário",
            description = "Busca um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(schema = @Schema(implementation = CriarUsuarioResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public CriarUsuarioResponse buscarUsuario(@PathVariable("id") String id) throws UsuarioException {
        return buscarUsuarioPorIdUseCase.buscarUsuario(id);
    }

    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(schema = @Schema(implementation = CriarUsuarioResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestHeader Map<String, String> headers, @RequestBody CriarUsuarioRequest request) throws UsuarioException {
        logPort.trace("-> POST /usuarios");
        logPort.debug(headers.toString());
        logPort.debug(request.toString());
        CriarUsuarioResponse reply = criarUsuarioUseCase.criarUsuario(request);
        logPort.debug(reply.toString());
        logPort.trace("<- POST /usuarios");
        return ResponseEntity.status(201).body(reply);
    }

    @Operation(
            summary = "Deletar usuário",
            description = "Deleta um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") String id, @RequestBody Usuario usuario) throws UsuarioException {
        deletarUsuarioUseCase.deletarUsuario(id, usuario);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza um usuário existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(schema = @Schema(implementation = AtualizarUsuarioResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
            }
    )
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
