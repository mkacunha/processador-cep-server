package com.mkacunha.processadorcep.infrastructure.exception;

public class DefaultException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "Não foi possível executar a operação, verifique sua conexão com a internet e tente novamente.";

	public DefaultException() {
		super(DEFAULT_MESSAGE);
	}
}
