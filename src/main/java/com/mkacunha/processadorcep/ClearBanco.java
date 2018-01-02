package com.mkacunha.processadorcep;

import com.mkacunha.processadorcep.infrastructure.jdbc.PoolConnection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClearBanco {

	public static void main(String[] args) throws PropertyVetoException, SQLException {
		PoolConnection poolConnection = new PoolConnection();
		final Connection connection = poolConnection.getConnection();
		execute("delete from cep", connection);
		execute("delete from cidade", connection);
		execute("delete from historicolog", connection);
		execute("delete from historico", connection);
	}

	private static void execute(String sql, Connection connection) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.execute();
			connection.commit();
			preparedStatement.close();
		}
	}
}
