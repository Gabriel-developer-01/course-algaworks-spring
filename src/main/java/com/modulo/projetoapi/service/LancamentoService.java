package com.modulo.projetoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modulo.projetoapi.model.Lancamento;
import com.modulo.projetoapi.model.Pessoa;
import com.modulo.projetoapi.repository.LancamentoRepository;
import com.modulo.projetoapi.repository.PessoaRepository;
import com.modulo.projetoapi.service.exception.PessoaInexistenteouInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento save(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteouInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}
}
