package br.com.edu.ifmt.sacode.infrastructure.adapters;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.ports.CategoriaPort;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.infrastructure.mappers.CategoriaORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
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
    public Categoria criarCategoria(Categoria categoria) {
        logPort.trace("-> CategoriaRepositoryAdapter.criarCategoria");
        CategoriaORM categoriaORM = categoriaORMMapper.domainToORM(categoria);
        logPort.debug(categoriaORM.toString());
        CategoriaORM savedCategoria = categoriaRepository.save(categoriaORM);
        logPort.info("Categoria inserida na tabela categoria.");
        logPort.debug(categoriaORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.saveCategoria");
        return categoriaORMMapper.ormToDomain(savedCategoria);
    }

    @Override
    public void excluirCategoria(UUID id, Categoria categoria) {
        logPort.trace("-> CategoriaRepositoryAdapter.excluirCategoria");
        try {
            categoriaRepository.deleteById(String.valueOf(id));
            logPort.info("Categoria excluida da tabela categoria.");
            logPort.trace("<- CategoriaRepositoryAdapter.excluirCategoria");
        } catch (Exception e) {
            logPort.error("Erro ao deletar categoria.".concat(e.getMessage()));
        }
    }


    @Override
    public Categoria buscarCategoria(UUID categoriaId) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscarCategoria()");

        Optional<CategoriaORM> categoriaORMOptional = categoriaRepository.findById(String.valueOf(categoriaId));

        if (categoriaORMOptional.isEmpty()) {
            logPort.debug("Categoria não encontrada para o ID: " + categoriaId);
            logPort.trace("<- CategoriaRepositoryAdapter.buscarCategoria()");
            return null;
        }

        // Categoria encontrada
        CategoriaORM categoriaORM = categoriaORMOptional.get();
        logPort.debug("Categoria encontrada: " + categoriaORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscarCategoria()");

        // Converter CategoriaORM
        return categoriaORMMapper.ormToDomain(categoriaORM);
    }

    @Override
    public List<Categoria> buscaSubCategorias(UUID categoriaIdPai) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaSubCategorias");
        List<CategoriaORM> categoriasORM = categoriaRepository.findByCategoriaPai(String.valueOf(categoriaIdPai));
        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaSubCategorias");
        return categoriaORMMapper.ormListToDomainList(categoriasORM);
    }



    @Override
    public Categoria atualizarCategoria(Categoria categoria) {
        CategoriaORM savedCategoria;

        logPort.trace("-> CategoriaRepositoryAdapter.atualizarCategoria");
        Categoria categoriaBuscada =  buscarCategoria(categoria.getId());
        logPort.debug(categoria.toString());
        categoriaBuscada.setNome(categoria.getNome());
        categoriaBuscada.setDescricao(categoria.getDescricao());
        categoriaBuscada.setIdCategoriaPai(categoria.getIdCategoriaPai());
        categoriaBuscada.setUsuario(categoria.getUsuario());


        try {
            savedCategoria = categoriaORMMapper.domainToORM(categoriaBuscada);
            savedCategoria = categoriaRepository.save(savedCategoria);
        } catch (Exception e) {
            logPort.error("Erro ao atualizar a categoria.".concat(e.getMessage()));
        }


        logPort.debug("Categoria atualizada: " + categoria.toString());
        logPort.info("Categoria atualizada na tabela categoria.");
        logPort.trace("<- CategoriaRepositoryAdapter.atualizarCategoria");

        return categoriaBuscada;
    }


    @Override
    public List<Categoria> buscaCategoriasPorNome(Nome nomeCategoria) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaCategoriasPorNome");
        List<CategoriaORM> categoriasORM = categoriaRepository.findByNome(nomeCategoria.toString());
        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaCategoriasPorNome");
        return categoriaORMMapper.ormListToDomainList(categoriasORM);
    }

    @Override
    public List<Categoria> buscaCategoriasPorUsuario(UUID usuarioId) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaCategoriasPorUsuario");
        List<CategoriaORM> categoriasORM = categoriaRepository.findByUsuario(String.valueOf(usuarioId));
        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaCategoriasPorUsuario");
        return categoriaORMMapper.ormListToDomainList(categoriasORM);
    }

}
