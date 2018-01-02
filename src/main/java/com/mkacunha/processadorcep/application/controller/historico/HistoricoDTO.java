package com.mkacunha.processadorcep.application.controller.historico;

import com.mkacunha.processadorcep.domain.historico.HistoricoStatus;

import java.util.Date;

public class HistoricoDTO {

	private String token;

	private String arquivo;

	private HistoricoStatus status = HistoricoStatus.PROCESSANDO;

	private Date data;

	private Integer quantidadeResgitrosNovos = 0;

	private Integer quantidadeRegistrosAlterados = 0;

	private Integer quantidadeRegistrosComErros = 0;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String aquivo) {
		this.arquivo = aquivo;
	}

	public HistoricoStatus getStatus() {
		return status;
	}

	public void setStatus(HistoricoStatus status) {
		this.status = status;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getQuantidadeResgitrosNovos() {
		return quantidadeResgitrosNovos;
	}

	public void setQuantidadeResgitrosNovos(Integer quantidadeResgitrosNovos) {
		this.quantidadeResgitrosNovos = quantidadeResgitrosNovos;
	}

	public Integer getQuantidadeRegistrosAlterados() {
		return quantidadeRegistrosAlterados;
	}

	public void setQuantidadeRegistrosAlterados(Integer quantidadeRegistrosAlterados) {
		this.quantidadeRegistrosAlterados = quantidadeRegistrosAlterados;
	}

	public Integer getQuantidadeRegistrosComErros() {
		return quantidadeRegistrosComErros;
	}

	public void setQuantidadeRegistrosComErros(Integer quantidadeRegistrosComErros) {
		this.quantidadeRegistrosComErros = quantidadeRegistrosComErros;
	}

	public Integer getQuantidadeRegistrosProcessados() {
		return this.quantidadeResgitrosNovos + this.quantidadeRegistrosAlterados + this.quantidadeRegistrosComErros;
	}

}
