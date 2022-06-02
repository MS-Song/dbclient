package com.song7749.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song7749.ModuleCommonApplicationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SuppressWarnings("unchecked")
@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class, properties = "spring.config.location=classpath:/application.yml")
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VersionControllerTest {

    @Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

    @Autowired
    private WebApplicationContext ctx;
	
	MvcResult result;
	Map<String, Object> responseObject;

	@BeforeEach
	public void setup() throws Exception{
		// Mock MVC 설정 추가
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
        		.addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("S/W Version 조회")
    void testGetVersion() throws Exception {
            // give
            drb=get("/version/dbclient")
                .accept(MediaType.APPLICATION_JSON)
                .locale(Locale.KOREA)
    //			.param("charset", dv.getCharset().toString())
                ;
   
            // when
            result = mvc.perform(drb)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("httpStatus").value("200"))
                    .andDo(print())
                    .andReturn();
    
             responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);
    }
}
