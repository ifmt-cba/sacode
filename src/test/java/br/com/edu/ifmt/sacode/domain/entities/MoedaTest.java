package br.com.edu.ifmt.sacode.domain.entities;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;

import java.io.IOException;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MoedaTest {

    @Mock
    private Moeda moeda;

    @Test
    public void testConverterMoeda() throws IOException, JSONException {
        // Criando instância da moeda
        Moeda moedaOrigem = new Moeda("USD", new BigDecimal(100));

        // Convertendo para outra moeda
        Moeda moedaConvertida = moedaOrigem.converterPara("BRL");

        // Verificando se a conversão está correta
        assertEquals("BRL", moedaConvertida.getCodigo());
        assertEquals(new BigDecimal("497.6700"), moedaConvertida.getValor());
    }
}
