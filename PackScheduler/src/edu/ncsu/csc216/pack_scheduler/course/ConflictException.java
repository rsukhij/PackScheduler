package edu.ncsu.csc216.pack_scheduler.course;

/**
 * ConflictException is to be thrown if two Activities' start and end times
 * overlap. ConflictException extends Exception.
 * 
 * @author Ethan Taylor
 */
public class ConflictException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a ConflictException with a specified message. Calls the parent class
	 * Exception with the message
	 * 
	 * @param message the message to go with the ConflictException
	 */
	public ConflictException(String message) {
		super(message);
	}

	/**
	 * Creates a Conflict Exception with the default message. Calls the overloaded
	 * constructor with a String parameter for a message for the default message
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
}
