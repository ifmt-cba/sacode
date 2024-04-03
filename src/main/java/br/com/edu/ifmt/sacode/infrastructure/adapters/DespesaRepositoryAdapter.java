package br.com.edu.ifmt.sacode.infrastructure.adapters;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.edu.ifmt.sacode.domain.entities.Despesa;
import br.com.edu.ifmt.sacode.domain.ports.LogPort;
import br.com.edu.ifmt.sacode.domain.ports.DespesaPort;
import br.com.edu.ifmt.sacode.infrastructure.mappers.DespesaORMMapper;
import br.com.edu.ifmt.sacode.infrastructure.persistence.DespesaORM;
import br.com.edu.ifmt.sacode.infrastructure.persistence.DespesaRepository;

public class DespesaRepositoryAdapter implements DespesaPort {
  private final DespesaRepository despesaRepository;
  private final DespesaORMMapper despesaORMMapper;

  @Autowired
  private final LogPort logPort = null;

  public DespesaRepositoryAdapter(DespesaRepository despesaRepository, DespesaORMMapper despesaORMMapper) {
    this.despesaRepository = despesaRepository;
    this.despesaORMMapper = despesaORMMapper;
  }

  @Override
  public Despesa criarDespesa(Despesa despesaDomainObj) {
    logPort.trace("-> DespesaRepositoryAdapter.criarDespesa");
    DespesaORM despesaORM = despesaORMMapper.toORM(despesaDomainObj);
    logPort.debug(despesaORM.toString());
    DespesaORM savedDespesa = despesaRepository.save(despesaORM);
    logPort.info("Despesa inserida na tabela despesa.");
    logPort.debug(despesaORM.toString());
    logPort.trace("<- DespesaRepositoryAdapter.criarDespesa");
    return despesaORMMapper.toDomainObj(savedDespesa);
  }

  @Override
  public Despesa atualizarDespesa(Despesa despesaDomainObj) {
    logPort.trace("-> DespesaRepositoryAdapter.atualizarDespesa");
    DespesaORM despesaORM = despesaORMMapper.toORM(despesaDomainObj);
    logPort.debug(despesaORM.toString());
    DespesaORM savedDespesa = despesaRepository.save(despesaORM);
    logPort.info("Despesa atualizanada na tabela despesa.");
    logPort.debug(despesaORM.toString());
    logPort.trace("<- DespesaRepositoryAdapter.atualizarDespesa");
    return despesaORMMapper.toDomainObj(savedDespesa);
  }
  
  @Override
  public int excluirDespesa(UUID idDespesa, UUID usuario) {
    logPort.trace("-> DespesaRepositoryAdapter.excluirDespesa");
    int retorno = despesaRepository.deleteByIdDespesaAndUsuario(idDespesa.toString(), usuario.toString());
    logPort.trace("<- DespesaRepositoryAdapter.escluirDespesa");
    return retorno;
  }
  
  @Override
  public Despesa buscarDespesa(UUID idDespesa) {
    logPort.trace("-> DespesaRepositoryAdapter.buscarDespesa");
    DespesaORM savedDespesa = despesaRepository.findByIdDespesa(idDespesa.toString());
    logPort.trace("<- DespesaRepositoryAdapter.buscarDespesa");
    return despesaORMMapper.toDomainObj(savedDespesa);
  }
  
  @Override
  public int excluirParcelas(UUID usuario, UUID correlacaoParcleas) {
    logPort.trace("-> DespesaRepositoryAdapter.excluirParcelas");
    int retorno = despesaRepository.deleteByUsuarioAndCorrelacaoParcelas(usuario.toString(), correlacaoParcleas.toString());
    logPort.trace("<- DespesaRepositoryAdapter.excluirParcelas");
    return retorno;
  }

  @Override
  public List<Despesa> buscarDespesaPorPeriodo(UUID usuario, LocalDate comeco, LocalDate fim) {
    logPort.trace("-> DespesaRepositoryAdapter.excluirParcelas");
    List<DespesaORM> retorno = despesaRepository.findByUsuarioAndDataBetween(usuario.toString(), comeco, fim);
    logPort.trace("<- DespesaRepositoryAdapter.excluirParcelas");
    return despesaORMMapper.toDomainList(retorno);
  }

  @Override
  public List<Despesa> buscarDespesaPorAutor(UUID usuario, String autor) {
    logPort.trace("-> DespesaRepositoryAdapter.excluirParcelas");
    List<DespesaORM> retorno = despesaRepository.findByUsuarioAndAutor(usuario.toString(), autor);
    logPort.trace("<- DespesaRepositoryAdapter.excluirParcelas");
    return despesaORMMapper.toDomainList(retorno);
  }

  @Override
  public List<Despesa> buscarDespesaPorFinanciador(UUID usuario, String financiador) {
    logPort.trace("-> DespesaRepositoryAdapter.excluirParcelas");
    List<DespesaORM> retorno = despesaRepository.findByUsuarioAndFinanciador(usuario.toString(), financiador);
    logPort.trace("<- DespesaRepositoryAdapter.excluirParcelas");
    return despesaORMMapper.toDomainList(retorno);
  }

}
