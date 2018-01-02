package com.mkacunha.processadorcep.domain.cep;

import com.mkacunha.processadorcep.domain.cidade.Cidade;
import com.mkacunha.processadorcep.domain.cidade.CidadeRepository;
import com.mkacunha.processadorcep.infrastructure.exception.CidadeNotFoundException;
import com.mkacunha.processadorcep.infrastructure.exception.PreparedStatementConsumerException;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer.CommandConsumerSupply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mkacunha.processadorcep.infrastructure.bean.ApplicationContextBean.getBean;

class CepSelectOneConsumer implements CommandConsumerSupply<Cep> {

	private final CidadeRepository cidadeRepository;

	private CepSelectOneConsumer() {
		this.cidadeRepository = getBean(CidadeRepository.class);
	}

	static CommandConsumerSupply<Cep> of() {
		return new CepSelectOneConsumer();
	}

	@Override
	public Cep apply(PreparedStatement preparedStatement) {
		try {
			final ResultSet resultSet = preparedStatement.getResultSet();
			if (resultSet.next()) {
				final long cidade_fk = resultSet.getLong("cidade_fk");
				return Cep.builder().resultSet(resultSet).cidade(buscarCidade(cidade_fk)).build();
			}
			return null;
		} catch (Exception e) {
			throw new PreparedStatementConsumerException(e);
		}
	}

	private Cidade buscarCidade(Long id) throws SQLException, TransactionException {
		return cidadeRepository.findOne(id).orElseThrow(new CidadeNotFoundException());
	}
}
