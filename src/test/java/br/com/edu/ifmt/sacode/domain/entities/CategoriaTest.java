package br.com.edu.ifmt.sacode.domain.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class CategoriaTest {

    @Test
    void testToStringJSONFormat() {
        assertDoesNotThrow(new Executable() {

            @Override
            public void execute() throws Throwable {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.readValue(new Categoria().toString().getBytes(), Map.class);
            }

        });

    }



}
