package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;

import java.util.List;
import java.util.UUID;

public interface CategoriaPort {
    Categoria criarCategoria(Categoria categoria) throws CategoriaException;

    Categoria atualizarCategoria(Categoria categoria);

    void excluirCategoria(UUID id, Categoria categoria);

    Categoria buscarCategoria(UUID categoriaId);

    List<Categoria> buscaSubCategorias(UUID categoriaIdPai);

    List<Categoria> buscaCategoriasPorNome(Nome nomeCategoria);

    List<Categoria> buscaCategoriasPorUsuario(UUID usuarioId);
}