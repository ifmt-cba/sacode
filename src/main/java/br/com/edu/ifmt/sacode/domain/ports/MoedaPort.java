package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;

public interface MoedaPort {
    Moeda createMoeda(Moeda moeda);
}
