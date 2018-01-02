package com.mkacunha.processadorcep.domain.cidade;

import com.mkacunha.processadorcep.infrastructure.exception.PreparedStatementConsumerException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer.CommandConsumerSupply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class CidadeSelectOneConsumer implements CommandConsumerSupply<Cidade> {

	private CidadeSelectOneConsumer() {
	}

	static CommandConsumerSupply<Cidade> of() {
		return new CidadeSelectOneConsumer();
	}

	@Override
	public Cidade apply(PreparedStatement preparedStatement) {
		try {
			final ResultSet resultSet = preparedStatement.getResultSet();
			if (resultSet.next()) {
				return Cidade.builder().resultSet(resultSet).build();
			}
			return null;
		} catch (SQLException e) {
			throw new PreparedStatementConsumerException(e);
		}
	}
}
