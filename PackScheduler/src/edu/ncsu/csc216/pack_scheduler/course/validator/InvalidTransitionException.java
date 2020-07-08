package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Exception throws when it is invalid transition.
 * @author Xuhui Lin
 *
 */
public class InvalidTransitionException extends Exception {
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a InvalidTransitionException with a specified message. Calls the parent class
	 * Exception with the message
	 * 
	 * @param message the message to go with the ConflictException
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Creates a Invalid Transition Exception with the default message. Calls the overloaded
	 * constructor with a String parameter for a message for the default message
	 */
	public InvalidTransitionException() {
		super("Invalid FSM Transition.");
	}
}
