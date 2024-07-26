package br.com.edu.ifmt.sacode.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.DespesaPort;
import br.com.edu.ifmt.sacode.domain.services.exception.DespesaException;

@Service
public class DespesaService {
    private ResourceBundle excRB;
    private final LogPort logPort;
    private final DespesaPort despesaPort;

    @Autowired
    public DespesaService(DespesaPort createDespesa, LogPort logPort) {
        this.despesaPort = createDespesa;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt", "BR"));
    }

    public Despesa criarDespesa(Despesa despesa) throws DespesaException {

        logPort.trace("-> DespesaService.criarDespesa()");
        StringBuilder exc = new StringBuilder();
        Despesa despesaResponse;
        try {
            if (despesa == null)
                exc.append(excRB.getString("despesa.isNull").concat(" "));

            if (verificarCampos(despesa))
                exc.append(excRB.getString("despesa.verificarCampos").concat(" "));

            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new DespesaException(exc.toString());
            }
            despesaResponse = despesaPort.criarDespesa(despesa);
        } catch (Exception e) {
            throw new DespesaException("{\n"+exc.toString()+"\nDetalhe: "+e.getMessage()+"\nCausa: "+e.getCause()+"\n}");
        }
        logPort.info("Despesa criada com sucesso.");
        logPort.debug(despesa.toString());
        logPort.trace("<- DespesaService.criarDespesa()");
        return despesaResponse;
    }

    
    private boolean verificarCampos(Despesa despesa) throws DespesaException {
        logPort.trace("-> DespesaService.verificarCampos()");

        StringBuilder exc = new StringBuilder();
        if (despesa.getDescricao().toString() == null)
            exc.append(excRB.getString("despesa.descricao.invalid").concat(" "));

        if (despesa.getValor().toString() == null)
            exc.append(excRB.getString("despesa.valor.invalid").concat(" "));

        if (despesa.getData().toString() == null)
            exc.append(excRB.getString("despesa.data.invalid").concat(" "));

        if (despesa.getUsuario().toString() == null)
            exc.append(excRB.getString("despesa.usuario.invalid").concat(" "));

        if (despesa.getAutorDespesa().toString() == null)
            exc.append(excRB.getString("despesa.autorDespesa.invalid").concat(" "));

        if (despesa.isFixa().equals(null))
            exc.append(excRB.getString("despesa.fixa.invalid").concat(" "));

        if (despesa.getFinanciadorDespesa().toString() == null)
            exc.append(excRB.getString("despesa.financiadorDespesa.invalid").concat(" "));

        if (despesa.getNumParcela().equals(null))
            exc.append(excRB.getString("despesa.numParcelas.invalid").concat(" "));

        if (despesa.getNumParcela() < 0)
            exc.append(excRB.getString("despesa.numParcelas.isNegative").concat(" "));

        if (despesa.getCorrelacaoParcelas().toString() == null && despesa.getNumParcela() > 0)
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

    /*
     * GETTERS
     * porAnos
     */
    public List<Despesa> porMes(UUID user, int ano, int mes) throws DespesaException {
        logPort.trace("-> DespesaService.porMes");
        StringBuilder exc = new StringBuilder();
        List<Despesa> despesaResponse;
        
        if (user == null)
            exc.append(excRB.getString("despesa.porMes.userinvalid").concat(" "));
        if(mes>12||mes<1)
            exc.append(excRB.getString("despesa.porMes.mesExcedido").concat(" "));
        if(ano<=0)
            exc.append(excRB.getString("despesa.porMes.anoNegativo").concat(" "));

        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new DespesaException(exc.toString());
        }
        
        despesaResponse = despesaPort.buscarDespesaPorPeriodo(user,
            LocalDate.of(ano,mes,1),
            LocalDate.of(ano, mes, 1).plusMonths(1).minusDays(1)
        );
        logPort.trace("<- DespesaService.porMes");
        return despesaResponse;
    }

    public List<Despesa> porPeriodo(UUID user, LocalDate comeco, LocalDate fim) throws DespesaException {
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
        List<Despesa> despesaResponse = despesaPort.buscarDespesaPorPeriodo(user, comeco, fim);
        logPort.trace("<- DespesaService.porPeriodo()");
        return despesaResponse;
    }

    public List<Despesa> porAutor(UUID usuario, String autorDespesa)  throws DespesaException {
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
        List<Despesa> despesaResponse = despesaPort.buscarDespesaPorAutor(usuario, autorDespesa);
        logPort.trace("<- DespesaService.porAutor()");
        return despesaResponse;
    }
    
    public List<Despesa> porFinanciador(UUID usuario, String financiadorDespesa) throws DespesaException {
        logPort.trace("-> DespesaService.porFinanciador()");
        StringBuilder exc = new StringBuilder();

        
        if (usuario.toString() == null)
            exc.append(excRB.getString("despesa.porFinanciador().userInvalid").concat(" "));
        if (financiadorDespesa.toString() == null)
            exc.append(excRB.getString("despesa.porFinanciador().financiadorInvalid").concat(" "));
        
        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new DespesaException(exc.toString());
        }
        List<Despesa> despesaResponse = despesaPort.buscarDespesaPorFinanciador(usuario, financiadorDespesa);
        logPort.trace("<- DespesaService.porFinanciador()");
        return despesaResponse;
    }
    
    public List<Despesa> porUsuario(UUID usuario)  throws DespesaException {
        logPort.trace("-> DespesaService.DespesaPorUsuario()");
        StringBuilder exc = new StringBuilder();

        
        if (usuario.toString() == null)
            exc.append(excRB.getString("despesa.DespesaPorUsuario().userInvalid").concat(" "));
        
        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new DespesaException(exc.toString());
        }
        List<Despesa> despesaResponse = despesaPort.buscarDespesaPorUsuario(usuario);
        logPort.trace("<- DespesaService.DespesaPorUsuario()");
        return despesaResponse;
    }
}