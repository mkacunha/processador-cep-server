package com.mkacunha.processadorcep.infrastructure.exception;

import java.util.function.Supplier;

public class CidadeNotFoundException extends RuntimeException implements Supplier<CidadeNotFoundException> {

	public static final String CIDADE_NOT_FOUND = "Cidade não econtrada.";

	public CidadeNotFoundException() {
		super(CIDADE_NOT_FOUND);
	}

	@Override
	public CidadeNotFoundException get() {
		return this;
	}
}
