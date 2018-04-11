package com.song7749.dbclient.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.paths(PathSelectors.any())
				.build();
	}

	public ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("IncidentAlert")
				.description("지정된 조건이 발생하면, 대상자에게 Alert 을 보내는 서비스")
				.version("1.0.0")
				.contact(new Contact("Minsoo Song"
						,"https://github.com/MS-Song/InsidentAlert.git"
						,"song7749@gmail.com"))
				.build();
	}
}
