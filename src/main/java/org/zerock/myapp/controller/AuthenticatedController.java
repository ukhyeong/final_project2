package org.zerock.myapp.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.myapp.common.CommonBeanCallbacks;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.Pagination;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Role;
import org.zerock.myapp.entity.User;
import org.zerock.myapp.service.BoardService;
import org.zerock.myapp.util.SharedAttributes;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/auth/")
@Controller
public class AuthenticatedController extends CommonBeanCallbacks{
	@Setter(onMethod_ = @Autowired)
	private BoardService service;

	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.trace("afterPropertiesSet() invoked.");
		
		Objects.requireNonNull(this.service);
		
		log.info("\t+ this.service: {}", this.service);
	} // deleteBoard
	

	@GetMapping("/getBoardList")
	void getBoardList(BoardDTO dto, Model model) {	
		log.trace("getBoardList({}) invoked.", dto);

		Page<Board> foundPage = this.service.findAllBoards(dto);

		Objects.requireNonNull(foundPage);
		Pagination pageDTO = new Pagination(foundPage);

		model.addAttribute(SharedAttributes.FOUND_PAGE, foundPage);
		model.addAttribute(SharedAttributes.PAGINATION, pageDTO);
	} // getBoardList
	
	@GetMapping(value = "/getBoard")
	void getBoard(BoardDTO dto, Model model) {
		log.trace("getBoard({}) invoked.", dto);

		Board foundBoard = this.service.findBoard(dto);
		
		User foundUser = this.service.findUser();
		
		model.addAttribute(SharedAttributes.USER_NUMBER, null);
		if(foundUser.getRole() == Role.PROFESSOR) {
			Long foundNumber = this.service.findNumber();
			model.addAttribute(SharedAttributes.USER_NUMBER, foundNumber);
		} // if

		model.addAttribute(SharedAttributes.BOARD, foundBoard);
		
		// 기존 User.getNumber() 로 공지 CUD 권한 부여했으나 계획 변경
//		model.addAttribute(SharedAttributes.USER_INFO, foundUser);
	} // getBoard
	
	@GetMapping("/main")
	void main() {	
		log.trace("main() invoked.");
	} // main
	
	@GetMapping("/myPage")
	void myPage(Model model) {	
		log.trace("myPage() invoked.");	
		
		User foundUser = this.service.findUser();

		model.addAttribute(SharedAttributes.USER_INFO, foundUser);
	} // myPage
	
} // end class
