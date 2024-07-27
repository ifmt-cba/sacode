package br.com.edu.ifmt.sacode.domain.ports;

import org.springframework.stereotype.Component;

@Component
public interface LogPort {
    void trace(String msg);
    void debug(String msg);
    void info(String msg);
    void warn(String msg);
    void error(String msg);
}
