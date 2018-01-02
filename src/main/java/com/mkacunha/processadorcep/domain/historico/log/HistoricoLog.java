package com.mkacunha.processadorcep.domain.historico.log;

import com.mkacunha.processadorcep.domain.historico.Historico;
import com.mkacunha.processadorcep.infrastructure.entidade.Entidade;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class HistoricoLog extends Entidade {

	private String cep;

	private HistoricoLogStatus status;

	private String log;

	private Historico historico;

	public HistoricoLog(HistoricoLogBuilder builder) {
		this.id = builder.getId();
		this.cep = builder.getCep();
		this.status = builder.getStatus();
		this.log = builder.getLog();
	}

	public static HistoricoLogBuilder builder() {
		return new HistoricoLogBuilder();
	}

	public void add(Historico historico) {
		checkArgument(nonNull(historico));
		checkArgument(isNull(this.historico));
		this.historico = historico;
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

	public Historico getHistorico() {
		return historico;
	}
}
