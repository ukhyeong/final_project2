package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Controller("tempController")
public class TempController {
	
	
	@GetMapping("/temp")
	void hello(Model model) {
		log.trace("temp({}) invoked.", model);
		
		model.addAttribute("greeting", "Hello, Thymeleaf.");
	} // hello
	
} // end class

