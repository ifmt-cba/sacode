package br.com.edu.ifmt.sacode.application.mappers;

import br.com.edu.ifmt.sacode.application.exceptions.NotFoundException;
import br.com.edu.ifmt.sacode.application.io.DespesaRequest;
import br.com.edu.ifmt.sacode.application.io.DespesaResponse;
import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.infrastructure.mappers.CategoriaORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class DespesaDTOMapper {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaDTOMapper categoriaDTOMapper;
    public DespesaResponse toResponse(Despesa despesa){

        return new DespesaResponse(despesa.getIdDespesa().toString(), despesa.getDescricao().toString(),
                despesa.getValor().toString(), despesa.getData(), despesa.getUsuario().toString(), despesa.getAutorDespesa().toString(),
                despesa.isFixa(), despesa.getFinanciadorDespesa().toString(),
                nonNull(despesa.getCorrelacaoParcelas()) ? despesa.getCorrelacaoParcelas().toString() : null, despesa.getNumParcela(),
                categoriaDTOMapper.toResponse(despesa.getCategoria()));
    }

    public Despesa toDespesa(DespesaRequest request){
        Despesa despesa = new Despesa();

        if(nonNull(request.idDespesa())){
            despesa.setIdDespesa(UUID.fromString(request.idDespesa()));
        }
        despesa.setDescricao(new Descricao(request.descricao()));
        despesa.setValor(new Moeda(request.valor()));
        despesa.setData(request.data());
        despesa.setUsuario(UUID.fromString(request.usuario()));
        despesa.setAutorDespesa(new Nome(request.autorDespesa()));
        despesa.setFixa(request.fixa());
        despesa.setFinanciadorDespesa(new Nome(request.financiadorDespesa()));
        if(nonNull(request.correlacaoParcelas())){
            despesa.setCorrelacaoParcelas(UUID.fromString(request.correlacaoParcelas()));
        }
        despesa.setNumParcela(request.numParcela());
        if(nonNull(request.categoriaId())){
            CategoriaORM categoria = categoriaRepository.findById(request.categoriaId())
                    .orElseThrow( () -> new NotFoundException("Categoria nao encontrada, ID: "+ request.categoriaId() ));
            despesa.setCategoria(CategoriaORMMapper.ormParaDominio(categoria));
        }


        return despesa;

    }
}
