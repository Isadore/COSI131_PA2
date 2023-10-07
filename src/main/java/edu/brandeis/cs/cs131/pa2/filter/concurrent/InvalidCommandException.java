package edu.brandeis.cs.cs131.pa2.filter.concurrent;

/**
 * Custom unchecked exception used to pass information on command errors (syntax
 * + piping errors) between filters, command builder and the REPL.
 * 
 * @author Chami Lamelas
 *
 */
public class InvalidCommandException extends RuntimeException {

	public InvalidCommandException(String errorMessage) {
		super(errorMessage);
	}

}
