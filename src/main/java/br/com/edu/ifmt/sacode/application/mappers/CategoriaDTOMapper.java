package br.com.edu.ifmt.sacode.application.mappers;

import br.com.edu.ifmt.sacode.application.io.CategoriaRequest;
import br.com.edu.ifmt.sacode.application.io.CategoriaResponse;
import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Objects.nonNull;


@Component
public class CategoriaDTOMapper {

    public CategoriaResponse toResponse(Categoria categoria){
        return new CategoriaResponse(categoria.getId().toString(), categoria.getUsuario().toString(),
                categoria.getNome().toString(), categoria.getDescricao().toString(),
                nonNull(categoria.getIdCategoriaSuperior()) ? categoria.getIdCategoriaSuperior().toString() : null );
    }

    public Categoria toCategoria(CategoriaRequest request) {
        Categoria categoria = new Categoria();

        if(nonNull(request.id())){
            categoria.setId(UUID.fromString(request.id()));
        }
        categoria.setUsuario(UUID.fromString(request.usuarioId()));
        categoria.setNome(new Nome(request.nome()));
        categoria.setDescricao(new Descricao(request.descricao()));
        if(nonNull(request.idCategoriaSuperior())){
            categoria.setIdCategoriaSuperior(UUID.fromString(request.idCategoriaSuperior()));
        }
        return categoria;

    }

}
