package org.zerock.myapp.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.common.CommonBeanCallbacks;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Professor;
import org.zerock.myapp.entity.User;
import org.zerock.myapp.persistence.BoardRepository;
import org.zerock.myapp.persistence.ProfessorRepository;
import org.zerock.myapp.persistence.StudentRepository;
import org.zerock.myapp.persistence.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class BoardServiceImpl extends CommonBeanCallbacks implements BoardService {
	// 영속성 계층의 기능을 이용해서, 각 메소드의 비지니스로직 구현
	@Autowired private BoardRepository boardDAO;
	@Autowired private UserRepository userDAO;
	@Autowired private ProfessorRepository professorDAO;
	@Autowired private StudentRepository studentDAO;
	
	@PostConstruct
	public void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.boardDAO);
		log.info("\t+ this.boardDAO: {}", this.boardDAO);
		
		Objects.requireNonNull(this.userDAO);
		log.info("\t+ this.UserDAO: {}", this.userDAO);
		
		Objects.requireNonNull(this.professorDAO);
		log.info("\t+ this.professorDAO: {}", this.professorDAO);
		
		Objects.requireNonNull(this.studentDAO);
		log.info("\t+ this.studentDAO: {}", this.studentDAO);
	} // postConstruct
	
	
	@Override
	public Page<Board> findAllBoards(BoardDTO dto) {	
		log.trace("findAllBoards({}) invoked.", dto);
		
		Integer currPage = dto.getCurrPage();	// 요청 페이지 번호
		Integer pageSize = dto.getPageSize();	// 페이지당 크기
		
		if(currPage == null || currPage < 1) currPage = 1;
		if(pageSize == null) pageSize = 10;
		
		// 첫 페이지는 0부터 시작 (***), Board 엔티티의 "id" 속성으로 내림차순 정렬
		Pageable paging	= PageRequest.of(--currPage, pageSize, Sort.Direction.DESC, "id");
		
		return this.boardDAO.findAll(paging);
	} // findAllBoards

	@Override
	public Board findBoard(BoardDTO dto) {
		log.trace("findBoard({}) invoked.", dto);
		
		Board foundBoard = this.boardDAO.findById(dto.getId()).orElse(null);

		if(foundBoard != null) {	
			// 조회수 +1 증가
			foundBoard.setCnt(foundBoard.getCnt()+1);
			this.boardDAO.save(foundBoard);
		} // if
		
		return foundBoard;
	} // findBoard
	
	@Override
	public User findUser() {
		log.trace("findUser() invoked.");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		
		Optional<User> foundUser = this.userDAO.findById(userId);
		if(foundUser.isEmpty()) return null; // if
		
		return foundUser.get();
	} // findUser
	
	@Override
	public Long findNumber() {
		log.trace("findProfessor() invoked.");
		
		// 교직원 번호를 얻는것으로 대체함.
		return findUser().getProfessor().getNumber();
	} // findProfessor
	
	@Override
	public boolean registerBoard(BoardDTO dto) {
		log.trace("registerBoard({}) invoked.", dto);

		Board transientBoard = new Board();
		
		try {
			String decoedTitle = URLDecoder.decode(dto.getTitle(), "UTF-8");
			String decoedContent = URLDecoder.decode(dto.getContent(), "UTF-8");
			transientBoard.setTitle(decoedTitle);
			transientBoard.setContent(decoedContent);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
		} // try catch
		
		Long professorNumber = this.professorDAO.findByUser(findUser()).getNumber();
		
		Optional<Professor> foundProfessor = this.professorDAO.findById(professorNumber);
		foundProfessor.ifPresent(p -> {
			transientBoard.setProfessor(p);
		}); // .ifPresent
		
		Board savedBoard = this.boardDAO.<Board>save(transientBoard);
		return (savedBoard.getId() != null);
	} // registerBoard

	@Override
	public boolean modifyBoard(BoardDTO dto) {
		log.trace("modifyBoard({}) invoked.", dto);
		
		// Step1.
		Optional<Board> optional = this.boardDAO.findById(dto.getId());

		// Step2.
		optional.ifPresent(b -> {	
			// Step3.
			try {
				String decoedTitle = URLDecoder.decode(dto.getTitle(), "UTF-8");
				String decoedContent = URLDecoder.decode(dto.getContent(), "UTF-8");
				b.setTitle(decoedTitle);
				b.setContent(decoedContent);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				
			} // try catch
			
			this.boardDAO.save(b);	// UPDATE
		}); // ifPresent

		return (optional.isPresent() == true);
	} // modifyBoard

	@Override
	public boolean removeBoard(BoardDTO dto) {
		log.trace("removeBoard({}) invoked.", dto);
		
		try { 
			this.boardDAO.deleteById(dto.getId());
		} catch(Exception e) { return false; }
		
		return true;
	} // removeBoard

} // end class

