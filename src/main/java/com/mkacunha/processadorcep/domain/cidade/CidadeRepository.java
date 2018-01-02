package com.mkacunha.processadorcep.domain.cidade;

import com.mkacunha.processadorcep.infrastructure.entidade.EntidadeInsertConsumer;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.Command;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

import static com.mkacunha.processadorcep.infrastructure.jdbc.sql.Command.init;
import static com.mkacunha.processadorcep.infrastructure.uitils.Optionals.newOptionalOrEmptyIfNull;

@Repository
public class CidadeRepository {

	public static final String SQL_INSERT = "insert into cidade (ibge, nome, uf) values (?, ?, ?)";

	public static final String SQL_SELECT_BY_ID = "select id, ibge, nome, uf from cidade where id = ?";

	public static final String SQL_SELECT_BY_IBGE = "select id, ibge, nome, uf from cidade where ibge = ?";

	private final CommandExecutor executor;

	@Autowired
	public CidadeRepository(CommandExecutor executor) {
		this.executor = executor;
	}

	public Cidade insert(Cidade cidade) throws TransactionException {
		final Command command = init(SQL_INSERT)
				.parameter(cidade.getIbge())
				.parameter(cidade.getNome())
				.parameter(cidade.getUf())
				.command();
		executor.execute(command, EntidadeInsertConsumer.of(cidade));
		return cidade;
	}

	public Optional<Cidade> findOne(Long id) throws SQLException, TransactionException {
		return executeCommandFindOne(init(SQL_SELECT_BY_ID).parameter(id).command());
	}

	public Optional<Cidade> findByIbge(String ibge) throws SQLException, TransactionException {
		return executeCommandFindOne(init(SQL_SELECT_BY_IBGE).parameter(ibge).command());
	}

	private Optional<Cidade> executeCommandFindOne(Command command) throws TransactionException {
		return newOptionalOrEmptyIfNull(executor.execute(command, CidadeSelectOneConsumer.of()));
	}
}
