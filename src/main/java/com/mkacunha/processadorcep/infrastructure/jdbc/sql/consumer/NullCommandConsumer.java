package com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer;

import java.sql.PreparedStatement;

public class NullCommandConsumer implements CommandConsumer {

	private NullCommandConsumer() {

	}

	public static NullCommandConsumer of() {
		return new NullCommandConsumer();
	}

	@Override
	public void accept(PreparedStatement preparedStatement) {
		// Implemetação Null Object Pattern
	}
}
