package com.mkacunha.processadorcep.infrastructure.jdbc.sql.consumer;

import java.sql.PreparedStatement;
import java.util.function.Consumer;

public interface CommandConsumer extends Consumer<PreparedStatement> {
}
