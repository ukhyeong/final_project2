package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/student/")
@Controller
public class StudentController {
	
	@GetMapping("/courseApply")
	void courseApply() {
		log.trace("courseApply() invoked.");		
	} // accessDenied
	
	@GetMapping("/checkApplyCourse")
	void checkApplyCourse() {
		log.trace("checkApplyCourse() invoked.");		
	} // checkApplyCourse
	
	@GetMapping("/checkGrade")
	void checkGrade() {
		log.trace("checkGrade() invoked.");		
	} // checkGrade

} // end class
