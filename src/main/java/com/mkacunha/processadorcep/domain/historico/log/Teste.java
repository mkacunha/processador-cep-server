package com.mkacunha.processadorcep.domain.historico.log;

import com.google.common.collect.Lists;
import com.mkacunha.processadorcep.domain.historico.Historico;
import com.mkacunha.processadorcep.infrastructure.exception.TransactionException;
import com.mkacunha.processadorcep.infrastructure.jdbc.PoolConnection;
import com.mkacunha.processadorcep.infrastructure.jdbc.Transaction;
import com.mkacunha.processadorcep.infrastructure.jdbc.sql.CommandExecutor;

import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Teste {

	public static void main(String[] args) throws PropertyVetoException, TransactionException {
		final PoolConnection poolConnection = new PoolConnection();
		final Transaction transaction = new Transaction(poolConnection);
		final CommandExecutor commandExecutor = new CommandExecutor(transaction);

		transaction.begin();
		final HistoricoLogRepository historicoLogRepository = new HistoricoLogRepository(commandExecutor);
		final List<HistoricoLog> logs = historicoLogRepository.findAllWithoutHistorico();

		final Stream<HistoricoLogStatus> historicoLogStatusStream = logs.parallelStream().map(log -> log.getStatus());

		final Map<HistoricoLogStatus, Long> collect = historicoLogStatusStream
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		List<Historico> historicos = Lists.newArrayList();

		transaction.finnaly();
	}

	public class BinaryOperator implements java.util.function.BinaryOperator<HistoricoLogStatus> {

		@Override
		public HistoricoLogStatus apply(HistoricoLogStatus historicoLogStatus, HistoricoLogStatus historicoLogStatus2) {
			return null;
		}
	}

}


