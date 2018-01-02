package com.mkacunha.processadorcep.domain.historico;

import com.mkacunha.processadorcep.infrastructure.exception.PreparedStatementConsumerException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer.CommandConsumerSupply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricoSelectOneConsumer implements CommandConsumerSupply<Historico> {

	public static CommandConsumerSupply<Historico> of() {
		return new HistoricoSelectOneConsumer();
	}

	@Override
	public Historico apply(PreparedStatement preparedStatement) {
		try (ResultSet resultSet = preparedStatement.getResultSet()) {
			if (resultSet.next()) {
				return Historico.builder().resultSet(resultSet).build();
			}
			return null;
		} catch (SQLException e) {
			throw new PreparedStatementConsumerException(e);
		}
	}
}
