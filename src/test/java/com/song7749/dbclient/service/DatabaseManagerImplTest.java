package com.song7749.dbclient.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseFindDto;
import com.song7749.dbclient.value.DatabaseModifyDto;
import com.song7749.dbclient.value.DatabaseRemoveDto;
import com.song7749.dbclient.value.DatabaseVo;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient","com.song7749.log"})
public class DatabaseManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	ModelMapper mapper;
	/**
	 * Fixture
	 */
	DatabaseAddDto databaseAddDto = new DatabaseAddDto("10.10.10.10", "Test Database", "Test Schema", "song7749",
			"1234", DatabaseDriver.MYSQL, Charset.UTF8, "3306");


	@Test
	public void testMapper() {

		//give
		//when
		Database database = mapper.map(databaseAddDto, Database.class);
		//then
		assertThat(database.getHost(),equalTo(database.getHost()));
		assertThat(database.getAccount(),equalTo(database.getAccount()));
		assertThat(database.getCharset(),equalTo(database.getCharset()));
		assertThat(database.getPort(),equalTo(database.getPort()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddDatabaseGivenNull() {
		//give
		DatabaseAddDto dto = null;
		//when
		databaseManager.addDatabase(dto);
		//then -- expected
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddDatabaseGivenHostNull() {
		//give
		databaseAddDto.setHost(null);
		//when
		databaseManager.addDatabase(databaseAddDto);
		//then expected
	}

	@Test
	public void testAddDatabase() {
		//give
		//when
		DatabaseVo dv = databaseManager.addDatabase(databaseAddDto);
		//then
		assertThat(dv.getId(), notNullValue());
		assertThat(dv.getCreateDate(), notNullValue());
		assertThat(dv.getModifyDate(), notNullValue());
		assertEquals(dv.getCreateDate(), dv.getModifyDate());
	}

	@Test
	public void testModifyDatabase() throws Exception {
		//give
		DatabaseVo dv = databaseManager.addDatabase(databaseAddDto);

		DatabaseModifyDto dto = new DatabaseModifyDto();
		dto.setId(dv.getId());
		dto.setPassword("1234abcd");

		//when
		Thread.sleep(1000);
		DatabaseVo rDv = databaseManager.modifyDatabase(dto);
		//then
		assertNotEquals(dv.getModifyDate(), rDv.getModifyDate());
	}

	@Test
	public void testFindDatabaseList() throws Exception {
		// give
		List<DatabaseVo> voList = new ArrayList<DatabaseVo>();
		voList.add(databaseManager.addDatabase(databaseAddDto));
		voList.add(databaseManager.addDatabase(databaseAddDto));
		voList.add(databaseManager.addDatabase(databaseAddDto));
		voList.add(databaseManager.addDatabase(databaseAddDto));
		voList.add(databaseManager.addDatabase(databaseAddDto));



		DatabaseFindDto dto = new DatabaseFindDto();
		dto.setHost(databaseAddDto.getHost());
		Pageable page = PageRequest.of(0, 10);//,Direction.DESC,"id");;
		// when
		Page<DatabaseVo> list = databaseManager.findDatabaseList(dto, page);
		// then
		assertThat(list.getSize(), equalTo(10));
		assertThat(list.getContent().size(), equalTo(5));

		// give
		dto.setId(voList.get(0).getId());
		dto.setHost(voList.get(0).getHost());
		// when
		list = databaseManager.findDatabaseList(dto, page);
		// then
		assertThat(list.getSize(), equalTo(10));
		assertThat(list.getContent().size(), equalTo(1));

		List<Long> ids = voList.stream().map(v -> v.getId()).collect(Collectors.toList());

		// give
		dto.setIds(ids);

		// when
		list = databaseManager.findDatabaseList(dto, page);

		// then

		assertThat(list.getSize(), equalTo(10));
		assertThat(list.getContent().size(), equalTo(5));
	}

	@Test
	public void testRemoveDatabase() throws Exception {
		//give
		DatabaseVo dv = databaseManager.addDatabase(databaseAddDto);
		DatabaseRemoveDto dto = new DatabaseRemoveDto();
		dto.setId(dv.getId());

		//when
		databaseManager.removeDatabase(dto);
		//then
		DatabaseFindDto find = new DatabaseFindDto();
		find.setId(dv.getId());
		Pageable page = PageRequest.of(0, 10);//,Direction.DESC,"id");;
		Page<DatabaseVo> list = databaseManager.findDatabaseList(find, page);

		assertThat(list.getSize(), equalTo(10));
		assertThat(list.getContent().size(), equalTo(0));


	}
}