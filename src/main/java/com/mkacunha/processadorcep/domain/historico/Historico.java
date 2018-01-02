package com.mkacunha.processadorcep.domain.historico;

import com.mkacunha.processadorcep.infrastructure.entidade.Entidade;

import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;
import static com.mkacunha.processadorcep.domain.historico.HistoricoStatus.PROCESSADO;
import static com.mkacunha.processadorcep.domain.historico.HistoricoStatus.PROCESSANDO;
import static java.util.Objects.isNull;

public class Historico extends Entidade {

	public static final String NAO_PREMITIDO_ENCERRAR = "Não é permitido encerrar histórico com status diferente de 'PROCESSANDO'.";

	private String token;

	private String arquivo;

	private HistoricoStatus status = PROCESSANDO;

	private Date data;

	private Integer quantidadeRegistrosNovos = 0;

	private Integer quantidadeRegistrosAlterados = 0;

	private Integer quantidadeRegistrosComErros = 0;

	public Historico(String token, String arquivo) {
		this.token = token;
		this.arquivo = arquivo;
		this.data = new Date();
	}

	public Historico(HistoricoBuilder builder) {
		super.id = builder.getId();
		this.token = builder.getToken();
		this.arquivo = builder.getArquivo();
		this.status = builder.getStatus();
		this.data = builder.getData();
		this.quantidadeRegistrosNovos = builder.getQuantidadeResgitrosNovos();
		this.quantidadeRegistrosAlterados = builder.getQuantidadeRegistrosAlterados();
		this.quantidadeRegistrosComErros = builder.getQuantidadeRegistrosComErros();
	}

	public static HistoricoBuilder builder() {
		return new HistoricoBuilder();
	}

	public void processado(Integer registrosNovos, Integer registrosAlterados, Integer registrosComErros) {
		checkArgument(PROCESSANDO.equals(status), NAO_PREMITIDO_ENCERRAR);
		this.status = PROCESSADO;
		this.quantidadeRegistrosNovos = registrosNovos;
		this.quantidadeRegistrosAlterados = registrosAlterados;
		this.quantidadeRegistrosComErros = registrosComErros;
	}

	public boolean compareGreaterNumberOfNewRegistrationsTo(Historico other) {
		return isNull(other) || this.quantidadeRegistrosNovos.compareTo(other.getQuantidadeRegistrosNovos()) >= 0;
	}

	public boolean compareGreaterNumberOfUpdatedTo(Historico other) {
		return isNull(other) ||
				this.quantidadeRegistrosAlterados.compareTo(other.getQuantidadeRegistrosAlterados()) >= 0;
	}

	public boolean compareGreaterNumberOfErrorsTo(Historico other) {
		return isNull(other) || this.quantidadeRegistrosComErros.compareTo(other.getQuantidadeRegistrosComErros()) >= 0;
	}

	public String getToken() {
		return token;
	}

	public String getArquivo() {
		return arquivo;
	}

	public HistoricoStatus getStatus() {
		return status;
	}

	public Date getData() {
		return data;
	}

	public Integer getQuantidadeRegistrosNovos() {
		return quantidadeRegistrosNovos;
	}

	public Integer getQuantidadeRegistrosAlterados() {
		return quantidadeRegistrosAlterados;
	}

	public Integer getQuantidadeRegistrosComErros() {
		return quantidadeRegistrosComErros;
	}

	@Override
	public String toString() {
		return "Historico{" +
				"token='" + token + '\'' +
				", status=" + status +
				", data=" + data +
				", quantidadeRegistrosNovos=" + quantidadeRegistrosNovos +
				", quantidadeRegistrosAlterados=" + quantidadeRegistrosAlterados +
				", quantidadeRegistrosComErros=" + quantidadeRegistrosComErros +
				'}';
	}
}
