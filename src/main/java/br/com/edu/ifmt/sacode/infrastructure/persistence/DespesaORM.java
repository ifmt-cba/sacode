package br.com.edu.ifmt.sacode.infrastructure.persistence;

import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "despesa")
public class DespesaORM {

    @Id
    private String idDespesa;
    private String descricao;
    private String valor;
    private LocalDate data;
    private String autor;
    private Boolean fixa;
    private String financiador;
    private String correlacaoParcelas;
    private Integer numParcela;

    public DespesaORM() {}

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaORM categoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioORM usuario;

    public DespesaORM(
        UUID idDespesa,
        Descricao descricao,
        Moeda valor,
        LocalDate data,
        UsuarioORM usuario,
        Nome autor,
        Boolean fixa,
        Nome financiador,
        UUID correlacaoParcelas,
        Integer numParcela,
        CategoriaORM categoria
        ) {
        this.idDespesa = idDespesa.toString();
        this.descricao = descricao.toString();
        this.valor = valor.toString();
        this.data = data;
        this.usuario = usuario;
        this.autor = autor.toString();
        this.fixa = fixa;
        this.financiador = financiador.toString();
        this.correlacaoParcelas = correlacaoParcelas.toString();
        this.numParcela = numParcela;
        this.categoria = categoria;
      }

    @Override
    public String toString() {
        return String.format( """
                {
                    "idDespesa":"%s",                   
                    "descricao":"%s",     
                    "categoria":"%s",               
                    "valor":%s,
                    "data":"%s",
                    "usuario":"%s",
                    "autoDespesa":"%s",
                    "fixa": %s,
                    "financiador":"%s",
                    "correlacaoParcelas":%s,
                    "numParcela":%s
                }
                """,
            this.idDespesa.toString(),
            this.descricao.toString(),
            this.categoria.getNome(),
            this.valor.toString(),
            this.data.toString(),
            this.usuario.toString(),
            this.autor.toString(),
            this.fixa.toString(),
            this.financiador.toString(),
            this.correlacaoParcelas.toString(),
            this.numParcela.toString()
        );
    }

    public String getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(String idDespesa) {
        this.idDespesa = idDespesa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getAutorDespesa() {
        return autor;
    }

    public void setAutorDespesa(String autor) {
        this.autor = autor;
    }

    public Boolean getFixa() {
        return fixa;
    }

    public void setFixa(Boolean fixa) {
        this.fixa = fixa;
    }

    public String getFinanciadorDespesa() {
        return financiador;
    }

    public void setFinanciadorDespesa(String financiador) {
        this.financiador = financiador;
    }

    public String getCorrelacaoParcelas() {
        return correlacaoParcelas;
    }

    public void setCorrelacaoParcelas(String correlacaoParcelas) {
        this.correlacaoParcelas = correlacaoParcelas;
    }

    public Integer getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(Integer numParcela) {
        this.numParcela = numParcela;
    }

    public CategoriaORM getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaORM categoria) {
        this.categoria = categoria;
    }

    public UsuarioORM getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioORM usuario) {
        this.usuario = usuario;
    }
}
