package com.mkacunha.processadorcep.infrastructure.uitils;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class Optionals {

	private Optionals() {
	}

	public static <T> Optional<T> newOptionalOrEmptyIfNull(T value) {
		if (nonNull(value)) {
			return of(value);
		}
		return empty();
	}
}
