package br.com.edu.ifmt.sacode.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sacode")
                        .version("1.0.0")
                        .description("Swagger do Sacode")
                        .contact(new Contact().name("Thiago").email("thiagoramalhosetubal6@gmail.com"))
                        .license(new License().name("Licença do Sistema").url("http://Sacode.com")));
    }
}
