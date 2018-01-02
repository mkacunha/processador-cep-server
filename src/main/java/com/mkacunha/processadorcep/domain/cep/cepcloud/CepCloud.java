package com.mkacunha.processadorcep.domain.cep.cepcloud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static com.google.common.base.MoreObjects.firstNonNull;
import static org.springframework.util.StringUtils.isEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CepCloud {

	private String cep;

	private String logradouro;

	private String complemento;

	private String bairro;

	private String localidade;

	private String uf;

	private String ibge;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = firstNonNull(cep, "").replace("-", "").replace(" ", "");
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	@Override
	public String toString() {
		return "CepCloud{" +
				"cep='" + cep + '\'' +
				", logradouro='" + logradouro + '\'' +
				", complemento='" + complemento + '\'' +
				", bairro='" + bairro + '\'' +
				", localidade='" + localidade + '\'' +
				", uf='" + uf + '\'' +
				", ibge='" + ibge + '\'' +
				'}';
	}

	public boolean containsCep() {
		return !isEmpty(this.getCep());
	}

	public boolean containsCidade() {
		return !isEmpty(this.getIbge()) && !isEmpty(this.getLocalidade()) && !isEmpty(this.getUf());
	}
}
