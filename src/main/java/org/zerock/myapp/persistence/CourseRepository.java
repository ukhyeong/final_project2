package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Course;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
	// Query Methods 선언해서 사용
	
} // end interface
