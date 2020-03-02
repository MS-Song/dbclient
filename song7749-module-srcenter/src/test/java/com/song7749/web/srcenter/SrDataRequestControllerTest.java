package com.song7749.web.srcenter;

import static org.castor.util.Base64Encoder.encode;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song7749.ModuleCommonApplicationTests;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberModifyDto;
import com.song7749.member.value.MemberVo;
import com.song7749.srcenter.service.SrDataReqeustService;
import com.song7749.srcenter.value.SrDataRequestVo;

@SuppressWarnings("unchecked")
@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class, properties = "spring.config.location=classpath:/srcenter-application.yml")
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SrDataRequestControllerTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebApplicationContext ctx;
    
    @Autowired
    private MockMvc mvc;

    private MockHttpServletRequestBuilder drb;

    MvcResult result;
    Map<String, Object> responseObject;

    @Autowired
    MemberManager memberManager;

    @Autowired
    DatabaseRepository databaseRepository;

    @Autowired
    SrDataReqeustService srDataReqeustService;

    // 테스트를 위한 회원 등록
    MemberAddDto dto = new MemberAddDto(
            "song1234@gmail.com"
            , "123456789"
            , "passwordQuestion"
            , "passwordAnswer"
            , "teamName"
            , "name"
            , "123-123-1234");

    // 테스트를 위한 DB 등록
    Database database;

    MemberVo vo;
    Cookie cookie;
    SrDataRequestVo srDataRequestVo;

   @BeforeEach
   public void setup() throws Exception{
		// Mock MVC 설정 추가
       mvc = MockMvcBuilders.webAppContextSetup(ctx)
       		.addFilters(new CharacterEncodingFilter("UTF-8", true))
               .alwaysDo(print())
               .build();

        database = new Database(
                "jdbc:h2:mem:~/dbclient",
                "DB CLIENT DB",
                "PUBLIC",
                "sa",
                "",
                DatabaseDriver.H2,
                Charset.UTF8,
                "");

        // 테스트를 위한 회원 등록
        vo = memberManager.addMemeber(dto);
        // 테스트를 위한 database 등록
        databaseRepository.saveAndFlush(database);

        //관리자만 변경 가능함으로 관라지로 업데이트 처리
        MemberModifyDto mmbaDto = new MemberModifyByAdminDto(
                vo.getId(),
                AuthType.ADMIN);
        memberManager.modifyMember(mmbaDto);

        // give - 로그인 실행
        drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("loginId", dto.getLoginId())
                .param("password", dto.getPassword())
        ;
        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andReturn();
        // login cookie 생성
        cookie = new Cookie("cipher", result.getResponse().getCookie("cipher").getValue());
        
        // 기초 데이터 적재
        add();
   }

    @Test
    public void add() throws Exception{
       String runSQL = new String(encode(URLEncoder.encode("select * from member where 1=1 {SQL1} {SQL2} {SQL3} ","UTF-8").getBytes(String.valueOf(Charset.UTF8))));
       String[] conditionWhereSqls = {
               new String(encode(URLEncoder.encode("AND member_id='{member_id}'","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
               new String(encode(URLEncoder.encode("AND name='{name}'","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
               new String(encode(URLEncoder.encode("AND login_id='{login_id}'","UTF-8").getBytes(String.valueOf(Charset.UTF8))))
       };
        String[] conditionValues = {
                new String(""),
                new String(encode(URLEncoder.encode("1^송민수|2^송민수","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
                new String(encode(URLEncoder.encode("select member_id as KEY, login_id as VALUE from member","UTF-8").getBytes(String.valueOf(Charset.UTF8))))
        };

        drb=post("/srDataRequest/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("subject",               "제목입니다제목입니다제목입니다제목입니다제목입니다")
                .param("runSql",                 runSQL)
                .param("downloadLimit",         "0")
                .param("downloadLimitType",     "HOURLY")
                .param("downloadStartDate",     "2019-12-31")
                .param("downloadEndDate",       "2020-12-31")
                .param("databaseId",            database.getId().toString())
                .param("memberId",              vo.getId().toString())
                .param("conditionWhereSql",     conditionWhereSqls[0],conditionWhereSqls[1],conditionWhereSqls[2])
                .param("conditionWhereSqlKey",  "{SQL1}","{SQL2}","{SQL3}")
                .param("conditionName",         "회원번호","상명","로그인ID")
                .param("conditionKey",          "{member_id}","{name}","{login_id}")
                .param("conditionType",         "STRING","ARRAY","SQL")
                .param("conditionValue",        conditionValues[0],conditionValues[1],conditionValues[2])
                .param("conditionRequired",     "Y","Y","Y")
                .param("srDataAllowMemberIds",  vo.getId().toString(),vo.getId().toString(),vo.getId().toString())
        ;

        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();



        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        // then
        assertThat(responseObject.get("httpStatus"), equalTo(200));
        assertThat(responseObject.get("rowCount"), notNullValue());
        assertThat(responseObject.get("message"), equalTo("SR Data Request 등록이 완료되었습니다."));
        assertThat(responseObject.get("contents"), notNullValue());
        
        // ID 조회
        drb=get("/srDataRequest/list").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA);
        drb.cookie(cookie);
        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        logger.debug("{}", ((Map)((List)((Map)responseObject.get("contents")).get("content")).get(0)).get("id") );
        
        srDataRequestVo = new SrDataRequestVo();
        srDataRequestVo.setId(Long.valueOf((Integer) ((Map)((List)((Map)responseObject.get("contents")).get("content")).get(0)).get("id")));
    }

    @Test
    public void modifyBeforeConfirm() throws Exception {

    	String runSQL = new String(encode(URLEncoder.encode("select * from member where 1=1 {SQL1} {SQL2} {SQL3}","UTF-8").getBytes(String.valueOf(Charset.UTF8))));
        String[] conditionWhereSqls = {
                new String(encode(URLEncoder.encode("AND member_id='{member_id}'","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
                new String(encode(URLEncoder.encode("AND name='{name}'","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
                new String(encode(URLEncoder.encode("AND login_id='{login_id}'","UTF-8").getBytes(String.valueOf(Charset.UTF8))))
        };
        String[] conditionValues = {
                new String(""),
                new String(encode(URLEncoder.encode("1^송민수|2^송민수","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
                new String(encode(URLEncoder.encode("select member_id as KEY, login_id as VALUE from member","UTF-8").getBytes(String.valueOf(Charset.UTF8))))
        };
        drb=put("/srDataRequest/modifyBeforeConfirm").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                    srDataRequestVo.getId().toString())
                .param("subject",               "상품 데이터 추출 일자별")
                .param("runSql",                 runSQL)
                .param("enableYN",              "Y")
                .param("downloadLimit",         "0")
                .param("downloadLimitType",     "HOURLY")
                .param("downloadStartDate",     "2019-12-31")
                .param("downloadEndDate",       "2020-12-31")
                .param("databaseId",            database.getId().toString())
                .param("memberId",              vo.getId().toString())
                .param("conditionWhereSql",     conditionWhereSqls[0],conditionWhereSqls[1],conditionWhereSqls[2])
                .param("conditionWhereSqlKey",  "{SQL1}","{SQL2}","{SQL3}")
                .param("conditionName",         "회원번호","상명","로그인ID")
                .param("conditionKey",          "{member_id}","{name}","{login_id}")
                .param("conditionType",         "STRING","ARRAY","SQL")
                .param("conditionValue",        conditionValues[0],conditionValues[1],conditionValues[2])
                .param("conditionRequired",     "Y","Y","Y")
                .param("srDataAllowMemberIds",  vo.getId().toString(),vo.getId().toString(),vo.getId().toString())
        ;


        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        // then
        assertThat(responseObject.get("httpStatus"), equalTo(200));
        assertThat(responseObject.get("rowCount"), notNullValue());
        assertThat(responseObject.get("message"), equalTo("SR Data Request 수정이 완료되었습니다."));
        assertThat(responseObject.get("contents"), notNullValue());
    }

    @Test
    public void confirm() throws Exception {
        drb=put("/srDataRequest/confirm").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                    srDataRequestVo.getId().toString())
                .param("confirmYN",             "Y")
        ;

        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        // then
        assertThat(responseObject.get("httpStatus"), equalTo(200));
        assertThat(responseObject.get("message"), equalTo("SR data Request 승인이 완료 되었습니다."));
    }

    @Test
    public void modifyAfterConfirm() throws Exception {

        drb=put("/srDataRequest/modifyAfterConfirm").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                    srDataRequestVo.getId().toString())
                .param("subject",               "제목입니다제목입니다제목입니다제목입니다제목입니다")
                .param("enableYN",              "Y")
                .param("downloadLimit",         "0")
                .param("downloadLimitType",     "HOURLY")
                .param("downloadStartDate",     "2019-12-31")
                .param("downloadEndDate",       "2020-12-31")
                .param("memberId",              vo.getId().toString())
                .param("srDataAllowMemberIds",  vo.getId().toString(),vo.getId().toString(),vo.getId().toString())
        ;


        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        // then
        assertThat(responseObject.get("httpStatus"), equalTo(200));
        assertThat(responseObject.get("rowCount"), notNullValue());
        assertThat(responseObject.get("message"), equalTo("SR Data Request 수정이 완료되었습니다."));
        assertThat(responseObject.get("contents"), notNullValue());
    }

    @Test
    public void list() throws Exception {
//        String runSQL = new String(encode(URLEncoder.encode("select * from member where 1=1 ","UTF-8").getBytes(String.valueOf(Charset.UTF8))));
        drb=get("/srDataRequest/list").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                    srDataRequestVo.getId().toString())
//                .param("subject",               "상품 데이터 추출 일자별")
//                .param("runSql",                 runSQL)
//                .param("confirmYN",             "Y")
//                .param("enableYN",              "Y")
//                .param("downloadLimit",         "0")
//                .param("downloadLimitType",     "HOURLY")
//                .param("fromCreateDate",        new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()-60*60*24*2000)))
//                .param("toCreateDate",          new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()+60*60*24*2000)))
//                .param("fromConfirmDate",        new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()-60*60*24*2000)))
//                .param("toConfirmDate",         new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()+60*60*24*2000)))
//                .param("fromLastRunDate",        new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()-60*60*24*2000)))
//                .param("toLastRunDate",         new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()+60*60*24*2000)))
//                .param("databaseId",            database.getId().toString())
//                .param("resistMemberId",        vo.getId().toString())
//                .param("confirmMemberId",       vo.getId().toString())
//                .param("srDataAllowMemberIds",  vo.getId().toString(),vo.getId().toString(),vo.getId().toString())
        ;


        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        // then
        assertThat(responseObject.get("httpStatus"), equalTo(200));
        assertThat(responseObject.get("rowCount"), equalTo(1));
        assertThat(responseObject.get("contents"), notNullValue());
    }

    @Test
    public void one() throws Exception {
        drb=get("/srDataRequest/one").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                    srDataRequestVo.getId().toString())
        ;

        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        // then
        assertThat(responseObject.get("httpStatus"), equalTo(200));
        assertThat(responseObject.get("contents"), notNullValue());
    }

    @Disabled
    @Test
    public void searchFromCreate() throws Exception {
        drb=get("/srDataRequest/searchFromCreate").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                  srDataRequestVo.getId().toString())
        ;

        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        // then
        assertThat(responseObject.get("httpStatus"), equalTo(200));
//        assertThat(responseObject.get("rowCount"), equalTo(1));
//        assertThat(responseObject.get("contents"), notNullValue());
    }

    @Disabled
    @Test
    public void runNow() throws Exception {
        drb=get("/srDataRequest/runNow").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                  srDataRequestVo.getId().toString())
                .param("member_id",           vo.getId().toString())
                .param("login_id",            vo.getLoginId())
                .param("name",                vo.getName())
        ;

        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        // then
        assertThat(responseObject.get("httpStatus"), equalTo(204));
//        assertThat(responseObject.get("rowCount"), equalTo(1));
//        assertThat(responseObject.get("contents"), notNullValue());
    }
}