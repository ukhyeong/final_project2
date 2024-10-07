package org.zerock.myapp;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Professor;
import org.zerock.myapp.persistence.BoardRepository;
import org.zerock.myapp.persistence.ProfessorRepository;
import org.zerock.myapp.util.RandomNumberGenerator;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@AutoConfigureMockMvc
@SpringBootTest
public class BoardRepositoryTests {
	@Autowired private MockMvc mockMvc;
	@Autowired private BoardRepository dao;
	@Autowired private ProfessorRepository prodao;
	
	
	@PostConstruct
	void postConstruct() {	// 전처리
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.mockMvc);
		log.info("\t+ this.mockMvc: {}", this.mockMvc);
		
		Objects.requireNonNull(this.dao);
		log.info("\t+ this.dao: {}", this.dao);
		
		Objects.requireNonNull(this.prodao);
		log.info("\t+ this.prodao: {}", this.prodao);
	} // postConstruct
	
	
//	@Disabled
	@Order(1)

	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testCreateBoard")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void testCreateBoard() {
		log.trace("testCreateBoard() invoked.");
		
		// 테스트 목적: 새로운 게시글 등록
		IntStream.rangeClosed(1, 200).forEachOrdered(seq -> {
			Board transientBoard = new Board();
			
			transientBoard.setTitle("TITLE");
			
			Optional<Professor> optional = this.prodao.findById(1L);
			
			optional.ifPresent(p -> {
				log.info("******************교수찾음******************");
				transientBoard.setProfessor(p);
			});
			
			transientBoard.setContent("CONTENT");
			
			Board savedBoard = this.dao.<Board>save(transientBoard);
			
			assert savedBoard != null;
			log.info("\t+ savedBoard: {}", savedBoard);		
		});	// .forEachOrdered
	} // testCreateBoard
	
	
//	@Disabled
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. testReadBoard")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void testReadBoard() {
		log.trace("testReadBoard() invoked.");
		
		// 테스트 목적: 특정 게시글 조회
		final Long id = RandomNumberGenerator.generateOneLong(1, 403);
		Optional<Board> foundBoard = this.dao.findById(id);
		
		foundBoard.ifPresent(b -> {
			log.info("\t+ Found board: {}", b);
		});	// .ifPresent
		
		foundBoard.orElseThrow();
	} // testReadBoard
	
	
//	@Disabled
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. testUpdateBoard")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void testUpdateBoard() {
		log.trace("testUpdateBoard() invoked.");
		
		// 테스트 목적: 특정 게시글 수정
		final Long id = RandomNumberGenerator.generateOneLong(1, 403);
		Optional<Board> foundBoard = this.dao.findById(id);
		
		foundBoard.ifPresent(b -> {
			log.info("\t+ Found board: {}", b);
			
			b.setTitle("MODIFIED");
			b.setCnt(b.getCnt()+1);
			
			this.dao.save(b);			// Update
		});	// .ifPresent
		
		foundBoard.orElseThrow();
	} // testUpdateBoard
	
	
//	@Disabled
	@Order(4)
	@Test
//	@RepeatedTest(1)
	@DisplayName("4. testDeleteBoard")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void testDeleteBoard() {
		log.trace("testDeleteBoard() invoked.");
		
		// 테스트 목적: 특정 게시글 삭제
		final Long id = RandomNumberGenerator.generateOneLong(1, 403);
		Optional<Board> foundBoard = this.dao.findById(id);
		
		foundBoard.ifPresent(b -> {
			log.info("\t+ Found board: {}", b);
			
			this.dao.delete(b);				// 1
//			this.dao.deleteById(b.getId());	// 2
		});	// .ifPresent
		
		foundBoard.orElseThrow();
	} // testDeleteBoard
	
	
//	@Disabled
	@Order(5)
	@Test
//	@RepeatedTest(1)
	@DisplayName("5. testGetBoardList")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void testGetBoardList() {
		log.trace("testGetBoardList() invoked.");
		
		// 테스트 목적: 전체 게시글 목록 조회
		List<Board> list = this.dao.findAll();
		list.forEach(b -> log.info("\t+ Found board: {}", b));
	} // testGetBoardList
	

} // end class
