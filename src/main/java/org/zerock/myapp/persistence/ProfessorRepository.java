package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Professor;
import org.zerock.myapp.entity.User;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	// Query Methods 선언해서 사용
	
	// 1. Professor 테이블의 FK 인 User 속성의 값으로 완전 일치 검색
	public abstract Professor findByUser(User user);
	
} // end interface
