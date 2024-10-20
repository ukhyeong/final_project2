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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data

@EntityListeners({ 
	CommonEntityLifecyleListener.class,
	AuditingEntityListener.class 
})
@Entity
@Table(name="lecture")
// IdClass: 복합키를 사용하기 위한 어노테이션
@IdClass(LectureId.class)
public class Lecture implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	// 1. 복합키, PK
	// 식별자
	@Id @Column(name = "lecture_name")
	private String lectureName;
	
	
	// FK
	@Id @ManyToOne(targetEntity = Professor.class)
    @JoinColumn(name = "professor_number", referencedColumnName = "professor_number", nullable = false)
    private Professor professor;
		
	public void setProfessor(Professor professor) {
		log.trace("setProfessor({}) invoked.", professor);
		
		Professor oldProfessor = this.getProfessor();
		
		if(oldProfessor != null) {
			boolean isRemoved = oldProfessor.getLectures().remove(this);
			log.info("\t+ isRemoved: {}", isRemoved);
		} // if
		
		if(professor != null) {
			this.professor = professor;
			
			this.professor.getLectures().add(this);
		} // if
	} // setProfessor
	
	
	// 2. 일반 속성
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private LectureDay lectureDay;
	
	@Basic(optional = false)
	private Integer lectureTime;
	
	
	// 3. 연관관계
	@ToString.Exclude
	@OneToMany(mappedBy = "lecture", targetEntity = Course.class)
    private List<Course> myCourse = new Vector<Course>();
} // end class