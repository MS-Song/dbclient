package com.song7749.srcenter.config;

import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.MemberDatabase;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberDatabaseRepository;
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
import com.song7749.srcenter.domain.SrDataCondition;
import com.song7749.srcenter.domain.SrDataRequest;
import com.song7749.srcenter.repository.SrDataRequestRepository;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.type.DownloadLimitType;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

import static com.song7749.util.LogMessageFormatter.format;

/**
 * <pre>
 * Class Name : InitSRdataRequestConfigBean.java
 * Description : App 실행 시 사전에 처리해야 하는 작업들을 정의 함.
 *
 * 1. SR Data Request 의 샘플 데이터를 입력하여, 사용자의 수정에 사용 되도록 처리 함.
 * 2. 테스트 Data 입력 -- 서비스 배포시에는 삭제 필요
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2019. 12. 10.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2019. 12. 10.
*/
@Profile({"local"})
@Component("InitSRdataRequestConfigBean")
@DependsOn("InitDBclientConfigBean")		// DB 정보 입력 후 기동
public class InitSRdataRequestConfigBean {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	MemberDatabaseRepository memberDatabaseRepository;

	@Autowired
	private SrDataRequestRepository srDataRequestRepository;

	@Transactional
	@PostConstruct
    public void init(){
		// 기초 데이터 입력
		List<Member> members = memberRepository.findAll();
		Member currentMember = null;
		for(Member m : members){
			if(m.getAuthType().equals(AuthType.DEVELOPER)){
				currentMember=m;
				break;
			}
		}

		List<Database> databases = databaseRepository.findAll();
		Database currentDatabase = null;
		for(Database d : databases ){
			if(d.getDriver().equals(DatabaseDriver.H2)){
				currentDatabase=d;
				break;
			}
		}

		MemberDatabase md = new MemberDatabase();
		md.setDatabase(currentDatabase);
		md.setMember(currentMember);
		memberDatabaseRepository.saveAndFlush(md);

		logger.debug("Sr Data Request Sample Database : {}", currentDatabase);

		// sample 1
		SrDataRequest sdr  = new SrDataRequest(
				"[샘플1] SR data Request 의 샘플 데이터 입니다. 확인 후에 사용 방법을 익히세요",
				"select * from database_info where 1=1 {whereDatabaseId} {whereHost} {wherePort}",
				0,
				0,
				DownloadLimitType.DAILY,
				null,
				null,
				YN.Y,
				currentDatabase,
				currentMember,
				members);

		List<SrDataCondition> conditions = Arrays.asList(
			new SrDataCondition("and database_id='{database_id}'", "{whereDatabaseId}", "DB번호", "{database_id}", DataType.SQL, "select null as NAME, '- database 선택 -' as VALUE union select database_id as NAME, host as VALUE  from database_info order by NAME ASC", YN.N, sdr),
			new SrDataCondition("and host like '%{host}%'", "{whereHost}", "host명", "{host}", DataType.STRING, null, YN.Y, sdr),
			new SrDataCondition("and port='{port}'", "{wherePort}", "Port", "{Port}", DataType.ARRAY, "3306^MYSQL|1512^ORACLE", YN.N, sdr)
		);

		sdr.setSrDataConditions(conditions);
		srDataRequestRepository.saveAndFlush(sdr);

		// sample 2
		SrDataRequest sdr2  = new SrDataRequest(
				"[샘플2] SR data Request 의 샘플 데이터 입니다. 확인 후에 사용 방법을 익히세요",
				"select * from database_info where 1=1 {whereDatabaseId} {whereHost} {wherePort}",
				2,
				0,
				DownloadLimitType.MINUTELY,
				new Date(),
				new Date(),
				YN.Y,
				currentDatabase,
				members.get(0),
				members);

		sdr2.setConfirmYN(YN.Y);
		sdr2.setConfirmMember(members.get(0));
		sdr2.setConfirmDate(new Date());

		List<SrDataCondition> conditions2 = Arrays.asList(
				new SrDataCondition("and database_id='{database_id}'", "{whereDatabaseId}", "DB번호", "{database_id}", DataType.SQL, "select null as NAME, '- database 선택 -' as VALUE union select database_id as NAME, host as VALUE  from database_info order by NAME ASC", YN.N, sdr2),
				new SrDataCondition("and host like '%{host}%'", "{whereHost}", "host명", "{host}", DataType.STRING, null, YN.Y, sdr2),
				new SrDataCondition("and port='{port}'", "{wherePort}", "Port", "{Port}", DataType.NUMBER, null, YN.N, sdr2)
		);


		sdr2.setSrDataConditions(conditions2);
		srDataRequestRepository.saveAndFlush(sdr2);

		// sample 3
		SrDataRequest sdr3  = new SrDataRequest(
				"[샘플3] SR data Request 의 샘플 데이터 입니다. 확인 후에 사용 방법을 익히세요",
				"select * from database_info where 1=1 {whereDatabaseId} {whereHost} {wherePort}",
				0,
				0,
				DownloadLimitType.DAILY,
				new Date(),
				new Date(),
				YN.N,
				currentDatabase,
				members.get(0),
				members);

		List<SrDataCondition> conditions3 = Arrays.asList(
				new SrDataCondition("and database_id='{database_id}'", "{whereDatabaseId}", "DB번호", "{database_id}", DataType.SQL, "select null as NAME, '- database 선택 -' as VALUE union select database_id as NAME, host as VALUE  from database_info order by NAME ASC", YN.N, sdr3),
				new SrDataCondition("and host like '%{host}%'", "{whereHost}", "host명", "{host}", DataType.STRING, null, YN.Y, sdr3),
				new SrDataCondition("and port='{port}'", "{wherePort}", "Port", "{Port}", DataType.NUMBER, null, YN.N, sdr3)
		);

		sdr3.setSrDataConditions(conditions3);
		srDataRequestRepository.saveAndFlush(sdr3);


	}
}