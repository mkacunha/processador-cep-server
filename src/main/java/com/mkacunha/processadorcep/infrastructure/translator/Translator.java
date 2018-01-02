package com.mkacunha.processadorcep.infrastructure.translator;

public interface Translator<F, T> {

	T translate(F f);
}
