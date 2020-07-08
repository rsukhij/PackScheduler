package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests CourseNameValidatorFSM.
 * 
 * @author Xuhui Lin
 * @author Ethan Taylor
 */
public class CourseNameValidatorFSMTest {

	/**
	 * Tests {@link edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidatorFSM#isValid(String)}
	 */
	@Test
	public void testIsValid() {
		CourseNameValidatorFSM a = new CourseNameValidatorFSM();
		
		try {
			a.isValid("/****/");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name can only contain letters and digits.");
		}
		
		try {
			a.isValid("123");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name must start with a letter.");
		}
		
		try {
			a.isValid("ABCDE");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name cannot start with more than 4 letters.");
		}
		
		
		try {
			a.isValid("ABC1A");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name must have 3 digits.");
		}
		
		try {
			a.isValid("ABC12A");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name must have 3 digits.");
		}
		
		try {
			a.isValid("ABC1234");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name can only have 3 digits.");
		}
		
		try {
			a.isValid("ABC123AA");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name can only have a 1 letter suffix.");
		}
		
		try {
			a.isValid("AB123A1");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name cannot contain digits after the suffix.");
		}
		
		try {
			a.isValid("ABCD123A1");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name cannot contain digits after the suffix.");
		}
		
		try {
			a.isValid("A123A");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}

}
