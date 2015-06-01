package com.song7749.app.mangofactory.swagger.configuration;

import static com.mangofactory.swagger.ScalaUtils.toOption;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import com.wordnik.swagger.model.ResponseMessage;

/**
 * <pre>
 * Class Name : CustomJavaPluginConfig.java
 * Description : Swagger API Document Configuration
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2014. 10. 8.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 10. 8.
 */

@Configuration
@EnableSwagger
@ComponentScan("com.song7749.app")
public class CustomJavaPluginConfig {

	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

	@SuppressWarnings("unchecked")
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {

		SwaggerSpringMvcPlugin plugin = new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
				.apiVersion("0.9")
				.apiInfo(apiInfo())
				.swaggerGroup("api")
				.pathProvider(swaggerPathProvider())
				.excludeAnnotations(ApiIgnore.class)
				.ignoredParameterTypes(ModelMap.class, HttpServletRequest.class)
				;
		// 기본 응답 추가
		Map<RequestMethod, List<ResponseMessage>> allResponseMessage = getResponseMessages();
		for (RequestMethod rm : allResponseMessage.keySet()) {
			plugin.globalResponseMessage(rm, allResponseMessage.get(rm));
		}

		return plugin;
	}

	/**
	 * API 기본 설명
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo(
			"DB Client API ",
			"DB Client 에서 사용 가능한 API 목록",
			"", "", "", "");
		return apiInfo;
	}

	/**
	 * API 기본 경로
	 * @return SwaggerPathProvider
	 */
	private SwaggerPathProvider swaggerPathProvider() {
		SwaggerPathProvider swaggerPathProvider = new SwaggerPathProvider() {

			@Override
			protected String getDocumentationPath() {
				return "/";
			}

			@Override
			protected String applicationPath() {
				return "/";
			}
		};
		return swaggerPathProvider;
	}

	/**
	 * 기본 응답 메세지
	 * @return Map<RequestMethod, List<ResponseMessage>>
	 */
	@SuppressWarnings("unchecked")
	private Map<RequestMethod, List<ResponseMessage>> getResponseMessages() {
		Map<RequestMethod, List<ResponseMessage>> responses = new HashMap<RequestMethod, List<ResponseMessage>>();

		responses.put(GET,
				asList(new ResponseMessage(OK.value(), OK.getReasonPhrase() + "<br/>[정상]",toOption("ResponseResult")),
						new ResponseMessage(NO_CONTENT.value(), NO_CONTENT.getReasonPhrase() + "<br/>[조회된 데이터 없음]",toOption("ResponseResult")),
//						new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase() + "<br/>[인증이 실패되었습니다.]", toOption("ResponseResult")),
//						new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase() + "<br/>[사용할수 없는 자원 입니다.]", toOption("ResponseResult")),
						new ResponseMessage(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase() + "<br/>[없는 자원]", toOption(null)),
						new ResponseMessage(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase() + "<br/>[서버오류. desc 에 오류 사유 포함]", toOption("ResponseResult"))
				));

		responses.put(PUT,
				asList(new ResponseMessage(OK.value(), OK.getReasonPhrase() + "<br/>[정상]",toOption("ResponseResult")),
//						new ResponseMessage(CREATED.value(), CREATED.getReasonPhrase(), toOption("ResponseResult")),
//						new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase() + "<br/>[인증 실패]", toOption("ResponseResult")),
//						new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase() + "<br/>[사용할수 없는 자원]", toOption("ResponseResult")),
						new ResponseMessage(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase() + "<br/>[없는 자원]", toOption(null)),
						new ResponseMessage(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase() + "[서버오류. desc 에 오류 사유 포함]", toOption("ResponseResult"))
				));

		responses.put(POST,
				asList(new ResponseMessage(OK.value(), OK.getReasonPhrase() + "<br/>[정상]",toOption("ResponseResult")),
//						new ResponseMessage(CREATED.value(), CREATED.getReasonPhrase(), toOption("ResponseResult")),
//						new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase() + "<br/>[인증 실패]", toOption("ResponseResult")),
//						new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase() + "<br/>[사용할수 없는 자원]", toOption("ResponseResult")),
						new ResponseMessage(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase() + "<br/>[없는 자원]", toOption(null)),
						new ResponseMessage(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase() + "<br/>[서버오류. desc 에 오류 사유 포함]", toOption("ResponseResult"))
				));

		responses.put(DELETE,
				asList(new ResponseMessage(OK.value(), OK.getReasonPhrase() + "<br/>[정상]",toOption("ResponseResult")),
//						new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase() + "<br/>[인증 실패]", toOption("ResponseResult")),
//						new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase() + "<br/>[사용할수 없는 자원]", toOption("ResponseResult")),
						new ResponseMessage(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase() + "<br/>[없는 자원]", toOption(null)),
						new ResponseMessage(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase() + "<br/>[서버오류. desc 에 오류 사유 포함]", toOption("ResponseResult"))
				));
		return responses;
	}

	/**
	 * Array to List
	 * @param responseMessages
	 * @return List<ResponseMessage>
	 */
	private List<ResponseMessage> asList(ResponseMessage... responseMessages) {
		List<ResponseMessage> list = new ArrayList<ResponseMessage>();
		for (ResponseMessage responseMessage : responseMessages) {
			list.add(responseMessage);
		}
		return list;
	}
}