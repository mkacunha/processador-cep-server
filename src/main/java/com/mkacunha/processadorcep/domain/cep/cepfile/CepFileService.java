package com.mkacunha.processadorcep.domain.cep.cepfile;

import com.mkacunha.processadorcep.domain.cep.cepfile.processor.CepFileProcessor;
import com.mkacunha.processadorcep.domain.historico.Historico;
import com.mkacunha.processadorcep.domain.historico.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class CepFileService {

	private final HistoricoService historicoService;

	private final ExecutorService executorService;

	@Autowired
	public CepFileService(HistoricoService historicoService, ExecutorService executorService) {
		this.historicoService = historicoService;
		this.executorService = executorService;
	}

	public Historico process(CepFile cepFile) {
		Historico historico = historicoService.createAndSaveHistorico(cepFile.getToken(), cepFile.getName());
		executorService.execute(CepFileProcessor.of(executorService, cepFile));
		return historico;
	}
}
