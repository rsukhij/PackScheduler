package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
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
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "A");
		CourseRoll roll = c.getCourseRoll();
		assertEquals(100, roll.getEnrollmentCap());
		assertEquals(100, roll.getOpenSeats());
		assertEquals(0, roll.getNumberOnWaitlist());
		
		try {
			new CourseRoll(c, 0);
			fail("Constructing a CourseRoll with an enrollmentCap of 0 should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment cap must be between 10 and 250", e.getMessage());
		}
		
		try {
			new CourseRoll(c, 500);
			fail("Constructing a CourseRoll with an enrollmentCap of 500 should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment cap must be between 10 and 250", e.getMessage());
		}
		
		try {
			new CourseRoll(null, 10);
			fail("Constructing a CourseRoll with a null Course should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Course cannot be null", e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#setEnrollmentCap(int)}.
	 */
	@Test
	public void testSetEnrollmentCap() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "A");
		CourseRoll roll = c.getCourseRoll();
		
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
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		StudentDirectory students = new StudentDirectory();
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		roll.enroll(students.getStudentById("student"));
		
		try {
			roll.enroll(null);
			fail("Enrolling null student should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Null student can't be enrolled", e.getMessage());
			assertEquals(9, roll.getOpenSeats());
		}
		
		try {
			roll.enroll(students.getStudentById("student"));
			fail("Enrolling duplicate student should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Student can't be enrolled", e.getMessage());
			assertEquals(9, roll.getOpenSeats());
		}
		
		students.loadStudentsFromFile("test-files/student_records.txt");
		for (int i = 0; i < students.getStudentDirectory().length; i++) {
			roll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		assertEquals(1, roll.getNumberOnWaitlist());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#drop(edu.ncsu.csc216.pack_scheduler.user.Student)}.
	 */
	@Test
	public void testDrop() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		StudentDirectory students = new StudentDirectory();
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		roll.enroll(students.getStudentById("student"));
		
		try {
			roll.drop(null);
			fail("Dropping a null student should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Null student can't be dropped", e.getMessage());
			assertEquals(9, roll.getOpenSeats());
		}
		
		roll.drop(students.getStudentById("student"));
		assertEquals(10, roll.getOpenSeats());
		
		try {
			roll.drop(students.getStudentById("student"));
		} catch (Exception e) {
			fail("Dropping a student not enrolled should not throw an error");
		}
		
		
		roll.enroll(students.getStudentById("student"));
		students.loadStudentsFromFile("test-files/student_records.txt");
		for (int i = 0; i < students.getStudentDirectory().length; i++) {
			roll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		roll.drop(students.getStudentById("student"));
		assertEquals(0, roll.getNumberOnWaitlist());
		assertEquals(0, roll.getOpenSeats());
		
		students.addStudent("Stu1", "Dent", "student1", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu2", "Dent", "student2", "student@ncsu.edu", "pw", "pw", 16);
		roll.enroll(students.getStudentById("student1"));
		roll.enroll(students.getStudentById("student2"));
		assertEquals(2, roll.getNumberOnWaitlist());
		roll.drop(students.getStudentById("student1"));
		assertEquals(1, roll.getNumberOnWaitlist());
		roll.enroll(students.getStudentById("student1"));
		roll.drop(students.getStudentById("student1"));
		assertEquals(1, roll.getNumberOnWaitlist());
		roll.drop(students.getStudentById("student2"));
		assertEquals(0, roll.getNumberOnWaitlist());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#getOpenSeats()}.
	 */
	@Test
	public void testGetOpenSeats() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		
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
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		StudentDirectory students = new StudentDirectory();
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		
		assertTrue(roll.canEnroll(students.getStudentById("student")));
		
		roll.enroll(students.getStudentById("student"));
		assertFalse(roll.canEnroll(students.getStudentById("student")));
		
		roll.drop(students.getStudentById("student"));
		students.loadStudentsFromFile("test-files/student_records.txt");
		for (int i = 0; i < students.getStudentDirectory().length; i++) {
			roll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		assertFalse(roll.canEnroll(students.getStudentById(students.getStudentDirectory()[0][2])));
		
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		
		assertTrue(roll.canEnroll(students.getStudentById("student")));
		
		roll.enroll(students.getStudentById("student"));
		assertFalse(roll.canEnroll(students.getStudentById("student")));
		
		for (int i = 0; i < students.getStudentDirectory().length; i++) {
			roll.drop(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		roll.drop(students.getStudentById("student"));
		
		assertTrue(roll.canEnroll(students.getStudentById("student")));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll#getNumberOnWaitlist()}.
	 */
	@Test
	public void testGetNumberOnWaitlist() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();		
		assertEquals(0, roll.getNumberOnWaitlist());

		StudentDirectory students = new StudentDirectory();
		students.loadStudentsFromFile("test-files/student_records.txt");
		for (int i = 0; i < students.getStudentDirectory().length; i++) {
			roll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		assertEquals(0, roll.getNumberOnWaitlist());
		
		students.addStudent("Stu", "Dent", "student", "student@ncsu.edu", "pw", "pw", 16);
		roll.enroll(students.getStudentById("student"));
		assertEquals(1, roll.getNumberOnWaitlist());
		
		students.addStudent("Stu1", "Dent", "student1", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu2", "Dent", "student2", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu3", "Dent", "student3", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu4", "Dent", "student4", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu5", "Dent", "student5", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu6", "Dent", "student6", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu7", "Dent", "student7", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu8", "Dent", "student8", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu9", "Dent", "student9", "student@ncsu.edu", "pw", "pw", 16);
		roll.enroll(students.getStudentById("student1"));
		roll.enroll(students.getStudentById("student2"));
		roll.enroll(students.getStudentById("student3"));
		roll.enroll(students.getStudentById("student4"));
		roll.enroll(students.getStudentById("student5"));
		roll.enroll(students.getStudentById("student6"));
		roll.enroll(students.getStudentById("student7"));
		roll.enroll(students.getStudentById("student8"));
		roll.enroll(students.getStudentById("student9"));
		assertEquals(10, roll.getNumberOnWaitlist());
	}
}
