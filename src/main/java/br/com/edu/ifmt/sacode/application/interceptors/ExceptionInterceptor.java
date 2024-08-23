package br.com.edu.ifmt.sacode.application.interceptors;

import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionInterceptor {

    private final LogPort logPort;

    ExceptionInterceptor(LogPort logPort){
        this.logPort = logPort;
    }

    @ExceptionHandler({ UsuarioException.class })
    public ResponseEntity<Object> handleAll(UsuarioException ex) {
        logPort.error(ex.getMessage());
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<Object> AuthenticationHandler(AuthenticationException ex, WebRequest request) {
        return ResponseEntity.badRequest().body("Usuário e/ou senha inválidos.");
    }

    @ExceptionHandler({ JWTVerificationException.class })
    public ResponseEntity<Object> JwtVerificationHandler(JWTVerificationException ex, WebRequest request) {
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler({ NullPointerException.class })
    public ResponseEntity<Object> handleMethodNullPointer(NullPointerException ex, WebRequest request) {
        String error = "";
        if (ex.getMessage() != null) {
            error = ex.getMessage() + " value null";
        }
        else {
            logPort.error("Erro: " + ex.getStackTrace());
            error = "Erro ao realizar validação, um campo nulo foi enviado";
        }
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> AccessDeniedHandler(AccessDeniedException ex, WebRequest request) {
        return ResponseEntity.status(401).body("Acesso não autorizado.");
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String erro = "Erro de integridade de dados. Verifique os dados enviados.";
        String detalheEspecifico = ex.getMostSpecificCause().getMessage();

        if (detalheEspecifico.contains("email_un")) {
            erro = "O email já está em uso. Por favor, tente outro.";
        }
        else if (detalheEspecifico.contains("username_un")) {
            erro = "O nome de usuário já está em uso. Por favor, tente outro.";
        }

        return new ResponseEntity<>(erro, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        String mensagem = "Erro de validação: " + ex.getMessage();
        return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaException.class)
    public ResponseEntity<String> handleCategoriaException(CategoriaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
