package com.modulo.projetoapi.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.modulo.projetoapi.model.Categoria;
import com.modulo.projetoapi.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}

	@GetMapping(value = "/{codigo}")
	public Categoria findById(@PathVariable Long codigo){
		return categoriaRepository.findOne(codigo);
	}

	@PostMapping
	public ResponseEntity<Categoria> insert(@RequestBody Categoria categoria,HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(categoriaSalva);
	}

}
