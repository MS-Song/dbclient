package com.song7749.srcenter.repository;

import com.song7749.UnitTest;
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
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SrDataRequestRepositoryTest extends UnitTest {

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

    @Before
    public void setUp() throws Exception {

        memberRepository.saveAndFlush(member);
        databaseRepository.saveAndFlush(database);
    }

    @After
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
                DownloadLimitType.Daily,
                new Date(),
                new Date(),
                YN.Y,
                database,
                member,
                members);

        List<SrDataCondition> srDataConditions = new ArrayList<SrDataCondition>();
        srDataConditions.add(new SrDataCondition("검색", "search", DataType.STRING, "검색어", sdr));

        // 자식 객체 추가
        sdr.setSrDataConditions(srDataConditions);
        // when
        srDataRequestRepository.saveAndFlush(sdr);
        // then
        assertThat(sdr.getId(),notNullValue());
    }
}