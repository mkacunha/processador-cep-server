package com.mkacunha.processadorcep.application.controller.historico;

import com.mkacunha.processadorcep.domain.historico.Historico;
import com.mkacunha.processadorcep.domain.historico.HistoricoService;
import com.mkacunha.processadorcep.infrastructure.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/historicos")
public class HistoricoController {

	private final HistoricoService service;

	private final HistoricoTranslator translator;

	@Autowired
	public HistoricoController(HistoricoService service,
			HistoricoTranslator translator) {
		this.service = service;
		this.translator = translator;
	}

	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
	public ResponseEntity upload(@PathVariable("token") String token) {
		try {
			Historico historico = service.findHistoricoByToken(token);
			return ResponseEntity.ok(translator.apply(historico));
		} catch (Exception e) {
			return Response.badRequest(e.getMessage());
		}
	}
}
