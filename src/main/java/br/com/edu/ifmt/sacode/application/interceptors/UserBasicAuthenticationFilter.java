package br.com.edu.ifmt.sacode.application.interceptors;

import java.io.IOException;
import java.util.Base64;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserBasicAuthenticationFilter extends OncePerRequestFilter {

    private static final int BASIC_LENGTH = 6;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {
        final var headerAuthorization = request.getHeader("Authorization");

        if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final var basicToken = headerAuthorization.substring(BASIC_LENGTH);
        final byte[] basicTokenDecoded = Base64.getDecoder().decode(basicToken);

        final String basicTokenValue = new String(basicTokenDecoded);

        final String[] basicAuthsSplit = basicTokenValue.split(":");

        final var userDetails = this.userDetailsService.loadUserByUsername(basicAuthsSplit[0]);

        if (userDetails.getPassword().equals(this.passwordEncoder.encode(basicAuthsSplit[1]))) {

            final var authToken = new UsernamePasswordAuthenticationToken(basicAuthsSplit[0], null, null);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);

    }

}