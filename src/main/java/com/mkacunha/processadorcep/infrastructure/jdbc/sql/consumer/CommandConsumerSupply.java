package com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer;

import java.sql.PreparedStatement;
import java.util.function.Function;

public interface CommandConsumerSupply<R> extends Function<PreparedStatement, R> {
}
