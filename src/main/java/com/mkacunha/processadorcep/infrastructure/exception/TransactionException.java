package com.mkacunha.processadorcep.infrastructure.exception;

import java.sql.SQLException;

public class TransactionException extends Exception {

	public TransactionException(String message) {
		super(message);
	}

	public TransactionException(SQLException e) {
		super(e);
	}
}
