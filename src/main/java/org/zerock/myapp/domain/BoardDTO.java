package org.zerock.myapp.domain;

import lombok.Data;


@Data
public class BoardDTO {
	// Board 엔티티와 관련있는 필드
	private Long id;
	private String title;
	private String writer;
	private String content;
	private Integer cnt;
	
	// Board 엔티티와 관련없는 필드
	// 게시판 서비스 구현상, 반드시 필요한 전송파라미터 수집
	private Integer currPage;	// 현재 페이지 번호 수집
	private Integer pageSize;	// 한 페이지당 보여줄 게시물 갯수 수집
} // end class
