package com.mkacunha.processadorcep.domain.cidade;

import com.mkacunha.processadorcep.infrastructure.entidade.Entidade;

public class Cidade extends Entidade {

	private String ibge;

	private String nome;

	private String uf;

	public Cidade(CidadeBuilder builder) {
		this.id = builder.getId();
		this.ibge = builder.getIbge();
		this.nome = builder.getNome();
		this.uf = builder.getUf();
	}

	public static CidadeBuilder builder() {
		return new CidadeBuilder();
	}

	public String getIbge() {
		return ibge;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

}
