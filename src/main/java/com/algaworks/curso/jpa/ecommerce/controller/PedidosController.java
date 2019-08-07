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

import com.algaworks.curso.jpa.ecommerce.model.Pedido;
import com.algaworks.curso.jpa.ecommerce.repository.Pedidos;
import com.algaworks.curso.jpa.ecommerce.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {
	
	@Autowired
	private Pedidos pedidos;
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping
	public List<Pedido> listar() {
		return pedidos.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscar(@PathVariable Integer id) {
		Pedido pedido = pedidos.buscar(id);
		
		if (pedido == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pedido);
	}
	
	@PostMapping
	public ResponseEntity<Pedido> salvar(@RequestBody Pedido pedido) {
		pedido.setId(null);

		pedido = pedidoService.salvar(pedido);
		
		return ResponseEntity.created(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.build(pedido.getId()))
				.build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pedido> atualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
		if (!pedidos.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		pedido.setId(id);
		
		return ResponseEntity.ok(pedidoService.salvar(pedido));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Integer id) {
		if (!pedidos.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		pedidos.remover(id);
		
		return ResponseEntity.ok().build();
	}
}