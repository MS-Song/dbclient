package com.song7749.dbclient.config;

import static com.song7749.util.LogMessageFormatter.format;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.service.DBclientManagerImpl;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberVo;

/**
 * <pre>
 * Class Name : InitConfigBean.java
 * Description : App 실행 시 사전에 처리해야 하는 작업들을 정의 함.
 *
 * 1. Local - H2 DB 에 대한 내용 확인
 * 2. 테스트 Data 입력 -- 서비스 배포시에는 삭제 필요
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 29.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 29.
*/
@Profile("!test")
@Component
public class InitDBclientConfigBean {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	MemberManager memberManager;

	@Autowired
	DBclientManager dbClientManager;

	@Autowired
	DataSource hikariCP;

	@Autowired
	ModelMapper mapper;

	@Value("${spring.datasource.url}")
	private String springDatasourceUrl;

	@Value("${spring.datasource.host}")
	private String springDatasourceHost;

	@Value("${spring.datasource.schemaName}")
	private String springDatasourceSchemaName;

	@Value("${spring.datasource.databaseName}")
	private String springDatasourceDatabaseName;

	@Value("${spring.datasource.username}")
	private String springDatasourceUsername;

	@Value("${spring.datasource.password}")
	private String springDatasourcePassword;

	@Value("${spring.datasource.driver-class-name}")
	private String springDatasourceDriverClassName;

	@Value("${spring.datasource.port}")
	private String springDatasourcePort;



	@Transactional
	@PostConstruct
    public void init(){
		// TODO - member 에서 root 유저 입력을 하기 때문에 삭제 필요할듯
		// root 회원에 대한 입력
		Member member = new Member(
				"root@test.com"
				, "12345678"
				, "Password Question?"
				, "Password Answer?"
				, "team name"
				, "your name"
				, AuthType.ADMIN);
		Member aleadyMember = memberRepository.findByLoginId("root@test.com");
		Database db=null;

		if(null==aleadyMember) {
			memberRepository.saveAndFlush(member);
			MemberVo memberVo = memberManager.renewApikeyByAdmin(member.getLoginId());
			logger.info(format("{}", "first Start Application with root user create"),memberVo);
		} else {
			logger.info(format("{}", "root user info"),aleadyMember.getMemberVo(mapper));
		}

		// h2 dbconnection add
		logger.info(format("{}", "Database Datasource"),hikariCP);
		if(null!=hikariCP) {
			// 데이터베이스 드라이버 명칭을 검색 한다.
			String databaseName = null;

			try {
				databaseName=hikariCP.getConnection().getMetaData().getDatabaseProductName();
				logger.trace(format("{}", "Database Name"),databaseName);
			} catch (SQLException e) {
				logger.error(format("{}", "Database Name Fail"),databaseName);
			}

			if(logger.isTraceEnabled()) {
				Object[] databaseInfo = {
					springDatasourceDatabaseName,
					springDatasourceHost,
					springDatasourceUrl,
					springDatasourcePort,
					springDatasourceSchemaName,
					springDatasourceUsername,
					springDatasourcePassword,
					springDatasourceDriverClassName};
				logger.trace(format("DatabaseName:{}\nhost:{}\nURL:{}\nport:{}\nSchemaName:{}\nUserName:{}\nPassword:{}\nClassName:{}", "Define Database"),databaseInfo);
			}
			// database 설정

			db = new Database(
					springDatasourceHost,
					springDatasourceDatabaseName,
					springDatasourceSchemaName,
					springDatasourceUsername,
					springDatasourcePassword,
					DatabaseDriver.getDatabaseDriverByDriverClassName(springDatasourceDriverClassName),
					Charset.UTF8,
					springDatasourcePort);

			Example<Database> example = Example.of(db);
			Optional<Database> oDB = databaseRepository.findOne(example);
			// 이미 입력된 내용이 없을 경우에만 입력한다.
			if(!oDB.isPresent()){
				databaseRepository.saveAndFlush(db);
			} else {
				db = oDB.get();
			}

			// db 를 pool map 객체에 넣는다.
			Map<Database, DataSource> map = ((DBclientManagerImpl)dbClientManager).getDataSourceMap();
			map.put(db, hikariCP);
			logger.info(format("{}", "DBClient Database Add Complete"),map);


			if("H2".endsWith(databaseName)) {
				// comment 입력
				String[] comments = {
						 "COMMENT ON TABLE DATABASE_INFO IS 'Database 연결 정보'"
						,"COMMENT ON TABLE LOG IS '로그 마스터 정보'"
						,"COMMENT ON TABLE LOG_LOGIN IS '로그인 로그 정보'"
						,"COMMENT ON TABLE MEMBER_DATABASE IS '회원과 데이터베이스 간의 연결'"
						,"COMMENT ON TABLE LOG_QUERY IS '쿼리 실행 로그'"
						,"COMMENT ON TABLE MEMBER_SAVE_QUERY IS '회원의 저장된 쿼리'"
						,"COMMENT ON TABLE MEMBER IS '회원'"
				};
				ExecuteQueryDto dto = new ExecuteQueryDto();
				dto.setId(db.getId());
				dto.setIp("10.10.10.10");
				dto.setAutoCommit(true);
				dto.setLoginId(member.getLoginId());
				for(String query : comments) {
					dto.setQuery(query);
					dbClientManager.executeQuery(dto);
				}
			}
		}

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
    }
}