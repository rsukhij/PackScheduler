package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Test case for Schedule
 * @author Xuhui Lin
 *
 */
public class ScheduleTest {
	
	private String emptyTitle;
	
	private Course emptyCourse;

	/**
	 * Test case for Schedule constrctor
	 */
	@Test
	public void testSchedule() {
		Schedule s = new Schedule();
		assertNotNull(s);
		assertEquals("My Schedule", s.getTitle());
	}

	/**
	 * Test case for addCourseToSchedule
	 */
	@Test
	public void testAddCourseToSchedule() {
		Schedule s = new Schedule();
		Course csc216 = new Course("CSC216", "Computer Science", "005", 4, "oerwijgr", 10, "MW", 1230, 1330);
		
		s.addCourseToSchedule(csc216);
		assertEquals(1, s.getScheduledCourses().length);
		
		Course duplicate = new Course("CSC216", "Computer Science", "003", 3, "oerwijgr", 10, "MW", 1030, 1130);
		try {
			s.addCourseToSchedule(duplicate);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You are already enrolled in CSC216", e.getMessage());
		}
		
		Course conflict = new Course("CSC226", "Computer Science 226", "004", 4, "xlin8", 10, "MW", 1230, 1330);
		try {
			s.addCourseToSchedule(conflict);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}

	}

	/**
	 * Test case for removeCourseFromSchedule
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		Schedule s = new Schedule();
		
		Course csc216 = new Course("CSC216", "Computer Science", "005", 4, "oerwijgr", 10, "MW", 1230, 1330);
		Course csc226 = new Course("CSC226", "Computer Science 226", "004", 3, "oerwijgr", 10, "MW", 1030, 1130);
		s.addCourseToSchedule(csc216);
		assertEquals(1, s.getScheduledCourses().length);
		s.addCourseToSchedule(csc226);
		assertEquals(2, s.getScheduledCourses().length);
		
		String[][] testString = s.getScheduledCourses();
		assertEquals("CSC216", testString[0][0]);
		assertEquals("005", testString[0][1]);
		assertEquals("Computer Science", testString[0][2]);
		assertEquals(csc216.getMeetingString(), testString[0][3]);
		
		s.removeCourseFromSchedule(csc216);
		testString = s.getScheduledCourses();
		assertEquals("CSC226", testString[0][0]);
		assertEquals("004", testString[0][1]);
		assertEquals("Computer Science 226", testString[0][2]);
		assertEquals(csc226.getMeetingString(), testString[0][3]);
		
		Course csc230 = new Course("CSC230", "Computer Science 230", "001", 2, "xlin8", 10, "MW", 1030, 1130);
		assertEquals(false, s.removeCourseFromSchedule(emptyCourse));
		assertEquals(false, s.removeCourseFromSchedule(csc230));
	}

	/**
	 * Test case for resetSchedule
	 */
	@Test
	public void testResetSchedule() {
		Schedule s = new Schedule();
		Course csc216 = new Course("CSC216", "Computer Science", "005", 4, "oerwijgr", 10, "MW", 1230, 1330);
		
		s.addCourseToSchedule(csc216);
		assertEquals(1, s.getScheduledCourses().length);
		
		s.resetSchedule();
		assertEquals(0, s.getScheduledCourses().length);
	}
	
	/**
	 * Test for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#getScheduledCourses()}
	 */
	@Test
	public void testGetScheduledCourses() {
		final String name = "CSC216";
		final String section = "005";
		final String title = "Computer Science";
		final String meeting = "MW 12:30PM-1:30PM";
		final int seats = 10;
		
		Schedule s = new Schedule();
		Course c = new Course(name, title, section, 4, "oerwijgr", seats, "MW", 1230, 1330);
		s.addCourseToSchedule(c);
		
		String[][] courses = s.getScheduledCourses();
		assertEquals(name, courses[0][0]);
		assertEquals(section, courses[0][1]);
		assertEquals(title, courses[0][2]);
		assertEquals(meeting, courses[0][3]);
		assertEquals(Integer.toString(seats), courses[0][4]);
		
		s.resetSchedule();
		StudentDirectory students = new StudentDirectory();
		students.addStudent("Stu1", "Dent", "student1", "student@ncsu.edu", "pw", "pw", 16);
		students.addStudent("Stu2", "Dent", "student2", "student@ncsu.edu", "pw", "pw", 16);
		c.getCourseRoll().enroll(students.getStudentById("student2"));
		s.addCourseToSchedule(c);
		assertEquals(1, s.getScheduledCourses().length);
	}

	/**
	 * Test case for setTitle
	 */
	@Test
	public void testSetTitle() {
		Schedule s = new Schedule();
		s.setTitle("happy every day");
		assertEquals("happy every day", s.getTitle());
		try {
			s.setTitle(emptyTitle);
		} catch (IllegalArgumentException e) {
			assertEquals("Title cannot be null", e.getMessage());
		}
	}

	/**
	 * Tests {@link Schedule#getScheduleCredits()}.
	 */
	@Test
	public void testGetScheduleCredits() {
		Schedule s = new Schedule();
		
		assertEquals(0, s.getScheduleCredits());
		
		s.addCourseToSchedule(new Course("CSC216", "Computer Science", "005", 4, "oerwijgr", 10, "MW", 1230, 1330));
		assertEquals(4, s.getScheduleCredits());
		
		s.addCourseToSchedule(new Course("CSC226", "Discrete Math", "001", 1, "oerwijgr", 10, "MW", 1430, 1530));
		assertEquals(5, s.getScheduleCredits());
	}
	
	/**
	 * Tests {@link Schedule#canAdd(Course)}.
	 */
	@Test
	public void testCanAdd() {
		Schedule s = new Schedule();
		
		Course c = new Course("CSC216", "Computer Science", "005", 4, "oerwijgr", 10, "MW", 1230, 1330);
		assertTrue(s.canAdd(c));
		s.addCourseToSchedule(c);
		
		assertFalse(s.canAdd(null));
		
		assertFalse(s.canAdd(c));
		
		assertFalse(s.canAdd(new Course("CSC226", "Discrete Math", "001", 4, "oerwijgr", 10, "MW", 1300, 1400)));
		
		s.resetSchedule();
		Course c1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "TH", 1330, 1445);
		Course c2 = new Course("CSC216", "Programming Concepts - Java", "002", 4, "jtking", 10, "MW", 1330, 1445);
		
		s.addCourseToSchedule(c1);
		assertFalse(s.canAdd(c2));
	}
}
