package com.modulo.projetoapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.modulo.projetoapi.service.LancamentoService;
import com.modulo.projetoapi.service.exception.PessoaInexistenteouInativaException;

@RestController
@RequestMapping(value = "/{lancamentos}")
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
		Lancamento lancamentoSave = lancamentoService.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSave.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSave);
	}
	
	@ExceptionHandler( {PessoaInexistenteouInativaException.class} )
	public ResponseEntity<Object> PessoaInexistenteouInativaException(PessoaInexistenteouInativaException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}
