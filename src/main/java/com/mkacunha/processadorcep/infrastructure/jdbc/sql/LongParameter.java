package com.mkacunha.processadorcep.infrastructure.jdbc.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.NUMERIC;

class LongParameter extends Parameter<Long> {

	public LongParameter(int index, Long parameter) {
		super(index, parameter);
	}

	@Override
	protected void acceptNonNull(PreparedStatement stmt) throws SQLException {
		stmt.setLong(index, value);
	}

	@Override
	protected void acceptNull(PreparedStatement stmt) throws SQLException {
		stmt.setNull(index, NUMERIC);
	}
}
