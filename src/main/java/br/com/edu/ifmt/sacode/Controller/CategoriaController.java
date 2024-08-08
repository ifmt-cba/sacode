package br.com.edu.ifmt.sacode.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.services.CategoriaService;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {this.categoriaService = categoriaService;}

    @PostMapping("/")
    public ResponseEntity<String> criarCategoria(@RequestBody Categoria categoria) 
    {
        try {
            categoriaService.criarCategoria(categoria);
            return new ResponseEntity<>("Categoria criada com sucesso", HttpStatus.CREATED);
        } catch (CategoriaException categoriaException) {
            return new ResponseEntity<>("Categoria rejeitada: {\n" + categoriaException.toString() + "\n Detalhe: " + categoriaException.getMessage() + "\n}", HttpStatus.CONFLICT);
        } catch (Exception exception) {
            return new ResponseEntity<>("Erro ao criar categoria : {\n" + exception.toString() +"\n}", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> atualizarCategoria(@RequestBody Categoria categoria) {
        try {
            categoriaService.atualizarCategoria(categoria);
            return new ResponseEntity<>("Categoria atualizada com sucesso", HttpStatus.CREATED);
        } catch (CategoriaException categoriaException) {
            return new ResponseEntity<>("Categoria rejeitada: {\n" + categoriaException.toString() + "\n Detalhe: " + categoriaException.getMessage() + "\n}", HttpStatus.CONFLICT);
        } catch (Exception exception) {
            return new ResponseEntity<>("Erro ao atualizar categoria : {\n" + exception.toString() +"\n}", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<String> excluirCategoria(@RequestBody Categoria categoria) {
        try {
            categoriaService.excluirCategoria(categoria);
            return new ResponseEntity<>("Categoria excluída com sucesso", HttpStatus.CREATED);
        } catch (CategoriaException categoriaException) {
            return new ResponseEntity<>("Categoria rejeitada: {\n" + categoriaException.toString() + "\n Detalhe: " + categoriaException.getMessage() + "\n}", HttpStatus.CONFLICT);
        } catch (Exception exception) {
            return new ResponseEntity<>("Erro ao excluir categoria : {\n" + exception.toString() +"\n}", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(categoriaService.buscarCategoria(UUID.fromString(String.valueOf(id))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Categoria>> buscarCategoriaPorUsuarioId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(categoriaService.buscarCategoriasPorUsuario(UUID.fromString(String.valueOf(id))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bucapornome/{nome}")
    public ResponseEntity<List<Categoria>> buscarCategoriaPorNome(@PathVariable String nome){
        try {
            return new ResponseEntity<>(categoriaService.buscarCategoriasPorNome(new Nome(nome)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/subcategorias/{id}")
    public ResponseEntity<List<Categoria>> buscarSubCategoriaPorIdCategoria(@PathVariable Long id){
        try {
            return new ResponseEntity<>(categoriaService.buscarSubCategorias(UUID.fromString(String.valueOf(id))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/adicionacategoriasuperior/{idcategoriasuperior}/{idcategoriainferior}")
    public ResponseEntity<String> adicionarCategoriaSuperior(@PathVariable Long idcategoriasuperior, @PathVariable Long idcategoriainferior){
        try {
            categoriaService.adicionarCategoriaSuperior(UUID.fromString(String.valueOf(idcategoriasuperior)), UUID.fromString(String.valueOf(idcategoriainferior)));
            return new ResponseEntity<>("Categoria superior adicionada", HttpStatus.CREATED);
        } catch (CategoriaException categoriaException) {
            return new ResponseEntity<>("Categoria rejeitada: {\n" + categoriaException.toString() + "\n Detalhe: " + categoriaException.getMessage() + "\n}", HttpStatus.CONFLICT);
        } catch (Exception exception) {
            return new ResponseEntity<>("Erro ao adicionar categoria : {\n" + exception.toString() +"\n}", HttpStatus.BAD_REQUEST);
        }
    }
}
