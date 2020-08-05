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
import com.modulo.projetoapi.model.Categoria;
import com.modulo.projetoapi.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Categoria> findById(@PathVariable Long codigo){
		Categoria categoriaBuscar = categoriaRepository.findOne(codigo);
		return !(categoriaBuscar == null) ? ResponseEntity.ok(categoriaBuscar) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Categoria> insert(@Valid @RequestBody Categoria categoria,HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
}
