package br.com.edu.ifmt.sacode.domain.ports;

import org.springframework.stereotype.Component;

import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;

@Component
public interface MoedaPort {
    Moeda createMoeda(Moeda moeda);
}
