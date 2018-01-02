package com.mkacunha.processadorcep.domain.historico;

import com.mkacunha.processadorcep.infrastructure.entidade.EntidadeInsertConsumer;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.Transaction;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.Command;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.CommandExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static com.mkacunha.processadorcep.infrastructure.jdbc.sql.Command.init;
import static java.util.Objects.nonNull;

@Repository
public class HistoricoRepository {

	public static final String SQL_INSERT = "insert into historico (token, arquivo, status, qt_registros_novos, qt_registros_alterados, qt_registros_com_erros, dt_historico) values (?, ?, ?, ?, ?, ?, ?)";

	public static final String SQL_UPDATE = "update historico set status = ?, qt_registros_novos = ?, qt_registros_alterados = ?, qt_registros_com_erros = ? where id = ?";

	public static final String SQL_SELECT_BY_TOKEN = "select id, token, arquivo, status, qt_registros_novos, qt_registros_alterados, qt_registros_com_erros, dt_historico from historico where token = ?";

	public static final String SQL_SELECT_ALL = "select id, token, arquivo, status, qt_registros_novos, qt_registros_alterados, qt_registros_com_erros, dt_historico from historico";

	public static final String HISTORICO_NOT_FOUND = "Histórico não encontrado.";

	private final Transaction transaction;

	private final CommandExecutor executor;

	public HistoricoRepository(Transaction transaction,
			CommandExecutor executor) {
		this.transaction = transaction;
		this.executor = executor;
	}

	public Historico insert(Historico historico) throws TransactionException {
		Command command = init(SQL_INSERT)
				.parameter(historico.getToken())
				.parameter(historico.getArquivo())
				.parameter(historico.getStatus().toString())
				.parameter(historico.getQuantidadeRegistrosNovos())
				.parameter(historico.getQuantidadeRegistrosAlterados())
				.parameter(historico.getQuantidadeRegistrosComErros())
				.parameter(new Date(historico.getData().getTime()))
				.command();

		executor.execute(command, EntidadeInsertConsumer.of(historico));
		return historico;
	}

	public Historico update(Historico historico) throws TransactionException {
		Command command = init(SQL_UPDATE)
				.parameter(historico.getStatus().toString())
				.parameter(historico.getQuantidadeRegistrosNovos())
				.parameter(historico.getQuantidadeRegistrosAlterados())
				.parameter(historico.getQuantidadeRegistrosComErros())
				.parameter(historico.getId())
				.command();
		executor.execute(command);
		return historico;
	}

	public Optional<Historico> findByToken(String token) throws TransactionException {
		Command command = init(SQL_SELECT_BY_TOKEN).parameter(token).command();
		Historico historico = executor.execute(command, HistoricoSelectOneConsumer.of());
		if (nonNull(historico)) {
			return Optional.of(historico);
		}
		return Optional.empty();
	}

	public List<Historico> findAll() throws TransactionException {
		return executor.execute(init(SQL_SELECT_ALL).command(), HistoricoSelectListConsumer.of());
	}
}
