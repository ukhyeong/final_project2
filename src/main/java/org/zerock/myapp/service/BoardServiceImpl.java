package org.zerock.myapp.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.myapp.common.CommonBeanCallbacks;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.persistence.BoardRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class BoardServiceImpl extends CommonBeanCallbacks implements BoardService {
	
	// 영속성 계층의 기능을 이용해서, 각 메소드의 비지니스로직 구현
	@Autowired private BoardRepository dao;

	
	@PostConstruct
	public void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.dao);
		log.info("\t+ this.dao: {}", this.dao);
	} // postConstruct
	
	
	@Override
	// 현재요청페이지번호(currPage), 페이지크기(pageSize) 2개의 전송파라미터 수신
	public Page<Board> findAllBoards(BoardDTO dto) {	
		log.trace("findAllBoards({}) invoked.", dto);
		
		// 요청받은 페이지와 크기에 해당하는 게시물만 획득하여 반환
		// 이를 위해, 영속성 계층의 DAO를 이용하면 된다!
		// 더불어서, 영속성 계층의 DAO인, JPA Repository 인터페이스에는
		// 페이징 & 정렬처리를 위한 메소드[ findAll(Pageable) ]를 이미 상속해주고 있습니다.
		
		Integer currPage = dto.getCurrPage();	// 요청 페이지 번호
		Integer pageSize = dto.getPageSize();	// 페이지당 크기
		
		// 페이징 처리가 가능하도록 수정하세요!!
		if(currPage == null || currPage < 1) currPage = 1;
		if(pageSize == null) pageSize = 10;
		
		// 첫 페이지는 0부터 시작 (***), Board 엔티티의 "id" 속성으로 내림차순 정렬
		Pageable paging	= PageRequest.of(--currPage, pageSize, Sort.Direction.DESC, "id");
		
		// 지정된 페이지번호와 정렬방식으로 해당 페이지의 게시물만 획득 & 반환
		return this.dao.findAll(paging);
	} // findAllBoards

	@Override
	public boolean registerBoard(BoardDTO dto) {
		log.trace("registerBoard({}) invoked.", dto);
		
		// DTO -> JPA Entity 로 변환
		Board transientBoard = new Board();
		
		transientBoard.setTitle(dto.getTitle());
		transientBoard.setWriter(dto.getWriter());
		transientBoard.setContent(dto.getContent());
		
		Board savedBoard = this.dao.<Board>save(transientBoard);
		return (savedBoard.getId() != null);
	} // registerBoard

	@Override
	public Board findBoard(BoardDTO dto) {
		log.trace("findBoard({}) invoked.", dto);
		
		// 찾을 엔티티가 없으면, null 을 반환하기로 한다!
		Board foundBoard = this.dao.findById(dto.getId()).orElse(null);
		
		// 엔티티를 찾았으면, 상세조회이기 때문에, 조회수를 +1증가시켜야 합니다.
		if(foundBoard != null) {	// 엔티티를 찾았다면...
			// 조횟수 +1 증가
			foundBoard.setCnt(foundBoard.getCnt()+1);
			this.dao.save(foundBoard);
		} // if
		
		return foundBoard;
	} // findBoard

	
	@Override
	public boolean modifyBoard(BoardDTO dto) {
		log.trace("modifyBoard({}) invoked.", dto);
		
		// Step1. DTO로 받은 정보중에, 수정할 게시글번호(id)로 찾고
		// Step2. 찾아낸 엔티티를, 나머지 DTO의 정보로 수정하고
		// Step3. 마지막, 엔티티로 저장해야 합니다.
		
		// Step1.
		Optional<Board> optional = this.dao.findById(dto.getId());
		
		// Step2.
		optional.ifPresent(b -> {	// If founded,
			// Step3.			
			b.setTitle(dto.getTitle());
			b.setWriter(dto.getWriter());
			b.setContent(dto.getContent());
			b.setCnt(dto.getCnt());
			
			this.dao.save(b);	// UPDATE
		}); // ifPresent

		return (optional.isPresent() == true);
	} // modifyBoard

	
	@Override
	public boolean removeBoard(BoardDTO dto) {
		log.trace("removeBoard({}) invoked.", dto);
		
		try { 
			this.dao.deleteById(dto.getId());
			return true;
		} catch(Exception e) { return false; }
	} // removeBoard

} // end class

