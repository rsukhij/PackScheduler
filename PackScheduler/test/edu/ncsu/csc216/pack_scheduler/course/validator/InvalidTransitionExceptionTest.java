package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests InvalidTransitionException.
 * 
 * @author Xuhui Lin
 * @author Ethan Taylor
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Tests {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException#InvalidTransitionException(String)}
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ce = new InvalidTransitionException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Tests {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException#InvalidTransitionException()}
	 */
	@Test
	public void testInvalidTransitionException() {
		InvalidTransitionException ce = new InvalidTransitionException();
	    assertEquals("Invalid FSM Transition.", ce.getMessage());
	}

}
