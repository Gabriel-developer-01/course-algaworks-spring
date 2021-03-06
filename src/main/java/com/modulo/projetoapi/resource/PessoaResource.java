package com.modulo.projetoapi.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modulo.projetoapi.event.RecursoCriadoEvent;
import com.modulo.projetoapi.model.Pessoa;
import com.modulo.projetoapi.repository.PessoaRepository;
import com.modulo.projetoapi.repository.filter.PessoaFilter;
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

	/*@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}*/
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public Page<Pessoa> filterName(PessoaFilter pessoaFilter, Pageable pageable) {
		return pessoaRepository.filterName(pessoaFilter, pageable);
	}

	@GetMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> findById(@PathVariable Long codigo) {
		Pessoa pessoaBuscar = pessoaRepository.findOne(codigo);
		return !(pessoaBuscar == null) ? ResponseEntity.ok().body(pessoaBuscar) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> insert(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalvar = pessoaRepository.save(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvar.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvar);
	}
	
	
	@DeleteMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Void> delete(@PathVariable Long codigo) {
		pessoaRepository.delete(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> update(@PathVariable Long codigo,@Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.update(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping(value = "/{codigo}/ativo")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Void> UpdateAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.updateAtivo(codigo, ativo);
		return ResponseEntity.noContent().build();
	}
}
