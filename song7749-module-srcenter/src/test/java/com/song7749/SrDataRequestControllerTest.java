package com.song7749;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.song7749.web.ControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    DatabaseManager databaseManager;

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
    DatabaseAddDto dad = new DatabaseAddDto(
            "jdbc:h2:file:~/dbclient",
            "DB Client Local Database",
            "PUBLIC",
            "sa",
            "1234",
            DatabaseDriver.H2,
            Charset.UTF8,
            "1234");

    MemberVo vo;
    DatabaseVo dv;
    Cookie cookie;

    @Before
    public void setup() throws Exception{
        // 테스트를 위한 회원 등록
        vo = memberManager.addMemeber(dto);
        // 테스트를 위한 databae 등록
        dv = databaseManager.addDatabase(dad);

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
    public void add() throws Exception{
        drb=post("/srDataRequest/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
                .param("subject",               "제목입니다제목입니다제목입니다제목입니다제목입니다")
                .param("runSql",                "select * from member where 1=1 ")
                .param("downloadLimit",         "0")
                .param("downloadLimitType",     "HOURLY")
                .param("downloadStartDate",     "2019-12-31")
                .param("downloadEndDate",       "2020-12-31")
                .param("databaseId",            dv.getId().toString())
                .param("memberId",              vo.getId().toString())
                .param("conditionWhereSql",     " AND member_id={member_id}")
                .param("conditionName",         "회원번호")
                .param("conditionKey",          "{member_id}")
                .param("conditionType",         "DATE")
                //.param("conditionValue",        "2017-12-12")
                .param("conditionRequired",     "Y")
                .param("srDataAllowMemberIds",  vo.getId().toString())

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


    }
}