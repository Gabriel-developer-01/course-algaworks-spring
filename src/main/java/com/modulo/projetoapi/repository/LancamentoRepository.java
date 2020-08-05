package com.modulo.projetoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modulo.projetoapi.model.Lancamento;
import com.modulo.projetoapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
