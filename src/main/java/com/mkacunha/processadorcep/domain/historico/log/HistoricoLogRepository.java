package com.mkacunha.processadorcep.domain.historico.log;

import com.mkacunha.processadorcep.infrastructure.entidade.EntidadeInsertConsumer;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.Command;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mkacunha.processadorcep.infrastructure.jdbc.sql.Command.init;

@Repository
public class HistoricoLogRepository {

	public static final String SQL_INSERT = "insert into historicolog(cep, status, log, historico_fk) values(?, ?, ?, ?)";

	public static final String SQL_SELECT_ALL_WITHOUT_HISTORICO = "select id, cep, status, log from processadorcep.historicolog";

	private final CommandExecutor executor;

	@Autowired
	public HistoricoLogRepository(CommandExecutor executor) {
		this.executor = executor;
	}

	public void insert(List<HistoricoLog> logs) throws TransactionException {
		for (HistoricoLog log : logs) {
			insert(log);
		}
	}

	public HistoricoLog insert(HistoricoLog log) throws TransactionException {
		final Command command = init(SQL_INSERT)
				.parameter(log.getCep())
				.parameter(log.getStatus().toString())
				.parameter(log.getLog())
				.parameter(log.getHistorico().getId())
				.command();
		executor.execute(command, EntidadeInsertConsumer.of(log));
		return log;
	}

	public List<HistoricoLog> findAllWithoutHistorico() throws TransactionException {
		return executor.execute(init(SQL_SELECT_ALL_WITHOUT_HISTORICO).command(), HistoricoLogSelectAllConsumer.of());
	}
}
