package org.zerock.myapp.util;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;


@NoArgsConstructor

@Component("sharedAttributes")
public final class SharedAttributes {
	public static final String CREDENTIAL = "_MEMBER_";
	public static final String LOGIN_ID = "_LOGIN_ID_";
	public static final String PAGE = "_PAGE_";
	public static final String BOARD = "_BOARD_";
	public static final String CURRENT_PAGE = "currPage";
	public static final String EXCEPTION = "_EXCEPTION_";
	public static final String STACKTRACE = "_STACKTRACE_";
	
	
} // end class

