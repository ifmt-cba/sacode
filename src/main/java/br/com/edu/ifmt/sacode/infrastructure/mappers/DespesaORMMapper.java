package br.com.edu.ifmt.sacode.infrastructure.mappers;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.infrastructure.persistence.DespesaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.UsuarioORM;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Component
public class DespesaORMMapper {
    CategoriaORMMapper categoriaORMMapper;

  public static DespesaORM toORM(Despesa despesaDomainObj) {
    UsuarioORM usuario = new UsuarioORM();
    usuario.setIdUsuario(despesaDomainObj.getUsuario().toString());

    return new DespesaORM(
            despesaDomainObj.getIdDespesa(),
            despesaDomainObj.getDescricao(),
            despesaDomainObj.getValor(),
            despesaDomainObj.getData(),
            usuario,
            despesaDomainObj.getAutorDespesa(),
            despesaDomainObj.isFixa(),
            despesaDomainObj.getFinanciadorDespesa(),
            nonNull(despesaDomainObj.getCorrelacaoParcelas()) ? despesaDomainObj.getCorrelacaoParcelas(): null,
            despesaDomainObj.getNumParcela(),
            CategoriaORMMapper.dominioParaOrm(despesaDomainObj.getCategoria())
        );
  }

  public static Despesa toDomainObj(DespesaORM despesaORM) {

    Despesa despesa = new Despesa();
    despesa.setIdDespesa( UUID.fromString( despesaORM.getIdDespesa()));
    despesa.setDescricao(new Descricao(despesaORM.getDescricao().toString()));
    despesa.setValor( new Moeda(despesaORM.getValor()));
    despesa.setData( despesaORM.getData());
    despesa.setUsuario(UUID.fromString(despesaORM.getUsuario().getIdUsuario()));
    despesa.setAutorDespesa(new Nome(despesaORM.getAutorDespesa().toString()));
    despesa.setFixa( despesaORM.getFixa());
    despesa.setFinanciadorDespesa(new Nome(despesaORM.getFinanciadorDespesa().toString()));
    if(nonNull(despesaORM.getCorrelacaoParcelas())){
        despesa.setCorrelacaoParcelas(UUID.fromString(despesaORM.getCorrelacaoParcelas()));
    }
    despesa.setNumParcela(despesaORM.getNumParcela());
    despesa.setCategoria(CategoriaORMMapper.ormParaDominio(despesaORM.getCategoria()));
    return despesa;
  }
  public List<Despesa> toDomainList(List<DespesaORM> despesasORM) {
        List<Despesa> despesas = new ArrayList<>();
        for (DespesaORM despesaORM : despesasORM) {
            despesas.add(toDomainObj(despesaORM));
        }
        return despesas;
    }
}