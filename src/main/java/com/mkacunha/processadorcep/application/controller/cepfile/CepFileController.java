package com.mkacunha.processadorcep.application.controller.cepfile;

import com.mkacunha.processadorcep.application.controller.historico.HistoricoTranslator;
import com.mkacunha.processadorcep.domain.cep.cepfile.CepFile;
import com.mkacunha.processadorcep.domain.historico.Historico;
import com.mkacunha.processadorcep.domain.cep.cepfile.CepFileService;
import com.mkacunha.processadorcep.infrastructure.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/cepfiles")
public class CepFileController {

	private final CepFileTranslator cepFileTranslator;

	private final HistoricoTranslator historicoTranslator;

	private final CepFileService cepFileService;

	@Autowired
	public CepFileController(CepFileTranslator cepFileTranslator, HistoricoTranslator historicoTranslator,
			CepFileService taskQueueService) {
		this.cepFileTranslator = cepFileTranslator;
		this.historicoTranslator = historicoTranslator;
		this.cepFileService = taskQueueService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
		try {
			CepFile cepFile = cepFileTranslator.apply(file);
			Historico historico = cepFileService.process(cepFile);
			return Response.ok(historicoTranslator.apply(historico));
		} catch (Exception e) {
			return Response.badRequest(e.getMessage());
		}
	}

}
