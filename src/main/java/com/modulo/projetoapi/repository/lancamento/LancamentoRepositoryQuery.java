package com.modulo.projetoapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.modulo.projetoapi.model.Lancamento;
import com.modulo.projetoapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	 public Page<Lancamento> filter(LancamentoFilter lancamentoFilter, Pageable pageable);

}
