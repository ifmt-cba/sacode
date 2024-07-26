package br.com.edu.ifmt.sacode.domain.services;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.ports.CategoriaPort;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private ResourceBundle excRB;
    private final CategoriaPort categoriaPort;
    private final LogPort logPort;

    public CategoriaService(CategoriaPort categoriaPort, LogPort logPort) {
        this.categoriaPort = categoriaPort;
        this.logPort = logPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt", "BR"));
    }

//    public void addSubCategoria(Categoria categoriaPai, Categoria categoriaFilho) {
//        categoriaFilho.setIdCategoriaPai(categoriaPai.getId());
//        categoriaPort.criarCategoria(categoriaFilho);
//
//    }
//
//    public void removeSubCategoria(Categoria categoriaPai, Categoria categoriaFilho) {
//        categoriaFilho.setIdCategoriaPai(null);
//        categoriaPort.criarCategoria(categoriaFilho);
//    }

    public Categoria criarCategoria(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.criarCategoria()");
        StringBuilder exc = new StringBuilder();

        Categoria categoriaResponse;
        try {
            if(!validarCamposObrigatorios(categoria)){
                exc.append(excRB.getString("categoria.campoObrigatorio()").concat(" "));
            }
            if (exc.length() > 0) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaResponse = categoriaPort.criarCategoria(categoria);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = exc.length() > 0 ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categoria criada com sucesso.");
        logPort.debug(categoria.toString());
        logPort.trace("<- CategoriaService.criarCategoria()");
        return categoriaResponse;
    }

    public void excluirCategoria(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.excluirCategoria()");
        StringBuilder exc = new StringBuilder();
        try {
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaPort.excluirCategoria(categoria.getId(), categoria);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categoria excluida com sucesso.");
        logPort.debug(categoria.toString());
        logPort.trace("<- CategoriaService.excluirCategoria()");

    }

    public boolean validarCamposObrigatorios(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.validarCamposObrigatorios()");
        StringBuilder exc = new StringBuilder();

        boolean operacaoValidada = false;

        if (categoria.getId() == null || categoria.getId().toString().isEmpty()) {
            exc.append(excRB.getString("categoria.id-nulo").concat(" "));
        }

        if (categoria.getNome().toString() == null) {
            exc.append(excRB.getString("categoria.nome").concat(" "));
        }
        if (categoria.getDescricao() == null) {
            exc.append(excRB.getString("categoria.descricao").concat(" "));
        }

        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new CategoriaException(exc.toString());
        }
        operacaoValidada = true;
        logPort.info("Categoria validada com sucesso.");
        logPort.trace("<- CategoriaService.validarCamposObrigatorios()");

        return operacaoValidada;
    }

    public void atualizarCategoria(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.atualizarCategoria()");
        StringBuilder exc = new StringBuilder();

        try {
            if(!validarCamposObrigatorios(categoria)){
                exc.append(excRB.getString("categoria.campoObrigatorio()").concat(" "));
            }

            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaPort.atualizarCategoria(categoria);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categoria atualizada com sucesso.");
        logPort.debug(categoria.toString());
        logPort.trace("<- CategoriaService. atualizarCategoria()");

    }

    public List<Categoria> buscaCategoriasPorNome(Nome nomeCategoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.buscaCategoriasPorNome()");
        StringBuilder exc = new StringBuilder();
        List<Categoria> despesaResponse;

        try {
            if (nomeCategoria == null) {
                exc.append(excRB.getString("categoria.nome").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            despesaResponse = categoriaPort.buscaCategoriasPorNome(nomeCategoria);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categorias por nome buscada com sucesso.");
        logPort.debug("<- CategoriaService.buscaCategoriasPorNome()");

        return despesaResponse;
    }

    public List<Categoria> buscaSubCategorias(UUID categoriaIdPai) throws CategoriaException {
        logPort.trace("-> CategoriaService.buscaSubCategorias()");
        StringBuilder exc = new StringBuilder();
        List<Categoria> despesaResponse;

        try {
            if (categoriaIdPai.toString() == null) {
                exc.append(excRB.getString("categoria.pai-id-nulo").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            despesaResponse = categoriaPort.buscaSubCategorias(categoriaIdPai);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("SubCategorias buscadas com sucesso.");
        logPort.debug("<- CategoriaService.buscaSubCategorias()");

        return despesaResponse;
    }

    public List<Categoria> buscaCategoriasPorUsuario(UUID usuarioId) throws CategoriaException {
        logPort.trace("-> CategoriaService.buscaCategoriasPorUsuario()");
        StringBuilder exc = new StringBuilder();
        List<Categoria> despesaResponse;

        try {
            if (usuarioId.toString() == null) {
                exc.append(excRB.getString("categoria.id-usuario").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            despesaResponse = categoriaPort.buscaCategoriasPorUsuario(usuarioId);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categorias por usuario buscada com sucesso.");
        logPort.debug("<- CategoriaService.buscaCategoriasPorUsuario()");

        return despesaResponse;
    }

    public Categoria buscarCategoria(UUID categoriaId) throws CategoriaException {
        logPort.trace("-> CategoriaService.buscarCategoria()");
        StringBuilder exc = new StringBuilder();
        Categoria despesaResponse;

        try {
            if (categoriaId.toString() == null) {
                exc.append(excRB.getString("categoria.id-nulo").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            despesaResponse = categoriaPort.buscarCategoria(categoriaId);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categoria buscada com sucesso.");
        logPort.debug("<- CategoriaService.buscarCategoria()");

        return despesaResponse;
    }
}
