package com.song7749.hibernate.util;

import java.sql.Types;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class CustomMysqlInnoDBDialect extends MySQLDialect {

	public CustomMysqlInnoDBDialect(){
		super();
		registerFunction("seal", new SQLFunctionTemplate(StandardBasicTypes.STRING,"seal(?1)"));
		registerFunction("unseal", new SQLFunctionTemplate(StandardBasicTypes.STRING,"unseal(?1)"));
		registerFunction("seal_text", new SQLFunctionTemplate(StandardBasicTypes.STRING,"seal_text(CAST(?1 AS CHAR CHARACTER SET UTF8))"));
		registerFunction("unseal_text", new SQLFunctionTemplate(StandardBasicTypes.STRING,"CAST(unseal_text(?1) AS CHAR CHARACTER SET UTF8)"));
		// mysql 에 boolean 타입을 매핑하기 위함
		registerColumnType(Types.BIT, "boolean");
	}

	@Override
	public boolean supportsCascadeDelete() {
		return true;
	}

	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}

	@Override
	public boolean hasSelfReferentialForeignKeyBug() {
		return true;
	}


}
