package com.mkacunha.processadorcep.infrastructure.jdbc.sql;

import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.Transaction;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer.CommandConsumerSupply;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer.NullCommandConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static org.apache.log4j.Logger.getLogger;

@Component
public class CommandExecutor {

	private static final Logger LOGGER = getLogger(CommandExecutor.class);

	private Transaction transaction;

	@Autowired
	public CommandExecutor(Transaction transaction) {
		this.transaction = transaction;
	}

	public void execute(Command command) throws TransactionException {
		execute(command, NullCommandConsumer.of());
	}

	public void execute(Command command, Consumer<PreparedStatement> consumer) throws TransactionException {
		try {
			PreparedStatement stmt = createAndExecutePreparedStatement(command);
			consumer.accept(stmt);
			stmt.close();
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new TransactionException(e);
		}
	}

	public <R> R execute(Command command, CommandConsumerSupply<R> suply) throws TransactionException {
		try {
			checkNotNull(suply);
			PreparedStatement stmt = createAndExecutePreparedStatement(command);
			R result = suply.apply(stmt);
			stmt.close();
			return result;
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new TransactionException(e);
		}
	}

	private PreparedStatement createAndExecutePreparedStatement(Command command)
			throws TransactionException, SQLException {
		Connection currentConnection = transaction.getCurrentConnection();
		PreparedStatement stmt = currentConnection.prepareStatement(command.getSql(), RETURN_GENERATED_KEYS);
		setParameters(command.getParameters(), stmt);
		stmt.execute();
		return stmt;
	}

	private void setParameters(List<Parameter> parameters, PreparedStatement stmt) {
		if (!CollectionUtils.isEmpty(parameters)) {
			parameters.forEach(parameter -> parameter.accept(stmt));
		}
	}
}
