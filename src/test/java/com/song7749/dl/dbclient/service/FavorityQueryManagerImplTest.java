package com.song7749.dl.dbclient.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.dbclient.dto.DeleteFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.ModifyFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.SaveFavorityQueryDTO;
import com.song7749.dl.dbclient.entities.FavorityQuery;
import com.song7749.dl.dbclient.repositories.FavorityQueryRepository;
import com.song7749.dl.login.exception.AuthorityUserException;
import com.song7749.util.ProxyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class FavorityQueryManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Mock
	private FavorityQueryRepository favorityQueryRepository;

	@Autowired
	private FavorityQueryManager favorityQueryManager;


	private FavorityQuery favorityQuery;
	@Before
	public void setup() {
		favorityQuery = new FavorityQuery(
				"song7749",
				"테스트쿼리",
				"select * from dual",
				new Date());
		favorityQuery.setFavorityQuerySeq(1);
	}

	@Before
	public void setupMock() throws Exception{
		MockitoAnnotations.initMocks(this);
		FavorityQueryManager o = (FavorityQueryManager)ProxyUtils.unwrapProxy(favorityQueryManager);
		ReflectionTestUtils.setField(o, "favorityQueryRepository", favorityQueryRepository);
	}


	@Test
	public void testSaveFavorityQuery() throws Exception {
		//give
		SaveFavorityQueryDTO dto = new SaveFavorityQueryDTO(
				favorityQuery.getId(),
				favorityQuery.getMemo(),
				favorityQuery.getQuery(),
				favorityQuery.getInputDate());
		//when
		favorityQueryManager.saveFavorityQuery(dto);
		//then
		verify(favorityQueryRepository,times(1)).save(any(FavorityQuery.class));
	}

	@Test(expected=AuthorityUserException.class)//then
	public void testModifyFavorityQuery_user_authority_check() throws Exception {
		//give
		ModifyFavorityQueryDTO dto = new ModifyFavorityQueryDTO(
				favorityQuery.getFavorityQuerySeq(),
				"song1234",
				favorityQuery.getMemo(),
				favorityQuery.getQuery(),
				favorityQuery.getInputDate());

		given(favorityQueryRepository.find(any(FavorityQuery.class))).willReturn(favorityQuery);

		//when
		favorityQueryManager.modifyFavorityQuery(dto);
	}

	@Test
	public void testModifyFavorityQuery() throws Exception {
		//give
		ModifyFavorityQueryDTO dto = new ModifyFavorityQueryDTO(
				favorityQuery.getFavorityQuerySeq(),
				favorityQuery.getId(),
				favorityQuery.getMemo(),
				favorityQuery.getQuery(),
				favorityQuery.getInputDate());

		given(favorityQueryRepository.find(any(FavorityQuery.class))).willReturn(favorityQuery);

		//when
		favorityQueryManager.modifyFavorityQuery(dto);
		//then
		verify(favorityQueryRepository,times(1)).update(any(FavorityQuery.class));
	}


	@Test(expected=AuthorityUserException.class)//then
	public void testDeleteFavorityQuery_user_authority_check() throws Exception {
		//give
		DeleteFavorityQueryDTO dto = new DeleteFavorityQueryDTO(
				favorityQuery.getFavorityQuerySeq(),
				"song1234");

		given(favorityQueryRepository.find(any(FavorityQuery.class))).willReturn(favorityQuery);

		//when
		favorityQueryManager.deleteFavorityQuery(dto);
	}

	@Test
	public void testDeleteFavorityQuery() throws Exception {
		//give
		DeleteFavorityQueryDTO dto = new DeleteFavorityQueryDTO(
				favorityQuery.getFavorityQuerySeq(),
				favorityQuery.getId());

		given(favorityQueryRepository.find(any(FavorityQuery.class))).willReturn(favorityQuery);

		//when
		favorityQueryManager.deleteFavorityQuery(dto);
		//then
		verify(favorityQueryRepository,times(1)).delete(any(FavorityQuery.class));
	}
}
