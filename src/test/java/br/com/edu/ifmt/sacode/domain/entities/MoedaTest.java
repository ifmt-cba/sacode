package br.com.edu.ifmt.sacode.domain.entities;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;
import br.com.edu.ifmt.sacode.domain.entities.vo.TipoMoeda;

import java.io.IOException;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MoedaTest {

    @Test
    public void testGetCodigo() {
        Moeda moeda = new Moeda(TipoMoeda.REAL, new BigDecimal(100));
        assertEquals("BRL", moeda.getCodigo());
    }

    @Test
    public void testGetValor() {
        Moeda moeda = new Moeda(TipoMoeda.DOLAR, new BigDecimal(50));
        assertEquals(new BigDecimal(50), moeda.getValor());
    }

    @Test
    public void testSetValor() {
        Moeda moeda = new Moeda(TipoMoeda.EURO, new BigDecimal(200));
        moeda.setValor(new BigDecimal(250));
        assertEquals(new BigDecimal(250), moeda.getValor());
    }

    @Test
    public void testMockMoeda() {
        Moeda moeda = new Moeda(TipoMoeda.REAL, new BigDecimal(0));
        Moeda mockMoeda = moeda.mockMoeda();
        assertEquals(TipoMoeda.REAL.getCodigo(), mockMoeda.getCodigo());
        assertEquals(new BigDecimal(100), mockMoeda.getValor());
    }
}
