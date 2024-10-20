package org.zerock.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;


@Slf4j

@NoArgsConstructor

// 전역 예외 처리 클래스
// 이를 통해 오류가 발생했을 때 클라이언트는 예외에 대한 정보와 함께 HTTP 응답을 받게 됩니다.
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	ResponseEntity<ErrorDetails> handleAllExceptions(Exception e, WebRequest req) {
		log.trace("handleAllExceptions({}, {}) invoked.", e, req);
		
		ErrorDetails errorDetails = new ErrorDetails(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage(),
				req.getDescription(true)
			);
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	} // handleAllExceptions
	

} // end class


@Value
final class ErrorDetails {
	private Integer statusCode;		
	private String message;
	private String description;		
	
	
} // end class
