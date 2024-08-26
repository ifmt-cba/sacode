package br.com.edu.ifmt.sacode.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.edu.ifmt.sacode.application.io.CategoriaRequest;
import br.com.edu.ifmt.sacode.application.io.CategoriaResponse;
import br.com.edu.ifmt.sacode.application.mappers.CategoriaDTOMapper;
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

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/categoria")
public class CategoriaRestAdapter {
    private final CategoriaService categoriaService;

    private final CategoriaDTOMapper mapper;

    @Autowired
    public CategoriaRestAdapter(CategoriaService categoriaService, CategoriaDTOMapper mapper) {
        this.categoriaService = categoriaService;
        this.mapper = mapper;
    }

    @PostMapping("/")
    public ResponseEntity<String> criarCategoria(@RequestBody CategoriaRequest request) throws CategoriaException {
            Categoria categoria = mapper.toCategoria(request);
            categoriaService.criarCategoria(categoria);
            return new ResponseEntity<>("Categoria criada com sucesso", HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<String> atualizarCategoria(@RequestBody CategoriaRequest request) throws CategoriaException {
        Categoria categoria = mapper.toCategoria(request);
        categoriaService.atualizarCategoria(categoria);
        return new ResponseEntity<>("Categoria atualizada com sucesso", HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> excluirCategoria(@RequestBody CategoriaRequest request) throws CategoriaException {
        Categoria categoria = mapper.toCategoria(request);
        categoriaService.excluirCategoria(categoria);
        return new ResponseEntity<>("Categoria excluída com sucesso", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarCategoriaPorId(@PathVariable String id) throws CategoriaException {
        Categoria categoria = categoriaService.buscarCategoria(UUID.fromString(id));
        return ResponseEntity.ok(new CategoriaResponse(categoria.getId().toString(), categoria.getUsuario().toString(), categoria.getNome().toString(),
                categoria.getDescricao().toString(), nonNull(categoria.getIdCategoriaSuperior()) ? categoria.getIdCategoriaSuperior().toString() : null));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<CategoriaResponse>> buscarCategoriaPorUsuarioId(@PathVariable String id) throws CategoriaException {
        List<Categoria> categorias = categoriaService.buscarCategoriasPorUsuario(UUID.fromString(id));
        return ResponseEntity.ok(categorias.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/bucapornome/{nome}")
    public ResponseEntity<List<CategoriaResponse>> buscarCategoriaPorNome(@PathVariable String nome) throws CategoriaException {
        List<Categoria> categorias = categoriaService.buscarCategoriasPorNome(new Nome(nome));
        return ResponseEntity.ok(categorias.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }


    @GetMapping("/subcategorias/{id}")
    public ResponseEntity<List<CategoriaResponse>> buscarSubCategoriaPorIdCategoria(@PathVariable String id) throws CategoriaException {
        List<Categoria> categorias = categoriaService.buscarSubCategorias(UUID.fromString(id));
        return ResponseEntity.ok(categorias.stream().map(mapper::toResponse).collect(Collectors.toList()));

    }

    @PostMapping("/adicionacategoriasuperior/{idcategoriasuperior}/{idcategoriainferior}")
    public ResponseEntity<String> adicionarCategoriaSuperior(@PathVariable String idcategoriasuperior, @PathVariable String idcategoriainferior) throws CategoriaException {
        categoriaService.adicionarCategoriaSuperior(UUID.fromString(idcategoriasuperior), UUID.fromString(idcategoriainferior));
        return new ResponseEntity<>("Categoria superior adicionada", HttpStatus.CREATED);

    }
}
