package com.algaworks.curso.jpa.ecommerce.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.curso.jpa.ecommerce.model.Produto;

@Repository
public class Produtos {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Produto> pesquisar(String nome) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		if (StringUtils.hasText(nome)) {
			criteriaQuery.where(criteriaBuilder.like(root.get("nome"), nome + "%"));
		}
		
		return entityManager.createQuery(criteriaQuery)
				.getResultList();
	}
	
	public Produto buscar(Integer id) {
		return entityManager.find(Produto.class, id);
	}
	
	public boolean existe(Integer id) {
		return entityManager
				.createQuery("select count(p) > 0 from Produto p where p.id = :id", Boolean.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	@Transactional
	public Produto salvar(Produto produto) {
		return entityManager.merge(produto);
	}
	
	@Transactional
	public void remover(Integer id) {
		entityManager.remove(
				entityManager.getReference(Produto.class, id));
	}
}
