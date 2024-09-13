package org.zerock.myapp.exception;

import java.io.Serial;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public abstract class BoardException extends Exception {
	@Serial private static final long serialVersionUID = 1L;

	protected BoardException(String message) { super(message); }
	protected BoardException(Exception cause) { super(cause); }
	
} // end class
