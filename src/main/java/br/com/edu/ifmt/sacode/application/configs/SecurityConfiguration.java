package br.com.edu.ifmt.sacode.application.configs;

import br.com.edu.ifmt.sacode.application.interceptors.UserAuthenticationFilter;
import br.com.edu.ifmt.sacode.application.interceptors.UserBasicAuthenticationFilter;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

        private final UserAuthenticationFilter userAuthenticationFilter;

        private final UserDetailsService userDetailsService;

        private final AdminServerProperties adminServer;

        // public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
        // "/usuarios/login",
        // "/usuarios",
        // "/swagger-resources/**",
        // "/swagger-ui/**",
        // "/swagger-ui/index.html",
        // "/swagger-ui/index",
        // "/v3/api-docs/**",
        // "/v3/api-docs/",
        // "/swagger-ui.html",
        // "/webjars/**"
        // };

        @Bean
        public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
                final SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
                successHandler.setTargetUrlParameter("redirectTo");
                successHandler.setDefaultTargetUrl(this.adminServer.path(""));
                return http.authorizeHttpRequests(req -> req
                                .requestMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
                                .requestMatchers(this.adminServer.getContextPath() + "/login").permitAll()
                                .requestMatchers(this.adminServer.getContextPath() + "/logout/**").permitAll()
                                .requestMatchers(this.adminServer.getContextPath() + "/instances/**").permitAll()
                                .requestMatchers("/usuarios", "/usuarios/login", "/v3/api-docs/**",
                                                "/swagger-resources/**",
                                                "/swagger-ui.html", "/swagger-ui/**", "/configuration/security",
                                                "/actuator/**",
                                                "/webjars/**", "/vendor/**")
                                .permitAll()
                                .anyRequest().authenticated())
                                .formLogin(formLogin -> formLogin
                                                .loginPage(this.adminServer.getContextPath() + "/login")
                                                .successHandler(successHandler))
                                .logout((logout) -> logout.logoutUrl(this.adminServer.getContextPath() + "/logout"))
                                .httpBasic(Customizer.withDefaults())
                                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                                .ignoringRequestMatchers(
                                                                new AntPathRequestMatcher(
                                                                                this.adminServer.getContextPath()
                                                                                                + "/instances",
                                                                                HttpMethod.POST.toString()),
                                                                new AntPathRequestMatcher(
                                                                                this.adminServer.getContextPath()
                                                                                                + "/instances/*",
                                                                                HttpMethod.DELETE.toString()),
                                                                new AntPathRequestMatcher(
                                                                                this.adminServer.getContextPath()
                                                                                                + "/actuator/**")))
                                .rememberMe(rememberMe -> rememberMe.key(UUID.randomUUID().toString())
                                                .tokenValiditySeconds(1209600))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(this.userAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(
                                                new UserBasicAuthenticationFilter(this.userDetailsService,
                                                                this.passwordEncoder()),
                                                BasicAuthenticationFilter.class)
                                .build();

                // return http.csrf(csrf -> csrf.disable())
                // .sessionManagement(session -> session
                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // .authorizeHttpRequests((authz) -> authz
                // .requestMatchers("/usuarios", "/usuarios/login", "/v3/api-docs/**",
                // "/swagger-resources/**", "/swagger-ui.html",
                // "/swagger-ui/**", "/configuration/security",
                // "/actuator/**", "/webjars/**", "/vendor/**")
                // .permitAll()
                // .anyRequest()
                // .authenticated())
                // .addFilterBefore(userAuthenticationFilter,
                // UsernamePasswordAuthenticationFilter.class)
                // .build();

        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new Sha256PasswordEncoder();
        }

}