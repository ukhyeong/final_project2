package org.zerock.myapp.util;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;


@NoArgsConstructor

@Component("sharedAttributes")
public final class SharedAttributes {
	public static final String CURRENT_PAGE = "_CURRENT_PAGE_";
	
	public static final String FOUND_PAGE = "_FOUND_PAGE_";
	public static final String PAGINATION = "_PAGINATION_";
	
//	public static final String LECTURE_PK_ATTRIBUTE_NAME = "id";
} // end class

