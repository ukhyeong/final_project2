package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Board;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	// Query Methods
} // end interface


