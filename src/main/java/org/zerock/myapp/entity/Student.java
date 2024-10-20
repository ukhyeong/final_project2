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

@EqualsAndHashCode(callSuper=false)
@Data

@EntityListeners({ 
	CommonEntityLifecyleListener.class,
	AuditingEntityListener.class 
})
@Entity
@Table(name="student")
public class Student extends JpaAudit implements Serializable { // Main, 1, FK
	@Serial private static final long serialVersionUID = 1L;

	// 1. PK
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_number")
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
         
          this.user.setStudent(this);
       } // if
    } // setUser
	
    
    // 4. 연관관계
    @OneToMany(mappedBy = "student", targetEntity = Course.class)
    @ToString.Exclude
    private List<Course> myCourse = new Vector<Course>();
} // end class
