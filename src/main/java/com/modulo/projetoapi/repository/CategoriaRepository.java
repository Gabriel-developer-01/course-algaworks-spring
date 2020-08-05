package com.modulo.projetoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modulo.projetoapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
