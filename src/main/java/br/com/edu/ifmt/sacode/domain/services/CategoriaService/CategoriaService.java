package br.com.edu.ifmt.sacode.domain.services.CategoriaService;

import java.util.Locale;
import java.util.ResourceBundle;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.ports.CategoriaPort;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;

public class CategoriaService {

    private ResourceBundle excRB;
    private final CategoriaPort categoriaPort;
    private final LogPort logPort;

    public CategoriaService(CategoriaPort categoriaPort, LogPort logPort) {
        this.categoriaPort = categoriaPort;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt", "BR"));
    }

    public void addSubCategoria(Categoria categoriaPai, Categoria categoriaFilho) {
        categoriaFilho.setIdCategoriaPai(categoriaPai.getId());
        categoriaPort.saveCategoria(categoriaFilho);

    }

    public void removeSubCategoria(Categoria categoriaPai, Categoria categoriaFilho) {
        categoriaFilho.setIdCategoriaPai(null);
        categoriaPort.saveCategoria(categoriaFilho);
    }

    public Categoria saveCategoria(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.saveCategoria()");
        StringBuilder exc = new StringBuilder();

        if (categoria.getId() == null || categoria.getId().toString().isEmpty()) {
            exc.append(excRB.getString("O id da categoria não pode ser nulo nem vazio").concat(" "));
        }

        if (categoria.getNome() == null || categoria.getNome().isValid()) {
            exc.append(excRB.getString("O nome da categoria não pode ser nulo nem vazio").concat(" "));
        }
        if (categoria.getDescricao() == null) {
            exc.append(excRB.getString("A descrição da categoria não pode ser nula nem vazia").concat(" "));
        }
        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new CategoriaException(exc.toString());
        }
        Categoria categoriaResponse = categoriaPort.saveCategoria(categoria);
        logPort.info("Categoria criada com sucesso.");
        logPort.debug(categoria.toString());
        logPort.trace("<- CategoriaService.saveCategoria()");
        return categoriaResponse;
    }

    public void deleteCategoria(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.deleteCategoria()");
        StringBuilder exc = new StringBuilder();

        if (categoria.getIdCategoriaPai() != null) {
            exc.append(excRB.getString("A categoria não pode ter uma categoria superior").concat(" "));
        }

        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new CategoriaException(exc.toString());
        }
        categoriaPort.deleteCategoria(categoria.getId(), categoria);
        logPort.info("Categoria deletada com sucesso.");
        logPort.debug(categoria.toString());
        logPort.trace("<- CategoriaService.deleteCategoria()");
    }

}
