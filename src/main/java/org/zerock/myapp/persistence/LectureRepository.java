package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Lecture;
import org.zerock.myapp.entity.LectureId;


@Repository
public interface LectureRepository extends JpaRepository<Lecture, LectureId>{
	// Query Methods 선언해서 사용
	
} // end interface
