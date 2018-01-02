package com.mkacunha.processadorcep.domain.cep.cepfile.processor;

import com.mkacunha.processadorcep.domain.cep.cepfile.CepFile;
import com.mkacunha.processadorcep.domain.historico.HistoricoService;
import com.mkacunha.processadorcep.domain.historico.log.HistoricoLog;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.collect.Lists.newArrayList;
import static com.mkacunha.processadorcep.infrastructure.bean.ApplicationContextBean.getBean;

public class CepFileProcessor implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(CepFileProcessor.class);

	private final HistoricoService historicoService;

	private final CepFile cepFile;

	private final ExecutorService executorService;

	private List<Future<HistoricoLog>> futures;

	private List<HistoricoLog> logs;

	private AtomicInteger registrosNovos;

	private AtomicInteger registrosAlterados;

	private AtomicInteger resgistrosComErros;

	private CepFileProcessor(ExecutorService executorService, CepFile cepFile) {
		initContadores();
		this.executorService = executorService;
		this.cepFile = cepFile;
		this.historicoService = getBean(HistoricoService.class);
		this.futures = newArrayList();
		this.logs = newArrayList();
	}

	public static Runnable of(ExecutorService executorService, CepFile cepFile) {
		return new CepFileProcessor(executorService, cepFile);
	}

	private void initContadores() {
		this.registrosNovos = new AtomicInteger();
		this.registrosAlterados = new AtomicInteger();
		this.resgistrosComErros = new AtomicInteger();
	}

	@Override
	public void run() {
		LOGGER.info(String.format("==> Iniciou o processamento do arquivo %s", cepFile.getName()));
		cepFile.getCeps().forEach(cep -> processCep(cep));
		futures.forEach(this::getResultFuture);
		historicoService.closeProcessing(logs, cepFile.getToken(), registrosNovos.get(), registrosAlterados.get(),
				resgistrosComErros.get());
	}

	private void processCep(String cep) {
		final Callable<HistoricoLog> taskCepSave = CepProcessor
				.of(cep, registrosNovos, registrosAlterados, resgistrosComErros);
		futures.add(executorService.submit(taskCepSave));
	}

	private void getResultFuture(Future<HistoricoLog> future) {
		try {
			logs.add(future.get());
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

}
