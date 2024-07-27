package br.com.edu.ifmt.sacode.config;

import br.com.edu.ifmt.sacode.domain.entities.vo.*;
import br.com.edu.ifmt.sacode.infrastructure.persistence.*;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
@Profile("teste")
public class TesteConfiguracao implements CommandLineRunner {

    private UsuarioRepository usuarioRepository;
    private DespesaRepository despesaRepository;
    private CategoriaRepository categoriaRepository;


    @Autowired
    public TesteConfiguracao(
            UsuarioRepository usuarioRepository,
            DespesaRepository despesaRepository,
            CategoriaRepository categoriaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.despesaRepository = despesaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String senha = Hashing.sha256()
                .hashString("ola mundo", StandardCharsets.UTF_8)
                .toString();

        UUID userId = UUID.randomUUID();
        Email email = new Email("heloise@heloise.com");
        Username username = new Username("heloise28");
        Password password = new Password(senha);
        Nome nome = new Nome("heloise da materia da faculdade");
        boolean superUsuario = true;
        List<String> membroFamiliar = Arrays.asList("membro1", "membro2");

        UsuarioORM usuarioORM = new UsuarioORM(
                userId,
                email,
                username,
                password,
                nome,
                superUsuario,
                membroFamiliar);


        UUID idCategoria = UUID.randomUUID();
        CategoriaORM categoriaORM = new CategoriaORM();
        categoriaORM.setUsuario(usuarioORM);
        categoriaORM.setDescricao("Categoria 1");
        categoriaORM.setNome("Nova Categoria");
        categoriaORM.setIdCategoria(idCategoria.toString());


        UUID idDespesa = UUID.randomUUID();
        Descricao descricao = new Descricao("Compra de equipamentos");
        Moeda valor = new Moeda(TipoMoeda.REAL, new BigDecimal(1000.5));
        LocalDate data = LocalDate.now();
        Nome autor = new Nome("Nome do Autor");
        Boolean fixa = true;
        Nome financiador = new Nome("Nome do Financiador");
        UUID correlacaoParcelas = UUID.randomUUID();
        Integer numParcela = 1;

        DespesaORM despesa = new DespesaORM(
                idDespesa,
                descricao,
                valor,
                data,
                usuarioORM,
                autor,
                fixa,
                financiador,
                correlacaoParcelas,
                numParcela,
                categoriaORM
        );


        usuarioRepository.save(usuarioORM);
        categoriaRepository.save(categoriaORM);
        despesaRepository.save(despesa);
    }


}
