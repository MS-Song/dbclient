package com.song7749.dbclient.drs.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.base.MessageVo;
import com.song7749.dbclient.drs.domain.Database;
import com.song7749.dbclient.drs.domain.Member;
import com.song7749.dbclient.drs.repository.DatabaseRepository;
import com.song7749.dbclient.drs.repository.MemberRepository;
import com.song7749.dbclient.drs.type.AuthType;
import com.song7749.dbclient.drs.type.Charset;
import com.song7749.dbclient.drs.type.DatabaseDriver;
import com.song7749.dbclient.drs.value.DatabaseAddDto;
import com.song7749.dbclient.drs.value.dbclient.ExecuteQueryDto;
import com.song7749.dbclient.drs.value.dbclient.FieldVo;
import com.song7749.dbclient.drs.value.dbclient.IndexVo;
import com.song7749.dbclient.drs.value.dbclient.TableVo;
import com.song7749.dbclient.drs.value.dbclient.ViewVo;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient.drs"})
public class DBclienManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DBclienManager dbclienManager;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ModelMapper mapper;

	/**
	 * Fixture
	 */
	Database mysql = new Database(
			"local-dev"
			, "mysql-local"
			, "dbBilling"
			, "song7749"
			, "12345678"
			, DatabaseDriver.MYSQL
			, Charset.UTF8
			, "3306"
			, null);

	Database oracle = new Database(
			"local-dev"
			, "oracle-local"
			, "XE"
			, "SONG7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "1521"
			, null);

	Member member = new Member("test@gmail.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);

	ExecuteQueryDto dto = new ExecuteQueryDto();


	@Before
	public void setup() {
		databaseRepository.saveAndFlush(mysql);
		databaseRepository.saveAndFlush(oracle);
		memberRepository.saveAndFlush(member);
	}

	@Ignore
	@Test
	public void testInsert() throws SQLException {

		Connection conn = dbclienManager.getConnection(mysql);


		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(true);
			ps = conn.prepareStatement("INSERT INTO tBank (sPublicBankCode,sBankName) VALUES(?,?)");
			for(int i=0;i<99;i++) {
				ps.setString(1, "9"+i);
				ps.setString(2, "은행"+i);
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				ps.close();
				} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn=null;
				ps=null;
			}
		}
	}

	@Test
	public void testExecuteQuery() throws Exception {
		// give
		dto.setId(mysql.getId());
		dto.setLoginId(member.getLoginId());
		dto.setQuery(mysql.getDriver().getValidateQuery());
		dto.setIp(InetAddress.getLocalHost().getHostAddress());
		dto.setUseLimit(false);
		// when
		MessageVo vo = dbclienManager.executeQuery(dto);
		// then
		assertThat(vo.getHttpStatus(),equalTo(200));

		// give
		dto.setId(oracle.getId());
		dto.setLoginId(member.getLoginId());
		dto.setQuery(oracle.getDriver().getValidateQuery());
		// when
		vo = dbclienManager.executeQuery(dto);
		// then
		assertThat(vo.getHttpStatus(),equalTo(200));

		// 실행 로그 기록을 위해 1초간 sleep.
		Thread.sleep(1000);
	}

	@Test
	public void testSelectTableVoList() throws Exception {
		// give
		dto.setId(mysql.getId());
		// when
		List<TableVo> list = dbclienManager.selectTableVoList(dto);
		// then
		assertTrue(list.size() > 0);

		// give
		dto.setId(oracle.getId());
		// when
		list = dbclienManager.selectTableVoList(dto);
		// then
		assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectTableFieldVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<TableVo> tlist = dbclienManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		//when
		List<FieldVo> flist = dbclienManager.selectTableFieldVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbclienManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		// when
		flist = dbclienManager.selectTableFieldVoList(dto);
		// then
		assertTrue(flist.size() > 0);

	}

	@Test
	public void testSelectTableIndexVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<TableVo> tlist = dbclienManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		//when
		List<IndexVo> flist = dbclienManager.selectTableIndexVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbclienManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		// when
		flist = dbclienManager.selectTableIndexVoList(dto);
		// then
		assertTrue(flist.size() > 0);

	}

	@Test
	public void testSelectViewVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		//when
		List<ViewVo> flist = dbclienManager.selectViewVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		// when
		flist = dbclienManager.selectViewVoList(dto);
		// then
		assertTrue(flist.size() > 0);
	}

	@Test
	public void testSelectViewDetailList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<ViewVo> tlist = dbclienManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		//when
		List<Map<String, String>> flist = dbclienManager.selectViewDetailList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbclienManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		// when
		flist = dbclienManager.selectViewDetailList(dto);
		// then
		assertTrue(flist.size() > 0);
	}

	@Test
	public void testSelectViewVoSourceList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<ViewVo> tlist = dbclienManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		//when
		List<ViewVo> flist = dbclienManager.selectViewVoSourceList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbclienManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		// when
		flist = dbclienManager.selectViewVoSourceList(dto);
		// then
		assertTrue(flist.size() > 0);
	}

	@Test
	public void testTestConnection() throws Exception {
		// give
		DatabaseAddDto mysqlDto = mapper.map(mysql, DatabaseAddDto.class) ;
		//when
		MessageVo vo = dbclienManager.testConnection(mysqlDto);
		//then
		assertThat(vo.getHttpStatus(), equalTo(200));

		// give
		DatabaseAddDto oracleDto = mapper.map(oracle, DatabaseAddDto.class);
		//when
		vo = dbclienManager.testConnection(oracleDto);
		//then
		assertThat(vo.getHttpStatus(), equalTo(200));
	}
}