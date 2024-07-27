package br.com.edu.ifmt.sacode.infrastructure.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.entities.vo.Nome;
import br.com.edu.ifmt.sacode.domain.entities.vo.Descricao;
import br.com.edu.ifmt.sacode.domain.entities.vo.Moeda;
import br.com.edu.ifmt.sacode.infrastructure.persistence.DespesaORM;

@Component
public class DespesaORMMapper {
  public DespesaORM toORM(Despesa despesaDomainObj) {
    return new DespesaORM(
            despesaDomainObj.getIdDespesa(),
            despesaDomainObj.getDescricao(),
            despesaDomainObj.getValor(),
            despesaDomainObj.getData(),
            despesaDomainObj.getUsuario(),
            despesaDomainObj.getAutorDespesa(),
            despesaDomainObj.isFixa(),
            despesaDomainObj.getFinanciadorDespesa(),
            despesaDomainObj.getCorrelacaoParcelas(),
            despesaDomainObj.getNumParcela()
        );
  }

  public Despesa toDomainObj(DespesaORM despesaORM) {
    Despesa despesa = new Despesa();
    despesa.setIdDespesa( UUID.fromString( despesaORM.getIdDespesa()));
    despesa.setDescricao(new Descricao(despesaORM.getDescricao().toString()));
    despesa.setValor( new Moeda(despesaORM.getValor()));
    despesa.setData( despesaORM.getData());
    despesa.setUsuario( UUID.fromString(despesaORM.getUsuario()));
    despesa.setAutorDespesa(new Nome(despesaORM.getAutorDespesa().toString()));
    despesa.setFixa( despesaORM.getFixa());
    despesa.setFinanciadorDespesa(new Nome(despesaORM.getFinanciadorDespesa().toString()));
    despesa.setCorrelacaoParcelas(  UUID.fromString(despesaORM.getCorrelacaoParcelas()));
    despesa.setNumParcela(despesaORM.getNumParcela());
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
