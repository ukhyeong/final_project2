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
public class PasswordConfig {
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		log.trace("passwordEncoder() invoked.");
		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	} // passwordEncoder

} // end class
