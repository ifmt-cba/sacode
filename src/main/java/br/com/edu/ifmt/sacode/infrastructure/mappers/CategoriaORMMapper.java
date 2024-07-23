package br.com.edu.ifmt.sacode.infrastructure.mappers;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaORM;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CategoriaORMMapper {
    
    public CategoriaORM domainToORM(Categoria categoriaDomain) {
        CategoriaORM categoriaORM = new CategoriaORM();
        
        categoriaORM.setDescricao(categoriaDomain.getDescricao().getDescricao());
        categoriaORM.setIdCategoria(categoriaDomain.getId().toString());
        categoriaORM.setNome(categoriaDomain.getNome().toString());
        categoriaORM.setCategoriaPai(categoriaDomain.getIdCategoriaPai().toString());
        return categoriaORM;
    
    }

    public Categoria ormToDomain(CategoriaORM categoriaORM) {
        Categoria categoriaDomain = new Categoria();
         
        categoriaDomain.setId(UUID.fromString(categoriaORM.getIdCategoria()));
        categoriaDomain.setNome( new Nome(categoriaORM.getNome()));
        categoriaDomain.setDescricao(new Descricao(categoriaORM.getDescricao()));
        categoriaDomain.setIdCategoriaPai(UUID.fromString(categoriaORM.getCategoriaPai()));
        
        return categoriaDomain;
    }

    public List<Categoria> ormListToDomainList(List<CategoriaORM> categoriasORM) {
        List<Categoria> categoriasDomain = new ArrayList<>();
        for (CategoriaORM categoriaORM : categoriasORM) {
            categoriasDomain.add(ormToDomain(categoriaORM));
        }

        return categoriasDomain;
    }
}
