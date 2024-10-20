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
//@Since 2.5.x & 3.x 에서 만드는 보안설정 클래스 Stub
public class SecurityConfig {	
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
	} // webSecurityCustomizer
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.trace("securityFilterChain() invoked.");

		// -----------------------------------------------
		// Step1. 요청자원에 대한 접근통제 설정(all, auth, professor, student)
		// -----------------------------------------------
		
		http.authorizeHttpRequests(
			customizer -> 
				customizer
					// permitAll
					.requestMatchers("/all/signIn").permitAll()
					.requestMatchers("/all/signUp").permitAll()
					.requestMatchers("/all/joinBoy").permitAll()
					.requestMatchers("/all/403").permitAll()
					.requestMatchers("/login").permitAll()
					.requestMatchers("/favicon.ico").permitAll()
					
					// permitAll - test
					.requestMatchers("/temp").permitAll()
					.requestMatchers("/api/mappings").permitAll()
					
					// authenticated
					.requestMatchers("/auth/main").authenticated()
					.requestMatchers("/auth/myPage").authenticated()
					.requestMatchers("/auth/getBoard").authenticated()
					.requestMatchers("/auth/getBoardList").authenticated()
					.requestMatchers("/logout").authenticated()	  
					
					// ROLE == STUDENT
					.requestMatchers("/student/checkApplyCourse").hasAuthority("ROLE_STUDENT")
					.requestMatchers("/student/courseApply").hasAuthority("ROLE_STUDENT")
					.requestMatchers("/student/checkGrade").hasAnyRole("STUDENT")
					
					// ROLE == PROFESSOR
					.requestMatchers("/professor/lectureOpen").hasAuthority("ROLE_PROFESSOR")
					.requestMatchers("/professor/checkOpenLecture").hasAuthority("ROLE_PROFESSOR")
					.requestMatchers("/professor/gradeEvaluation").hasAnyRole("PROFESSOR")
					.requestMatchers("/professor/checkEvaluateGrade").hasAnyRole("PROFESSOR")
					
					// ROLE == PROFESSOR, for Board
					.requestMatchers("/professor/registerBoard").hasAuthority("ROLE_PROFESSOR")
					.requestMatchers("/professor/updateBoard").hasAuthority("ROLE_PROFESSOR")
					.requestMatchers("/professor/deleteBoard").hasAuthority("ROLE_PROFESSOR")
					.requestMatchers("/professor/updateBoardView").hasAuthority("ROLE_PROFESSOR")
					.requestMatchers("/professor/registerBoardView").hasAuthority("ROLE_PROFESSOR")
		);	// .authorizeHttpRequests
		

		// -----------------------------------------------
		// Step2. 로그인 커스텀 설정
		// -----------------------------------------------
		
		http.formLogin(
				customizer -> 
					customizer
						// (1) 인증되지 않은 상태로 임의의 URI 접근시 "/all/signIn" 로 리다이렉트
						.loginPage("/all/signIn")
						// (2) 로그인 폼이 제출되면 "/login URL" 로 인증요청이 전송
						// + 실제 로그인 처리를 하는 URL 을 "/login" 으로 지정
						// 클라이언트가 이 URL로 로그아웃 요청을 보내면, Spring Security 가 로그아웃을 처리합니다.
						.loginProcessingUrl("/login")
						// (3) 인증후에 "/auth/main" 로 리다이렉트 -> true, 무시하고 최초 요청한 URL 로 리다이렉트 -> false
						.defaultSuccessUrl("/auth/main", true)
						// (4) 인증실패시, 다시 로그인 요청
						.failureUrl("/all/signIn")
			); // .formLogin

		
		// -----------------------------------------------
		// Step3. 로그아웃 커스텀 설정
		// -----------------------------------------------
		
		// CSRF (Cross-Site Request Forgery) 해킹공격방어 비활성화 설정 (필수)
		http.csrf(customizer -> customizer.disable());	

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
					// (4) 실제 로그아웃 처리를 하는 URL 을 "/logout" 으로 지정
					//     클라이언트가 이 URL로 로그아웃 요청을 보내면, Spring Security 가 로그아웃을 처리합니다.
					.logoutUrl("/logout")
					// (5) 로그아웃처리가 성공하면, 다시 커스텀 로그인 화면으로 이동
					.logoutSuccessUrl("/all/signIn")
		); // .logout

		
		// -----------------------------------------------
		// Step4. 403 Forbidden 발생시, 사용자에게 보여줄 화면
		// -----------------------------------------------
		
		// 접근권한이 없는 페이지 방문시 이동할 페이지 설정
		http.exceptionHandling(cutomizer -> cutomizer.accessDeniedPage("/all/403"));
		
		return http.build();
	} // securityFilterChain
	
	
	// -----------------------------------------------
	// JDBC기반의 사용자 인증 관리를 위하여, 필요한 메소드
	// -----------------------------------------------
	
	// Spring Security 에서 사용자 인증(로그인)과 권한(역할)을 처리하기 위한 설정
	@Autowired
	void authenticate(AuthenticationManagerBuilder auth) throws Exception {
		log.trace("authenticate({}) invoked.", auth);
		
		// uzer 테이블에서 user_id, password, 활성화 여부를 조회합니다.
		final String userQuery = "SELECT user_id as username, password, true FROM uzer WHERE user_id = ?";
		// uzer 테이블에서 user_id에 해당하는 사용자의 role(역할)을 조회합니다.
		final String authorityQuery = "SELECT user_id as username, upper(role) as authority FROM uzer WHERE user_id =?";
		
		// Spring Security가 데이터베이스에서 인증 정보를 조회할 수 있도록 JDBC 기반 인증을 사용
		auth.jdbcAuthentication()
			// 인증에 사용할 데이터베이스 연결을 제공합니다. (H2 데이터베이스)
			.dataSource(this.dataSource)
			// 모든 역할 앞에 ROLE_이라는 접두어를 붙여 Spring Security 의 권한 모델과 일치시킵니다.
			.rolePrefix("ROLE_")
			// 지정된 userQuery를 사용해 사용자 인증을 위한 정보를 조회합니다.
			.usersByUsernameQuery(userQuery)
			// 지정된 authorityQuery를 사용해 사용자의 권한(역할)을 조회합니다.
			.authoritiesByUsernameQuery(authorityQuery);
	} // authenticate
	

} // end class
