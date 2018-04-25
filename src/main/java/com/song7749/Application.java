

package com.song7749;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.modelmapper.AbstractCondition;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.filter.OrderedHttpPutFormContentFilter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.HttpPutFormContentFilter;

@SpringBootApplication(scanBasePackages = { "com.song7749" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableJpaRepositories
@EnableCaching
@PropertySource("classpath:/config/config.properties")
public class Application {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 후 처리 정의
	 */
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}


	/**
	 * ModelMapper config
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {

		// string blank condition
		Condition<?, ?> isStringBlank = new AbstractCondition<Object, Object>() {
		    @Override
			public boolean applies(MappingContext<Object, Object> context) {
		    	if(context.getSource() instanceof String) {
			    	return null!=context.getSource() && !"".equals(context.getSource());
		    	} else {
		    		return context.getSource() != null;
		    	}
		    }
		};

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setSkipNullEnabled(true)								// null 인 필드는 제거
				.setPropertyCondition(isStringBlank)					// String Blank 제거
				.setSourceNamingConvention(NamingConventions.NONE)		// 소스측 필드 네이밍 설정 초기화
				.setDestinationNamingConvention(NamingConventions.NONE)	// 타겟측 필드 네이밍 설정 초기화
				.setMatchingStrategy(MatchingStrategies.STRICT)			// 정확한 명칭 매핑만 허용
				;
		return modelMapper;
	}

	/**
	 * validate 설정
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	/**
	 * PUT Method 를  request param 으로 받기 위한 설정
	 * @return
	 */
	@Bean
	public HttpPutFormContentFilter httpPutFormFilter() {
		return new HttpPutFormContentFilter();
	}

	/**
	 * PUT Method 를  request param 으로 받기 위한 설정
	 * @return
	 */
	@Bean
	public OrderedHttpPutFormContentFilter orderedHttpPutFormContentFilter() {
		return new OrderedHttpPutFormContentFilter();
	}

	/**
	 * App Start
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}