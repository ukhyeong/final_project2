package org.zerock.myapp.service;

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
import org.zerock.myapp.domain.LectureDTO;
import org.zerock.myapp.entity.Lecture;
import org.zerock.myapp.entity.Professor;
import org.zerock.myapp.entity.User;
import org.zerock.myapp.persistence.LectureRepository;
import org.zerock.myapp.persistence.ProfessorRepository;
import org.zerock.myapp.persistence.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class LectureServiceImpl extends CommonBeanCallbacks implements LectureService {
	
	@Autowired private LectureRepository lectureRepo;
	@Autowired private ProfessorRepository professorRepo;
	@Autowired private UserRepository userRepo;
	
	
	
	@PostConstruct
	public void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.lectureRepo);
		Objects.requireNonNull(this.professorRepo);
		Objects.requireNonNull(this.userRepo);
		
		log.info("\t+ this.lectureRepo: {}", this.lectureRepo);
		log.info("\t+ this.professorRepo: {}", this.professorRepo);
		log.info("\t+ this.userRepo: {}", this.userRepo);
	} // postConstruct
	
	
	
	// 모든 강좌 반환
	@Override
	public Page<Lecture> findAllLecture(LectureDTO dto) {
		log.trace("findAllBoards({}) invoked.", dto);
		
		Pageable paging = setSort(dto, Sort.Direction.ASC, "lectureName");
		
		return this.lectureRepo.findAll(paging);
	} // findAllLecture

	
	// 본인이 개설한 강좌만 반환
	@Override
	public Page<Lecture> findAllMyLecture(LectureDTO dto) {
		log.trace("findAllMyLecture({}) invoked.", dto);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName(); // 사용자의 username 반환. 즉, user 테이블의 pk 반환 됨.
		
		Optional<User> optional = this.userRepo.findById(username);
		
		if(optional.isEmpty()) return null; // if
	
		// username 으로 교수테이블에서 교수 번호를 알아내고, 
		Professor foundProfessor = this.professorRepo.findByUser(optional.get());
		Long professorNumber = foundProfessor.getNumber();
		
		// 교수 번호로 개설 강좌(Lecture)를 알아내자
		Pageable paging = setSort(dto, Sort.Direction.ASC, "lectureName");
		
		return this.lectureRepo.findByProfessorNumber(professorNumber, paging);
	} // findAllMyLecture
	
	
	// 페이징 처리 메소드
	public Pageable setSort(LectureDTO dto, Sort.Direction sort ,String name) {
		log.trace("setSort({}, {}) invoked.", dto, name);
		
		Integer currPage = dto.getCurrPage();	// 요청 페이지 번호
		Integer pageSize = dto.getPageSize();	// 페이지당 크기
		
		if(currPage == null || currPage < 1) currPage = 1;
		if(pageSize == null) pageSize = 10;
		
		return PageRequest.of(--currPage, pageSize, sort, name);
	} // setSort
	
} // end class

