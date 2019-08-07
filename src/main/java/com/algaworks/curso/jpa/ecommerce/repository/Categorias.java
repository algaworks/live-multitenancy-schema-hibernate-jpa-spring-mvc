package com.algaworks.curso.jpa.ecommerce.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.algaworks.curso.jpa.ecommerce.model.Categoria;

@Repository
public class Categorias {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Categoria> listar() {
		return entityManager.createQuery(
				"select c from Categoria c", Categoria.class)
				.getResultList();
	}
	
	public Categoria buscar(Integer id) {
		return entityManager.find(Categoria.class, id);
	}
	
	public boolean existe(Integer id) {
		return entityManager
				.createQuery("select count(c) > 0 from Categoria c where c.id = :id", Boolean.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	@Transactional
	public Categoria salvar(Categoria categoria) {
		return entityManager.merge(categoria);
	}
	
	@Transactional
	public void remover(Integer id) {
		entityManager.remove(
				entityManager.getReference(Categoria.class, id));
	}
}
