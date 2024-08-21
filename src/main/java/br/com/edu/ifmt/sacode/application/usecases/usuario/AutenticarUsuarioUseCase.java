package br.com.edu.ifmt.sacode.application.usecases.usuario;

import br.com.edu.ifmt.sacode.application.configs.JwtTokenService;
import br.com.edu.ifmt.sacode.application.configs.UserDetailsImpl;
import br.com.edu.ifmt.sacode.application.io.AutenticarUsuarioRequest;
import br.com.edu.ifmt.sacode.application.io.RecuperaJwTokenDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AutenticarUsuarioUseCase(AuthenticationManager authenticationManager,
                                    JwtTokenService jwtTokenService){
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    public RecuperaJwTokenDto autenticar(AutenticarUsuarioRequest request)  throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.senha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecuperaJwTokenDto(String.valueOf(userDetails.getUsuario().getIdUsuario()) ,
                String.valueOf(userDetails.getUsuario().getName()), String.valueOf(userDetails.getUsuario().getEmail()),
                userDetails.getUsuario().isSuperUsuario(), jwtTokenService.generateToken(userDetails));
    }


}
