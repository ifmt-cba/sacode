package br.com.edu.ifmt.sacode.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info()
                        .title("Sacode")
                        .version("1.0.0")
                        .description("Para núcleos familiares no qual a falta de controle na " +
                                "despesa familiar é um problema, o Sacode " +
                                "(Sistema de Auxílio ao Controle de Despesas) " +
                                "é uma API para registro de despesas e monitoramento da evolução de gastos, " +
                                "que melhora a conscientização das famílias acerca dos seus gastos, " +
                                "auxiliando no gerenciamento dos seus recursos financeiros e " +
                                "possibilita futuros investimentos. Ao contrário das formas convencionais " +
                                "de gerenciar despesas, como planilhas ou papéis, este produto facilita " +
                                "o desenvolvimento de ferramentas de controle financeiro.")

                        .contact(new Contact().name("Equipe Sacode")
                                .email("preti.joao@ifmt.edu.br, heloisebastos28@hotmail.com, mateusgoulart20@hotmail.com, samuelbianch38@gmail.com, thiagoramalhosetubal6@gmail.com, viniciusdemoraespro@gmail.com, wendersonpfarias@gmail.com"))
                        .license(new License().name("Site do ESA Lab").url("https://esa.ifmt.edu.br/")));
    }
}
