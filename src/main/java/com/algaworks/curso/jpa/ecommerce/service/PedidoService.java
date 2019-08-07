package com.algaworks.curso.jpa.ecommerce.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.curso.jpa.ecommerce.model.Pedido;
import com.algaworks.curso.jpa.ecommerce.repository.Pedidos;
import com.algaworks.curso.jpa.ecommerce.repository.Produtos;

@Service
public class PedidoService {
	
	@Autowired
	private Pedidos pedidos;
	
	@Autowired
	private Produtos produtos;
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		pedido.getItens().forEach(
				i -> i.setProduto(produtos.buscar(i.getProduto().getId())));
		
		pedido.fechar();
		
		return pedidos.salvar(pedido);
	}
}
