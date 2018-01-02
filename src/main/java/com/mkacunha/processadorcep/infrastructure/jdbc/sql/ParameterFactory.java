package com.mkacunha.processadorcep.infrastructure.jdbc.sql;

import java.sql.Date;

final class ParameterFactory {

	private ParameterFactory() {
	}

	static IntegerParameter of(int index, Integer value) {
		return new IntegerParameter(index, value);
	}

	static LongParameter of(int index, Long value) {
		return new LongParameter(index, value);
	}

	static StringParameter of(int index, String value) {
		return new StringParameter(index, value);
	}

	static DateParameter of(int index, Date value) {
		return new DateParameter(index, value);
	}
}
