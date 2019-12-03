package com.song7749;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.service.DatabaseManager;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberModifyDto;
import com.song7749.member.value.MemberVo;
import com.song7749.srcenter.service.SrDataReqeustService;
import com.song7749.srcenter.value.SrDataRequestFindDto;
import com.song7749.srcenter.value.SrDataRequestVo;
import com.song7749.util.LogMessageFormatter;
import com.song7749.util.StringUtils;
import com.song7749.web.ControllerTest;
import org.castor.util.Base64Decoder;
import org.castor.util.Base64Encoder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.castor.util.Base64Encoder.encode;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SrDataRequestControllerTest extends ControllerTest {

    Logger logger = LoggerFactory.getLogger(getClass());

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
    Database database = new Database(
            "jdbc:h2:file:~/dbclient",
            "DB CLIENT DB",
            "PUBLIC",
            "sa",
            "",
            DatabaseDriver.H2,
            Charset.UTF8,
            "");

    MemberVo vo;
    Cookie cookie;
    SrDataRequestVo srDataRequestVo;

    @Before
   public void setup() throws Exception{
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
   }

   @Test
    public void allTests() throws Exception {
        add();
        modifyBeforeConfirm();
        confirm();
        list();
        one();
        modifyAfterConfirm();
        runNow();
    }

    public void add() throws Exception{
       String runSQL = new String(encode(URLEncoder.encode("select * from member where 1=1 ","UTF-8").getBytes(String.valueOf(Charset.UTF8))));
       String[] conditionWhereSqls = {
               new String(encode(URLEncoder.encode("AND member_id='{member_id}'","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
               new String(encode(URLEncoder.encode("AND name='{name}'","UTF-8").getBytes(String.valueOf(Charset.UTF8))))
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
                .param("conditionWhereSql",     conditionWhereSqls[0],conditionWhereSqls[1])
                .param("conditionName",         "회원번호","상명")
                .param("conditionKey",          "{member_id}","{name}")
                .param("conditionType",         "STRING","STRING")
                .param("conditionValue",        "","")
                .param("conditionRequired",     "Y","Y")
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

       // 등록된 SR Request 를 조회
       Pageable page = PageRequest.of(0, 10);
       Page<SrDataRequestVo> voPages = srDataReqeustService.find(new SrDataRequestFindDto(), page);
       srDataRequestVo = voPages.getContent().get(0);
       logger.info(LogMessageFormatter.format("{}", "SrDAtaRequestVo Print"), voPages.getContent().get(0));

    }

    public void modifyBeforeConfirm() throws Exception {

        String runSQL = new String(encode(URLEncoder.encode("select * from member where 1=1 and 1=1 ","UTF-8").getBytes(String.valueOf(Charset.UTF8))));
        String[] conditionWhereSqls = {
                new String(encode(URLEncoder.encode("AND member_id='{member_id}'","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
                new String(encode(URLEncoder.encode("AND login_id='{login_id}'","UTF-8").getBytes(String.valueOf(Charset.UTF8)))),
                new String(encode(URLEncoder.encode("AND name='{name}'","UTF-8").getBytes(String.valueOf(Charset.UTF8))))
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
                .param("conditionName",         "회원번호","로그인ID","성명")
                .param("conditionKey",          "{member_id}","{login_id}","{name}")
                .param("conditionType",         "STRING","STRING","STRING")
                .param("conditionValue",        "","","")
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

    public void modifyAfterConfirm() throws Exception {

        drb=put("/srDataRequest/modifyAfterConfirm").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                    srDataRequestVo.getId().toString())
                .param("subject",               "제목입니다제목입니다제목입니다제목입니다제목입니다")
                .param("enableYN",              "Y")
                .param("downloadLimit",         "0")
                .param("downloadLimitType",     "HOURLY")
                .param("downloadStartDate",     "2019-12-31")
                .param("downloadEndDate",       "2020-12-31")
                .param("databaseId",            database.getId().toString())
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


    public void list() throws Exception {
        String runSQL = new String(encode(URLEncoder.encode("select * from member where 1=1 and 1=1 ","UTF-8").getBytes(String.valueOf(Charset.UTF8))));
        drb=get("/srDataRequest/list").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                    srDataRequestVo.getId().toString())
                .param("subject",               "상품 데이터 추출 일자별")
                .param("runSql",                 runSQL)
                .param("confirmYN",             "Y")
                .param("enableYN",              "Y")
                .param("downloadLimit",         "0")
                .param("downloadLimitType",     "HOURLY")
                .param("fromCreateDate",        new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()-60*60*24*2000)))
                .param("toCreateDate",          new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()+60*60*24*2000)))
                .param("fromConfirmDate",        new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()-60*60*24*2000)))
                .param("toConfirmDate",         new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()+60*60*24*2000)))
//                .param("fromLastRunDate",        new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()-60*60*24*2000)))
//                .param("toLastRunDate",         new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()+60*60*24*2000)))
                .param("databaseId",            database.getId().toString())
                .param("resistMemberId",        vo.getId().toString())
                .param("confirmMemberId",       vo.getId().toString())
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
        assertThat(responseObject.get("rowCount"), equalTo(1));
        assertThat(responseObject.get("contents"), notNullValue());
    }

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

    public void runNow() throws Exception {
        drb=get("/srDataRequest/runNow").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("id",                    srDataRequestVo.getId().toString())
                .param("member_id",         "8")
                .param("login_id",            "song0002@test.com")
                .param("name",                "testMember2")
        ;

        // 로그인 cookie 정보 추가
        drb.cookie(cookie);

        // when
        result = mvc.perform(drb)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        logger.info(LogMessageFormatter.format("{}", "SR Data Request run now Contents"), responseObject);
        // then
        assertThat(responseObject.get("httpStatus"), equalTo(200));
        assertThat(responseObject.get("rowCount"), equalTo(1));
        assertThat(responseObject.get("contents"), notNullValue());
    }

}