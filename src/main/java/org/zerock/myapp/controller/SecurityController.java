package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

// 인증(Authentication)과 관련된 요청을 처리하는 컨트롤러
@RequestMapping("/common/")		// Base URI
@Controller
public class SecurityController {
	
	@GetMapping("/main")
	void main() {	
		log.trace("main() invoked.");
	} // main
	
	
	@GetMapping("/myPage")
	void myPage() {	
		log.trace("myPage() invoked.");		
	} // myPage
	
	
	@GetMapping("/applicationStatus")
	void applicationStatus() { 
		log.trace("applicationStatus() invoked.");		
	} // applicationStatus
	
	
	@GetMapping("/courseApply")
	void courseApply() { 
		log.trace("courseApply() invoked.");		
	} // courseApply
	
	
	@GetMapping("/courseOpen")
	void courseOpen() { 
		log.trace("courseOpen() invoked.");		
	} // courseOpen
	
	
	@GetMapping("/gradeRating")
	void gradeRating() { 
		log.trace("gradeRating() invoked.");		
	} // gradeRating
	
} // end class
