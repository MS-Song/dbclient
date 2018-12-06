package com.song7749.chakra.service;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.song7749.UnitTest;
import com.song7749.chakra.value.ChakraConfigFindDto;
import com.song7749.chakra.value.ChakraConfigSaveDto;
import com.song7749.chakra.value.ChakraConfigVo;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;

public class ChakraConfigManagerImplTest extends UnitTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ChakraConfigManager chakraConfigManager;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	DBclientManager dbClientManager;

	Database oracle;
	Database monetedb;

	@Before
	public void setup() {
		oracle = new Database("local-dev"
			, "oracle-local"
			, "XE"
			, "SONG7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "49161"
			,"");
		oracle.setSchemaOwner(oracle.getAccount());
		databaseRepository.saveAndFlush(oracle);

		monetedb = new Database("10.10.22.233"
				, "chakraDB"
				, "chakramax_v2"
				, "chakraselect"
				, "chakramax12!@"
				, DatabaseDriver.MONETDB
				, Charset.UTF8
				, "50000"
				,"");

		databaseRepository.saveAndFlush(monetedb);
	}

	@Test
	public void testSaveChakraConfig() throws Exception {
		// give
		ChakraConfigSaveDto dto = new ChakraConfigSaveDto();
		dto.setAutoSyncYN(YN.Y);
		dto.setChakraDatabaseId(monetedb.getId());
		dto.setTargetDatabaseId(oracle.getId());

		// when
		chakraConfigManager.saveChakraConfig(dto);

		// then
		ChakraConfigFindDto findDto = new ChakraConfigFindDto();
		findDto.setTargetDatabaseId(dto.getTargetDatabaseId());
		ChakraConfigVo vo = chakraConfigManager.getChakraConfig(findDto);
		assertThat(vo.getId(),notNullValue());
	}

	@Test
	public void testSyncChakraPrivacyPolicy() throws Exception {
		// give
		ChakraConfigSaveDto dto = new ChakraConfigSaveDto();
		dto.setAutoSyncYN(YN.Y);
		dto.setChakraDatabaseId(monetedb.getId());
		dto.setTargetDatabaseId(oracle.getId());
		chakraConfigManager.saveChakraConfig(dto);

		// Database connection 을 미리 생성 한다.
		List<Database> list = databaseRepository.findAll();
		if(null!=list && list.size() !=0) {
			for(Database database : list) {
				try {
					logger.info(format("{}", "Database Try Connection "),database);
					dbClientManager.getConnection(database);
				} catch (SQLException e) {
					logger.error(format("{}", "Database Connection Fail log"),e.getMessage());
				}
			}
		}

		Thread.sleep(1000);

		// when
		ChakraConfigFindDto findDto = new ChakraConfigFindDto();
		findDto.setTargetDatabaseId(dto.getTargetDatabaseId());
		chakraConfigManager.syncChakraPrivacyPolicy(findDto);

		// then
	}
}
