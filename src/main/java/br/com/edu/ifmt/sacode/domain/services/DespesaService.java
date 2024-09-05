package br.com.edu.ifmt.sacode.domain.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static java.util.Objects.isNull;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.DespesaPort;
import br.com.edu.ifmt.sacode.domain.services.exception.DespesaException;

@Service
@RequiredArgsConstructor
public class DespesaService {
    private ResourceBundle excRB;
    private final LogPort logPort;
    private final DespesaPort despesaPort;


    public Despesa criarDespesa(Despesa despesa) throws DespesaException {

        logPort.trace("-> DespesaService.criarDespesa()");
        StringBuilder exc = new StringBuilder();
        Despesa despesaResponse;
        try {
            if (despesa == null) {
                exc.append(excRB.getString("despesa.isNull").concat(" "));
                logPort.warn(exc.toString());
                throw new DespesaException(exc.toString());
            }

            if (!verificarCampos(despesa)) {
                exc.append(excRB.getString("despesa.verificarCampos").concat(" "));
                logPort.warn(exc.toString());
                throw new DespesaException(exc.toString());
            }
            despesaResponse = despesaPort.criarDespesa(despesa);
        } catch (DespesaException e) {
            throw new DespesaException(e.getMessage());
        }
        logPort.info("Despesa criada com sucesso.");
        logPort.debug(despesa.toString());
        logPort.trace("<- DespesaService.criarDespesa()");
        return despesaResponse;
    }

    public boolean verificarCampos(Despesa despesa) throws DespesaException {
        logPort.trace("-> DespesaService.verificarCampos()");

        StringBuilder exc = new StringBuilder();

        if (isNull(despesa.getDescricao()))
            exc.append(excRB.getString("despesa.descricao.invalid").concat(" "));

        if (isNull(despesa.getValor()))
            exc.append(excRB.getString("despesa.valor.invalid").concat(" "));

        if (isNull(despesa.getData()))
            exc.append(excRB.getString("despesa.data.invalid").concat(" "));

        if (isNull(despesa.getUsuario()))
            exc.append(excRB.getString("despesa.usuario.invalid").concat(" "));

        if (isNull(despesa.getAutorDespesa()))
            exc.append(excRB.getString("despesa.autorDespesa.invalid").concat(" "));

        if (isNull(despesa.isFixa()))
            exc.append(excRB.getString("despesa.fixa.invalid").concat(" "));

        if (isNull(despesa.getFinanciadorDespesa()))
            exc.append(excRB.getString("despesa.financiadorDespesa.invalid").concat(" "));

        if (isNull(despesa.getNumParcela()))
            exc.append(excRB.getString("despesa.numParcelas.invalid").concat(" "));
        else if (despesa.getNumParcela() < 0)
            exc.append(excRB.getString("despesa.numParcelas.isNegative").concat(" "));

        if (isNull(despesa.getCorrelacaoParcelas()) && !isNull(despesa.getNumParcela()) && despesa.getNumParcela() > 0)
            exc.append(excRB.getString("despesa.parcelamento.semRefrencia").concat(" "));

        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new DespesaException(exc.toString());
        }
        logPort.trace("<- DespesaService.verificarCampos()");

