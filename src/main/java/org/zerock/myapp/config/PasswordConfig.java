package org.zerock.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Configuration

// 비밀번호 암호화를 위한 설정 클래스
public class PasswordConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		log.trace("passwordEncoder() invoked.");
		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	} // passwordEncoder

} // end class
