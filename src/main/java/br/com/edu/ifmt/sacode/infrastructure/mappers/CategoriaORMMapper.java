package br.com.edu.ifmt.sacode.infrastructure.mappers;

import java.util.UUID;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.CategoryName;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.infrastructure.persistence.Categoria.CategoriaORM.CategoriaORM;

public class CategoriaORMMapper {
    
    public CategoriaORM domainToORM(Categoria categoriaDomain) {
        CategoriaORM categoriaORM = new CategoriaORM();
        
        categoriaORM.setDescricao(categoriaDomain.getDescricao().getDescricao());
        categoriaORM.setIdCategoria(categoriaDomain.getId().toString());
        categoriaORM.setNome(categoriaDomain.getNome().getCategoryName());
        categoriaORM.setCategoriaPai(categoriaDomain.getIdCategoriaPai().toString());
        return categoriaORM;
    
    }

    public Categoria ormToDomain(CategoriaORM categoriaORM) {
        Categoria categoriaDomain = new Categoria();
         
        categoriaDomain.setId(UUID.fromString(categoriaORM.getIdCategoria()));
        categoriaDomain.setNome( new CategoryName(categoriaORM.getNome()));
        categoriaDomain.setDescricao(new Descricao(categoriaORM.getDescricao()));
        categoriaDomain.setIdCategoriaPai(UUID.fromString(categoriaORM.getCategoriaPai()));
        
        return categoriaDomain;
    }
}