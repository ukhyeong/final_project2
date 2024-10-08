package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

// 인증(Authentication)과 관련된 요청을 처리하는 컨트롤러
@RequestMapping("/all/")		// Base URI
@Controller
public class PermitAllController {
	
	@GetMapping("/signIn")
	void signIn() {	
		log.trace("signIn() invoked.");
	} // signIn
	
	@GetMapping("/signUp")
	void signUp() {	
		log.trace("signUp() invoked.");		
	} // signUp
	
	@GetMapping("/403")
	void accessDenied() {
		log.trace("accessDenied() invoked.");		
	} // accessDenied
	
} // end class
