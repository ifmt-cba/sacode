package br.com.edu.ifmt.sacode.domain.ports;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public interface CategoriaPort {
    Categoria criarCategoria(Categoria categoria) throws CategoriaException;

    Categoria atualizarCategoria(Categoria categoria) throws CategoriaException;;

    void excluirCategoria(UUID idCategoria) throws CategoriaException;

    Categoria buscarCategoria(UUID categoriaId);

    List<Categoria> buscaSubCategorias(UUID categoriaSuperior);

    List<Categoria> buscaCategoriasPorNome(Nome nomeCategoria);

    List<Categoria> buscaCategoriasPorUsuario(UUID usuarioId);
}