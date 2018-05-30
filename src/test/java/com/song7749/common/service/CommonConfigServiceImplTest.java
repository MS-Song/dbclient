package com.song7749.common.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.common","com.song7749.mail"})
public class CommonConfigServiceImplTest {
	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	CommonConfigService commonConfigService;

	@Ignore
	@Test
	public void testTestMailConfig() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Ignore
	@Test
	public void testSaveMailConfig() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Ignore
	@Test
	public void testFindMailConfig() throws Exception {
		throw new RuntimeException("not yet implemented");
	}


}
