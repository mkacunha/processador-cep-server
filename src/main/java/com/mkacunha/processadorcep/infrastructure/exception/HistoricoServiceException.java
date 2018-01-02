package com.mkacunha.processadorcep.infrastructure.exception;

public class HistoricoServiceException extends RuntimeException {

	public HistoricoServiceException(String message) {
		super(message);
	}

	public HistoricoServiceException(Throwable cause) {
		super(cause);
	}
}
