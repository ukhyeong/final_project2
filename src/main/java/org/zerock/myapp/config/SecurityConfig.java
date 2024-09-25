package org.zerock.myapp.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@NoArgsConstructor


@Configuration
public class SecurityConfig {	// @Since 2.5.x & 3.x 에서 만드는 보안설정 클래스 Stub
	@Autowired private PasswordEncoder encoder;
	@Autowired private DataSource dataSource;
	
		
	@PostConstruct
	void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.encoder);
		log.info("\t+ this.encoder: {}", this.encoder);
		
		Objects.requireNonNull(this.dataSource);
		log.info("\t+ this.dataSource: {}", this.dataSource);
	} // postConstruct
	
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		log.trace("webSecurityCustomizer() invoked.");
		
		return web -> web.debug(false);	// 여러설정항목중에, 디버그모드만 설정(ON)
//		return web -> web.debug(false).ignoring().anyRequest();
	} // webSecurityCustomizer
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.trace("securityFilterChain() invoked.");

		// -----------------------------------------------
		// Step1. 요청자원에 대한 접근통제 설정(모두 허용, 인증된 사용자만 허용, 학생만 허용, 교수만 허용)
		// -----------------------------------------------
		
		http.authorizeHttpRequests(
			customizer -> 
				customizer
					// permitAll
					.requestMatchers("/permitAll/signIn").permitAll()
					.requestMatchers("/permitAll/signUp").permitAll()
					.requestMatchers("/login").permitAll()
					
					// permitAll - test
					.requestMatchers("/temp").permitAll()
					
					// permitAll - exception
					.requestMatchers("/exception/SQLException").permitAll()
					.requestMatchers("/exception/BoardException").permitAll()
					.requestMatchers("/exception/IllegalArgumentException").permitAll()
					
					// authenticated
					.requestMatchers("/auth/main").authenticated()
					.requestMatchers("/auth/myPage").authenticated()
					.requestMatchers("/auth/403").authenticated()
					.requestMatchers("/logout").authenticated()	  
					
					// ROLE == STUDENT
					.requestMatchers("/student/checkApplyCourse").hasAuthority("ROLE_STUDENT")
					.requestMatchers("/student/courseApply").hasAuthority("ROLE_STUDENT")
					.requestMatchers("/student/checkGrade").hasAnyRole("STUDENT")
					
					// ROLE == PROFESSOR
					.requestMatchers("/professor/courseOpen").hasAuthority("ROLE_PROFESSOR")
					.requestMatchers("/professor/checkOpenCourse").hasAuthority("ROLE_PROFESSOR")
					.requestMatchers("/professor/gradeEvaluation").hasAnyRole("PROFESSOR")
					.requestMatchers("/professor/checkEvaluateGrade").hasAnyRole("PROFESSOR")
		);	// .authorizeHttpRequests
		

		// -----------------------------------------------
		// Step2. 로그인 커스텀 설정
		// -----------------------------------------------
		
		// 로그인 기본 설정, Login URL : /login 으로 기본 설정됨
		// 기본설정 로그인에 성공하면 최초에 요청했던 URL 로 리다이렉트 
//		http.formLogin(Customizer.withDefaults());				// Default Login URL : /login	
		
		http.formLogin(
			customizer -> 
				customizer
					// (1) 인증되지 않은 상태로 임의의 URI 접근시 "/permitAll/customLogin" 로 리다이렉트
					.loginPage("/permitAll/signIn")
					// (2) 로그인 폼이 제출되면 "/login URL" 로 인증요청이 전송
					// +++ 로그인 처리를 할 URL 을 "/login" 으로 지정이 더 알맞은 설명 이지 않을까
					.loginProcessingUrl("/login")
					// (3) 인증후에 "/auth/main" 로 리다이렉트 -> false, 무시하고 최초 요청한 URL 로 리다이렉트 -> true
					.defaultSuccessUrl("/auth/main", true)
					// (4) 인증실패시, 다시 커스텀로그인으로 요청
					.failureUrl("/permitAll/signIn")
		); // .formLogin

		
		// -----------------------------------------------
		// Step3. 로그아웃 커스텀 설정
		// -----------------------------------------------

		// 로그아웃 기본 설정, Logout URL : /logout 으로 기본 설정됨
		// 로그아웃 처리 완료되면, 로그인 화면으로 다시 이동
