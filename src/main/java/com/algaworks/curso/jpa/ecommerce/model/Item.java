package com.algaworks.curso.jpa.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item implements Entidade<Item.ItemId> {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private Item.ItemId id = new Item.ItemId();
	
	@JsonBackReference
	@MapsId("pedido")
	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private Pedido pedido;

	@JsonIgnoreProperties({ "categorias", "tags", "atributos" })
	@MapsId("produto")
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	public void fechar() {
		preco = produto.getPreco();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ItemId implements Serializable {
		
		private static final long serialVersionUID = 1L;

		private Integer produto;
		
		private Integer pedido;
		
		public ItemId() { }
		
		public ItemId(Integer pedido, Integer produto) {
			this.produto = produto;
			this.pedido = pedido;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
			result = prime * result + ((produto == null) ? 0 : produto.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			
			if (obj == null) {
				return false;
			}
			
			if (getClass() != obj.getClass()) {
				return false;
			}
			
			ItemId other = (ItemId) obj;
			
			if (pedido == null || produto == null) {
				return false;
			} 
			
			return pedido.equals(other.pedido) 
					&& produto.equals(other.produto);
		}
	}
}
