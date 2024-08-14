package br.com.edu.ifmt.sacode.application.interceptors;

import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.exception.UsuarioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

}
