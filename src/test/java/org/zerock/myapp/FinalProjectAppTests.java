package org.zerock.myapp;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Grade;
import org.zerock.myapp.entity.Lecture;
import org.zerock.myapp.entity.LectureDay;
import org.zerock.myapp.entity.Professor;
import org.zerock.myapp.entity.Rank;
import org.zerock.myapp.entity.Role;
import org.zerock.myapp.entity.Student;
import org.zerock.myapp.entity.User;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.GradeRepository;
import org.zerock.myapp.persistence.LectureRepository;
import org.zerock.myapp.persistence.ProfessorRepository;
import org.zerock.myapp.persistence.StudentRepository;
import org.zerock.myapp.persistence.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

// create, update 테스트 하기 쉽도록 어노테이션에 지정
@SpringBootTest(
//		properties = "spring.jpa.hibernate.ddl-auto=create"
		properties = "spring.jpa.hibernate.ddl-auto=update"
		)
class FinalProjectAppTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		Objects.requireNonNull(this.userRepository);
		Objects.requireNonNull(this.professorRepository);
		Objects.requireNonNull(this.studentRepository);
		Objects.requireNonNull(this.lectureRepository);
		Objects.requireNonNull(this.courseRepository);
		Objects.requireNonNull(this.gradeRepository);
		Objects.requireNonNull(this.bcryptEncoder);
		
		log.info("\t+ this.userRepository : {}", this.userRepository);
		log.info("\t+ this.professorRepository : {}", this.professorRepository);
		log.info("\t+ this.studentRepository : {}", this.studentRepository);
		log.info("\t+ this.lectureRepository : {}", this.lectureRepository);
		log.info("\t+ this.courseRepository : {}", this.courseRepository);
		log.info("\t+ this.gradeRepository : {}", this.gradeRepository);
		log.info("\t+ this.passwordConfig : {}", this.bcryptEncoder);
	} // beforeAll
	
	
	@Test
	@Order(1)
	@Transactional
	@Rollback(false)
	void contextLoads() {
		log.trace("contextLoads() invoked.");
		
		IntStream.rangeClosed(1, 2).forEach(n -> {
			User user = new User();
			user.setId("PROFESSOR_ID_" + n);
			
			String password = "PASSWORD_" + n;
			
			user.setPassword(bcryptEncoder.encode(password));
			user.setRole(Role.PROFESSOR);
			
			this.userRepository.save(user);
			
			Professor professor = new Professor();
			professor.setName("김교수" + n);
			professor.setDepartment("컴공과");
			professor.setUser(user);
			
			this.professorRepository.save(professor);
		}); // forEach
		
		
		
		IntStream.rangeClosed(1, 4).forEach(n -> {
			User user = new User();
			user.setId("STUDENT_ID_" + n);
			
			String password = "PASSWORD_" + n;
			
			user.setPassword(bcryptEncoder.encode(password));
			user.setRole(Role.STUDENT);
			
			this.userRepository.save(user);
			
			Student student = new Student();
			student.setName("최학생" + n);
			student.setDepartment("컴공과");
			student.setUser(user);
			
			this.studentRepository.save(student);
		}); // forEach
		
	} // contextLoads
	
	
	@Test
	@Order(2)
	@Transactional
	@Rollback(false)
	void lectureAddTest() {
		log.trace("lectureAddTest() invoked.");
		
		IntStream.rangeClosed(1, 2).forEach(n -> {
			Lecture lecture = new Lecture();
			
			lecture.setLectureName("LECTURE_" + n);
			
			Optional<Professor> professor = this.professorRepository.findById(Long.valueOf(n));
			professor.ifPresent(p -> {
				lecture.setProfessor(p);
			}); // ifPresent
			
			lecture.setLectureDay(LectureDay.MON);
			
			Integer[] time = {123, 567};
			lecture.setLectureTime(time[n-1]);
			
			this.lectureRepository.save(lecture);
		}); // forEach

		
	} // lectureAddTest

	
	@Test
	@Order(3)
	@Transactional
	@Rollback(false)
	void courseAddTest() {
		log.trace("courseAddTest() invoked.");
		
		IntStream.rangeClosed(1, 4).forEach(n -> {
			Course course = new Course();
			
			// 어떤 학생이 수강신청을 하는가?
			Optional<Student> student = this.studentRepository.findById(Long.valueOf(n));
			student.ifPresent(p -> {
				course.setStudent(p);
			}); // ifPresent
			
			// 위 학생이 어떤 강좌를 신청하는가?
			Lecture lecture = new Lecture();
			
			lecture.setLectureName("LECTURE_" + (n > 2 ? n-2 : n));
			
			Optional<Professor> professor = this.professorRepository.findById(Long.valueOf(n > 2 ? n-2 : n));
			professor.ifPresent(p -> {
				lecture.setProfessor(p);
				course.setLecture(lecture);
			}); // ifPresent
			
			this.courseRepository.save(course);
		}); // forEach

		
	} // courseAddTest
	
	
	@Test
	@Order(4)
	@Transactional
	@Rollback(false)
	void gradeAddTest() {
		log.trace("gradeAddTest() invoked.");
		
		IntStream.rangeClosed(1, 4).forEach(n -> {
			Grade grade = new Grade();
			
			Optional<Course> course = this.courseRepository.findById(Long.valueOf(n));
			course.ifPresent(p ->{
				grade.setCourse(p);
				grade.setRank(Rank.A);
			}); // ifPresent
			
			this.gradeRepository.save(grade);
		}); // forEach

		
	} // gradeAddTest
	
	
} // end class
