package br.com.edu.ifmt.sacode.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.services.DespesaService;
import br.com.edu.ifmt.sacode.domain.services.exception.DespesaException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/despesas")
public class DespesaRestAdapter {
    static final String OBJETO_NULO = "Objeto nulo";
    static final String DESPESA_REJEITADA = "Despesa rejeitada:{\n";
    static final String DETALHE = "\nDetalhe: ";
    static final String CAUSA = "\nCausa: ";
    private final DespesaService despesaService;
    
    @Autowired
    public DespesaRestAdapter(DespesaService despesaService) {this.despesaService = despesaService;}
    
    @GetMapping("/{id}")
    public ResponseEntity<List<Despesa>> buscarDespesasPorUsuario (@PathVariable Long id) throws DespesaException {
        return new ResponseEntity<>(
            despesaService.buscarDespesasPorUsuario(UUID.fromString(String.valueOf(id))),
            HttpStatusCode.valueOf(203)
        );
    }
    
    @PostMapping("/")
    public ResponseEntity<String> criarDespesa(@RequestBody Despesa despesa) {
        try {
            if(despesa == null) throw new DespesaException(OBJETO_NULO);
            despesaService.criarDespesa(despesa);
            return new ResponseEntity<>("Despesa criada com sucesso!", HttpStatus.CREATED);
        } catch (DespesaException e) {
            return new ResponseEntity<>(DESPESA_REJEITADA+e.toString()+DETALHE+e.getMessage()+CAUSA+e.getCause()+"\n}", HttpStatus.CONFLICT);
            } catch (Exception e){
                return new ResponseEntity<>(e.toString() ,HttpStatus.CONFLICT);
            }
        }
        
        @PutMapping("/")
        public ResponseEntity<String> atualizarDespesa(@RequestBody Despesa despesa) {
        try {
            if(despesa == null) throw new DespesaException(OBJETO_NULO);
            despesaService.atualizarDespesa(despesa);
            return new ResponseEntity<>("Despesa atualizada com sucesso!", HttpStatus.OK);
        } catch (DespesaException e) {
            return new ResponseEntity<>(DESPESA_REJEITADA+e.toString()+DETALHE+e.getMessage()+CAUSA+e.getCause()+"\n}", HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.toString() ,HttpStatus.CONFLICT);
        }
    }
    
    @DeleteMapping("/")
    public ResponseEntity<String> excluirDespesa(@RequestBody Despesa despesa) {
        try {
            if(despesa == null) throw new DespesaException(OBJETO_NULO);
            despesaService.excluirDespesa(despesa.getIdDespesa(), despesa.getUsuario());
            return new ResponseEntity<>("Despesa excluida com sucesso!", HttpStatus.OK);
        } catch (DespesaException e) {
            return new ResponseEntity<>(DESPESA_REJEITADA+e.toString()+DETALHE+e.getMessage()+CAUSA+e.getCause()+"\n}", HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.toString() ,HttpStatus.CONFLICT);
        }
    }
    
    @GetMapping("/{id}/autor/{autor}")
    public ResponseEntity<List<Despesa>> buscarDespesasPorUsuarioPorAutor(@PathVariable Long id, @PathVariable String autor) throws DespesaException {
        return new ResponseEntity<>(
            despesaService.buscarDespesasPorAutor( UUID.fromString(String.valueOf(id)), autor),
            HttpStatusCode.valueOf(203)
        );
    }

    @GetMapping("/{id}/financiador/{financiador}")
    public ResponseEntity<List<Despesa>> buscarDespesasPorUsuarioPorFinanciador(@PathVariable Long id, @PathVariable String financiador) throws DespesaException {
        return new ResponseEntity<>( 
            despesaService.buscarDespesasPorFinanciador( UUID.fromString(String.valueOf(id)), financiador),
            HttpStatusCode.valueOf(203)
        );
    }
        
    @GetMapping("/{id}/{ano}-{mes}")
    public ResponseEntity<List<Despesa>> buscarDespesasPorMes(@PathVariable Long id, @PathVariable int ano, @PathVariable int mes) throws DespesaException {
        return new ResponseEntity<>(
            despesaService.buscarDespesasPorMes( UUID.fromString(String.valueOf(id)), ano, mes),
            HttpStatusCode.valueOf(203)
        );    
    }

    @GetMapping("/{id}/{ano1}-{mes1}-{dia1}/{ano2}-{mes2}-{dia2}")//
    public ResponseEntity<List<Despesa>> buscarDespesasPorPeriodo (
        @PathVariable Long id,
        @PathVariable int ano1,
        @PathVariable int mes1,
        @PathVariable int dia1,
        @PathVariable int ano2,
        @PathVariable int mes2,
        @PathVariable int dia2
    ) throws DespesaException {
        return new ResponseEntity<>(    
            despesaService.buscarDespesasPorPeriodo(
                UUID.fromString(String.valueOf(id)),
                LocalDate.of(ano1, mes1, dia1),
                LocalDate.of(ano2, mes2, dia2)
            ),
            HttpStatusCode.valueOf(203)
        );
    }
    
    @DeleteMapping("/{id}/parcelas/{parcela}")
    public ResponseEntity<Integer> excluirParcelas(@PathVariable Long id, @PathVariable String parcela) throws DespesaException {
        return new ResponseEntity<> (
            despesaService.excluirParcelas( UUID.fromString(String.valueOf(id)), UUID.fromString(String.valueOf(parcela))),
            HttpStatusCode.valueOf(203)
        );
    }
}