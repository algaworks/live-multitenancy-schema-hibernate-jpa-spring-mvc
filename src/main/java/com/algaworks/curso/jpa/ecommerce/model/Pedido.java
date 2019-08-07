package com.algaworks.curso.jpa.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pedido extends EntidadeAbstrata {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private LocalDateTime data;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "pedido", 
			cascade = CascadeType.ALL)
	private List<Item> itens;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Pedido.Status status;
	
	@Column(nullable = false)
	private BigDecimal total;
	
	public void fechar() {
		if (id == null) {
			data = LocalDateTime.now();
			status = Pedido.Status.AGUARDANDO;
			itens.forEach(i -> i.setPedido(this));
		}
		
		if (Pedido.Status.AGUARDANDO.equals(status)) {
			total = BigDecimal.ZERO;
			
			total = itens.stream()
					.peek(Item::fechar)
					.map(i -> i.getPreco())
					.reduce(BigDecimal.ZERO, BigDecimal::add); 			
		}
	}
	
	public static enum Status {
		AGUARDANDO, CANCELADO, PAGO
	}
}
