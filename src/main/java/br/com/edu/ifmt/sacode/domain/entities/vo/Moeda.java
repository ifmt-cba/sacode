package br.com.edu.ifmt.sacode.domain.entities.vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class Moeda {
    private String codigo;
    private BigDecimal valor;

    public Moeda(String codigo, BigDecimal valor) {
        this.codigo = codigo;
        this.valor = valor;
    }

    public Moeda criar(String codigo, BigDecimal valor) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("Código da moeda não pode ser nulo ou vazio");
        }
        if (valor.compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalArgumentException("Valor da moeda não pode ser negativo");
        }
        return new Moeda(codigo.toUpperCase(), valor);
    }

    public String getCodigo() {
        return this.codigo;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor){
        this.valor = valor;
    }

    public Moeda converterPara(String codigoAlvo) throws IOException, JSONException {
        BigDecimal taxaDeCambio = obterTaxaDeCambio(codigo, codigoAlvo);
        BigDecimal valorConvertido = this.valor.multiply(taxaDeCambio);
        return new Moeda(codigoAlvo, valorConvertido);
    }

    private static BigDecimal obterTaxaDeCambio(String moedaOrigem, String moedaAlvo) throws IOException, JSONException {
        //String apiKey = "SUA_CHAVE_DE_API";
        String urlStr = "https://economia.awesomeapi.com.br/json/last/" + moedaOrigem + moedaAlvo;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONObject rates = jsonObject.getJSONObject("bid");
        return new BigDecimal(rates.getDouble(moedaAlvo));
    }
}
