package com.mkacunha.processadorcep.infrastructure.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public final class Response {

	private Response() {
	}

	public static <T> ResponseEntity<T> ok(T body) {
		return ResponseEntity.ok(body);
	}

	public static ResponseEntity badRequest(String message) {
		return ResponseEntity.badRequest()
							 .contentType(MediaType.TEXT_PLAIN)
							 .body(message);
	}
}
