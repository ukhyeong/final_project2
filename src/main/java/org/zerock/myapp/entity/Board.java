package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.myapp.common.CommonEntityLifecyleListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Slf4j

@EqualsAndHashCode(callSuper=false)
@Data

@EntityListeners({ 
	CommonEntityLifecyleListener.class,
	
	// 스프링부트의 실행클래스에 붙여놓은, @EnableJpaAuditing 어노테이션대로
	// 각 엔티티 클래스의 @CreateDate, @LastModifiedDate 어노테이션이, 
	// 감사자료(최초등록일시, 최종수정일시)를 자동생성하려면, 아래의 리스너 타입정보를 추가로 제공해야 합니다.
	AuditingEntityListener.class 
})

@Entity(name = "Board")
@Table(name = "board")
public class Board extends JpaAudit implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	// 1. PK 속성
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;

	
	// 2. 일반속성
	@Basic(optional = false)
	private String title;
	
	@Basic
	private String content;
	
	@Column(columnDefinition = "INTEGER default 0", insertable = false, nullable = false)
	private Integer cnt;
	
	
	// 4. 연관관계 매핑
	// (1) before, common property
//	@Basic(optional = false)
//	private String writer;
	
	// (2) after, FK
	@ManyToOne(targetEntity = Professor.class)
    @JoinColumn(name = "professor_number", referencedColumnName = "professor_number", nullable = false)
    private Professor professor;
	
	public void setProfessor(Professor professor) {
		log.trace("setProfessor({}) invoked.", professor);
		
		Professor oldProfessor = this.getProfessor();
		
		if(oldProfessor != null) {
			boolean isRemoved = oldProfessor.getBoards().remove(this);
			log.info("\t+ isRemoved: {}", isRemoved);
		} // if
		
		if(professor != null) {
			this.professor = professor;
			
			this.professor.getBoards().add(this);
		} // if
	} // setProfessor
	
	@OneToMany(mappedBy = "board", targetEntity = Comment.class, fetch = FetchType.EAGER)
	@ToString.Exclude
    private List<Comment> comments = new Vector<>();
} // end class


