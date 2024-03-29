package com.song7749.dbclient.service;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.common.base.MessageVo;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.dbclient.value.FieldVo;
import com.song7749.dbclient.value.IndexVo;
import com.song7749.dbclient.value.TableVo;
import com.song7749.dbclient.value.ViewVo;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class, properties = "spring.config.location=classpath:/application.yml")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
public class DBclienManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DBclientManager dbClientManager;

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

	Database mssql = new Database(
			"local-dev"
			, "mssql-local"
			, "TestDB"
			, "SA"
			, "12345678dsCD!"
			, DatabaseDriver.MSSQL
			, Charset.UTF8
			, "1401"
			, null);

	Member member =Member.builder()
		.loginId("song7749@test.com")
		.password("12345678")
		.passwordQuestion("패스워드질문은?")
		.passwordAnswer("패스워드답변은?")
		.teamName("제일잘나가는팀")
		.name("song7749")
		.authType(AuthType.ADMIN)
		.build();

	ExecuteQueryDto dto = new ExecuteQueryDto();


	@BeforeEach
	public void setup() {
		databaseRepository.saveAndFlush(mysql);
		oracle.setSchemaOwner(oracle.getAccount());
		databaseRepository.saveAndFlush(oracle);
		databaseRepository.saveAndFlush(mssql);
		memberRepository.saveAndFlush(member);
	}

	@Test
	public void testInsert() throws SQLException {

		Connection conn = dbClientManager.getConnection(mysql);

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
		MessageVo vo = dbClientManager.executeQuery(dto);
		// then
		assertThat(vo.getHttpStatus(),equalTo(200));

		// give
		dto.setId(oracle.getId());
		dto.setLoginId(member.getLoginId());
		dto.setQuery(oracle.getDriver().getValidateQuery());
		// when
		vo = dbClientManager.executeQuery(dto);
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
		List<TableVo> list = dbClientManager.selectTableVoList(dto);
		// then
		assertTrue(list.size() > 0);

		// give
		dto.setId(oracle.getId());
		// when
		list = dbClientManager.selectTableVoList(dto);
		// then
		assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectTableFieldVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<TableVo> tlist = dbClientManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		//when
		List<FieldVo> flist = dbClientManager.selectTableFieldVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbClientManager.selectTableVoList(dto);
		logger.trace(format("{}", "Log Message"),tlist);

		dto.setName(tlist.get(0).getTableName());
		// when
		flist = dbClientManager.selectTableFieldVoList(dto);
		// then
		assertTrue(flist.size() > 0);

	}

	@Test
	public void testSelectTableIndexVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<TableVo> tlist = dbClientManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		//when
		List<IndexVo> flist = dbClientManager.selectTableIndexVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbClientManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		// when
		flist = dbClientManager.selectTableIndexVoList(dto);
		// then
		assertTrue(flist.size() > 0);

	}

	@Test
	public void testSelectViewVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		//when
		List<ViewVo> flist = dbClientManager.selectViewVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		// when
		flist = dbClientManager.selectViewVoList(dto);
		// then
		assertTrue(flist.size() > 0);
	}

	@Test
	public void testSelectViewDetailList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<ViewVo> tlist = dbClientManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		//when
		List<Map<String, String>> flist = dbClientManager.selectViewDetailList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbClientManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		// when
		flist = dbClientManager.selectViewDetailList(dto);
		// then
		assertTrue(flist.size() > 0);
	}

	@Test
	public void testSelectViewVoSourceList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<ViewVo> tlist = dbClientManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		//when
		List<ViewVo> flist = dbClientManager.selectViewVoSourceList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbClientManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		// when
		flist = dbClientManager.selectViewVoSourceList(dto);
		// then
		assertTrue(flist.size() > 0);
	}

	@Test
	public void testTestConnection() throws Exception {
		// give
		DatabaseAddDto mysqlDto = mapper.map(mysql, DatabaseAddDto.class) ;
		//when
		MessageVo vo = dbClientManager.testConnection(mysqlDto);
		//then
		assertThat(vo.getHttpStatus(), equalTo(200));

		// give
		DatabaseAddDto oracleDto = mapper.map(oracle, DatabaseAddDto.class);
		//when
		vo = dbClientManager.testConnection(oracleDto);
		//then
		assertThat(vo.getHttpStatus(), equalTo(200));
	}
}