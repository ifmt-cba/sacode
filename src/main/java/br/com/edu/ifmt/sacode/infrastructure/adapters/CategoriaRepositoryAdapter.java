package br.com.edu.ifmt.sacode.infrastructure.adapters;

import br.com.edu.ifmt.sacode.application.exceptions.NotFoundException;
import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.ports.CategoriaPort;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.infrastructure.mappers.CategoriaORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CategoriaRepositoryAdapter implements CategoriaPort {
    private final CategoriaRepository categoriaRepository;

    private final LogPort logPort;

    @Autowired
    public CategoriaRepositoryAdapter(CategoriaRepository categoriaRepository, LogPort logPort) {
        this.categoriaRepository = categoriaRepository;
        this.logPort = logPort;

    }

    @Override
    public Categoria criarCategoria(Categoria categoriaDominio) {
        logPort.trace("-> CategoriaRepositoryAdapter.criarCategoria");
        CategoriaORM categoriaORM = new CategoriaORM();

        try {
            categoriaORM = CategoriaORMMapper.dominioParaOrm(categoriaDominio);
            logPort.debug(categoriaORM.toString());
            categoriaORM = categoriaRepository.save(categoriaORM);
        } catch (Exception e) {
            logPort.error("Erro ao criar categoria.".concat(e.getMessage()));
        }
        logPort.info("Categoria inserida na tabela categoria.");
        logPort.debug(categoriaORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.saveCategoria");

        return categoriaDominio;
    }

    @Override
    public void excluirCategoria(UUID id) {
        logPort.trace("-> CategoriaRepositoryAdapter.excluirCategoria");
        try {
            categoriaRepository.deleteById(String.valueOf(id));
        } catch (Exception e) {
            logPort.error("Erro ao deletar categoria.".concat(e.getMessage()));
        }

        logPort.info("Categoria excluida da tabela categoria.");
        logPort.trace("<- CategoriaRepositoryAdapter.excluirCategoria");
    }


    @Override
    public Categoria buscarCategoria(UUID categoriaId) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscarCategoria()");

        Optional<CategoriaORM> categoriaORMOpcional = categoriaRepository.findById(String.valueOf(categoriaId));

        if (categoriaORMOpcional.isEmpty()) {
            logPort.debug("Categoria não encontrada para o ID: " + categoriaId);
            logPort.trace("<- CategoriaRepositoryAdapter.buscarCategoria()");
            throw new NotFoundException("Categoria não encontrada para o ID: " + categoriaId);
        }

        // Categoria encontrada
        CategoriaORM categoriaORM = categoriaORMOpcional.get();
        logPort.debug("Categoria encontrada: " + categoriaORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscarCategoria()");

        // Converter CategoriaORM
        return CategoriaORMMapper.ormParaDominio(categoriaORM);
    }

    @Override
    public List<Categoria> buscarSubCategorias(UUID idCategoriaSuperior) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscarSubCategorias");
        List<CategoriaORM> categoriasORM = null;
        try {
            categoriasORM = categoriaRepository.findByCategoriaSuperior(String.valueOf(idCategoriaSuperior));
        } catch (Exception e) {
            logPort.error("Erro ao buscar subCategorias.".concat(e.getMessage()));
        }

        logPort.debug(categoriasORM != null ?  categoriasORM.toString(): "Categoria nula");
        logPort.trace("<- CategoriaRepositoryAdapter.buscarSubCategorias");
        return CategoriaORMMapper.ormListParaDominioList(categoriasORM);
    }



    @Override
    public Categoria atualizarCategoria(Categoria categoriaDominio) {
        CategoriaORM categoriaORM = new CategoriaORM();

        logPort.trace("-> CategoriaRepositoryAdapter.atualizarCategoria");
        try {
            categoriaORM = CategoriaORMMapper.dominioParaOrm(categoriaDominio);
            categoriaORM = categoriaRepository.save(categoriaORM);
        } catch (Exception e) {
            logPort.error("Erro ao atualizar a categoria.".concat(e.getMessage()));

        }


        logPort.debug("Categoria atualizada: " + CategoriaORMMapper.ormParaDominio(categoriaORM));
        logPort.info("Categoria atualizada na tabela categoria.");
        logPort.trace("<- CategoriaRepositoryAdapter.atualizarCategoria");

        return categoriaDominio;
    }


    @Override
    public List<Categoria> buscarCategoriasPorNome(Nome nomeCategoria) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscarCategoriasPorNome");
        List<CategoriaORM> categoriasORM = null;
        try {
            categoriasORM = categoriaRepository.findByNomeContainingIgnoreCase(nomeCategoria.toString());
        } catch (Exception e) {
            logPort.error("Erro ao erro ao buscar categorias por nome.".concat(e.getMessage()));
        }
        logPort.debug(categoriasORM != null ?  categoriasORM.toString(): "Categorias nula");
        logPort.trace("<- CategoriaRepositoryAdapter.buscarCategoriasPorNome");
        return CategoriaORMMapper.ormListParaDominioList(categoriasORM);
    }

    @Override
    public List<Categoria> buscarCategoriasPorUsuario(UUID usuarioId) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscarCategoriasPorUsuario");
        List<CategoriaORM> categoriasORM = null;
        try {
            categoriasORM = categoriaRepository.findByUsuarioIdUsuario(String.valueOf(usuarioId));
        } catch (Exception e) {
            logPort.error("Erro ao erro ao buscar categorias por usuario.".concat(e.getMessage()));
        }
        logPort.debug(categoriasORM != null ?  categoriasORM.toString(): "Categoria nula");
        logPort.trace("<- CategoriaRepositoryAdapter.buscarCategoriasPorUsuario");
        return CategoriaORMMapper.ormListParaDominioList(categoriasORM);
    }

}
