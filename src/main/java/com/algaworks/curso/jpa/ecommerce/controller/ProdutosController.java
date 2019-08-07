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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.curso.jpa.ecommerce.model.Produto;
import com.algaworks.curso.jpa.ecommerce.repository.Produtos;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private Produtos produtos;
	
	@GetMapping
	public List<Produto> pesquisar(@RequestParam(required = false) String nome) {
		return produtos.pesquisar(nome);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscar(@PathVariable Integer id) {
		Produto produto = produtos.buscar(id);
		
		if (produto == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(produto);
	}
	
	@PostMapping
	public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
		produto.setId(null);

		produto = produtos.salvar(produto);
		
		return ResponseEntity.created(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.build(produto.getId()))
				.build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(@PathVariable Integer id, @RequestBody Produto produto) {
		if (!produtos.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		produto.setId(id);
		
		return ResponseEntity.ok(produtos.salvar(produto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Integer id) {
		if (!produtos.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		produtos.remover(id);
		
		return ResponseEntity.ok().build();
	}
}