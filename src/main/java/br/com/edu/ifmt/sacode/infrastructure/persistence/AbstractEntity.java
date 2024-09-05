package br.com.edu.ifmt.sacode.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Getter
@ToString
@MappedSuperclass
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners({ AuditingEntityListener.class })
public abstract class AbstractEntity<T> {

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(updatable = false)
    private Instant criado;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Instant atualizado;

}
