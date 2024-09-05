package br.com.edu.ifmt.sacode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Clock;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class JpaAuditingConfig {

    @Bean
    public AuditingDateTimeProvider auditingDateTimeProvider() {
        return new AuditingDateTimeProvider(Clock.systemUTC());
    }

}
