package br.com.edu.ifmt.sacode.domain.entities.vo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailTest {

    @Test
    void testNullEmail() {
        assertNull(new Email(null).toString());
    }

    @Test
    void testInvalidLocalPart() {
        assertNull(new Email("@domain.com").toString());
    }

    @Test
    void testInvalidDomainPart() {
        assertNull(new Email("localpart@domain").toString());
    }

    @Test
    void testValidEmail() {
        assertNotNull(new Email("localpart@domain.com").toString());
    }
}

