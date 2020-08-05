package com.modulo.projetoapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.modulo.projetoapi.model.Pessoa;
import com.modulo.projetoapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa update(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = findPeopleByCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo"); // copia as propriedades para o pessoaSalva(1° parâmetro vc passa o Objeto que quer copiar,2° parâmetro o Objeto que vai receber e 3° parâmetro as propriedades que será ignorada.)
		return pessoaRepository.save(pessoaSalva);
	}

	public void updateAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = findPeopleByCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}

	public Pessoa findPeopleByCodigo(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
}
