package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Faculty
 * 
 * @author Ethan Taylor
 */
public class FacultyTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#Faculty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testFaculty() {
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		assertEquals("first", f.getFirstName());
		assertEquals("last", f.getLastName());
		assertEquals("id", f.getId());
		assertEquals("email@ncsu.edu", f.getEmail());
		assertEquals("pw", f.getPassword());
		assertEquals(2, f.getMaxCourses());
		
		try {
			new Faculty("first", "last", null, "email@ncsu.edu", "pw", 2);
			fail("Null id should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid id", e.getMessage());
		}
		
		try {
			new Faculty("first", "last", "", "email@ncsu.edu", "pw", 2);
			fail("Empty id should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid id", e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.User#setFirstName(java.lang.String)}.
	 */
	@Test
	public void testSetFirstName() {
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		
		f.setFirstName("newFirst");
		assertEquals("newFirst", f.getFirstName());
		
		try {
			new Faculty(null, "last", "id", "email@ncsu.edu", "pw", 2);
			fail("Null first name should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid first name", e.getMessage());
		}
		
		try {
			new Faculty("", "last", "id", "email@ncsu.edu", "pw", 2);
			fail("Empty first name should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid first name", e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.User#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		
		f.setLastName("newLast");
		assertEquals("newLast", f.getLastName());
		
		try {
			new Faculty("first", null, "id", "email@ncsu.edu", "pw", 2);
			fail("Null last name should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid last name", e.getMessage());
		}
		
		try {
			new Faculty("first", "", "id", "email@ncsu.edu", "pw", 2);
			fail("Empty last name should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid last name", e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.User#setEmail(java.lang.String)}.
	 */
	@Test
	public void testSetEmail() {
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		
		f.setEmail("newEmail@ncsu.edu");
		assertEquals("newEmail@ncsu.edu", f.getEmail());
		
		try {
			f.setEmail("e.mail@ncsu.edu");
			assertEquals("e.mail@ncsu.edu", f.getEmail());
		} catch (IllegalArgumentException e) {
			fail("Email with . before @ should be valid");
		}
		
		try {
			new Faculty("first", "last", "id", null, "pw", 2);
			fail("Null email should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
		}
		
		try {
			new Faculty("first", "last", "id", "", "pw", 2);
			fail("Empty email should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
		}
		
		try {
			new Faculty("first", "last", "id", "emailatncsu.edu", "pw", 2);
			fail("Email without @ should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
		}
		
		try {
			new Faculty("first", "last", "id", "email@ncsudotedu", "pw", 2);
			fail("Email without . should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
		}
		
		try {
			new Faculty("first", "last", "id", "email.ncsu@edu", "pw", 2);
			fail("Email with @ after . should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.User#setPassword(java.lang.String)}.
	 */
	@Test
	public void testSetPassword() {
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		
		f.setPassword("newPW");
		assertEquals("newPW", f.getPassword());
		
		try {
			new Faculty("first", "last", "id", "email@ncsu.edu", null, 2);
			fail("Null password should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid password", e.getMessage());
		}
		
		try {
			new Faculty("first", "last", "id", "email@ncsu.edu", "", 2);
			fail("Empty password should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid password", e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setMaxCourses(int)}.
	 */
	@Test
	public void testSetMaxCourses() {
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		
		f.setMaxCourses(1);
		assertEquals(1, f.getMaxCourses());
		f.setMaxCourses(3);
		assertEquals(3, f.getMaxCourses());
		
		try {
			new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 0);
			fail("Max courses less than 1 should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid max courses", e.getMessage());
		}
		
		try {
			new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 4);
			fail("Max courses greater than 3 should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid max courses", e.getMessage());
		}
	}	
	
	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		Faculty f1 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		Faculty f2 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		Faculty f3 = new Faculty("difFirst", "last", "id", "email@ncsu.edu", "pw", 2);
		Faculty f4 = new Faculty("first", "difLast", "id", "email@ncsu.edu", "pw", 2);
		Faculty f5 = new Faculty("first", "last", "difId", "email@ncsu.edu", "pw", 2);
		Faculty f6 = new Faculty("first", "last", "id", "difEmail@ncsu.edu", "pw", 2);
		Faculty f7 = new Faculty("first", "last", "id", "email@ncsu.edu", "difPw", 2);
		Faculty f8 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 3);
		
		assertEquals(f1.hashCode(), f2.hashCode());
		
		assertNotEquals(f1.hashCode(), f3.hashCode());
		assertNotEquals(f1.hashCode(), f4.hashCode());
		assertNotEquals(f1.hashCode(), f5.hashCode());
		assertNotEquals(f1.hashCode(), f6.hashCode());
		assertNotEquals(f1.hashCode(), f7.hashCode());
		assertNotEquals(f1.hashCode(), f8.hashCode());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Faculty f1 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		Faculty f2 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		Faculty f3 = new Faculty("difFirst", "last", "id", "email@ncsu.edu", "pw", 2);
		Faculty f4 = new Faculty("first", "difLast", "id", "email@ncsu.edu", "pw", 2);
		Faculty f5 = new Faculty("first", "last", "difId", "email@ncsu.edu", "pw", 2);
		Faculty f6 = new Faculty("first", "last", "id", "difEmail@ncsu.edu", "pw", 2);
		Faculty f7 = new Faculty("first", "last", "id", "email@ncsu.edu", "difPw", 2);
		Faculty f8 = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 3);
		
		assertTrue(f1.equals(f1));
		assertTrue(f1.equals(f2));
		assertTrue(f2.equals(f1));
		
		assertFalse(f1.equals(f3));
		assertFalse(f1.equals(f4));
		assertFalse(f1.equals(f5));
		assertFalse(f1.equals(f6));
		assertFalse(f1.equals(f7));
		assertFalse(f1.equals(f8));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#toString()}.
	 */
	@Test
	public void testToString() {
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "pw", 2);
		assertEquals("first,last,id,email@ncsu.edu,pw,2", f.toString());
	}
}
