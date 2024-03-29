package com.song7749.srcenter.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.common.base.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.LoginAuthVo;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.type.DownloadLimitType;
import com.song7749.srcenter.value.SrDataRequestAddDto;
import com.song7749.srcenter.value.SrDataRequestConfirmDto;
import com.song7749.srcenter.value.SrDataRequestFindDto;
import com.song7749.srcenter.value.SrDataRequestModifyAfterConfirmDto;
import com.song7749.srcenter.value.SrDataRequestModifyBeforeConfirmDto;
import com.song7749.srcenter.value.SrDataRequestRemoveDto;
import com.song7749.srcenter.value.SrDataRequestVo;
import com.song7749.util.ProxyUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class, properties = "spring.config.location=classpath:/application.yml")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SrDataRequestServiceImplTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    DatabaseRepository databaseRepository;

    @Autowired
    SrDataReqeustService srDataReqeustService;

    @Autowired
    ModelMapper mapper;

    @Mock
    LoginSession loginSession;

    /**
     * fixture
     */
    Member member = Member.builder()
        .loginId("song7749@test.com")
        .password("12345678")
        .passwordQuestion("패스워드질문은?")
        .passwordAnswer("패스워드답변은?")
        .teamName("제일잘나가는팀")
        .name("song7749")
        .authType(AuthType.ADMIN)
        .build();


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
    List<String> whereSqls = new ArrayList<>();
    List<String> whereSqlKeys = new ArrayList<>();
    List<String> keys  = new ArrayList<>();
    List<String> values = new ArrayList<>();
    List<DataType> types = new ArrayList<>();
    List<YN> requireds = new ArrayList<>();
    List<Long> srDataAlowMemberIds = new ArrayList<>();

    SrDataRequestVo vo = null;

    @BeforeEach
    public void setUp() throws Exception {
        memberRepository.saveAndFlush(member);
        databaseRepository.saveAndFlush(database);
        whereSqls.add(new String(Base64.getEncoder().encode(URLEncoder.encode("and a=#{key}","UTF-8").getBytes(String.valueOf(Charset.UTF8)))));
        whereSqlKeys.add("${Key}");
        names.add("검색");
        keys.add("변수명");
        values.add(new String(Base64.getEncoder().encode(URLEncoder.encode("value1","UTF-8").getBytes(String.valueOf(Charset.UTF8)))));
        types.add(DataType.DATE);
        requireds.add(YN.Y);
        srDataAlowMemberIds.add(new Long(member.getId()));

        // Mock Object 설정을 먼저 하지 않으면, 생성된 객체를 overwrite 하는 문제가 있음.
        MockitoAnnotations.initMocks(this);

        // login session 에 대한 mock 테스트 설정
        loginSession = new LoginSession();
        loginSession.setSesseion(new LoginAuthVo(member.getId(),member.getAuthType()));
        ReflectionTestUtils.setField(ProxyUtils.unwrapProxy(srDataReqeustService), "loginSession", loginSession);

        // 등록은 선처리 하여, CRUD 가 원활이 구동되도록 처리 한다.
        add();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void add() throws UnsupportedEncodingException {

        // give
        SrDataRequestAddDto dto = new SrDataRequestAddDto(
                "제목입니다제목입니다제목입니다제목입니다",
                new String(Base64.getEncoder().encode(URLEncoder.encode("select sysdate from dual","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
                0,
                DownloadLimitType.HOURLY,
                new Date(),
                new Date(),
                database.getId(),
                member.getId(),
                whereSqls,
                whereSqlKeys,
                names,
                keys,
                types,
                requireds,
                values,
                srDataAlowMemberIds);

        // when
        vo = srDataReqeustService.add(dto);

        // then
        assertThat(vo.getId(), notNullValue());

    }


    @Test
    public void modifyBeforeConfirm() throws UnsupportedEncodingException {
        // give
        whereSqls.add(new String(Base64.getEncoder().encode(URLEncoder.encode("and a=#{key2}","UTF-8").getBytes(String.valueOf(Charset.UTF8)))));
        whereSqlKeys.add("${Key}");
        names.add("검색2");
        keys.add("변수명2");
        values.add(new String(Base64.getEncoder().encode(URLEncoder.encode("value2","UTF-8").getBytes(String.valueOf(Charset.UTF8)))));
        types.add(DataType.DATE);
        requireds.add(YN.Y);
        srDataAlowMemberIds.add(member.getId());
        SrDataRequestModifyBeforeConfirmDto beforeDto = new SrDataRequestModifyBeforeConfirmDto(
                vo.getId(),
                "검색으로변경합니다.검색으로변경합니다.검색으로변경합니다.",
                new String(Base64.getEncoder().encode(URLEncoder.encode("select sysdate from dual","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
                YN.Y,
                0,
                DownloadLimitType.DAILY,
                new Date(),
                new Date(),
                database.getId(),
                whereSqls,
                whereSqlKeys,
                names ,
                keys,
                types,
                values,
                requireds,
                srDataAlowMemberIds,
                member.getId());

        // when
        vo = srDataReqeustService.modify(beforeDto);

        // then
        assertThat(vo.getSubject(),equalTo(beforeDto.getSubject()));
        assertThat(vo.getSrDataConditionVos().size(),equalTo(beforeDto.getConditionName().size()));
    }


    @Test
    public void modifyAfterConfirm(){
        // give
        confirm();
        SrDataRequestModifyAfterConfirmDto dto = new SrDataRequestModifyAfterConfirmDto(
                vo.getId(),
                "테스트코드테스트코드테스트코드테스트코드테스트코드테스트코드테스트코드",
                YN.N,
                0,
                DownloadLimitType.DAILY,
                new Date(),
                new Date(),
                Arrays.asList(new Long(member.getId())),
                member.getId());

        // when
        vo = srDataReqeustService.modify(dto);

        // then
        assertThat(vo.getSubject(),equalTo(dto.getSubject()));
        assertThat(vo.getDownloadLimitType(),equalTo(dto.getDownloadLimitType()));


    }

    @Test
    public void confirm(){
        // give
        SrDataRequestConfirmDto dto = new SrDataRequestConfirmDto(
                vo.getId(),
                YN.Y,
                new Date(),
                member.getId());

        // when
        srDataReqeustService.confirm(dto);

        //then
        assertTrue(true);
    }

    @Test
    public void removeRequest(){
        // give
        SrDataRequestRemoveDto dto = new SrDataRequestRemoveDto(vo.getId(), vo.getResistMemberVo().getId());
        // when
        srDataReqeustService.remove(dto);
        // then
        assertTrue(true);
    }

    @Test
    public void testFindAll() {
        // give
        Pageable page = PageRequest.of(0, 10);//,Direction.DESC,"id");

        SrDataRequestFindDto dto = new SrDataRequestFindDto();
        dto.setId(vo.getId());
//        dto.setSubject(vo.getSubject());
//        dto.setRunSql(vo.getRunSql());
//        dto.setEnableYN(vo.getEnableYN());
//        dto.setConfirmYN(vo.getConfirmYN());
//        dto.setFromCreateDate(vo.getCreateDate());
//        dto.setToCreateDate(vo.getCreateDate());
//        dto.setFromConfirmDate(vo.getConfirmDate());
//        dto.setToConfirmDate(vo.getConfirmDate());
//        dto.setFromLastRunDate(vo.getLastRunDate());
//        dto.setToLastRunDate(vo.getLastRunDate());
//        dto.setLastErrorMessage(vo.getLastErrorMessage());
//        dto.setDatabaseId(vo.getDatabaseVo().getId());
//        dto.setResistMemberId(vo.getResistMemberVo().getId());
//        dto.setConfirmMemberId(vo.getConfirmMemberVo().getId());
//        dto.setSrDataAllowMemberIds(vo.getSrDataAllowMemberIds());

        // when
        Page<SrDataRequestVo> srDataRequestVos = srDataReqeustService.find(dto, page);
        // then
        assertThat(srDataRequestVos.getContent().size(),equalTo(1));

        //logger.info("{}", srDataRequestVos.getContent());
    }

    @Test
    public void testFindOne() {
        // give
        SrDataRequestFindDto dto = new SrDataRequestFindDto(vo.getId());
        // when
        SrDataRequestVo vo = srDataReqeustService.find(dto);
        // then
        assertThat(vo.getId(),equalTo(dto.getId()));
    }
}