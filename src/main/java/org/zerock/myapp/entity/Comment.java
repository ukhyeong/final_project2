package org.zerock.myapp.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.myapp.common.CommonEntityLifecyleListener;

import jakarta.persistence.Basic	;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;


@Slf4j

@EqualsAndHashCode(callSuper=false)
@Data

@EntityListeners({ 
	CommonEntityLifecyleListener.class,
	AuditingEntityListener.class 
})

@Entity
@Table(name = "comment")
public class Comment extends JpaAudit {
	private static final long serialVersionUID = 1L;
	
	// 1. pk 속성
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	
	
	// 2. 일반 속성
	@Basic(optional = false)
	private String writer;
	
	@Basic(optional = false)
	private String content;
	
	@Basic(optional = false)
	private Integer level1;
	
	@Basic(optional = false)
	private Integer level2;
	
	
	// 3. fk 속성
	@ManyToOne(targetEntity = Board.class)
	@JoinColumn(name = "board_id", referencedColumnName = "board_id", nullable = false)
	private Board board;
	
	public void setBoard(Board board) {
		log.trace("setBoard({}) invoked.", board);
		
		Board oldBoard = this.getBoard();
		
		if(oldBoard != null) {
			boolean isRemoved = oldBoard.getComments().remove(this);
			log.info("\t+ isRemoved: {}", isRemoved);
		} // if
		
		if(board != null) {
			this.board = board;
			
			this.board.getComments().add(this);
		} // if
	} // setBoard
	
	// 4. 연관 관계
	
} // end class
