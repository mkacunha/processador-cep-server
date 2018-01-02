package com.mkacunha.processadorcep.infrastructure.exception;

import java.util.function.Supplier;

public class ServiceException extends RuntimeException implements Supplier<ServiceException> {

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Object... args) {
		this(String.format(message, args));
	}

	@Override
	public ServiceException get() {
		return this;
	}
}
