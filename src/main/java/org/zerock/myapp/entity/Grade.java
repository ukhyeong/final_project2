package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.myapp.common.CommonEntityLifecyleListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data

@EntityListeners({ 
	CommonEntityLifecyleListener.class,
	AuditingEntityListener.class 
})
@Entity
@Table(name = "grade")
public class Grade implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	// 1. 인조키, PK
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grade_id")
	private Long id;

	
	// 2. 일반 속성
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private Rank rank;
	
	
	// 3. FK
	@OneToOne(targetEntity = Course.class)
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", unique = true)
	private Course course;
		
} // end class
