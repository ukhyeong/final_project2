package org.zerock.myapp.service;

public interface UserService {
	
	// 1. 회원가입 처리
	public abstract boolean registerUser(String username, String password, String role);
	
} // end interface


