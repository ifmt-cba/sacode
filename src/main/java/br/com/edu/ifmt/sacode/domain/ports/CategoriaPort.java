package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.CategoryName;

import java.util.List;
import java.util.UUID;

public interface CategoriaPort {
    Categoria saveCategoria(Categoria categoria);

    boolean deleteCategoria(UUID id, Categoria categoria);

    Categoria findByIdCategoria(UUID categoriaId);

//    List<Categoria> buscaSubCategorias(UUID categoriaIdPai);

    List<Categoria> buscaCategoriasPorNome(CategoryName nome);

//    List<Categoria> buscaCategoriasPorUsuario(UUID usuarioId);
}