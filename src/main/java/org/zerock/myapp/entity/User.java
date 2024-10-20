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
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper=false)
@Data

@EntityListeners({ 
	CommonEntityLifecyleListener.class,
	AuditingEntityListener.class 
})
@Entity
@Table(name="UZER")
public class User extends JpaAudit implements Serializable { 
	@Serial private static final long serialVersionUID = 1L;

	// 1. PK
	@Id 
//	@GeneratedValue // 사용자가 지정한아이디가 PK 로 사용됨으로 자동적으로 생성해주면 안됨
	@Column(name = "user_id")
	private String id;
	
	
	// 2. 일반속성
	@Basic(optional = false)
	private String password;
	
	// Role : 학생/교수 2가지 값만 들어오기 때문에 enum 열거타입 사용해봄
	@Basic(optional = false)
	// @Enumerated : h2 데이터베이스에서 enum 타입을 String 문자열로 사용하도록 하기위한 어노테이션
	@Enumerated(EnumType.STRING)
	private Role role;

	
	// 3. FK
    @OneToOne(
    		mappedBy = "user",
    		targetEntity = Student.class,
		    optional = true)
	private Student student;
    
    @OneToOne(
    		mappedBy = "user",
    		targetEntity = Professor.class,
		    optional = true)
	private Professor professor;
	
} // end class
