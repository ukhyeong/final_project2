package org.zerock.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.myapp.service.UserService;
import org.zerock.myapp.util.SharedAttributes;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

// 인증(Authentication)과 관련된 요청을 처리하는 컨트롤러
@RequestMapping("/all/")
@Controller
public class PermitAllController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signIn")
	void signIn() {	
		log.trace("signIn() invoked.");
	} // signIn
	
	@GetMapping("/signUp")
	void signUp() {	
		log.trace("signUp() invoked.");		
	} // signUp
	
	@PostMapping(value="/joinBoy", params = {"username", "password", "role"})
	String joinBoy(String username, String password, String role, RedirectAttributes rttrs) {	
		log.trace("joinBoy() invoked.");		
		
		boolean isRegistered = this.userService.registerUser(username, password, role);
		
		rttrs.addFlashAttribute(SharedAttributes.IS_REGISTERED, isRegistered);
		rttrs.addAttribute("currPage", 1);	
		
	    return "redirect:/all/signIn";
	} // joinBoy
	
	@GetMapping("/403")
	void accessDenied() {
		log.trace("accessDenied() invoked.");		
	} // accessDenied
	
} // end class
