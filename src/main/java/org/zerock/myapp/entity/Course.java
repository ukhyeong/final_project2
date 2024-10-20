package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.myapp.common.CommonEntityLifecyleListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data

@EntityListeners({ 
	CommonEntityLifecyleListener.class,
	AuditingEntityListener.class 
})
@Entity
@Table(name="course")
public class Course implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	// 1. 인조키, PK
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Long id;
	
	
	// 2. FK 
	@ManyToOne(targetEntity = Student.class)
	@JoinColumn(name = "student_number", referencedColumnName = "student_number", nullable = false)
	private Student student;
	
	// Lecture 의 PK인 복합키를 FK로 사용
	@ManyToOne(targetEntity = Lecture.class)
	@JoinColumns({
	    @JoinColumn(name = "lecture_name", referencedColumnName = "lecture_name", nullable = false),
	    @JoinColumn(name = "professor_number", referencedColumnName = "professor_number", nullable = false)
	})
	private Lecture lecture;
	
	
	// 3. 연관관계
	@OneToOne(mappedBy = "course", targetEntity = Grade.class)
	private Grade grade;
} // end class
