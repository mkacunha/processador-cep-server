package com.mkacunha.processadorcep.domain.historico.log;

import com.mkacunha.processadorcep.infrastructure.exception.PreparedStatementConsumerException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer.CommandConsumerSupply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class HistoricoLogSelectAllConsumer implements CommandConsumerSupply<List<HistoricoLog>> {

	private HistoricoLogSelectAllConsumer() {
	}

	public static CommandConsumerSupply<List<HistoricoLog>> of() {
		return new HistoricoLogSelectAllConsumer();
	}

	@Override
	public List<HistoricoLog> apply(PreparedStatement preparedStatement) {
		List<HistoricoLog> logs = newArrayList();
		buildLogs(preparedStatement, logs);
		return logs;
	}

	private void buildLogs(PreparedStatement preparedStatement, List<HistoricoLog> logs) {
		try {
			final ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()) {
				logs.add(HistoricoLog.builder().resultSet(resultSet).build());
			}
		} catch (SQLException e) {
			throw new PreparedStatementConsumerException(e);
		}
	}
}
