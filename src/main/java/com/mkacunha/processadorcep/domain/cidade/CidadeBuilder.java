package com.mkacunha.processadorcep.domain.cidade;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CidadeBuilder {

	private Long id;

	private String ibge;

	private String nome;

	private String uf;

	CidadeBuilder() {
	}

	public CidadeBuilder resultSet(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong("id");
		this.ibge = resultSet.getString("ibge");
		this.nome = resultSet.getString("nome");
		this.uf = resultSet.getString("uf");
		return this;
	}

	public CidadeBuilder ibge(String ibge) {
		this.ibge = ibge;
		return this;
	}

	public CidadeBuilder nome(String nome) {
		this.nome = nome;
		return this;
	}

	public CidadeBuilder uf(String uf) {
		this.uf = uf;
		return this;
	}

	public Cidade build() {
		return new Cidade(this);
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

	public Long getId() {
		return id;
	}
}
