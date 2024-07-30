package br.com.edu.ifmt.sacode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class DespesaController {

    private final DespesaService despesaService;
    
    @Autowired
    public DespesaController(DespesaService despesaService) {this.despesaService = despesaService;}
    
    @GetMapping("/{id}")
    public List<Despesa> buscarDespesasPorUsuario(@PathVariable Long id)   {
        try {
            return despesaService.buscarDespesasPorUsuario(UUID.fromString(String.valueOf(id)));
        } catch (Exception e) {
            return null;
        }
    }
    
    
    @PostMapping("/")
    public ResponseEntity<String> criarDespesa(@RequestBody Despesa despesa) {
        try {
            despesaService.criarDespesa(despesa);
            return new ResponseEntity<>("Despesa criada com sucesso!", HttpStatus.CREATED);
        } catch (DespesaException e) {
            return new ResponseEntity<>("Despesa rejeitada:{\n"+e.toString()+"\nDetalhe: "+e.getMessage()+"\nCausa: "+e.getCause()+"\n}", HttpStatus.CONFLICT);
            //} catch (ExceptionLog e) {
        //    return  new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
    } catch (Exception e){
            return new ResponseEntity<>(e.toString() ,HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/")
    public ResponseEntity<String> atualizarDespesa(@RequestBody Despesa despesa) {
        try {
            despesaService.atualizarDespesa(despesa);
            return new ResponseEntity<>("Despesa atualizada com sucesso!", HttpStatus.OK);
        } catch (DespesaException e) {
            return new ResponseEntity<>("Despesa rejeitada:{\n"+e.toString()+"\nDetalhe: "+e.getMessage()+"\nCausa: "+e.getCause()+"\n}", HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.toString() ,HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/")
    public ResponseEntity<String> excluirDespesa(@RequestBody Despesa despesa) {
        try {
            despesaService.excluirDespesa(despesa.getIdDespesa(), despesa.getUsuario());
            return new ResponseEntity<>("Despesa excluida com sucesso!", HttpStatus.OK);
        } catch (DespesaException e) {
            return new ResponseEntity<>("Despesa rejeitada:{\n"+e.toString()+"\nDetalhe: "+e.getMessage()+"\nCausa: "+e.getCause()+"\n}", HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.toString() ,HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}/autor/{autor}")
    public List<Despesa> buscarDespesasPorUsuarioPorAutor(@PathVariable Long id, @PathVariable String autor)   {
        try {
            return despesaService.buscarDespesasPorAutor( UUID.fromString(String.valueOf(id)), autor);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/{id}/financiador/{financiador}")
    public List<Despesa> buscarDespesasPorUsuarioPorFinanciador(@PathVariable Long id, @PathVariable String financiador)  {
        try {
            return despesaService.buscarDespesasPorFinanciador( UUID.fromString(String.valueOf(id)), financiador);
        } catch (Exception e) {
            return null;
        }
    }
    
    @GetMapping("/{id}/{ano}-{mes}")
    public List<Despesa> buscarDespesasPorMes(@PathVariable Long id, @PathVariable int ano, @PathVariable int mes)  {
        try {
            return despesaService.buscarDespesasPorMes( UUID.fromString(String.valueOf(id)), ano, mes);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/{id}/{ano1}-{mes1}-{dia1}/{ano2}-{mes2}-{dia2}")//
    public List<Despesa> buscarDespesasPorPeriodo(
        @PathVariable Long id,
        @PathVariable int ano1,
        @PathVariable int mes1,
        @PathVariable int dia1,
        @PathVariable int ano2,
        @PathVariable int mes2,
        @PathVariable int dia2
        )  {
        try {
            return despesaService.buscarDespesasPorPeriodo(
                UUID.fromString(String.valueOf(id)),
                LocalDate.of(ano1, mes1, dia1),
                LocalDate.of(ano2, mes2, dia2)
                );
        } catch (Exception e) {
            return null;
        }
    }
    
    @DeleteMapping("/{id}/parcelas/{parcela}")
    public int excluirParcelas(@PathVariable Long id, @PathVariable String parcela)  {
        try {
            return despesaService.excluirParcelas( UUID.fromString(String.valueOf(id)), UUID.fromString(String.valueOf(parcela)));
        } catch (Exception e) {
            return -1;
        }
    }
}