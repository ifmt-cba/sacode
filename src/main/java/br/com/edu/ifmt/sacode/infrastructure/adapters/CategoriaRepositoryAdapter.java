package br.com.edu.ifmt.sacode.infrastructure.adapters;

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
    private final CategoriaORMMapper categoriaORMMapper;


    private final LogPort logPort;

    @Autowired
    public CategoriaRepositoryAdapter(CategoriaRepository categoriaRepository, CategoriaORMMapper categoriaORMMapper, LogPort logPort) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaORMMapper = categoriaORMMapper;
        this.logPort = logPort;

    }

    @Override
    public Categoria criarCategoria(Categoria categoriaDominio) {
        logPort.trace("-> CategoriaRepositoryAdapter.criarCategoria");
        CategoriaORM categoriaORM = new CategoriaORM();

        try {
            categoriaORM = categoriaORMMapper.dominioParaOrm(categoriaDominio);
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
            return null;
        }

        // Categoria encontrada
        CategoriaORM categoriaORM = categoriaORMOpcional.get();
        logPort.debug("Categoria encontrada: " + categoriaORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscarCategoria()");

        // Converter CategoriaORM
        return categoriaORMMapper.ormParaDominio(categoriaORM);
    }

    @Override
    public List<Categoria> buscaSubCategorias(UUID idCategoriaSuperior) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaSubCategorias");
        List<CategoriaORM> categoriasORM = null;
        try {
            categoriasORM = categoriaRepository.findByCategoriaSuperior(String.valueOf(idCategoriaSuperior));
        } catch (Exception e) {
            logPort.error("Erro ao buscar subCategorias.".concat(e.getMessage()));
        }

        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaSubCategorias");
        return categoriaORMMapper.ormListParaDominioList(categoriasORM);
    }



    @Override
    public Categoria atualizarCategoria(Categoria categoriaDominio) {
        CategoriaORM categoriaORM = new CategoriaORM();

        logPort.trace("-> CategoriaRepositoryAdapter.atualizarCategoria");
        try {
            categoriaORM = categoriaORMMapper.dominioParaOrm(categoriaDominio);
            categoriaORM = categoriaRepository.save(categoriaORM);
        } catch (Exception e) {
            logPort.error("Erro ao atualizar a categoria.".concat(e.getMessage()));

        }


        logPort.debug("Categoria atualizada: " + categoriaORMMapper.ormParaDominio(categoriaORM));
        logPort.info("Categoria atualizada na tabela categoria.");
        logPort.trace("<- CategoriaRepositoryAdapter.atualizarCategoria");

        return categoriaDominio;
    }


    @Override
    public List<Categoria> buscaCategoriasPorNome(Nome nomeCategoria) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaCategoriasPorNome");
        List<CategoriaORM> categoriasORM = null;
        try {
            categoriasORM = categoriaRepository.findByNome(nomeCategoria.toString());
        } catch (Exception e) {
            logPort.error("Erro ao erro ao buscar categorias por nome.".concat(e.getMessage()));
        }
        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaCategoriasPorNome");
        return categoriaORMMapper.ormListParaDominioList(categoriasORM);
    }

    @Override
    public List<Categoria> buscaCategoriasPorUsuario(UUID usuarioId) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaCategoriasPorUsuario");
        List<CategoriaORM> categoriasORM = null;
        try {
            categoriasORM = categoriaRepository.findByUsuario_IdUsuario(String.valueOf(usuarioId));
        } catch (Exception e) {
            logPort.error("Erro ao erro ao buscar categorias por usuario.".concat(e.getMessage()));
        }
        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaCategoriasPorUsuario");
        return categoriaORMMapper.ormListParaDominioList(categoriasORM);
    }

}
