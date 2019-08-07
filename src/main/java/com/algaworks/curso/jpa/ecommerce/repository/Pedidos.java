package com.algaworks.curso.jpa.ecommerce.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.algaworks.curso.jpa.ecommerce.model.Pedido;

@Repository
public class Pedidos {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Pedido> listar() {
		return entityManager.createQuery(
				"select p from Pedido p", Pedido.class)
				.getResultList();
	}
	
	public Pedido buscar(Integer id) {
		return entityManager.find(Pedido.class, id);
	}
	
	public boolean existe(Integer id) {
		return entityManager
				.createQuery("select count(p) > 0 from Pedido p where p.id = :id", Boolean.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		return entityManager.merge(pedido);
	}
	
	@Transactional
	public void remover(Integer id) {
		entityManager.remove(
				entityManager.getReference(Pedido.class, id));
	}
}
