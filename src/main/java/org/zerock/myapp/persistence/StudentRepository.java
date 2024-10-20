package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Professor;
import org.zerock.myapp.entity.Student;
import org.zerock.myapp.entity.User;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	// Query Methods 선언해서 사용
	
	public abstract Student findByUser(User user);
} // end interface
