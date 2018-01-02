package com.mkacunha.processadorcep.domain.cep;

import com.mkacunha.processadorcep.domain.cidade.Cidade;
import com.mkacunha.processadorcep.infrastructure.entidade.Entidade;

import static java.util.Objects.nonNull;

public class Cep extends Entidade {

	private String cep;

	private String logradouro;

	private String bairro;

	private String complemento;

	private String numero;

	private Cidade cidade;

	public Cep(CepBuilder builder) {
		this.id = builder.getId();
		this.cep = builder.getCep();
		this.logradouro = builder.getLogradouro();
		this.bairro = builder.getBairro();
		this.complemento = builder.getComplemento();
		this.numero = builder.getNumero();
		this.cidade = builder.getCidade();
	}

	public static CepBuilder builder() {
		return new CepBuilder();
	}

	public CepBuilder toBuilder() {
		return new CepBuilder(this);
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

	public Long getCidadeFk() {
		if (nonNull(cidade)) {
			return cidade.getId();
		}
		return null;
	}
}
