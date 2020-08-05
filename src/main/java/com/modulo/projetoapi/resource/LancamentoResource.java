package com.modulo.projetoapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modulo.projetoapi.event.RecursoCriadoEvent;
import com.modulo.projetoapi.model.Lancamento;
import com.modulo.projetoapi.repository.LancamentoRepository;

@RestController
@RequestMapping(value = "/{lancamentos}")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Lancamento> findAll(){
		return lancamentoRepository.findAll();
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Lancamento> findById(@PathVariable Long codigo){
		Lancamento findById= lancamentoRepository.findOne(codigo);
		return !(findById == null) ? ResponseEntity.ok().body(findById) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> insert(@Valid @RequestBody Lancamento lancamento,HttpServletResponse response){
		Lancamento lancamentoSave = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSave.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSave);
	}
}
