package com.modulo.projetoapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.modulo.projetoapi.event.RecursoCriadoEvent;
import com.modulo.projetoapi.model.Pessoa;
import com.modulo.projetoapi.repository.PessoaRepository;
import com.modulo.projetoapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaService pessoaService;

	@GetMapping
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Pessoa> findById(@PathVariable Long codigo) {
		Pessoa pessoaBuscar = pessoaRepository.findOne(codigo);
		return (!(pessoaBuscar == null)) ? ResponseEntity.ok().body(pessoaBuscar) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Pessoa> insert(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalvar = pessoaRepository.save(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvar.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvar);
	}
	
	@DeleteMapping(value = "/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		pessoaRepository.delete(codigo);
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Pessoa> update(@PathVariable Long codigo,@Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.update(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping(value = "/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void UpdateAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.updateAtivo(codigo, ativo);
	}
}
