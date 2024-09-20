package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/professor/")
@Controller
public class ProfessorController {
	
	@GetMapping("/courseOpen")
	void courseOpen() {
		log.trace("courseOpen() invoked.");		
	} // courseOpen
	
	@GetMapping("/checkOpenCourse")
	void checkOpenCourse() {
		log.trace("checkOpenCourse() invoked.");		
	} // checkOpenCourse
	
	@GetMapping("/gradeEvaluation")
	void gradeEvaluation() {
		log.trace("gradeEvaluation() invoked.");		
	} // gradeEvaluation
	
	@GetMapping("/checkEvaluateGrade")
	void checkEvaluateGrade() {
		log.trace("checkEvaluateGrade() invoked.");		
	} // checkEvaluateGrade

} // end class
