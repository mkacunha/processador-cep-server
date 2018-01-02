package com.mkacunha.processadorcep.infrastructure.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mkacunha.processadorcep.infrastructure.exception.ConnectionException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.apache.log4j.Logger.getLogger;

@Component
public class PoolConnection {

	public static final String MSG_NAO_FOI_POSSIVEL_CONECTAR = "Não foi possível connectar ao banco de dados. Entre em contato com o administrador.";

	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/processadorcep";

	public static final String USER = "root";

	public static final String PASSWORD = "root";

	public static final int MIN_POOL_SIZE = 1;

	public static final int ACQUIRE_INCREMENT = 2;

	public static final int MAX_POOL_SIZE = 5;

	private static final Logger LOGGER = getLogger(PoolConnection.class);

	private final ComboPooledDataSource connectionPoolDatasource;

	public PoolConnection() throws PropertyVetoException {
		connectionPoolDatasource = new ComboPooledDataSource();
		connectionPoolDatasource.setDriverClass(DRIVER_CLASS);
		connectionPoolDatasource.setJdbcUrl(JDBC_URL);
		connectionPoolDatasource.setUser(USER);
		connectionPoolDatasource.setPassword(PASSWORD);
		connectionPoolDatasource.setMinPoolSize(MIN_POOL_SIZE);
		connectionPoolDatasource.setAcquireIncrement(ACQUIRE_INCREMENT);
		connectionPoolDatasource.setMaxPoolSize(MAX_POOL_SIZE);
	}

	public Connection getConnection() {
		try {
			Connection connection = connectionPoolDatasource.getConnection();
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new ConnectionException(MSG_NAO_FOI_POSSIVEL_CONECTAR);
		}
	}
}
