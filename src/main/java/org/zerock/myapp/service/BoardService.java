package org.zerock.myapp.service;

import org.springframework.data.domain.Page;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.entity.Board;

// 앞 계층인 표현(Presentation)계층의 컨트롤러에서 사용가능한 메소드만
// 인터페이스의 추상메소드로 노출시킵니다.
public interface BoardService {
	
	// 주의사항: 이 서비스 인터페이스는 "게시판" 구현을 위한
	//           것이기 때문에, 게시판 "관리"(CRUD, 목록조회)와 관련된
	//           로직이, "비지니스 로직"이 됩니다.
	
	// 이렇게 서비스 계층의 인터페이스는, "비지니스 로직"에 맞게, 각
	// 필요한 메소드를 추상메소드로 선언합니다.
	
	// 주의사항2: 참고로, 서비스 계층의 인터페이스의 메소드 이름은,
	//			  다른 웹3계층과 마찬가지로, 계층의 성격에 맞게 짓습니다.
		
	// 1. 전체 게시글 목록조회 (페이징 처리 고려해서 수정한다!)
	public abstract Page<Board> findAllBoards(BoardDTO dto);
	
	// 2. 새로운 게시글 등록
	public abstract boolean registerBoard(BoardDTO dto);
	
	// 3. 지정된 게시글 상세조회
	public abstract Board findBoard(BoardDTO dto);
	
	// 4. 지정된 게시글 수정
	public abstract boolean modifyBoard(BoardDTO dto);
	
	// 5. 지정된 게시글 삭제
	public abstract boolean removeBoard(BoardDTO dto);

	
} // end interface


