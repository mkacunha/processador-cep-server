package com.mkacunha.processadorcep.infrastructure.exception;

public class CepCloudNotFoundException extends RuntimeException {

	public CepCloudNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CepCloudNotFoundException(String message) {
		super(message);
	}
}
