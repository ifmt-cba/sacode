package br.com.edu.ifmt.sacode.infrastructure.adapters;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.CategoryName;
import br.com.edu.ifmt.sacode.domain.ports.CategoriaPort;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.infrastructure.mappers.CategoriaORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.CategoriaRepository;

public class CategoriaRepositoryAdapter implements CategoriaPort {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaORMMapper categoriaORMMapper;

    @Autowired
    private LogPort logPort = null;

    public CategoriaRepositoryAdapter(CategoriaRepository categoriaRepository, CategoriaORMMapper categoriaORMMapper, LogPort logPort) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaORMMapper = categoriaORMMapper;
        this.logPort = logPort;
    }

    @Override
    public Categoria saveCategoria(Categoria categoria) {
        logPort.trace("-> CategoriaRepositoryAdapter.saveCategoria");
        CategoriaORM categoriaORM = categoriaORMMapper.domainToORM(categoria);
        logPort.debug(categoriaORM.toString());
        CategoriaORM savedCategoria = categoriaRepository.save(categoriaORM);
        logPort.info("Categoria inserida na tabela categoria.");
        logPort.debug(categoriaORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.saveCategoria");
        return categoriaORMMapper.ormToDomain(savedCategoria);
    }

    @Override
    public boolean deleteCategoria(UUID id, Categoria categoria) {
        logPort.trace("-> CategoriaRepositoryAdapter.deleteCategoria");
        try {
            categoriaRepository.deleteById(id.toString());
            logPort.info("Categoria deletada da tabela categoria.");
            logPort.trace("<- CategoriaRepositoryAdapter.deleteCategoria");
            return true;
        } catch (Exception e) {
            logPort.error("Erro ao deletar categoria.");
            return false;
        }
    }

    @Override
    public Categoria findByIdCategoria(UUID categoriaId) {
        logPort.trace("-> CategoriaRepositoryAdapter.findByIdCategoria");
        CategoriaORM categoriaORM = categoriaRepository.findByIdCategoria(categoriaId.toString());
        logPort.debug(categoriaORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.findByIdCategoria");
        return categoriaORMMapper.ormToDomain(categoriaORM);
    }

    @Override
    public List<Categoria> buscaSubCategorias(UUID categoriaIdPai) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaSubCategorias");
        List<CategoriaORM> categoriasORM = categoriaRepository.findByCategoriaPaiByIdCategoria(categoriaIdPai.toString());
        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaSubCategorias");
        return categoriaORMMapper.ormListToDomainList(categoriasORM);
    }

    @Override
    public List<Categoria> buscaCategoriasPorNome(CategoryName nome) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaCategoriasPorNome");
        List<CategoriaORM> categoriasORM = categoriaRepository.findByNome(nome.getCategoryName());
        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaCategoriasPorNome");
        return categoriaORMMapper.ormListToDomainList(categoriasORM);
    }

    @Override
    public List<Categoria> buscaCategoriasPorUsuario(UUID usuarioId) {
        logPort.trace("-> CategoriaRepositoryAdapter.buscaCategoriasPorUsuario");
        List<CategoriaORM> categoriasORM = categoriaRepository.findByUsuarioId(usuarioId.toString());
        logPort.debug(categoriasORM.toString());
        logPort.trace("<- CategoriaRepositoryAdapter.buscaCategoriasPorUsuario");
        return categoriaORMMapper.ormListToDomainList(categoriasORM);
    }

}
