package com.mkacunha.processadorcep.domain.cep;

import com.google.common.base.MoreObjects;
import com.mkacunha.processadorcep.infrastructure.entidade.EntidadeInsertConsumer;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.Command;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mkacunha.processadorcep.infrastructure.jdbc.sql.Command.init;
import static com.mkacunha.processadorcep.infrastructure.uitils.Optionals.newOptionalOrEmptyIfNull;

@Repository
public class CepRepository {

	public static final String SQL_INSERT = "insert into cep (cep, logradouro, bairro, complemento, numero, cidade_fk) values (?, ?, ?, ?, ?, ?)";

	public static final String SQL_UPDATE = "update cep set logradouro = ?, bairro = ?, complemento = ?, numero = ?, cidade_fk = ? where id = ?";

	public static final String SQL_FIND_ONE = "select id, cep, logradouro, bairro, complemento, numero, cidade_fk from cep where cep = ?";

	private final CommandExecutor executor;

	@Autowired
	public CepRepository(CommandExecutor executor) {
		this.executor = executor;
	}

	public Cep insert(Cep cep) throws TransactionException {
		final Command command = init(SQL_INSERT)
				.parameter(cep.getCep())
				.parameter(cep.getLogradouro())
				.parameter(cep.getBairro())
				.parameter(cep.getComplemento())
				.parameter(cep.getNumero())
				.parameter(cep.getCidadeFk())
				.command();
		executor.execute(command, EntidadeInsertConsumer.of(cep));
		return cep;
	}

	public Cep update(Cep cep) throws TransactionException {
		final Command command = init(SQL_UPDATE)
				.parameter(cep.getLogradouro())
				.parameter(cep.getBairro())
				.parameter(cep.getComplemento())
				.parameter(cep.getNumero())
				.parameter(cep.getCidade().getId())
				.parameter(cep.getId())
				.command();
		executor.execute(command);
		return cep;
	}

	public Optional<Cep> findByCep(String cep) throws TransactionException {
		final Cep cepFind = executor.execute(init(SQL_FIND_ONE).parameter(cep).command(), CepSelectOneConsumer.of());
		return newOptionalOrEmptyIfNull(cepFind);

	}
}
