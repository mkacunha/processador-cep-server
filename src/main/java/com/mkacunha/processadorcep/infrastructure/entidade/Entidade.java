package com.mkacunha.processadorcep.infrastructure.entidade;

public abstract class Entidade {

	protected Long id;

	public Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}
}
