package com.mkacunha.processadorcep.infrastructure.jdbc.sql;

import com.google.common.collect.Lists;

import java.sql.Date;
import java.util.List;

import static com.mkacunha.processadorcep.infrastructure.jdbc.sql.ParameterFactory.of;

public class Command {

	private String sql;

	private List<Parameter> parameters;

	public Command(Builder builder) {
		this.sql = builder.sql;
		this.parameters = builder.parameters;
	}

	public static Builder init(String sql) {
		return new Builder(sql);
	}

	public String getSql() {
		return sql;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public static class Builder {

		private String sql;

		private List<Parameter> parameters = Lists.newArrayList();

		private int index = 0;

		private Builder(String sql) {
			this.sql = sql;
		}

		public Builder parameter(Integer value) {
			index++;
			parameters.add(of(index, value));
			return this;
		}

		public Builder parameter(String value) {
			index++;
			parameters.add(of(index, value));
			return this;
		}

		public Builder parameter(Long value) {
			index++;
			parameters.add(of(index, value));
			return this;
		}

		public Builder parameter(Date value) {
			index++;
			parameters.add(of(index, value));
			return this;
		}

		public Command command() {
			return new Command(this);
		}

	}
}

