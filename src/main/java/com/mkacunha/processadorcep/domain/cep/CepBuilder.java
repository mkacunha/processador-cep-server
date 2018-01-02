package com.mkacunha.processadorcep.domain.cep;

import com.mkacunha.processadorcep.domain.cidade.Cidade;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CepBuilder {

	private Long id;

	private String cep;

	private String logradouro;

	private String bairro;

	private String complemento;

	private String numero;

	private Cidade cidade;

	CepBuilder() {

	}

	CepBuilder(Cep cep) {
		this.id = cep.getId();
		this.cep = cep.getCep();
		this.logradouro = cep.getLogradouro();
		this.bairro = cep.getBairro();
		this.complemento = cep.getComplemento();
		this.numero = cep.getNumero();
		this.cidade = cep.getCidade();
	}

	public CepBuilder resultSet(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong("id");
		this.cep = resultSet.getString("cep");
		this.logradouro = resultSet.getString("logradouro");
		this.bairro = resultSet.getString("bairro");
		this.complemento = resultSet.getString("complemento");
		this.numero = resultSet.getString("numero");
		return this;
	}

	public CepBuilder cep(String cep) {
		this.cep = cep;
		return this;
	}

	public CepBuilder logradouro(String logradouro) {
		this.logradouro = logradouro;
		return this;
	}

	public CepBuilder bairro(String bairro) {
		this.bairro = bairro;
		return this;
	}

	public CepBuilder complemento(String complemento) {
		this.complemento = complemento;
		return this;
	}

	public CepBuilder numero(String numero) {
		this.numero = numero;
		return this;
	}

	public CepBuilder cidade(Cidade cidade) {
		this.cidade = cidade;
		return this;
	}

	public Cep build() {
		return new Cep(this);
	}

	public Long getId() {
		return id;
	}

	public String getCep() {
		return cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getNumero() {
		return numero;
	}

	public Cidade getCidade() {
		return cidade;
	}
}
