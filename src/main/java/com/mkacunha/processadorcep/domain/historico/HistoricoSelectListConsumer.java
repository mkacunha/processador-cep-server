package com.mkacunha.processadorcep.domain.historico;

import com.mkacunha.processadorcep.infrastructure.exception.PreparedStatementConsumerException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer.CommandConsumerSupply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class HistoricoSelectListConsumer implements CommandConsumerSupply<List<Historico>> {

	private HistoricoSelectListConsumer() {
	}

	public static CommandConsumerSupply<List<Historico>> of() {
		return new HistoricoSelectListConsumer();
	}

	@Override
	public List<Historico> apply(PreparedStatement preparedStatement) {
		List<Historico> historicos = newArrayList();
		buildHistoricos(preparedStatement, historicos);
		return historicos;
	}

	private void buildHistoricos(PreparedStatement preparedStatement, List<Historico> historicos) {
		try {
			final ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()) {
				historicos.add(Historico.builder().resultSet(resultSet).build());
			}
		} catch (SQLException e) {
			throw new PreparedStatementConsumerException(e);
		}
	}
}
