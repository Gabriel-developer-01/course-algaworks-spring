package com.modulo.projetoapi.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.modulo.projetoapi.model.Pessoa;
import com.modulo.projetoapi.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public Page<Pessoa> filterName(PessoaFilter pessoa, Pageable pageable);
}
