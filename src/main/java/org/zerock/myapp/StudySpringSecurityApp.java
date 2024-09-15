package org.zerock.myapp;

import java.util.Arrays;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//@Log4j2
@Slf4j

@NoArgsConstructor

@EntityScan
@EnableJpaAuditing
@EnableJpaRepositories
@ConfigurationPropertiesScan

// Spring Security를 위해서 반드시 붙여야 하는 어노테이션으로
// 아무 클래스에나 붙일 수 있는게 아니라, "자바설정클래스"에만 붙일 수 있습니다.
// 즉, @Configuration 또는 @SpringBootConfiguration 어노테이션이 붙어 있는
// 클래스에만 붙일 수 있습니다. 대신, 한번만 붙이면 됩니다.
@EnableWebSecurity(debug = true)

@ServletComponentScan
@SpringBootApplication
public class StudySpringSecurityApp extends ServletInitializer {

	
	public static void main(String[] args) {
//		SpringApplication.run(StudySpringSecurityApp.class, args);	// 1st. method: 자동생성코드
		
		// -------
		
		SpringApplication app = new SpringApplication(StudySpringSecurityApp.class);		
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.setBannerMode(Mode.CONSOLE);
		app.addListeners(e -> log.trace("onApplicationEvent({})", e.getClass().getSimpleName()));
		app.run(args);
		
		log.trace("main({}) invoked.", Arrays.toString(args));
	} // main

	// git test
} // end class
