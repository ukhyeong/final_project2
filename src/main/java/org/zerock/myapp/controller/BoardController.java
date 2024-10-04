//package org.zerock.myapp.controller;
//
//import java.util.Objects;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.zerock.myapp.common.CommonBeanCallbacks;
//import org.zerock.myapp.domain.BoardDTO;
//import org.zerock.myapp.domain.Pagination;
//import org.zerock.myapp.entity.Board;
//import org.zerock.myapp.service.BoardService;
//import org.zerock.myapp.util.SharedAttributes;
//
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//
//
//@Slf4j
//@NoArgsConstructor
//
//@RequestMapping("/auth/")
//@Controller
//public class BoardController extends CommonBeanCallbacks {
//	// 표현계층(Presentation Layer) 은, 바로 뒤의 비지니스 계층(Business Layer)의 서비스 빈을
//	// 이용해서, 수신받은 요청을 의뢰(Delegation)하고, 그 결과인 모델(Model)을 받고, 마지막으로
//	// 뷰의 이름를 반환하면 됩니다(즉, Model and View를 반환하는 것이, Controller의 각 handler 메소드의 역할)
//	@Setter(onMethod_ = @Autowired)
//	private BoardService service;
//	
//
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		log.trace("afterPropertiesSet() invoked.");
//		
//		// 필드에 의존성 주입 확인
//		Objects.requireNonNull(this.service);	// 1, 표준라이브러리의 메소드 이용
//		assert this.service != null;			// 2, 자바언어의 키워드 문법이용
//		
//		log.info("\t+ this.service: {}", this.service);
//	} // deleteBoard
//	
//
//	// Model and View 로, 전송파라미터로 전송된 페이지번호에 해당되는 게시물목록을
//	// Model로 반환하고, 이 Model과 DTO를 기반으로 UI(화면)을 만들어낼 책임이 있는
//	// View 이름을 반환
//	@GetMapping("/getBoardList")
//	void getBoardList(BoardDTO dto, Model model) {	// DTO: Spring Command Object, View까지 자동으로 전달
//		log.trace("getBoardList({}) invoked.", dto);
//
//		// 반환값이 바로 Model 이다! 즉, 비지니스 로직수행의 결과물이다!!
//		// 그럼, 이 Model 데이터를, Model 상자(Map객체)안에 저장 => Request Scope의
//		// 공유속성이 되어, 마지막 호출될 View(즉, JSP파일)까지 자동전달
//		
//		// ---------
//		Page<Board> foundPage = this.service.findAllBoards(dto);
//
//		// ---------
//		Objects.requireNonNull(foundPage);
//		Pagination pageDTO = new Pagination(foundPage);
//		
//		// ---------
//		model.addAttribute(SharedAttributes.CURRENT_PAGE, foundPage);
//		model.addAttribute(SharedAttributes.PAGINATION, pageDTO);
//	} // getBoardList
//	
//	
//	@GetMapping("/registerBoard")
//	String registerBoardView() {		// View-Controller: 신규 게시글 등록화면
//		log.trace("registerBoardView() invoked.");
//		
//		return "board/registerBoardView";	// 뷰이름 반환 - 이때 Base URI도 포함시켜야 함
//	} // registerBoardView
//	
//	
//	// 신규 게시글 등록버튼 눌렀을 때에, 발생할 요청처리(즉, 등록처리)
//	@PostMapping(value="/registerBoard", params= { "title", "writer", "content" })
//	String registerBoard(BoardDTO dto, RedirectAttributes rttrs) {
//		log.trace("registerBoard({}) invoked.", dto);
//		
//		// Step1. 등록처리
//		boolean isRegistered = this.service.registerBoard(dto);
//		log.info("\t+ isRegistered: {}", isRegistered);
//		
//		// Step2. 화면전환시 필요한 전송파라미터 설정
//		rttrs.addFlashAttribute(SharedAttributes.IS_REGISTERED, isRegistered);
//
//		// 신규등록후에는, 새로운 게시글을 보여주기 위해 1페이지로 이동
//		rttrs.addAttribute("currPage", 1);	
//		
//		// Step3. 목록조회화면으로 이동		
//		return "redirect:getBoardList";		// 현재화면과의 인연을 끊어서 이동
////		return "board/getBoardList";		// Request Forwarding으로 이동
//	} // registerBoard
//	
//	
//	@GetMapping("/getBoard")
//	void getBoard(BoardDTO dto, Model model) {
//		log.trace("getBoard({}) invoked.", dto);
//		
//		// 모델 데이터인 foundBoard 가 null이면, Model상자(Map객체)안에
//		// 넣어도 들어가지 않는다!!!
//		Board foundBoard = this.service.findBoard(dto);
//		model.addAttribute(SharedAttributes.BOARD, foundBoard);
//	} // getBoard
//	
//	
//	@GetMapping("/updateBoard")
//	String updateBoardView() {	// View Controller: 수정화면을 띄워주는 핸들러
//		log.trace("updateBoardView() invoked.");
//		
//		return "board/updateBoardView"; // 직접 뷰이름 반환
//	} // updateBoardView
//	
//	
//	@PostMapping("/updateBoard")
//	String updateBoard(BoardDTO dto, RedirectAttributes rttrs) {	// 수정처리 핸들러
//		log.trace("updateBoard({}) invoked.", dto);
//		
//		// Step1. 수정처리
//		boolean isModified = this.service.modifyBoard(dto);
//		
//		// Step2. 화면전환에 필요한 전송파라미터 설정
//		rttrs.addFlashAttribute(SharedAttributes.IS_MODIFIED, isModified);
//		rttrs.addAttribute(SharedAttributes.CURRENT_PAGE, dto.getCurrPage());
//		
//		// Step3. 목록조회 화면으로 이동(수정 전, 페이지번호에 맞게)
//		return "redirect:getBoardList";
//	} // updateBoard
//	
//	
//	@GetMapping("/deleteBoard")
//	String deleteBoard(BoardDTO dto, RedirectAttributes rttrs) {
//		log.trace("deleteBoard({}) invoked.", dto);
//		
//		// Step1. 삭제처리
//		boolean isRemoved = this.service.removeBoard(dto);
//		
//		// Step2. 화면전환에 필요한 전송파라미터 설정
//		//        임시상자안에 넣은 속성명=속성값은, 리다이렉션 URI에 Query String 형태로
//		//        즉, GET 방식 형태로 붙어서 함께 나갑니다.
//		rttrs.addFlashAttribute(SharedAttributes.IS_REMOVED, isRemoved);
//		rttrs.addAttribute(SharedAttributes.CURRENT_PAGE, dto.getCurrPage());
//		
//		// Step3. 현재 페이지번호에 맞게, 목록조회 화면으로 이동
//		return "redirect:getBoardList";
//	} // deleteBoard
//	
//	
//} // end class
//
//
