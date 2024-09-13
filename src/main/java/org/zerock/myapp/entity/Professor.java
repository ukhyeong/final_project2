package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import org.zerock.myapp.common.CommonEntityLifecyleListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Slf4j

@EqualsAndHashCode(callSuper = false)
@Data

@EntityListeners(CommonEntityLifecyleListener.class)
@Entity
@Table(name="professor")
public class Professor extends JpaAudit implements Serializable { // Main, 1, FK
	@Serial private static final long serialVersionUID = 1L;

	// 1. PK
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "professor_number")
	private Long number;
   
	
	// 2. 일반속성
	@Column(nullable = false)
	private String name;
	
	@Basic(optional = false)
	private String department;

	
	// 3. FK
    @OneToOne(targetEntity = User.class)
    @JoinColumn(
       name = "my_id",
       referencedColumnName = "user_id",
       unique = true)
    @ToString.Exclude
    private User user;   
   
    public void setUser(User user) {
       log.trace("setUser({}) invoked.", user);
      
       if(user != null) {
          this.user = user;
          this.user.setProfessor(this);
       } // if
    } // setUser
    
    
    // 4. 연관관계, 양방향을 위한 벡터 선언
 	@OneToMany(mappedBy = "professor", targetEntity = Lecture.class)
 	@ToString.Exclude
 	private List<Lecture> lectures = new Vector<>(); 
	
} // end class
