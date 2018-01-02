package com.mkacunha.processadorcep.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController(value = "api/version")
public class Version {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> version() {
		return ResponseEntity.ok("1.0");
	}
}
