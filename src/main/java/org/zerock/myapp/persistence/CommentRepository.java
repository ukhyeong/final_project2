package org.zerock.myapp.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	// Query Methods
	public abstract Page<Comment> findByBoard(Board board, Pageable paging);
} // end interface


