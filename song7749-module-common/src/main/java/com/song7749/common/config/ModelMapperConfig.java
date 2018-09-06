package com.song7749.common.config;

import org.modelmapper.AbstractCondition;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
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
}
