package org.zerock.myapp;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Comment;
import org.zerock.myapp.entity.Role;
import org.zerock.myapp.entity.User;
import org.zerock.myapp.persistence.BoardRepository;
import org.zerock.myapp.persistence.CommentRepository;
import org.zerock.myapp.persistence.ProfessorRepository;
import org.zerock.myapp.persistence.StudentRepository;
import org.zerock.myapp.persistence.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@AutoConfigureMockMvc
@SpringBootTest(
//		properties = "spring.jpa.hibernate.ddl-auto=create"
		properties = "spring.jpa.hibernate.ddl-auto=update"
		)
public class CommentRepositoryTests {
	@Autowired private CommentRepository commentRepo;
	@Autowired private UserRepository userRepo;
	@Autowired private ProfessorRepository professorRepo;
	@Autowired private StudentRepository studentRepo;
	@Autowired private BoardRepository boradRepo;
	
	
	@PostConstruct
	void postConstruct() {	// 전처리
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.commentRepo);
		log.info("this.commentRepo : {}", this.commentRepo);
		
		Objects.requireNonNull(this.userRepo);
		log.info("this.userRepo : {}", this.userRepo);
		
		Objects.requireNonNull(this.professorRepo);
		log.info("this.professorRepo : {}", this.professorRepo);
		
		Objects.requireNonNull(this.studentRepo);
		log.info("this.studentRepo : {}", this.studentRepo);
		
		Objects.requireNonNull(this.boradRepo);
		log.info("this.boradRepo : {}", this.boradRepo);
	} // postConstruct
	
	
	@Test
	@Order(1)
	@Transactional
	@Rollback(false)
	void commentsAdd() {
		log.trace("commentsAdd() invoked.");
		
		// 교수 댓글 삽입
		IntStream.rangeClosed(1, 2).forEach(n -> {
			Comment comment = new Comment();
			
			comment.setContent("댓글" + n);
			comment.setLevel1(n);
			comment.setLevel2(1);
			
			Optional<User> foundUser = this.userRepo.findById("PROFESSOR_ID_1");
			foundUser.ifPresent(p -> {
				Role foundRole = p.getRole();
				
				String foundName = null;
				
				if(foundRole == Role.PROFESSOR) {
					foundName = p.getProfessor().getName();
				} else if(foundRole == Role.STUDENT){
					foundName = p.getStudent().getName();
				} // if else
				
				comment.setWriter(foundName);
			});
			
			Optional<Board> foundBoard = this.boradRepo.findById(1L);
			foundBoard.ifPresent(p -> {
				comment.setBoard(p);
			});
			
			this.commentRepo.save(comment);
		}); // forEach
		
		// 학생 댓글 삽입
		IntStream.rangeClosed(3, 4).forEach(n -> {
			Comment comment = new Comment();
			
			comment.setContent("댓글" + n);
			comment.setLevel1(n);
			comment.setLevel2(1);
			
			Optional<User> foundUser = this.userRepo.findById("STUDENT_ID_1");
			foundUser.ifPresent(p -> {
				Role foundRole = p.getRole();
				
				String foundName = null;
				
				if(foundRole == Role.PROFESSOR) {
					foundName = p.getProfessor().getName();
				} else if(foundRole == Role.STUDENT){
					foundName = p.getStudent().getName();
				} // if else
				
				comment.setWriter(foundName);
			});
			
			Optional<Board> foundBoard = this.boradRepo.findById(1L);
			foundBoard.ifPresent(p -> {
				comment.setBoard(p);
			});
			
			this.commentRepo.save(comment);
		}); // forEach
		
		// 교수 대댓글 삽입
		IntStream.rangeClosed(1, 2).forEach(n -> {
			Comment comment = new Comment();
			
			comment.setContent("대댓글" + n);
			comment.setLevel1(1);
			comment.setLevel2(n+1);
			
			Optional<User> foundUser = this.userRepo.findById("PROFESSOR_ID_1");
			foundUser.ifPresent(p -> {
				Role foundRole = p.getRole();
				
				String foundName = null;
				
				if(foundRole == Role.PROFESSOR) {
					foundName = p.getProfessor().getName();
				} else if(foundRole == Role.STUDENT){
					foundName = p.getStudent().getName();
				} // if else
				
				comment.setWriter(foundName);
			});
			
			Optional<Board> foundBoard = this.boradRepo.findById(1L);
			foundBoard.ifPresent(p -> {
				comment.setBoard(p);
			});
			
			this.commentRepo.save(comment);
		}); // forEach
		
		// 학생 대댓글 삽입
		IntStream.rangeClosed(1, 2).forEach(n -> {
			Comment comment = new Comment();
			
			comment.setContent("대댓글" + n);
			comment.setLevel1(3);
			comment.setLevel2(n+1);
			
			Optional<User> foundUser = this.userRepo.findById("STUDENT_ID_1");
			foundUser.ifPresent(p -> {
				Role foundRole = p.getRole();
				
				String foundName = null;
				
				if(foundRole == Role.PROFESSOR) {
					foundName = p.getProfessor().getName();
				} else if(foundRole == Role.STUDENT){
					foundName = p.getStudent().getName();
				} // if else
				
				comment.setWriter(foundName);
			});
			
			Optional<Board> foundBoard = this.boradRepo.findById(1L);
			foundBoard.ifPresent(p -> {
				comment.setBoard(p);
			});
			
			this.commentRepo.save(comment);
		}); // forEach
		
	} // commentsAdd

	
	@Test
	@Order(2)
	@Transactional
	@Rollback(false)
	void commentsCheck() {
		log.trace("commentsCheck() invoked.");
		
		Optional<Board> foundBoard = this.boradRepo.findById(1L);
		foundBoard.ifPresent(p -> {
			Pageable paging = PageRequest.of(0, 8, Sort.Direction.ASC, "level1", "level2");
			
			Page<Comment> foundComments = this.commentRepo.findByBoard(p, paging);
			
			for(Comment comment : foundComments) {
				log.info(comment.getContent());
			} // enhanced for
			
		});
		
	} // commentsCheck
} // end class
