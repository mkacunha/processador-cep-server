package com.mkacunha.processadorcep.infrastructure.entidade;

import com.mkacunha.processadorcep.infrastructure.exception.PreparedStatementConsumerException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer.CommandConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class EntidadeInsertConsumer implements CommandConsumer {

	private final Entidade entidade;

	private EntidadeInsertConsumer(Entidade entidade) {
		this.entidade = entidade;
	}

	public static Consumer<PreparedStatement> of(Entidade entidade) {
		return new EntidadeInsertConsumer(entidade);
	}

	@Override
	public void accept(PreparedStatement preparedStatement) {
		try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
			if (keys.next()) {
				entidade.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			throw new PreparedStatementConsumerException(e);
		}
	}
}
