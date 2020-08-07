package com.modulo.projetoapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modulo.projetoapi.event.RecursoCriadoEvent;
import com.modulo.projetoapi.exceptionsHandler.ExceptionHandler.Erro;
import com.modulo.projetoapi.model.Lancamento;
import com.modulo.projetoapi.repository.LancamentoRepository;
import com.modulo.projetoapi.repository.filter.LancamentoFilter;
import com.modulo.projetoapi.service.LancamentoService;
import com.modulo.projetoapi.service.exception.PessoaInexistenteouInativaException;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> search(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filter(lancamentoFilter, pageable);
	}

	@GetMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> findById(@PathVariable Long codigo) {
		Lancamento findById = lancamentoRepository.findOne(codigo);
		return !(findById == null) ? ResponseEntity.ok().body(findById) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> insert(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSave = lancamentoService.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSave.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSave);
	}

	@DeleteMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Void> delete(@PathVariable Long codigo) {
		lancamentoRepository.delete(codigo);
		return ResponseEntity.noContent().build();
	}

	// lançar exception ao salvar um lançamento para uma pessoa inativa ou inexistente.
	@ExceptionHandler({ PessoaInexistenteouInativaException.class })
	public ResponseEntity<Object> PessoaInexistenteouInativaException(PessoaInexistenteouInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}
