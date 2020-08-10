package com.modulo.projetoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modulo.projetoapi.model.Pessoa;
import com.modulo.projetoapi.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery{

}
