package org.zerock.myapp.service;

import org.springframework.data.domain.Page;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.User;

// 앞 계층인 표현(Presentation)계층의 컨트롤러에서 사용가능한 메소드만
// 인터페이스의 추상메소드로 노출시킵니다.
public interface BoardService {
	
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

	// 6. 현재 사용자 알아내기
	public abstract User findUser();
	
	// 7. 현재 사용자의 교직원/학생 번호 알아내기
	public abstract Long findNumber();
} // end interface


