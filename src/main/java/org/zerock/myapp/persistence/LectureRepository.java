package org.zerock.myapp.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Lecture;
import org.zerock.myapp.entity.LectureId;


@Repository
public interface LectureRepository extends JpaRepository<Lecture, LectureId>{
	// Query Methods 선언해서 사용
	
	// 1. 교수번호를 기준으로 검색
	public abstract Page<Lecture> findByProfessorNumber(Long number, Pageable paging);
} // end interface
