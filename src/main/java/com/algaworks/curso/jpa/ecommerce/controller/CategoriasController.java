package com.algaworks.curso.jpa.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.curso.jpa.ecommerce.model.Categoria;
import com.algaworks.curso.jpa.ecommerce.repository.Categorias;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

	@Autowired
	private Categorias categorias;

	@GetMapping
	public List<Categoria> listar() {
		return categorias.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscar(@PathVariable Integer id) {
		Categoria categoria = categorias.buscar(id);

		if (categoria == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(categoria);
	}

	@PostMapping
	public ResponseEntity<Categoria> salvar(@RequestBody Categoria categoria) {
		categoria.setId(null);

		categoria = categorias.salvar(categoria);
		
		return ResponseEntity.created(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.build(categoria.getId()))
				.build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {
		if (!categorias.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		categoria.setId(id);

		return ResponseEntity.ok(categorias.salvar(categoria));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Integer id) {
		if (!categorias.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		categorias.remover(id);
		
		return ResponseEntity.ok().build();
	}
}