        return true;
    }

    public int excluirDespesa(UUID idDespesa, UUID usuario) throws DespesaException {

        logPort.trace("-> DespesaService.excluirDespesa()");
        StringBuilder exc = new StringBuilder();
        int resposta = -1;
        try {
            resposta = despesaPort.excluirDespesa(idDespesa, usuario);
        } catch (Exception e) {
            logPort.warn(exc.toString());
            throw new DespesaException("despesa.excluirDespesa().fail " + e);
        }
        logPort.info("Depesa excluida com sucesso. Resultado: " + resposta);
        logPort.trace("<- DespesaService.excluirDespesa()");
        return resposta;
    }

    public int excluirParcelas(UUID usuario, UUID correlacaoParcelas) throws DespesaException {

        logPort.trace("-> DespesaService.excluirParcelas()");
        StringBuilder exc = new StringBuilder();
        int resposta = -1;
        try {
            resposta = despesaPort.excluirParcelas(usuario, correlacaoParcelas);
        } catch (Exception e) {
            logPort.warn(exc.toString());
            throw new DespesaException("despesa.excluirParceclas().fail " + e);
        }
        logPort.info("Depesa excluida com sucesso. Resultado: " + resposta);
        logPort.trace("<- DespesaService.excluirParcelas()");
        return resposta;
    }

    public void atualizarDespesa(Despesa despesa) throws DespesaException {
        logPort.trace("-> DespesaService.atualizarDespesa()");
        StringBuilder exc = new StringBuilder();
        try {
            despesaPort.atualizarDespesa(despesa);
        } catch (Exception e) {
            logPort.warn(exc.toString());
            throw new DespesaException("despesa.atualizarDespesa().fail " + e);
        }

        logPort.info("Depesa atualizada com sucesso.");
        logPort.trace("<- DespesaService.atualizarDespesa()");

    }


    public List<Despesa> buscarDespesasPorMes(UUID user, int ano, int mes) throws DespesaException {
        logPort.trace("-> DespesaService.porMes");
        StringBuilder exc = new StringBuilder();
        String x = new String();
        List<Despesa> despesaResponse;

        if (user == null)
            x += "despesa.porMes.userinvalid"; // exc.append(excRB.getString("despesa.porMes.userinvalid").concat(" "));
        if (mes > 12 || mes < 1)
            x += "despesa.porMes.userinvalid"; // exc.append(excRB.getString("despesa.porMes.userinvalid").concat(" "));
        if (ano <= 0)
            x += "despesa.porMes.userinvalid"; // exc.append(excRB.getString("despesa.porMes.userinvalid").concat(" "));

        if (!x.isEmpty()) {// (!exc.isEmpty()) {
            logPort.warn(x.toString());
            // logPort.warn(exc.toString());
            throw new DespesaException(x.toString());
            // throw new DespesaException(exc.toString());
        }

        despesaResponse = despesaPort.buscarDespesasPorPeriodo(user,
                LocalDate.of(ano, mes, 1),
                LocalDate.of(ano, mes, 1).plusMonths(1).minusDays(1));
        logPort.trace("<- DespesaService.porMes");
        return despesaResponse;
    }

    public List<Despesa> buscarDespesasPorPeriodo(UUID user, LocalDate comeco, LocalDate fim) throws DespesaException {
        logPort.trace("-> DespesaService.porPeriodo()");
        StringBuilder exc = new StringBuilder();

        if (user.toString() == null)
            exc.append(excRB.getString("despesa.porPeriodo().userInvalid").concat(" "));
        if (comeco.toString() == null)
            exc.append(excRB.getString("despesa.porPeriodo().comecoInvalid").concat(" "));
        if (fim.toString() == null)
            exc.append(excRB.getString("despesa.porPeriodo().fimInvalid").concat(" "));

        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new DespesaException(exc.toString());
        }
        List<Despesa> despesaResponse = despesaPort.buscarDespesasPorPeriodo(user, comeco, fim);
        logPort.trace("<- DespesaService.porPeriodo()");
        return despesaResponse;
    }

    public List<Despesa> buscarDespesasPorAutor(UUID usuario, String autorDespesa) throws DespesaException {
        logPort.trace("-> DespesaService.porAutor()");
        StringBuilder exc = new StringBuilder();

        if (usuario.toString() == null)
            exc.append(excRB.getString("despesa.porAutor().userInvalid").concat(" "));
        if (autorDespesa.toString() == null)
            exc.append(excRB.getString("despesa.porAutor().autorInvalid").concat(" "));

        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new DespesaException(exc.toString());
        }
        List<Despesa> despesaResponse = despesaPort.buscarDespesasPorAutor(usuario, autorDespesa);
        logPort.trace("<- DespesaService.porAutor()");
        return despesaResponse;
    }

    public List<Despesa> buscarDespesasPorFinanciador(UUID usuario, String autorDespesa) throws DespesaException {
        logPort.trace("-> DespesaService.porFinanciador()");
        StringBuilder exc = new StringBuilder();

        if (usuario.toString() == null)
            exc.append(excRB.getString("despesa.porFinanciador().userInvalid").concat(" "));
        if (autorDespesa.toString() == null)
            exc.append(excRB.getString("despesa.porFinanciador().financiadorInvalid").concat(" "));

        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new DespesaException(exc.toString());
        }
        List<Despesa> despesaResponse = despesaPort.buscarDespesasPorFinanciador(usuario, autorDespesa);
        logPort.trace("<- DespesaService.porFinanciador()");
        return despesaResponse;
    }

    public List<Despesa> buscarDespesasPorUsuario(UUID usuario) throws DespesaException {
        logPort.trace("-> DespesaService.DespesaPorUsuario()");
        StringBuilder exc = new StringBuilder();

        if (usuario.toString() == null)
            exc.append(excRB.getString("despesa.DespesaPorUsuario().userInvalid").concat(" "));

        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new DespesaException(exc.toString());
        }
        List<Despesa> despesaResponse = despesaPort.buscarDespesasPorUsuario(usuario);
        logPort.trace("<- DespesaService.DespesaPorUsuario()");
        return despesaResponse;
    }

    public Map<String, Map<String, BigDecimal>> buscarDespesasPorAnoEUsuario(int ano, String idUsuario) {
        logPort.trace("-> DespesaService.buscarDespesasPorAnoEUsuario()");
        Map<String, Map<String, BigDecimal>> response = despesaPort.buscarDespesasPorAnoEUsuario(ano, UUID.fromString(idUsuario));
        logPort.trace("<- DespesaService.buscarDespesasPorAnoEUsuario()");
        return response;
    }

}