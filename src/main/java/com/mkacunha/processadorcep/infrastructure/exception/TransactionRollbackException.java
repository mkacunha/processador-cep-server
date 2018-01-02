package com.mkacunha.processadorcep.infrastructure.exception;

public class TransactionRollbackException extends RuntimeException {

	public TransactionRollbackException(Throwable cause) {
		super(cause);
	}
}
