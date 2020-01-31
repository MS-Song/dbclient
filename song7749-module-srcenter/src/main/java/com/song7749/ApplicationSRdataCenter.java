package com.song7749;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.song7749" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableJpaRepositories
@EnableCaching
public class ApplicationSRdataCenter {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ApplicationSRdataCenter.class)
			.properties("spring.config.location="
					+ "classpath:/mvc-application.yml"
					+ ",classpath:/dbclient-application.yml"
					+ ",classpath:/incident-application.yml"
					+ ",classpath:/srcenter-application.yml")
			.run(args);
	}
}