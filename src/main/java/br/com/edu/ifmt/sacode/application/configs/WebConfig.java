package br.com.edu.ifmt.sacode.application.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;
import java.util.Properties;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    @ConditionalOnMissingBean(BuildProperties.class)
    BuildProperties buildProperties() {
        return new BuildProperties(new Properties());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific
                .allowedHeaders("*");
    }

}
