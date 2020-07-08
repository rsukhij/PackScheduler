package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * This test will test the student information like name,Id, email, Passward and
 * credit
 * 
 * @author Rohan Sukhija
 *
 */

public class StudentTest {

	/**
	 * Tests the hashCode() method
	 */
	@Test
	public void testHashCode() {
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User d = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User e = new Student("last", "last", "id", "email@ncsu.edu", "hashedpassword", 15);

		assertEquals(s.hashCode(), d.hashCode());
		assertNotEquals(s.hashCode(), e.hashCode(), 0);
	}

	/**
	 * Tests one of the constructors of the Student class
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {
		Student s = null;
		try {
			s = new Student(null, "last", "id", "email@ncsu.edu", "hashedpassword", 17);

		} catch (IllegalArgumentException e) {

			assertNull(s);
		}

		try {
			s = new Student("Sukhija", "Rohan", "rsukhij", "rsukhij@ncsu.edu", "password", 17);
			assertEquals("password", s.getPassword());
		} catch (IllegalArgumentException e) {
			fail();
		}

	}

	/**
	 * Tests the other constructor of the Student class
	 */
	@Test
	public void testStudentStringStringStringStringString() {
		Student s = null; // Initialize a student reference to null
		try {
			s = new Student(null, "last", "id", "email@ncsu.edu", "hashedpassword");

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(s);
		}

		try {
			s = new Student("Rohan", "Sukhija", "rsukhij", "rsukhij@ncsu.edu", "password");
			assertEquals("Rohan", s.getFirstName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test for the setter of the the email
	 */
	@Test
	public void testSetEmail() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
			s.setEmail(null);
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals("email@ncsu.edu", s.getEmail());
		}

		try {
			s.setEmail("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		try {
			s.setEmail("emailncsu.edu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		try {
			s.setEmail("email@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		try {
			s.setEmail("ema.il@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("email@ncsu.edu", s.getEmail());
		}

	}

	/**
	 * Test for checking if the student password setter method is working
	 */
	@Test
	public void testSetPassword() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
			s.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals("hashedpassword", s.getPassword());
		}

		try {
			s.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("hashedpassword", s.getPassword());
		}
	}

	/** Test for the first name setter of the Student class */

	@Test
	public void testSetFirstName() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
			s.setFirstName(null);
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("first", s.getFirstName());
		}

		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("first", s.getFirstName());
		}

		try {
			s.setFirstName("John");
			assertEquals("John", s.getFirstName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/** Test to check student's last name set up */

	@Test
	public void testSetLastName() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
			s.setLastName(null);
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("last", s.getLastName());
		}

		try {
			s.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("last", s.getLastName());
		}

		try {
			s.setLastName("Appleseed");
			assertEquals("Appleseed", s.getLastName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/** Test check the student Id set up */

	@Test
	public void testSetId() {

		try {
			new Student("first", "last", null, "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid id", e.getMessage());

		}

		try {
			new Student("first", "last", "", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid id", e.getMessage());
		}

	}

	/** Test to check the student maximum credits score */

	@Test
	public void testSetMaxCredits() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);

		try {
			s.setMaxCredits(2);
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals(15, s.getMaxCredits());
		}

		try {
			s.setMaxCredits(19);
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals(15, s.getMaxCredits());
		}
	}

	/** Test to check if the equals override is performing correctly */
	@Test
	public void testEqualsObject() {
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User d = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User e = new Student("last", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User f = new Student("first", "first", "id", "email@ncsu.edu", "hashedpassword", 15);
		User something = new Student("first", "last", "NOTid", "email@ncsu.edu", "hashedpassword", 15);
		User r = new Student("first", "last", "id", "emaghnil@ncsu.edu", "hashedpassword", 15);
		User p = new Student("first", "last", "id", "email@ncsu.edu", "hashedord", 15);
		User t = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 12);
		assertTrue(s.equals(d));
		assertTrue(s.equals(s));
		assertFalse(s == null);
		assertFalse(s.equals("String"));
		assertFalse(s.equals(e));
		assertFalse(s.equals(f));
		assertFalse(s.equals(something));
		assertFalse(s.equals(r));
		assertFalse(s.equals(p));
		assertFalse(s.equals(t));

	}

	/** Test to check the conversion the state to a String representation */
	@Test
	public void testToString() {
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		assertEquals("The Strings should be equal", "first,last,id,email@ncsu.edu,hashedpassword,15", s.toString());
	}

	/** Test to the check if the compareTo method is functioning properly */
	@Test
	public void testCompareTo() {
		Student s = new Student("first", "alpha", "id", "email@ncsu.edu", "hashedpassword", 15);
		Student d = new Student("first", "beta", "id", "email@ncsu.edu", "hashedpassword", 15);
		assertTrue(s.compareTo(d) < 0);
		s = new Student("first", "beta", "id", "email@ncsu.edu", "hashedpassword", 15);
		d = new Student("first", "alpha", "id", "email@ncsu.edu", "hashedpassword", 15);
		assertTrue(s.compareTo(d) > 0);
		s = new Student("first", "alpha", "id", "email@ncsu.edu", "hashedpassword", 15);
		d = new Student("firstd", "alpha", "id", "email@ncsu.edu", "hashedpassword", 15);
		assertTrue(s.compareTo(d) < 0);
		s = new Student("first", "alpha", "id", "email@ncsu.edu", "hashedpassword", 15);
		d = new Student("first", "alpha", "id", "email@ncsu.edu", "hashedpassword", 15);
		assertEquals(0, s.compareTo(d));
		s = new Student("first", "alpha", "id", "email@ncsu.edu", "hashedpassword", 15);
		d = new Student("first", "alpha", "zid", "email@ncsu.edu", "hashedpassword", 15);
		assertTrue(s.compareTo(d) < 0);
	}

	/**
	 * Test {@link Student#canAdd(Course)}.
	 */
	@Test
	public void testCanAdd() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpasswrod", 5);
		Course c = new Course("CSC216", "Computer Science", "005", 4, "oerwijgr", 10, "MW", 1230, 1330);
		
		assertTrue(s.canAdd(c));
		
		s.setMaxCredits(4);
		assertTrue(s.canAdd(c));
		
		s.setMaxCredits(3);
		assertFalse(s.canAdd(c));
	}
}
