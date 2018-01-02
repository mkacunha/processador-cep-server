package com.mkacunha.processadorcep.domain.cep.cepfile.processor;

import com.mkacunha.processadorcep.domain.cep.Cep;
import com.mkacunha.processadorcep.domain.cep.CepBuilder;
import com.mkacunha.processadorcep.domain.cep.CepService;
import com.mkacunha.processadorcep.domain.cidade.Cidade;
import com.mkacunha.processadorcep.domain.cidade.CidadeService;
import com.mkacunha.processadorcep.domain.historico.log.HistoricoLog;
import com.mkacunha.processadorcep.domain.historico.log.HistoricoLogBuilder;
import com.mkacunha.processadorcep.domain.cep.cepcloud.CepCloud;
import com.mkacunha.processadorcep.domain.cep.cepcloud.CepCloudFind;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mkacunha.processadorcep.domain.historico.log.HistoricoLogStatus.ERRO;
import static com.mkacunha.processadorcep.domain.historico.log.HistoricoLogStatus.SUCESSO;
import static com.mkacunha.processadorcep.infrastructure.bean.ApplicationContextBean.getBean;

class CepProcessor implements Callable<HistoricoLog> {

	public static final String LOG_SUCESSO = "Cep processado com sucesso";

	private static final Logger LOGGER = Logger.getLogger(CepProcessor.class);

	private final CepCloudFind cepCloudFind;

	private final CidadeService cidadeService;

	private final HistoricoLogBuilder logBuilder;

	private final CepService cepService;

	private final String cep;

	private AtomicInteger novosRegistros;

	private AtomicInteger registrosAlterados;

	private AtomicInteger resgistrosComErros;

	public CepProcessor(String cep, AtomicInteger novosRegistros,
			AtomicInteger registrosAlterados,
			AtomicInteger resgistrosComErros) {
		this.cepCloudFind = getBean(CepCloudFind.class);
		this.cidadeService = getBean(CidadeService.class);
		this.cepService = getBean(CepService.class);
		this.logBuilder = HistoricoLog.builder();
		this.cep = cep;
		this.novosRegistros = novosRegistros;
		this.registrosAlterados = registrosAlterados;
		this.resgistrosComErros = resgistrosComErros;
	}

	public static Callable<HistoricoLog> of(String cep, AtomicInteger novosRegistros,
			AtomicInteger registrosAlterados,
			AtomicInteger resgistrosComErros) {
		return new CepProcessor(cep, novosRegistros, registrosAlterados, resgistrosComErros);
	}

	@Override
	public HistoricoLog call() throws Exception {
		try {
			CepCloud cepCloud = cepCloudFind.find(cep);
			Cidade cidade = findOrCreateCidade(cepCloud);
			CepBuilder builder = initBuilder(cepCloud.getCep());
			Cep cep = build(builder, cepCloud, cidade);
			cepService.save(cep);
			logBuilder.status(SUCESSO).log(LOG_SUCESSO);
		} catch (Exception e) {
			logBuilder.status(ERRO).log(e.getMessage());
			LOGGER.error(e);
			resgistrosComErros.set(resgistrosComErros.get() + 1);
		}

		return logBuilder.cep(cep).build();
	}

	private CepBuilder initBuilder(String cep) {
		final Optional<Cep> cepFind = cepService.findByCep(cep);
		if (cepFind.isPresent()) {
			this.registrosAlterados.set(this.registrosAlterados.get() + 1);
			return cepFind.get().toBuilder();
		}

		this.novosRegistros.set(this.novosRegistros.get() + 1);
		return Cep.builder();
	}

	private Cidade findOrCreateCidade(CepCloud cepCloud) {
		if (cepCloud.containsCidade()) {
			return cidadeService.findOrCreate(cepCloud.getIbge(), cepCloud.getLocalidade(), cepCloud.getUf());
		}
		return null;
	}

	private Cep build(CepBuilder builder, CepCloud cepCloud, Cidade cidade) {
		return builder.cep(cepCloud.getCep()).logradouro(cepCloud.getLogradouro())
					  .bairro(cepCloud.getBairro()).complemento(cepCloud.getComplemento()).cidade(cidade)
					  .build();
	}
}
