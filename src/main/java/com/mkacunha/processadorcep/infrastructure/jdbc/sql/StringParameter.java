package com.mkacunha.processadorcep.infrastructure.jdbc.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.VARCHAR;

class StringParameter extends Parameter<String> {

	public StringParameter(int index, String parameter) {
		super(index, parameter);
	}

	@Override
	protected void acceptNonNull(PreparedStatement stmt) throws SQLException {
		stmt.setString(index, value);
	}

	@Override
	protected void acceptNull(PreparedStatement stmt) throws SQLException {
		stmt.setNull(index, VARCHAR);
	}
}
