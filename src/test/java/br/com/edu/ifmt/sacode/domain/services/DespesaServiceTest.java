package br.com.edu.ifmt.sacode.domain.services;


import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.DespesaPort;
import br.com.edu.ifmt.sacode.domain.services.exception.DespesaException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@SpringBootTest
public class DespesaServiceTest {
    static DespesaService despesaService;

    @BeforeAll
    static void init() {
        despesaService = new DespesaService(new DespesaPort() {

            @Override
            public Despesa criarDespesa(Despesa despesa) {
                try {
                    despesaService.criarDespesa(
                    new Despesa(
                        
                    )
                    );
                } catch (Exception e) {
                    // TODO: handle exception
                }
                

                return despesa;
            }

            @Override
            public Despesa atualizarDespesa(Despesa despesa) {
                return despesa;
            }

            @Override
            public int excluirDespesa(UUID idDespesa, UUID usuario){
                return 2;
            }

            @Override
            public Despesa buscarDespesa(UUID despesaId) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getByIdDespesa'");
            }

            @Override
            public int excluirParcelas(UUID usuario, UUID correlacaoParcleas) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'excluirParcelas'");
            }

            @Override
            public List<Despesa> buscarDespesaPorPeriodo(UUID usuario, LocalDate primeiroDia, LocalDate ultimoDia) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'buscarDespesaPorPeriodo'");
            }

            @Override
            public List<Despesa> buscarDespesaPorAutor(UUID usuario, String autor) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'buscarDespesaPorAutor'");
            }

            @Override
            public List<Despesa> buscarDespesaPorFinanciador(UUID usuario, String autor) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'buscarDespesaPorFianciador'");
            }

            


        },
                new LogPort() {

                    @Override
                    public void trace(String msg) { }

                    @Override
                    public void debug(String msg) { }

                    @Override
                    public void info(String msg) { }

                    @Override
                    public void warn(String msg) { }

                    @Override
                    public void error(String msg) { }

                });
    }

    @Test
    void shouldThrowException() {
        assertThrows(DespesaException.class, () -> {
            despesaService.criarDespesa(new Despesa());
        });
    }
}