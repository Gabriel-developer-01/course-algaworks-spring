package com.modulo.projetoapi.repository.lancamento;

import java.util.List;

import com.modulo.projetoapi.model.Lancamento;
import com.modulo.projetoapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filter(LancamentoFilter lancamentoFilter);

}
