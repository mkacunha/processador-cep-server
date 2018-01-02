package com.mkacunha.processadorcep.domain.historico.log;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricoLogBuilder {

	private Long id;

	private String cep;

	private HistoricoLogStatus status;

	private String log;

	HistoricoLogBuilder() {
	}

	public HistoricoLogBuilder resultSet(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong("id");
		this.cep = resultSet.getString("cep");
		this.status = HistoricoLogStatus.valueOf(resultSet.getString("status"));
		this.log = resultSet.getString("log");
		return this;
	}

	public HistoricoLogBuilder cep(String cep) {
		this.cep = cep;
		return this;
	}

	public HistoricoLogBuilder status(HistoricoLogStatus status) {
		this.status = status;
		return this;
	}

	public HistoricoLogBuilder log(String log) {
		this.log = log;
		return this;
	}

	public HistoricoLog build() {
		return new HistoricoLog(this);
	}

	public Long getId() {
		return id;
	}

	public String getCep() {
		return cep;
	}

	public HistoricoLogStatus getStatus() {
		return status;
	}

	public String getLog() {
		return log;
	}
}
