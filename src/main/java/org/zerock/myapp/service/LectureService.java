package org.zerock.myapp.service;

import org.springframework.data.domain.Page;
import org.zerock.myapp.domain.LectureDTO;
import org.zerock.myapp.entity.Lecture;


public interface LectureService {
	
	// 1. Lecture 테이블 모든 데이터 조회
	public abstract Page<Lecture> findAllLecture(LectureDTO dto);
	
	// 2. Lecture 테이블에서 본인(교수)가 개설한 강좌만 조회
	public abstract Page<Lecture> findAllMyLecture(LectureDTO dto);
	
} // end interface