//		http.logout(Customizer.withDefaults());					// Default Login URL : /logout	
		
		// CSRF (Cross-Site Request Forgery) 해킹공격방어 비활성화 설정 (필수!)
		http.csrf(customizer -> customizer.disable());	//	<--- 추가하세요!!!!

		http.logout(
			customizer ->
				// 최소한의 설정으로, 아래에서 (4)와 (5)는 설정되어야 합니다.
				customizer
					// (1) 로그인 후 생성된 인증 정보를 삭제합니다. 
					//     이는 로그아웃 시 사용자의 인증 정보를 제거하여, 재인증이 필요한 상태로 만듭니다.
					.clearAuthentication(true)
					// (2) 로그인 성공후 생성된 세션 금고상자(Session Scope 공유영역) 파괴
					//     로그인 후 생성된 HTTP 세션을 무효화합니다. 해당 세션에 저장된 정보가 모두 지워지도록 합니다.
					.invalidateHttpSession(true)
					// (3) WAS 인 Apache Tomcat 이, 최초요청을 보낸 웹브라우저에 지어주는 이름을
					//     해당 브라우저에 전달할 때 사용하는 쿠키 이름
					//     이는 세션 식별자 쿠키로, 로그아웃 시 해당 쿠키를 삭제하여 세션을 종료합니다.
					.deleteCookies("JSESSIONID")
					// (4) 실제 로그아웃처리를 수행하는 주소설정 (기본: 시큐리티 로그아웃처리주소)
					//     클라이언트가 이 URL로 로그아웃 요청을 보내면, Spring Security 가 로그아웃을 처리합니다.
					.logoutUrl("/logout")
					// (5) 로그아웃처리가 성공하면, 다시 커스텀 로그인 화면으로 이동
					.logoutSuccessUrl("/permitAll/signIn")
		); // .logout

		
		// -----------------------------------------------
		// Step4. 403 Forbidden 발생시(접근권한이 없는 페이지를 요청했을 때), 사용자에게 보여줄 화면에 대한 커스터마이징
		// -----------------------------------------------
		
		// 403 오류발생시, 보여줄 화면을 만들어낼 요청 URI 설정
		// ***** 다만 권한이 없다고 해서 꼭 페이지 이동을 해야할까.. 그냥 팝업창 정도로 해결할수는 없는가 *****
		http.exceptionHandling(cutomizer -> cutomizer.accessDeniedPage("/auth/403"));
		
		return http.build();
	} // securityFilterChain
	
	
	// -----------------------------------------------
	// JDBC기반의 사용자 인증 관리를 위하여, 필요한 메소드
	// -----------------------------------------------
	
	// 로그인한 사용자의 Role(역할) 정보는 Spring Security의 Security Context에 저장됩니다.
	@Autowired
	void authenticate(AuthenticationManagerBuilder auth) throws Exception {
		log.trace("authenticate({}) invoked.", auth);

		// -------------------------
		// 서비스에서 만든 사용자 테이블을 이용한 사용자 인증관리 설정
		// -------------------------
		
		final String userQuery = "SELECT user_id as username, password, true FROM uzer WHERE user_id = ?";
		final String authorityQuery = "SELECT user_id as username, upper(role) as authority FROM uzer WHERE user_id =?";
		
		auth.jdbcAuthentication()
			.dataSource(this.dataSource)
//			.passwordEncoder(null)			// 사용자 회원테이블의 암호는 해쉬가 미적용 -> with "{noop}"
			.rolePrefix("ROLE_")
			.usersByUsernameQuery(userQuery)
			.authoritiesByUsernameQuery(authorityQuery);
	} // authenticate
	

} // end class
