package br.com.edu.ifmt.sacode.domain.services.MoedaService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;
import br.com.edu.ifmt.sacode.domain.entities.vo.TipoMoeda;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.MoedaPort;
import br.com.edu.ifmt.sacode.domain.services.exception.MoedaException;

public class MoedaService {

    private ResourceBundle excRB;
    private final MoedaPort moedaPort;
    private final LogPort logPort;

    public MoedaService(MoedaPort moedaPort, LogPort logPort){
        this.moedaPort = moedaPort;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt","BR"));
    }
    
    public Moeda createMoeda(Moeda moeda) throws MoedaException {
        logPort.trace("-> MoedaService.createMoeda()");
        StringBuilder exc = new StringBuilder();

        if (moeda.getCodigo() == null || moeda.getCodigo().isEmpty()) {
            exc.append(excRB.getString("O código da moeda não pode ser nulo nem vazio").concat(" "));
        }
        if (moeda.getValor().compareTo(BigDecimal.ZERO) < 0) {
            exc.append(excRB.getString("Valor da moeda não pode ser negativo").concat(" "));
        }if (!exc.isEmpty()){
            logPort.warn(exc.toString());
            throw new MoedaException(exc.toString());
        }
        Moeda moedaResponse = moedaPort.createMoeda(moeda);
        logPort.info("Moeda criada com sucesso.");
        logPort.debug(moeda.toString());
        logPort.trace("<- MoedaService.createMoeda()");
        return moedaResponse;
    }

    public Moeda converterPara(Moeda moeda, TipoMoeda tipoMoeda) throws IOException, JSONException {
        BigDecimal taxaDeCambio = obterTaxaDeCambio(moeda.getCodigo(), tipoMoeda.getCodigo());
        BigDecimal valorConvertido = moeda.getValor().multiply(taxaDeCambio);
        return new Moeda(tipoMoeda, valorConvertido);
    }

    public static BigDecimal obterTaxaDeCambio(String moedaOrigem, String moedaAlvo) throws IOException, JSONException {
        //String apiKey = "SUA_CHAVE_DE_API";
        String urlStr = "https://economia.awesomeapi.com.br/json/last/" + moedaOrigem + "-" + moedaAlvo;

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
        System.out.println(jsonObject.toString());
        String rates = jsonObject.getJSONObject(moedaOrigem + moedaAlvo).getString("bid");
        
        return new BigDecimal(rates);
    }

}
