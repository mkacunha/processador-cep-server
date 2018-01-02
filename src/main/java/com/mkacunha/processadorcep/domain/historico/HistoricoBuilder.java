package com.mkacunha.processadorcep.domain.historico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class HistoricoBuilder {

	private Long id;

	private String token;

	private HistoricoStatus status = HistoricoStatus.PROCESSANDO;

	private Date data;

	private Integer quantidadeResgitrosNovos = 0;

	private Integer quantidadeRegistrosAlterados = 0;

	private Integer quantidadeRegistrosComErros = 0;

	private String arquivo;

	HistoricoBuilder() {
	}

	public HistoricoBuilder resultSet(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong("id");
		this.token = resultSet.getString("token");
		this.arquivo = resultSet.getString("arquivo");
		this.status = HistoricoStatus.valueOf(resultSet.getString("status"));
		this.data = resultSet.getDate("dt_historico");
		this.quantidadeResgitrosNovos = resultSet.getInt("qt_registros_novos");
		this.quantidadeRegistrosAlterados = resultSet.getInt("qt_registros_alterados");
		this.quantidadeRegistrosComErros = resultSet.getInt("qt_registros_com_erros");
		return this;
	}

	public Historico build() {
		return new Historico(this);
	}

	public Long getId() {
		return id;
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

	public Integer getQuantidadeResgitrosNovos() {
		return quantidadeResgitrosNovos;
	}

	public Integer getQuantidadeRegistrosAlterados() {
		return quantidadeRegistrosAlterados;
	}

	public Integer getQuantidadeRegistrosComErros() {
		return quantidadeRegistrosComErros;
	}

}
