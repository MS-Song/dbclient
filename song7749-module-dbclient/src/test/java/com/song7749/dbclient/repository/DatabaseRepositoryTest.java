package com.song7749.dbclient.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.song7749.UnitTest;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;

public class DatabaseRepositoryTest extends UnitTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseRepository databaseRepository;

	/**
	 * fixture
	 */
	Database ds = new Database("10.10.10.10"
			, "test server"
			, "dbTest"
			, "song7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "3333"
			,"");

	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testSaveGivenNull() {
		//give
		//when
		databaseRepository.saveAndFlush(null);
		//then -- expacted
	}

	@Test
	public void testSave() {
		//give
		//when
		ds = databaseRepository.saveAndFlush(ds);
		//than
		assertThat(ds.getId(),notNullValue());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//give
		ds.setHost("10.200.200.200");
		ds.setCharset(Charset.EUCKR);
		//when
		Database dsi = databaseRepository.saveAndFlush(ds);
		//then
		assertThat(dsi.getId(),equalTo(ds.getId()));
		assertThat(dsi.getHost(),equalTo(ds.getHost()));
		assertThat(dsi.getCharset(),equalTo(ds.getCharset()));

		databaseRepository.deleteById(dsi.getId());
		databaseRepository.flush();

		assertFalse(databaseRepository.existsById(dsi.getId()));

	}
}