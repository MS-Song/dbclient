package com.song7749.dl.dbclient.type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DatabaseDriverTest {

	@Test
	public void testIsAffectedRowCommand() throws Exception {
		DatabaseDriver d = DatabaseDriver.oracle;
		String query = "comment on table god_goodinfo is \"상품정보\"";
		assertThat(d.isAffectedRowCommand(query),is(true));
	}

}
