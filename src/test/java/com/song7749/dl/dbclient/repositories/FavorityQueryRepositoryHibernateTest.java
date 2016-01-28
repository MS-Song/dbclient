package com.song7749.dl.dbclient.repositories;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.dbclient.dto.FindFavorityQueryListDTO;
import com.song7749.dl.dbclient.entities.FavorityQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class FavorityQueryRepositoryHibernateTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	FavorityQueryRepository favorityQueryRepository;

	@Before
	public void setUp(){
	}

	@Test
	public void CURDFasade() throws Exception{
		FavorityQuery favorityQuery = testSave();
		testFind(favorityQuery);
		testFindFavorityQueryList(favorityQuery);
		testUpdate(favorityQuery);
		testDelete(favorityQuery);
	}

	public FavorityQuery testSave() throws Exception {
		// give
		FavorityQuery favorityQuery = new FavorityQuery(
				"song7749",
				"테스트용쿼리",
				"select * from dual",
				new Date());
		// when
		favorityQueryRepository.save(favorityQuery);

		// then
		assertThat(favorityQuery.getFavorityQuerySeq(), notNullValue());

		return favorityQuery;
	}

	public void testUpdate(FavorityQuery favorityQuery) throws Exception {
		// given
		favorityQuery.setMemo("테스트용 쿼리 2");
		favorityQuery.setInputDate(new Date());
		favorityQuery.setQuery("select * from dual where 1");
		// when
		favorityQueryRepository.update(favorityQuery);
		// then
		assertTrue(true);
	}

	public void testDelete(FavorityQuery favorityQuery) throws Exception {
		// given

		// when
		favorityQueryRepository.delete(favorityQuery);
		// then
		assertTrue(true);
	}

	public void testFind(FavorityQuery favorityQuery) throws Exception {
		// give // when
		FavorityQuery returnValue = favorityQueryRepository.find(favorityQuery);
		// then
		assertThat(favorityQuery.getFavorityQuerySeq(), is(returnValue.getFavorityQuerySeq()));
	}

	public void testFindFavorityQueryList(FavorityQuery favorityQuery) throws Exception {
		// given
		FindFavorityQueryListDTO dto = new FindFavorityQueryListDTO();
		dto.setUseLimit(true);
		dto.setLimit(10L);
		dto.setOffset(0L);
		dto.setFavorityQuerySeq(favorityQuery.getFavorityQuerySeq());
		dto.setId(favorityQuery.getId());
		// when
		List<FavorityQuery> list = favorityQueryRepository.findFavorityQueryList(dto);
		// then

		assertThat(list.get(0).getFavorityQuerySeq(), is(favorityQuery.getFavorityQuerySeq()));
	}
}