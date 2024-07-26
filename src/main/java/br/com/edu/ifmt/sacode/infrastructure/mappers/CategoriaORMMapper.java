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
    
    public CategoriaORM dominioParaOrm(Categoria categoriaDomain) {
        CategoriaORM categoriaORM = new CategoriaORM();

        categoriaORM.setIdCategoria(categoriaDomain.getId().toString());
        categoriaORM.setDescricao(categoriaDomain.getDescricao().toString());
        categoriaORM.setNome(categoriaDomain.getNome().toString());
        categoriaORM.setCategoriaSuperior(categoriaDomain.getIdCategoriaSuperior().toString());
        return categoriaORM;
    
    }

    public Categoria ormParaDominio(CategoriaORM categoriaORM) {
        Categoria categoriaDominio = new Categoria();

        categoriaDominio.setId(UUID.fromString(categoriaORM.getIdCategoria()));
        categoriaDominio.setNome( new Nome(categoriaORM.getNome()));
        categoriaDominio.setDescricao(new Descricao(categoriaORM.getDescricao()));
        categoriaDominio.setIdCategoriaSuperior(UUID.fromString(categoriaORM.getCategoriaSuperior()));
        
        return categoriaDominio;
    }

    public List<Categoria> ormListParaDominioList(List<CategoriaORM> categoriasORM) {
        List<Categoria> categoriasDomain = new ArrayList<>();
        for (CategoriaORM categoriaORM : categoriasORM) {
            categoriasDomain.add(ormParaDominio(categoriaORM));
        }

        return categoriasDomain;
    }
}
