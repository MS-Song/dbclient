package com.song7749;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.song7749" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableJpaRepositories
@EnableCaching
public class ApplicationIncidentAlarm {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ApplicationIncidentAlarm.class)
			.properties("spring.config.location="
					+ "classpath:/mvc-application.yml"
					+ ",classpath:/dbclient-application.yml"
					+ ",classpath:/incident-application.yml")
			.run(args);
	}
}