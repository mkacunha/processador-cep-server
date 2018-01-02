package com.mkacunha.processadorcep.infrastructure.jdbc.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.INTEGER;

class IntegerParameter extends Parameter<Integer> {

	public IntegerParameter(int index, Integer value) {
		super(index, value);
	}

	@Override
	protected void acceptNonNull(PreparedStatement stmt) throws SQLException {
		stmt.setInt(index, value);
	}

	@Override
	protected void acceptNull(PreparedStatement stmt) throws SQLException {
		stmt.setNull(index, INTEGER);
	}
}
