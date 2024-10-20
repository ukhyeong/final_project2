package org.zerock.myapp.service;

import org.springframework.data.domain.Page;
import org.zerock.myapp.domain.LectureDTO;
import org.zerock.myapp.entity.Lecture;

//앞 계층인 표현(Presentation)계층의 컨트롤러에서 사용가능한 메소드만
//인터페이스의 추상메소드로 노출시킵니다.
public interface LectureService {
	
	// 1. Lecture 테이블 모든 데이터 조회
	public abstract Page<Lecture> findAllLecture(LectureDTO dto);
	
	// 2. Lecture 테이블에서 본인(교수)가 개설한 강좌만 조회
	public abstract Page<Lecture> findAllMyLecture(LectureDTO dto);
	
} // end interface


