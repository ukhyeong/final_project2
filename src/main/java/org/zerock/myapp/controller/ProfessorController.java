package org.zerock.myapp.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.myapp.common.CommonBeanCallbacks;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.LectureDTO;
import org.zerock.myapp.domain.Pagination;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Lecture;
import org.zerock.myapp.service.BoardService;
import org.zerock.myapp.service.LectureService;
import org.zerock.myapp.util.SharedAttributes;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/professor/")
@Controller
public class ProfessorController extends CommonBeanCallbacks {
	@Autowired
	private LectureService lectureService;
	@Setter(onMethod_ = @Autowired)
	private BoardService boardService;
	
	
	@PostConstruct
	public void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.lectureService);
		
		log.info("\t+ this.lectureService: {}", this.lectureService);
	} // postConstruct
	
	
	@GetMapping("/registerBoardView")
	void registerBoardView() {
		log.trace("registerBoardView() invoked.");
		
	} // registerBoardView
	
	@PostMapping(value="/registerBoard", params= { "title", "content" })
	String registerBoard(BoardDTO dto, RedirectAttributes rttrs) {
		log.trace("registerBoard({}) invoked.", dto);
		
		boolean isRegistered = this.boardService.registerBoard(dto);
		log.info("\t+ isRegistered: {}", isRegistered);
		
		rttrs.addFlashAttribute(SharedAttributes.IS_REGISTERED, isRegistered);
		
		rttrs.addAttribute("currPage", 1);	
		
		return "redirect:/auth/getBoardList";
	} // registerBoard
	
	@PostMapping("/updateBoardView")
	void updateBoardView(BoardDTO dto,  Model model) {	
		log.trace("updateBoardView({}) invoked.", dto);
		
		Board foundBoard = this.boardService.findBoard(dto);
		
		model.addAttribute(SharedAttributes.BOARD, foundBoard);
	} // updateBoardView
	
	@PostMapping(value = "/updateBoard", params= { "title", "content" })
	String updateBoard(BoardDTO dto, RedirectAttributes rttrs) {
		log.trace("updateBoard({}) invoked.", dto);
		
		boolean isModified = this.boardService.modifyBoard(dto);
		
		rttrs.addFlashAttribute(SharedAttributes.IS_MODIFIED, isModified);
		rttrs.addAttribute("currPage", dto.getCurrPage());
		
		return "redirect:/auth/getBoardList";
	} // updateBoard
	
	@GetMapping("/deleteBoard")
	String deleteBoard(BoardDTO dto, RedirectAttributes rttrs) {
		log.trace("deleteBoard({}) invoked.", dto);
		
		boolean isRemoved = this.boardService.removeBoard(dto);
		
		rttrs.addFlashAttribute(SharedAttributes.IS_REMOVED, isRemoved);
		rttrs.addAttribute("currPage", dto.getCurrPage());
		
		return "redirect:/auth/getBoardList";
	} // deleteBoard
	
	@GetMapping("/lectureOpen")
	void lectureOpen(LectureDTO dto, Model model) {
		log.trace("lectureOpen() invoked.");		
		
		Page<Lecture> foundPage = this.lectureService.findAllLecture(dto);
		
		Objects.requireNonNull(foundPage);
		Pagination pageDTO = new Pagination(foundPage);
		
		model.addAttribute(SharedAttributes.FOUND_PAGE, foundPage);
		model.addAttribute(SharedAttributes.PAGINATION, pageDTO);
	} // lectureOpen
	
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
