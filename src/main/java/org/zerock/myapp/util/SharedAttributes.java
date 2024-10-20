package org.zerock.myapp.util;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;


@NoArgsConstructor

@Component("sharedAttributes")
public final class SharedAttributes {
	// 공지에서 현재 페이지를 표시, 현재 미사용
//	public static final String CURRENT_PAGE = "_CURRENT_PAGE_";
	
	public static final String FOUND_PAGE = "_FOUND_PAGE_";
	public static final String PAGINATION = "_PAGINATION_";
	
	//	신규 게시물, 사용자 등록처리결과를 저장하는 모델의 속성명
	public static final String IS_REGISTERED = "_IS_REGISTERED_";

	// 상세조회된 게시물을 저장하는 모델의 속성명
	public static final String BOARD = "_BOARD_";

	// 수정완료된 게시물의 수정결과를 저장하는 모델의 속성명
	public static final String IS_MODIFIED = "_IS_MODIFIED_";
	
	//  삭제완료된 게시물의 삭제결과를 저장하는 모델의 속성명
	public static final String IS_REMOVED = "_IS_REMOVED_";
	
	// 현재 사용자를 저장하는 모델의 속성명
	public static final String USER_INFO = "_USER_INFO_";
	
	// 현재 사용자의 번호를 저장하는 모델의 속성명
	public static final String USER_NUMBER = "_USER_NUMBER_";
	
} // end class

