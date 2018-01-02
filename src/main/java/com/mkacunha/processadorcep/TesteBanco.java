package com.mkacunha.processadorcep;

import com.mkacunha.processadorcep.domain.historico.Historico;
import com.mkacunha.processadorcep.domain.historico.HistoricoRepository;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.PoolConnection;
import com.mkacunha.processadorcep.infrastructure.jdbc.Transaction;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.CommandExecutor;

import java.beans.PropertyVetoException;
import java.util.UUID;

public class TesteBanco {

	public static void main(String[] args) throws PropertyVetoException, TransactionException {
		final PoolConnection poolConnection = new PoolConnection();
		final Transaction transaction = new Transaction(poolConnection);
		final CommandExecutor commandExecutor = new CommandExecutor(transaction);

		transaction.begin();
		final Historico historico = new Historico(UUID.randomUUID().toString(), "file.txt");
		final HistoricoRepository historicoRepository = new HistoricoRepository(transaction, commandExecutor);
		historicoRepository.insert(historico);

		transaction.commit();
	}
}
