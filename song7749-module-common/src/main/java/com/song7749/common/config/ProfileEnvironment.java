package com.song7749.common.config;

import static com.song7749.util.LogMessageFormatter.format;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * <pre>
 * Class Name : ProfileEnvironment.java
 * Description : Profile 환경 변수
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 8. 28.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 8. 28.
 */
@Configuration
public class ProfileEnvironment {

	Logger logger = LoggerFactory.getLogger(getClass());

	private static String profile;

	public ProfileEnvironment() {}

	/**
	 * Profile environment.
	 */
	@Autowired
	public ProfileEnvironment(Environment environment) {
		profile = ArrayUtils.isNotEmpty(environment.getActiveProfiles()) ? environment.getActiveProfiles()[0] : null;
		logger.info(format("{}", "Active Profile"),profile);
		logger.info(format("{}", "EVN allow-bean-definition-overriding Loading"), environment.getProperty("spring.main.allow-bean-definition-overriding"));
	}

	/**
	 * Gets the profile.
	 *
	 * @return the profile
	 */
	public static String getProfile() {
		return profile;
	}
}
