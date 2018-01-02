package com.mkacunha.processadorcep.infrastructure.jdbc;

import com.google.common.collect.Maps;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionRollbackException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static org.apache.log4j.Logger.getLogger;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(value = SCOPE_SINGLETON)
public class Transaction {

	public static final String NAO_EXISTE_TRASACAO_ABERTA = "Não existe transação aberta para a thread atual.";

	public static final String JA_EXISTE_TRANSACAO_ABERTA = "Já existe uma transação aberta para a thread atual.";

	private static final Logger LOGGER = getLogger(Transaction.class);

	private PoolConnection poolConnection;

	private Map<Long, Connection> connectionsOpened = Maps.newHashMap();

	@Autowired
	public Transaction(PoolConnection poolConnection) {
		this.poolConnection = poolConnection;
	}

	public void begin() throws TransactionException {
		long currentThread = getCurrentThread();
		if (connectionsOpened.containsKey(currentThread)) {
			throw new TransactionException(JA_EXISTE_TRANSACAO_ABERTA);
		}
		Connection connection = this.poolConnection.getConnection();
		connectionsOpened.put(currentThread, connection);
	}

	public void commit() throws TransactionException {
		try {
			getCurrentConnection().commit();
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new TransactionException(e);
		} finally {
			leaveCurrentConnection();
		}
	}

	public void finnaly() {
		leaveCurrentConnection();
	}

	public void rollback() {
		try {
			getCurrentConnection().rollback();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new TransactionRollbackException(e);
		} finally {
			leaveCurrentConnection();
		}
	}

	private long getCurrentThread() {
		return Thread.currentThread().getId();
	}

	public Connection getCurrentConnection() throws TransactionException {
		long currentThread = getCurrentThread();

		if (!connectionsOpened.containsKey(currentThread)) {
			throw new TransactionException(NAO_EXISTE_TRASACAO_ABERTA);
		}

		return connectionsOpened.get(currentThread);
	}

	private void leaveCurrentConnection() {
		try {
			long currentThread = getCurrentThread();
			if (connectionsOpened.containsKey(currentThread)) {
				Connection connection = connectionsOpened.get(currentThread);
				connection.close();
				connectionsOpened.remove(currentThread);
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

}
