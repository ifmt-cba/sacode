package br.com.edu.ifmt.sacode.infrastructure.mappers;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static java.util.Objects.nonNull;

public class CategoriaORMMapper {


    public static CategoriaORM dominioParaOrm(Categoria categoriaDominio) {


        CategoriaORM categoriaORM = new CategoriaORM();
        categoriaORM.setIdCategoria(categoriaDominio.getId().toString());
        categoriaORM.setDescricao(categoriaDominio.getDescricao().toString());
        categoriaORM.setNome(categoriaDominio.getNome().toString());
        if(nonNull(categoriaDominio.getIdCategoriaSuperior())){
            categoriaORM.setCategoriaSuperior(categoriaDominio.getIdCategoriaSuperior().toString());
        }

        UsuarioORM usuario = new UsuarioORM();
        usuario.setIdUsuario(categoriaDominio.getUsuario().toString());
        categoriaORM.setUsuario(usuario);

        return categoriaORM;
    
    }

    public static Categoria ormParaDominio(CategoriaORM categoriaORM) {
        Categoria categoriaDominio = new Categoria();
        categoriaDominio.setId(UUID.fromString(categoriaORM.getIdCategoria()));
        categoriaDominio.setNome( new Nome(categoriaORM.getNome()));
        categoriaDominio.setDescricao(new Descricao(categoriaORM.getDescricao()));
        if(nonNull(categoriaORM.getCategoriaSuperior())){
            categoriaDominio.setIdCategoriaSuperior(UUID.fromString(categoriaORM.getCategoriaSuperior()));
        }
        categoriaDominio.setUsuario(UUID.fromString(categoriaORM.getUsuario().getIdUsuario()));
        return categoriaDominio;
    }

    public static List<Categoria> ormListParaDominioList(List<CategoriaORM> categoriasORM) {
        List<Categoria> categoriasDomain = new ArrayList<>();
        for (CategoriaORM categoriaORM : categoriasORM) {
            categoriasDomain.add(ormParaDominio(categoriaORM));
        }

        return categoriasDomain;
    }
}
