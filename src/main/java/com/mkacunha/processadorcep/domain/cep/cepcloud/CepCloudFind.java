package com.mkacunha.processadorcep.domain.cep.cepcloud;

import com.mkacunha.processadorcep.infrastructure.exception.CepCloudNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Component
public class CepCloudFind {

	public static final String CEP_NOT_FOUND = "CEP %s não encontrado";

	public static final String SERVIDOR_INDISPONIVEL = "Não foi possivel conectar ao servidor, indisponível";

	private static final Logger LOGGER = Logger.getLogger(CepCloudFind.class);

	private final static String URL = "https://viacep.com.br/ws/%s/json/";

	private final RestTemplate restTemplate;

	@Autowired
	public CepCloudFind(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public CepCloud find(String cep) {
		try {
			final CepCloud cepCloud = restTemplate.getForObject(format(URL, cep), CepCloud.class);

			if (!cepCloud.containsCep()) {
				throw new CepCloudNotFoundException(format(CEP_NOT_FOUND, cep));
			}

			return cepCloud;
		} catch (ResourceAccessException e) {
			LOGGER.error(e);
			throw new CepCloudNotFoundException(SERVIDOR_INDISPONIVEL);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new CepCloudNotFoundException(format(CEP_NOT_FOUND, cep), e);
		}
	}
}
