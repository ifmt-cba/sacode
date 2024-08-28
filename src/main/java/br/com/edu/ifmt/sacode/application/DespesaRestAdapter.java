package br.com.edu.ifmt.sacode.application;

import br.com.edu.ifmt.sacode.application.io.DespesaRequest;
import br.com.edu.ifmt.sacode.application.io.DespesaResponse;
import br.com.edu.ifmt.sacode.application.mappers.DespesaDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.services.DespesaService;
import br.com.edu.ifmt.sacode.domain.services.exception.DespesaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/despesas")
@RequiredArgsConstructor
@Tag(name = "Despesas", description = "Operações relacionada a despesa")
public class DespesaRestAdapter {
    static final String OBJETO_NULO = "Objeto nulo";
    static final String DESPESA_REJEITADA = "Despesa rejeitada:{\n";
    static final String DETALHE = "\nDetalhe: ";
    static final String CAUSA = "\nCausa: ";

    private final DespesaService despesaService;
    private final DespesaDTOMapper mapper;

    @Operation(summary = "Buscar despesa", description = "Busca uma despesa pelo id de usuário")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<DespesaResponse>> buscarDespesasPorUsuario(@PathVariable String id) throws DespesaException {
        List<Despesa> despesas = despesaService.buscarDespesasPorUsuario(UUID.fromString(id));
        return ResponseEntity.ok(despesas.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Criar despesa", description = "Cria uma despesa")
    @PostMapping("/")
    public ResponseEntity<String> criarDespesa(@RequestBody DespesaRequest request) throws DespesaException {
        Despesa despesa = mapper.toDespesa(request);
        despesaService.criarDespesa(despesa);
        return new ResponseEntity<>("Despesa criada com sucesso!", HttpStatus.CREATED);

    }

    @Operation(summary = "Atualizar despesa", description = "Atualiza uma despesa")
    @PutMapping("/")
    public ResponseEntity<String> atualizarDespesa(@RequestBody DespesaRequest request) throws DespesaException {
        Despesa despesa = mapper.toDespesa(request);
        despesaService.atualizarDespesa(despesa);
        return new ResponseEntity<>("Despesa atualizada com sucesso!", HttpStatus.OK);

    }

    @Operation(summary = "Excluir despesa", description = "Exclui uma despesa")
    @DeleteMapping("/")
    public ResponseEntity<String> excluirDespesa(@RequestBody DespesaRequest request) throws DespesaException {
        Despesa despesa = mapper.toDespesa(request);
        despesaService.excluirDespesa(despesa.getIdDespesa(), despesa.getUsuario());
        return new ResponseEntity<>("Despesa excluida com sucesso!", HttpStatus.OK);
    }

    @Operation(summary = "Buscar despesa por criador", description = "Busca uma despesa pelo id de usuário e id de despesa")
    @GetMapping("/usuario/{id}/autor/{autor}")
    public ResponseEntity<List<DespesaResponse>> buscarDespesasPorUsuarioPorAutor(@PathVariable String id, @PathVariable String autor) throws DespesaException {
        List<Despesa> despesas = despesaService.buscarDespesasPorAutor(UUID.fromString(id), autor);
        return ResponseEntity.ok(despesas.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Buscar despesa por financiador", description = "Busca uma despesa pelo id de usuário e financiador")
    @GetMapping("/usuario/{id}/financiador/{financiador}")
    public ResponseEntity<List<DespesaResponse>> buscarDespesasPorUsuarioPorFinanciador(@PathVariable String id, @PathVariable String financiador) throws DespesaException {
        List<Despesa> despesas = despesaService.buscarDespesasPorFinanciador(UUID.fromString(id), financiador);
        return ResponseEntity.ok(despesas.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Buscar despesa por categoria em um mês", description = "Busca uma despesa pelo id de usuário e período do ano")
    @GetMapping("/usuario/{id}/{ano}-{mes}")
    public ResponseEntity<List<DespesaResponse>> buscarDespesasPorMes(@PathVariable String id, @PathVariable int ano, @PathVariable int mes) throws DespesaException {
        List<Despesa> despesas = despesaService.buscarDespesasPorMes(UUID.fromString(id), ano, mes);
        return ResponseEntity.ok(despesas.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Buscar despesa por categoria em um período", description = "Busca uma despesa pelo id de usuário e período do ano")
    @GetMapping("/usuario/{id}/{ano1}-{mes1}-{dia1}/{ano2}-{mes2}-{dia2}")//
    public ResponseEntity<List<DespesaResponse>> buscarDespesasPorPeriodo(
            @PathVariable String id,
            @PathVariable int ano1,
            @PathVariable int mes1,
            @PathVariable int dia1,
            @PathVariable int ano2,
            @PathVariable int mes2,
            @PathVariable int dia2
    ) throws DespesaException {
        List<Despesa> despesas = despesaService.buscarDespesasPorPeriodo(UUID.fromString(id),
                LocalDate.of(ano1, mes1, dia1), LocalDate.of(ano2, mes2, dia2));
        return ResponseEntity.ok(despesas.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Exclui as parcelas de uma despesa", description = "Exclui as parcelas de uma despesa por id de despesa")
    @DeleteMapping("/{id}/parcelas/{parcela}")
    public ResponseEntity<Integer> excluirParcelas(@PathVariable String id, @PathVariable String parcela) throws DespesaException {
        return ResponseEntity.ok(despesaService
                .excluirParcelas(UUID.fromString(id), UUID.fromString(parcela)));
    }
}