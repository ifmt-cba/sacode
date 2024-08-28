package br.com.edu.ifmt.sacode.application;

import br.com.edu.ifmt.sacode.application.io.CategoriaRequest;
import br.com.edu.ifmt.sacode.application.io.CategoriaResponse;
import br.com.edu.ifmt.sacode.application.mappers.CategoriaDTOMapper;
import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.services.CategoriaService;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria", description = "Operações relacionada a categoria")
public class CategoriaRestAdapter {
    private final CategoriaService categoriaService;
    private final CategoriaDTOMapper mapper;

    @Autowired
    public CategoriaRestAdapter(CategoriaService categoriaService, CategoriaDTOMapper mapper) {
        this.categoriaService = categoriaService;
        this.mapper = mapper;
    }

    @Operation(summary = "Cria uma nova categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<String> criarCategoria(
            @Parameter(description = "Detalhes da nova categoria", required = true)
            @RequestBody CategoriaRequest request)
            throws CategoriaException {
        Categoria categoria = mapper.toCategoria(request);
        categoriaService.criarCategoria(categoria);
        return new ResponseEntity<>("Categoria criada com sucesso", HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PutMapping("/")
    public ResponseEntity<String> atualizarCategoria(
            @Parameter(description = "Detalhes da categoria a ser atualizada", required = true)
            @RequestBody CategoriaRequest request)
            throws CategoriaException {
        Categoria categoria = mapper.toCategoria(request);
        categoriaService.atualizarCategoria(categoria);
        return new ResponseEntity<>("Categoria atualizada com sucesso", HttpStatus.OK);
    }

    @Operation(summary = "Exclui uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @DeleteMapping("/")
    public ResponseEntity<String> excluirCategoria(
            @Parameter(description = "Detalhes da categoria a ser excluída", required = true)
            @RequestBody CategoriaRequest request)
            throws CategoriaException {
        Categoria categoria = mapper.toCategoria(request);
        categoriaService.excluirCategoria(categoria);
        return new ResponseEntity<>("Categoria excluída com sucesso", HttpStatus.OK);
    }

    @Operation(summary = "Busca uma categoria pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarCategoriaPorId(
            @Parameter(description = "ID da categoria", required = true)
            @PathVariable String id)
            throws CategoriaException {
        Categoria categoria = categoriaService.buscarCategoria(UUID.fromString(id));
        return ResponseEntity.ok(new CategoriaResponse(categoria.getId().toString(), categoria.getUsuario().toString(), categoria.getNome().toString(),
                categoria.getDescricao().toString(), nonNull(categoria.getIdCategoriaSuperior()) ? categoria.getIdCategoriaSuperior().toString() : null));
    }

    @Operation(summary = "Busca categorias por ID de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<CategoriaResponse>> buscarCategoriaPorUsuarioId(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable String id)
            throws CategoriaException {
        List<Categoria> categorias = categoriaService.buscarCategoriasPorUsuario(UUID.fromString(id));
        return ResponseEntity.ok(categorias.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca categorias por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/bucapornome/{nome}")
    public ResponseEntity<List<CategoriaResponse>> buscarCategoriaPorNome(
            @Parameter(description = "Nome da categoria", required = true)
            @PathVariable String nome)
            throws CategoriaException {
        List<Categoria> categorias = categoriaService.buscarCategoriasPorNome(new Nome(nome));
        return ResponseEntity.ok(categorias.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca subcategorias pelo ID da categoria superior")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subcategorias encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/subcategorias/{id}")
    public ResponseEntity<List<CategoriaResponse>> buscarSubCategoriaPorIdCategoria(
            @Parameter(description = "ID da categoria superior", required = true)
            @PathVariable String id)
            throws CategoriaException {
        List<Categoria> categorias = categoriaService.buscarSubCategorias(UUID.fromString(id));
        return ResponseEntity.ok(categorias.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Adiciona uma categoria superior a uma categoria inferior")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria superior adicionada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/adicionacategoriasuperior/{idcategoriasuperior}/{idcategoriainferior}")
    public ResponseEntity<String> adicionarCategoriaSuperior(
            @Parameter(description = "ID da categoria superior", required = true)
            @PathVariable String idcategoriasuperior,
            @Parameter(description = "ID da categoria inferior", required = true)
            @PathVariable String idcategoriainferior)
            throws CategoriaException {
        categoriaService.adicionarCategoriaSuperior(UUID.fromString(idcategoriasuperior), UUID.fromString(idcategoriainferior));
        return new ResponseEntity<>("Categoria superior adicionada", HttpStatus.CREATED);
    }
}
