package com.modulo.projetoapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modulo.projetoapi.model.Lancamento;
import com.modulo.projetoapi.repository.LancamentoRepository;

@RestController
@RequestMapping(value = "/{lancamentos}")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@GetMapping
	public List<Lancamento> findAll(){
		return lancamentoRepository.findAll();
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Lancamento> findById(@PathVariable Long codigo){
		Lancamento findById= lancamentoRepository.findOne(codigo);
		return !(findById == null) ? ResponseEntity.ok().body(findById) : ResponseEntity.notFound().build();
	}

}
