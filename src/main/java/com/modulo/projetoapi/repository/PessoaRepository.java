package com.modulo.projetoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modulo.projetoapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
