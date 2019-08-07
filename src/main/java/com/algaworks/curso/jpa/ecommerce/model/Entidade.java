package com.algaworks.curso.jpa.ecommerce.model;

import java.io.Serializable;

public interface Entidade<ID> extends Serializable {
	
	ID getId();

}
