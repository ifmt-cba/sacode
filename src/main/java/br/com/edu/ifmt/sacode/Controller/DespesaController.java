package br.com.edu.ifmt.sacode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.services.DespesaService;
import br.com.edu.ifmt.sacode.domain.services.exception.DespesaException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/despesa")
public class DespesaController {

    private final DespesaService despesaService;
    
    @Autowired
    public DespesaController(DespesaService despesaService) {this.despesaService = despesaService;}
    
    @GetMapping("/{id}")
    public List<Despesa> buscarDespesasPorUsuario(@PathVariable Long id)   {
        try {
            return despesaService.porUsuario(UUID.fromString(String.valueOf(id)));
        } catch (Exception e) {
            return null;
        }
    }
    
    @GetMapping("/{id}/{autor}")
    public List<Despesa> buscarDespesasPorUsuarioPorAutor(@PathVariable Long id, @PathVariable String autor)   {
        try {
            return despesaService.porAutor( UUID.fromString(String.valueOf(id)), autor);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/{id}/{financiador}")
    public List<Despesa> buscarDespesasPorUsuarioPorFinanciador(@PathVariable Long id, @PathVariable String financiador)  {
        try {
            return despesaService.porAutor( UUID.fromString(String.valueOf(id)), financiador);
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
            despesaService.atualizarDespesa(despesa);
            return new ResponseEntity<>("Despesa excluida com sucesso!", HttpStatus.OK);
        } catch (DespesaException e) {
            return new ResponseEntity<>("Despesa rejeitada:{\n"+e.toString()+"\nDetalhe: "+e.getMessage()+"\nCausa: "+e.getCause()+"\n}", HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.toString() ,HttpStatus.BAD_REQUEST);
        }
    }
}