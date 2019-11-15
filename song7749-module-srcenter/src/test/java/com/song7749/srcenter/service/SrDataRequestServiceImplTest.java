package com.song7749.srcenter.service;

import com.song7749.UnitTest;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.type.DownloadLimitType;
import com.song7749.srcenter.value.SrDataRequestAddDto;
import java.util.List;

import com.song7749.srcenter.value.SrDataRequestModifyBeforeConfirmDto;
import com.song7749.srcenter.value.SrDataRequestVo;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.util.calendar.LocalGregorianCalendar;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SrDataRequestServiceImplTest extends UnitTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    DatabaseRepository databaseRepository;

    @Autowired
    SrDataReqeustService srDataReqeustService;

    @Autowired
    ModelMapper mapper;

    /**
     * fixture
     */
    Member member = new Member(
            "song7749@incident.com"
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
            , "");

    List<String> names = new ArrayList<>();
    List<String> keys  = new ArrayList<>();
    List<String> values = new ArrayList<>();
    List<DataType> types = new ArrayList<>();
    List<Long> srDataAlowMemberIds = new ArrayList<>();

    SrDataRequestVo vo = null;

    @Before
    public void setUp() throws Exception {
        memberRepository.saveAndFlush(member);
        databaseRepository.saveAndFlush(database);

        names.add("검색");
        keys.add("변수명");
        values.add("값");
        types.add(DataType.DATE);
        srDataAlowMemberIds.add(new Long(member.getId()));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void allTests(){
        add();
        modifyBeforeConfirm();
    }

    public void add() {
        // give
        SrDataRequestAddDto dto = new SrDataRequestAddDto(
                "제목입니다제목입니다제목입니다제목입니다",
                "select 1 from dual",
                0,
                DownloadLimitType.HOURLY,
                new Date(),
                new Date(),
                database.getId(),
                member.getId(),
                names,
                keys,
                types,
                values,
                srDataAlowMemberIds);

        // when
        vo = srDataReqeustService.add(dto);

        // then
        assertThat(vo.getId(), notNullValue());

    }

    public void modifyBeforeConfirm(){
        // give
        names.add("검색2");
        keys.add("${조건2}");
        types.add(DataType.NUMBER);
        values.add("2000-01-01");
        srDataAlowMemberIds.add(member.getId());
        SrDataRequestModifyBeforeConfirmDto beforeDto = new SrDataRequestModifyBeforeConfirmDto(
                vo.getId(),
                "검색으로변경합니다.검색으로변경합니다.검색으로변경합니다.",
                "select sysdate from dual",
                YN.Y,
                0,
                DownloadLimitType.WEEKLY,
                new Date(),
                new Date(),
                database.getId(),
                names ,
                keys,
                types,
                values,
                srDataAlowMemberIds);

        // when
        vo = srDataReqeustService.modify(beforeDto);

        // then
        assertThat(vo.getSubject(),equalTo(beforeDto.getSubject()));
    }
 }