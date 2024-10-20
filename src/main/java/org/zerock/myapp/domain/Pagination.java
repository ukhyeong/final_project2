package org.zerock.myapp.domain;

import org.springframework.data.domain.Page;

import lombok.Data;


@Data
public class Pagination {
	private int currPage = 1;
	private int pageSize = 10;
	private int pagesPerPage = 10;
	
	private long totalElements;			// 총 레코드 건수
	private int totalPages;					// 총 페이지 수

	private int startPage;					// 한 페이지당 페이지목록의 시작번호
	private int endPage;					// 한 패이지당 페이지목록의 끝번호
	
	private boolean first;					// 첫페이지 여부
	private boolean last;					// 끝페이지 여부
	
	private boolean prev;					// 이전 페이지 존재여부
	private boolean next;					// 다음 페이지 존재여부
	
	
	public <T> Pagination(Page<T> page) {
		this.currPage = page.getNumber() + 1;
		this.pageSize = page.getSize();
		
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		
		this.first = page.isFirst();
		this.last = page.isLast();
		
		this.prev = page.hasPrevious();
		this.next = page.hasNext();

		// ----------
		// Step.1 : 현재 페이지의 페이지번호목록의 끝페이지번호 구하기
		// ----------
		// (공식) 끝페이지번호 = (int) Math.ceil( (double) 현재페이지번호 / 페이지목록길이 ) x 페이지목록길이
		this.endPage = (int) Math.ceil( ( this.currPage * 1.0 ) / pagesPerPage ) * pagesPerPage;

		// ----------
		// Step.2 : 현재 페이지의 페이지번호목록의 시작페이지번호 구하기
		// ----------
		// (공식) 시작페이지번호 = 끝페이지번호 - ( 페이지목록길이 - 1 )
		this.startPage = this.endPage - ( pagesPerPage - 1 );

		// ----------
		// Step.3 : 실제 끝페이지번호 구하기
		// ----------
		// (공식) 실제끝페이지번호 = (int) Math.ceil( (double) 모든행의개수 / 한페이지당행의수 )
		this.endPage = ( this.endPage > this.totalPages ) ? this.totalPages : this.endPage;
	} // constructor
	

} // end class
