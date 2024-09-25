package org.zerock.myapp.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.myapp.common.CommonBeanCallbacks;
import org.zerock.myapp.domain.LectureDTO;
import org.zerock.myapp.domain.Pagination;
import org.zerock.myapp.entity.Lecture;
import org.zerock.myapp.service.LectureService;
import org.zerock.myapp.util.SharedAttributes;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/professor/")
@Controller
public class ProfessorController extends CommonBeanCallbacks {
	
	@Autowired
	private LectureService lectureService;
	
	
	@PostConstruct
	public void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.lectureService);
		
		log.info("\t+ this.lectureService: {}", this.lectureService);
	} // postConstruct
	
	
	// 교수본인이 강좌를 개설 할 페이지로 이동
	@GetMapping("/lectureOpen")
	void lectureOpen(LectureDTO dto, Model model) {
		log.trace("lectureOpen() invoked.");		
		
		Page<Lecture> foundPage = this.lectureService.findAllLecture(dto);
		
		Objects.requireNonNull(foundPage);
		Pagination pageDTO = new Pagination(foundPage);
		
		model.addAttribute(SharedAttributes.FOUND_PAGE, foundPage);
		model.addAttribute(SharedAttributes.PAGINATION, pageDTO);
	} // lectureOpen
	
	// 교수본인이 개설한 강좌를 확인 할 페이지로 이동
	@GetMapping("/checkOpenLecture")
	void checkOpenLecture(LectureDTO dto, Model model) {
		log.trace("checkOpenLecture() invoked.");		
		
		Page<Lecture> foundPage = this.lectureService.findAllMyLecture(dto);
		
		Objects.requireNonNull(foundPage);
		Pagination pageDTO = new Pagination(foundPage);
		
		model.addAttribute(SharedAttributes.FOUND_PAGE, foundPage);
		model.addAttribute(SharedAttributes.PAGINATION, pageDTO);
	} // checkOpenLecture
	
	@GetMapping("/gradeEvaluation")
	void gradeEvaluation() {
		log.trace("gradeEvaluation() invoked.");		
	} // gradeEvaluation
	
	@GetMapping("/checkEvaluateGrade")
	void checkEvaluateGrade() {
		log.trace("checkEvaluateGrade() invoked.");		
	} // checkEvaluateGrade

} // end class
