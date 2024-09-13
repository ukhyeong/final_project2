package org.zerock.myapp.exception;

import java.io.Serial;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class BoardNotFoundException extends BoardException {
	@Serial private static final long serialVersionUID = 1L;
	
	public BoardNotFoundException(String message) { super(message); }
	public BoardNotFoundException(Exception cause) { super(cause); }

} // end class
