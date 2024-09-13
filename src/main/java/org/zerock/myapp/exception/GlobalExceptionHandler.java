package org.zerock.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;


//@Log4j2
@Slf4j

@NoArgsConstructor

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
