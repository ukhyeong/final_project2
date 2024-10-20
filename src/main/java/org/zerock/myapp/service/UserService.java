package org.zerock.myapp.service;

//앞 계층인 표현(Presentation)계층의 컨트롤러에서 사용가능한 메소드만
//인터페이스의 추상메소드로 노출시킵니다.
public interface UserService {
	
	// 1. 회원가입 처리
	public abstract boolean registerUser(String username, String password, String role, String name, String department);
	
} // end interface


