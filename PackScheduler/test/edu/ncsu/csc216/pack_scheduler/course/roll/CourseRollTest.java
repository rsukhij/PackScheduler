package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;

/**
 * Tests CourseRoll
 * 
 * @author Ethan Taylor
 */
public class CourseRollTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#CourseRoll(int)}
	 * and {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#getEnrollmentCap()}.
	 */
	@Test
	public void testCourseRoll() {
		CourseRoll roll = new CourseRoll(100);
		assertEquals(100, roll.getEnrollmentCap());
		assertEquals(100, roll.getOpenSeats());
		
		try {
			new CourseRoll(0);
			fail("Constructing a CourseRoll with an enrollmentCap of 0 should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment cap must be between 10 and 250", e.getMessage());
		}
		
		try {
			new CourseRoll(500);
			fail("Constructing a CourseRoll with an enrollmentCap of 500 should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment cap must be between 10 and 250", e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#setEnrollmentCap(int)}.
	 */
	@Test
	public void testSetEnrollmentCap() {
		CourseRoll roll = new CourseRoll(100);
		
		roll.setEnrollmentCap(200);
		assertEquals(200, roll.getEnrollmentCap());
		
		roll.setEnrollmentCap(12);
		assertEquals(12, roll.getEnrollmentCap());
		
		StudentDirectory students = new StudentDirectory();
		students.loadStudentsFromFile("test-files/student_records.txt");
		students.addStudent("Student", "11", "s11", "s11@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Student", "12", "s12", "s11@ncsu.edu", "pw", "pw", 16);
		for (int i = 0; i < students.getStudentDirectory().length; i++) {
			roll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		assertEquals(0, roll.getOpenSeats());
		
		try {
			roll.setEnrollmentCap(10);
			fail("Setting enrollment cap less than number of students enrolled should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment cap must be at least the number of students enrolled", e.getMessage());
		}
		
		assertEquals(12, roll.getEnrollmentCap());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#enroll(edu.ncsu.csc216.pack_scheduler.user.Student)}.
	 */
	@Test
	public void testEnroll() {
		CourseRoll roll = new CourseRoll(100);
		StudentDirectory students = new StudentDirectory();
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		roll.enroll(students.getStudentById("student"));
		
		try {
			roll.enroll(null);
			fail("Enrolling null student should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Null student can't be enrolled", e.getMessage());
			assertEquals(99, roll.getOpenSeats());
		}
		
		try {
			roll.enroll(students.getStudentById("student"));
			fail("Enrolling duplicate student should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Student can't be enrolled", e.getMessage());
			assertEquals(99, roll.getOpenSeats());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#drop(edu.ncsu.csc216.pack_scheduler.user.Student)}.
	 */
	@Test
	public void testDrop() {
		CourseRoll roll = new CourseRoll(100);
		StudentDirectory students = new StudentDirectory();
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		roll.enroll(students.getStudentById("student"));
		
		try {
			roll.drop(null);
			fail("Dropping a null student should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Null student can't be dropped", e.getMessage());
			assertEquals(99, roll.getOpenSeats());
		}
		
		roll.drop(students.getStudentById("student"));
		assertEquals(100, roll.getOpenSeats());
		
		try {
			roll.drop(students.getStudentById("student"));
		} catch (Exception e) {
			fail("Dropping a student not enrolled should not throw an error");
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#getOpenSeats()}.
	 */
	@Test
	public void testGetOpenSeats() {
		CourseRoll roll = new CourseRoll(10);
		
		assertEquals(10, roll.getOpenSeats());
		
		StudentDirectory students = new StudentDirectory();
		students.loadStudentsFromFile("test-files/student_records.txt");
		for (int i = 0; i < students.getStudentDirectory().length; i++) {
			roll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		assertEquals(0, roll.getOpenSeats());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#canEnroll(edu.ncsu.csc216.pack_scheduler.user.Student)}.
	 */
	@Test
	public void testCanEnroll() {
		CourseRoll roll = new CourseRoll(10);
		StudentDirectory students = new StudentDirectory();
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		
		assertTrue(roll.canEnroll(students.getStudentById("student")));
		
		roll.enroll(students.getStudentById("student"));
		assertFalse(roll.canEnroll(students.getStudentById("student")));		
		
		students.loadStudentsFromFile("test-files/student_records.txt");
		for (int i = 0; i < students.getStudentDirectory().length - 1; i++) {
			roll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		
		assertFalse(roll.canEnroll(students.getStudentById("student")));
	}

}
