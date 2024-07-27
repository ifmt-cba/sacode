package br.com.edu.ifmt.sacode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class DespesaTest {

  @Test
  void testToStringJSONFormat() {
    assertDoesNotThrow(new Executable() {

      @Override
      public void execute() throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readValue(new Usuario().toString().getBytes(),Map.class);
      }
    });
  }
}
