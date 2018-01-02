package com.mkacunha.processadorcep.infrastructure.jdbc.sql;

import com.mkacunha.processadorcep.infrastructure.exception.AcceptParameterException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.util.Objects.nonNull;
import static org.apache.log4j.Logger.getLogger;

abstract class Parameter<T> {

	private static final Logger LOGGER = getLogger(Parameter.class);

	protected int index;

	protected T value;

	public Parameter(int index, T value) {
		this.index = index;
		this.value = value;
	}

	protected abstract void acceptNonNull(PreparedStatement stmt) throws SQLException;

	protected abstract void acceptNull(PreparedStatement stmt) throws SQLException;

	public void accept(PreparedStatement stmt) {
		try {
			if (nonNull(value)) {
				acceptNonNull(stmt);
			} else {
				acceptNull(stmt);
			}
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new AcceptParameterException(e);
		}
	}

}
