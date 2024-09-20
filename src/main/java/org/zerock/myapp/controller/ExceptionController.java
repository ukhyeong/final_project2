package org.zerock.myapp.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.myapp.util.SharedAttributes;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

// 사실상 지역 예외 처리 테스트를 위한 컨트롤러인듯
@RequestMapping("/exception/")
@Controller("exceptionController")
public class ExceptionController {
	
	// 아래의 지역 예외처리기 발동을 위한 매핑 URI
	@GetMapping("/SQLException")
	void raiseSQLException() throws SQLException {
		log.trace("raiseSQLException() invoked.");
		throw new SQLException("SQL syntax error");
	} // raiseSQLException
	
	
	// Local Controller Exception Handler
	@ExceptionHandler(SQLException.class)
	String handleLocalException(Exception e, Model model) {
		log.trace("handleLocalException({}, model) invoked.", e.toString());
		e.printStackTrace();
		
		model.addAttribute(SharedAttributes.EXCEPTION, e);
		model.addAttribute(SharedAttributes.STACKTRACE, e.getStackTrace());
		
		return "/exception/localError.html";
	} // handleLocalException

} // end class


