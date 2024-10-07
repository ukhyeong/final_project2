package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	// Query Methods
} // end interface


