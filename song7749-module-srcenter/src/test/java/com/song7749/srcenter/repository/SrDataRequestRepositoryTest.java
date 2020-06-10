package com.song7749.srcenter.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;
import com.song7749.srcenter.domain.SrDataCondition;
import com.song7749.srcenter.domain.SrDataRequest;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.type.DownloadLimitType;


@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = ModuleCommonApplicationTests.class, properties = "spring.config.location=classpath:/application.yml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SrDataRequestRepositoryTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SrDataRequestRepository srDataRequestRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    DatabaseRepository databaseRepository;

    @PersistenceContext
    EntityManager em;

    /**
     * fixture
     */
    Member member = new Member("song7749@incident.com"
            , "12345678"
            , "패스워드질문은?"
            , "패스워드답변은?"
            , "제일잘나가는팀"
            , "song7749"
            , AuthType.ADMIN);

    Database database = new Database("10.10.10.10"
            , "test server"
            , "dbTest"
            , "song7749"
            , "12345678"
            , DatabaseDriver.ORACLE
            , Charset.UTF8
            , "3333"
            ,"");

    @BeforeEach
    public void setUp() throws Exception {

        memberRepository.saveAndFlush(member);
        databaseRepository.saveAndFlush(database);
    }

    @AfterEach
    public void tearDown() throws Exception {

    }

    @Test
    public void testSave(){
        // give
        List<Member> members = new ArrayList<Member>();
        members.add(member);

        SrDataRequest sdr  = new SrDataRequest(
                "제목",
                "select 1 from dual",
                0,
                0,
                DownloadLimitType.DAILY,
                new Date(),
                new Date(),
                YN.Y,
                database,
                member,
                members);

        List<SrDataCondition> srDataConditions = new ArrayList<SrDataCondition>();
        srDataConditions.add(new SrDataCondition("and a=b","${SQL-KEY}", "검색", "a", DataType.DATE, "b", YN.Y, sdr));

        // 자식 객체 추가
        sdr.setSrDataConditions(srDataConditions);
        // when
        srDataRequestRepository.saveAndFlush(sdr);
        // then
        assertThat(sdr.getId(),notNullValue());
    }
}