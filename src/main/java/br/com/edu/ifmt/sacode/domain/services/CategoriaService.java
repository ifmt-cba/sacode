package br.com.edu.ifmt.sacode.domain.services;

import br.com.edu.ifmt.sacode.domain.entities.Categoria;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.ports.CategoriaPort;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.UsuarioPort;
import br.com.edu.ifmt.sacode.domain.services.exception.CategoriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class CategoriaService {

    private ResourceBundle excRB;
    private final CategoriaPort categoriaPort;
    private final LogPort logPort;
    private final UsuarioPort usuarioPort;

    @Autowired
    public CategoriaService(CategoriaPort categoriaPort, LogPort logPort,UsuarioPort usuarioPort) {
        this.categoriaPort = categoriaPort;
        this.logPort = logPort;
        this.usuarioPort = usuarioPort;
        this.excRB = ResourceBundle.getBundle("exceptions", new Locale("pt", "BR"));
    }

    public Categoria criarCategoria(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.criarCategoria()");
        StringBuilder exc = new StringBuilder();

        Categoria categoriaResposta;

        try {
            if (!validarCamposObrigatorios(categoria)) {
                exc.append(excRB.getString("categoria.campoObrigatorio.validacao").concat(" "));
            }
            if (exc.length() > 0) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaResposta = categoriaPort.criarCategoria(categoria);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = exc.length() > 0 ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categoria criada com sucesso.");
        logPort.debug(categoria.toString());
        logPort.trace("<- CategoriaService.criarCategoria()");
        return categoriaResposta;
    }

    public void excluirCategoria(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.excluirCategoria()");
        StringBuilder exc = new StringBuilder();
        try {
            if (categoria.getId() == null || categoria.getId().toString().isEmpty()) {
                exc.append(excRB.getString("categoria.id.nulo").concat(" "));
                throw new CategoriaException(exc.toString());
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }

            if (exc.length() > 0) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaPort.excluirCategoria(categoria.getId());
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

        if (categoria.getId() == null || categoria.getId().toString().isEmpty()) {
            exc.append(excRB.getString("categoria.id.nulo").concat(" "));
        }

        if (categoria.getNome() == null) {
            exc.append(excRB.getString("categoria.nome.nulo").concat(" "));
        }
        if (categoria.getDescricao() == null) {
            exc.append(excRB.getString("categoria.descricao.nula").concat(" "));
        }

        if (categoria.getUsuario() == null) {
            exc.append(excRB.getString("usuario.not.found").concat(" "));
            exc.append(excRB.getString("usuario.id.nulo").concat(" "));
        } else if (categoria.getUsuario().toString().isEmpty()) {
            exc.append(excRB.getString("usuario.not.found").concat(" "));
            exc.append(excRB.getString("usuario.id.nulo").concat(" "));
        } else if (!usuarioPort.checarUsuarioExistente(categoria.getUsuario().toString())) {
            exc.append(excRB.getString("usuario.not.found").concat(" "));
        }


        if (!exc.isEmpty()) {
            logPort.warn(exc.toString());
            throw new CategoriaException(exc.toString());
        }
        logPort.info("Categoria validada com sucesso.");
        logPort.trace("<- CategoriaService.validarCamposObrigatorios()");

        return true;
    }

    public Categoria atualizarCategoria(Categoria categoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.atualizarCategoria()");
        StringBuilder exc = new StringBuilder();
        Categoria categoriaSalva = new Categoria();
        try {
            if (!validarCamposObrigatorios(categoria)) {
                exc.append(excRB.getString("categoria.campoObrigatorio.validacao").concat(" "));
            }

            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }

            categoriaSalva = categoriaPort.buscarCategoria(categoria.getId());
            categoriaSalva.setNome(categoria.getNome());
            categoriaSalva.setDescricao(categoria.getDescricao());
            categoriaSalva.setIdCategoriaSuperior(categoria.getIdCategoriaSuperior());
            categoriaSalva.setUsuario(categoria.getUsuario());

            categoriaPort.atualizarCategoria(categoriaSalva);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categoria atualizada com sucesso.");
        logPort.debug(categoria.toString());
        logPort.trace("<- CategoriaService. atualizarCategoria()");

        return categoriaSalva;
    }

    public void verificarSubCategorias(Categoria categoriaSuperior) throws CategoriaException {
        logPort.trace("-> CategoriaService.veririficaSubCategorias()");
        StringBuilder exc = new StringBuilder();


        try {
            List<Categoria> subCategorias = categoriaPort.buscaSubCategorias(categoriaSuperior.getId());

            if (!subCategorias.isEmpty()) {
                for (Categoria subCategoria : subCategorias) {
                    removerCategoriaSuperior(categoriaSuperior.getIdCategoriaSuperior(), subCategoria.getId());
                }
            }
        } catch (CategoriaException e) {
            throw new RuntimeException(e);
        }

        logPort.trace("<- CategoriaService.veririficaSubCategorias()");

    }

    public List<Categoria> buscarCategoriasPorNome(Nome nomeCategoria) throws CategoriaException {
        logPort.trace("-> CategoriaService.buscaCategoriasPorNome()");
        StringBuilder exc = new StringBuilder();
        List<Categoria> categoriaResposta;

        try {
            if (nomeCategoria == null) {
                exc.append(excRB.getString("categoria.nome.nulo").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaResposta = categoriaPort.buscaCategoriasPorNome(nomeCategoria);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categorias por nome buscada com sucesso.");
        logPort.debug("<- CategoriaService.buscaCategoriasPorNome()");

        return categoriaResposta;
    }

    public List<Categoria> buscarSubCategorias(UUID categoriaSuperior) throws CategoriaException {
        logPort.trace("-> CategoriaService.buscaSubCategorias()");
        StringBuilder exc = new StringBuilder();
        List<Categoria> categoriaResposta;

        try {
            if (categoriaSuperior == null) {
                exc.append(excRB.getString("categoria.idSuperior.invalido").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaResposta = categoriaPort.buscaSubCategorias(categoriaSuperior);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("SubCategorias buscadas com sucesso.");
        logPort.debug("<- CategoriaService.buscaSubCategorias()");

        return categoriaResposta;
    }

    public List<Categoria> buscarCategoriasPorUsuario(UUID usuarioId) throws CategoriaException {
        logPort.trace("-> CategoriaService.buscaCategoriasPorUsuario()");
        StringBuilder exc = new StringBuilder();
        List<Categoria> categoriaResposta;

        try {
            if (usuarioId == null) {
                exc.append(excRB.getString("usuario.not.found").concat(" "));
                exc.append(excRB.getString("usuario.id.nulo").concat(" "));
            } else if (usuarioId.toString().isEmpty()) {
                exc.append(excRB.getString("usuario.not.found").concat(" "));
                exc.append(excRB.getString("usuario.id.nulo").concat(" "));
            } else if (!usuarioPort.checarUsuarioExistente(usuarioId.toString())) {
                exc.append(excRB.getString("usuario.not.found").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaResposta = categoriaPort.buscaCategoriasPorUsuario(usuarioId);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categorias por usuario buscada com sucesso.");
        logPort.debug("<- CategoriaService.buscaCategoriasPorUsuario()");

        return categoriaResposta;
    }

    public Categoria buscarCategoria(UUID categoriaId) throws CategoriaException {
        logPort.trace("-> CategoriaService.buscarCategoria()");
        StringBuilder exc = new StringBuilder();
        Categoria categoriaResposta;

        try {
            if (categoriaId == null) {
                exc.append(excRB.getString("categoria.id.nulo").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }
            categoriaResposta = categoriaPort.buscarCategoria(categoriaId);
        } catch (Exception ex) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(ex.getMessage()));
        }

        logPort.info("Categoria buscada com sucesso.");
        logPort.debug("<- CategoriaService.buscarCategoria()");

        return categoriaResposta;
    }

    public void adicionarCategoriaSuperior(UUID idCategoriaSuperior, UUID idCategoriaInferior) throws CategoriaException {
        logPort.trace("-> CategoriaService.adicionarCategoriaSuperior()");
        StringBuilder exc = new StringBuilder();
        Categoria categoriaResposta;
        try {
            if (idCategoriaSuperior == null) {
                exc.append(excRB.getString("categoria.idSuperior.invalido").concat(" "));
            }
            if (idCategoriaInferior == null) {
                exc.append(excRB.getString("categoria.idInferior.invalido").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }

            Categoria categoriaSuperior = this.buscarCategoria(idCategoriaSuperior);
            Categoria categoriaInferior = this.buscarCategoria(idCategoriaInferior);

            categoriaInferior.setIdCategoriaSuperior(categoriaSuperior.getIdCategoriaSuperior());
            categoriaResposta = categoriaPort.atualizarCategoria(categoriaInferior);

        } catch (Exception e) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(e.getMessage()));
        }

        logPort.info("categoria superior adicionada com sucesso.");
        logPort.debug(categoriaResposta.toString());
        logPort.trace("<- CategoriaService.adicionarCategoriaSuperior()");


    }

    public void removerCategoriaSuperior(UUID idCategoriaSuperior, UUID idCategoriaInferior) throws CategoriaException {
        logPort.trace("-> CategoriaService.removeCategoriaSuperior()");
        StringBuilder exc = new StringBuilder();
        Categoria categoriaResposta;
        try {
            if (idCategoriaSuperior == null) {
                exc.append(excRB.getString("categoria.idSuperior.invalido").concat(" "));
            }
            if (idCategoriaInferior == null) {
                exc.append(excRB.getString("categoria.idInferior.invalido").concat(" "));
            }
            if (!exc.isEmpty()) {
                logPort.warn(exc.toString());
                throw new CategoriaException(exc.toString());
            }

            Categoria categoriaSuperior = this.buscarCategoria(idCategoriaSuperior);
            Categoria categoriaInferior = this.buscarCategoria(idCategoriaInferior);

            categoriaInferior.setIdCategoriaSuperior(null);
            categoriaResposta = categoriaPort.atualizarCategoria(categoriaInferior);


        } catch (Exception e) {
            // Captura todas as exceções e lança uma CategoriaException com a mensagem acumulada e a mensagem original da exceção
            String mensagemErro = !exc.isEmpty() ? exc.toString() : "";
            throw new CategoriaException(mensagemErro.concat(e.getMessage()));
        }
        logPort.info("categoria superior removida com sucesso.");
        logPort.debug(categoriaResposta.toString());
        logPort.trace("<- CategoriaService.removeCategoriaSuperior()");

    }


}
