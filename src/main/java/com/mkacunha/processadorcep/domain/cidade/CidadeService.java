package com.mkacunha.processadorcep.domain.cidade;

import com.mkacunha.processadorcep.infrastructure.exception.ServiceException;
import com.mkacunha.processadorcep.infrastructure.jdbc.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

	private final Transaction transaction;

	private final CidadeRepository cidadeRepository;

	@Autowired
	public CidadeService(Transaction transaction, CidadeRepository cidadeRepository) {
		this.transaction = transaction;
		this.cidadeRepository = cidadeRepository;
	}

	public Cidade findOrCreate(String ibge, String localidade, String uf) {
		try {
			transaction.begin();
			final Cidade cidade = cidadeRepository.findByIbge(ibge)
												  .orElseGet(() -> createWithoutTransaction(ibge, localidade, uf));
			transaction.commit();
			return cidade;
		} catch (Exception e) {
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		}
	}

	private Cidade createWithoutTransaction(String ibge, String localidade, String uf) {
		try {
			final Cidade cidade = cidadeRepository.insert(Cidade.builder().ibge(ibge).nome(localidade).uf(uf).build());
			return cidade;
		} catch (Exception e) {
			throw new ServiceException("Não foi possível criar a cidade %s: $s", localidade, e.getMessage());
		}
	}

}
