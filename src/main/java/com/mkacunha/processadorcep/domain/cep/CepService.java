package com.mkacunha.processadorcep.domain.cep;

import com.mkacunha.processadorcep.infrastructure.exception.ServiceException;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.Transaction;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class CepService {

	public static final String MSG_ERRO_AO_SALVAR_CEP = "Ocorreu um erro na tentativa de salvar o CEP %s.";

	private static final Logger LOGGER = Logger.getLogger(CepService.class);

	private static final String MSG_ERRO_AO_BUSCAR_CEP = "Ocorreu um erro ao buscar por CEP %s";

	private final Transaction transaction;

	private final CepRepository repotiroty;

	public CepService(Transaction transaction, CepRepository repotiroty) {
		this.transaction = transaction;
		this.repotiroty = repotiroty;
	}

	public void save(Cep cep) {
		try {
			transaction.begin();
			createOrUpdate(cep);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			LOGGER.error(e);
			throw new ServiceException(MSG_ERRO_AO_SALVAR_CEP, cep.getCep());
		}
	}

	private Cep createOrUpdate(Cep cep) throws TransactionException {
		if (isNull(cep.getId())) {
			return repotiroty.insert(cep);
		}
		return repotiroty.update(cep);
	}

	public Optional<Cep> findByCep(String cep) {
		try {
			transaction.begin();
			return repotiroty.findByCep(cep);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new ServiceException(MSG_ERRO_AO_BUSCAR_CEP, cep);
		} finally {
			transaction.finnaly();
		}
	}

}
