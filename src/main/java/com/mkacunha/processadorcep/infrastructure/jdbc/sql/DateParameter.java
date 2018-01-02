package com.mkacunha.processadorcep.infrastructure.jdbc.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.DATE;

class DateParameter extends Parameter<Date> {

	public DateParameter(int index, Date value) {
		super(index, value);
	}

	@Override
	protected void acceptNonNull(PreparedStatement stmt) throws SQLException {
		stmt.setDate(index, value);
	}

	@Override
	protected void acceptNull(PreparedStatement stmt) throws SQLException {
		stmt.setNull(index, DATE);
	}

}
