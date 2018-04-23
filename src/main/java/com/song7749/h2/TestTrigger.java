package com.song7749.h2;

import static com.song7749.util.LogMessageFormatter.format;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.api.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTrigger implements Trigger {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) throws SQLException {
		logger.trace(format("{}", "Test H2 Trigger init"),tableName);
	}

	@Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
		logger.trace(format("OLD : {} \nNEW: {}", "Test H2 Trigger fire"), oldRow, newRow);
	}

	@Override
	public void close() throws SQLException {
		logger.trace(format("{}", "Test H2 Trigger close"),"close");
	}

	@Override
	public void remove() throws SQLException {
		logger.trace(format("{}", "Test H2 Trigger remove"),"remove");
	}
}