package org.zerock.myapp.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.common.CommonBeanCallbacks;
import org.zerock.myapp.entity.Role;
import org.zerock.myapp.entity.User;
import org.zerock.myapp.persistence.ProfessorRepository;
import org.zerock.myapp.persistence.StudentRepository;
import org.zerock.myapp.persistence.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor	

@Service
public class UserServiceImpl extends CommonBeanCallbacks implements UserService {
	
	@Autowired private StudentRepository studentRepo;
	@Autowired private ProfessorRepository professorRepo;
	@Autowired private UserRepository userRepo;
	@Autowired private PasswordEncoder bcryptEncoder;
	
	@PostConstruct
	public void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.studentRepo);
		Objects.requireNonNull(this.professorRepo);
		Objects.requireNonNull(this.userRepo);
		Objects.requireNonNull(this.bcryptEncoder);
		
		log.info("\t+ this.studentRepo: {}", this.studentRepo);
		log.info("\t+ this.professorRepo: {}", this.professorRepo);
		log.info("\t+ this.userRepo: {}", this.userRepo);
		log.info("\t+ this.bcryptEncoder: {}", this.bcryptEncoder);
	} // postConstruct
	
	
	@Override
	public boolean registerUser(String username, String password, String role) {
		log.trace("registerUser({}, {}, {}) invoked.", username, password, role);
		
		User newUser = new User();
		
		try {
			String decoedUsername = URLDecoder.decode(username, "UTF-8");
			String decoedPassword = URLDecoder.decode(password, "UTF-8");
			String decoedRole = URLDecoder.decode(role, "UTF-8");
			
			newUser.setId(decoedUsername);
			newUser.setPassword(bcryptEncoder.encode(decoedPassword));
			
			if(decoedRole.equals("PROFESSOR")) {
				newUser.setRole(Role.PROFESSOR);
			} else if(decoedRole.equals("STUDENT")) {
				newUser.setRole(Role.STUDENT);
			} // if else
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
		} // try catch
		
		User saveUser = this.userRepo.<User>save(newUser);
		
		return (saveUser.getId() != null);
	} // registerUser
	
} // end class

