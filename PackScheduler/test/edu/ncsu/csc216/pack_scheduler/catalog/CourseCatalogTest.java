package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import org.junit.Test;


import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the CourseCatalog class
 * 
 * @author Ethan Taylor
 * @author Rohan Sukhija
 */
public class CourseCatalogTest {

	/**
	 * Test method for CourseCatalog()
	 */
	@Test
	public void testCourseCatalog() {
		CourseCatalog a = new CourseCatalog();
		assertEquals(0, a.catalog.size());
	}

	/**
	 * Test method for newCourseCatalog()
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog a = new CourseCatalog();
		assertEquals(0, a.catalog.size());
		a.addCourseToCatalog("CSC216", "Computer Science", "005", 4, "oerwijgr", 10, "MW", 1230, 1330);
		a.newCourseCatalog();
		assertEquals(0, a.catalog.size());
	}

	/**
	 * Test method for loadCoursesFromFile()
	 */
	@Test
	public void testLoadCoursesFromFile() {
		CourseCatalog a = new CourseCatalog();
		a.loadCoursesFromFile("test-files/expected_course_catalog.txt");
		assertEquals(5, a.catalog.size());
		a.newCourseCatalog();
		try {
			a.loadCoursesFromFile("test-files/expectd_course_catalog.txt");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, a.catalog.size());
		}
	}

	/**
	 * Test method for addCourseToCatalog()
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog a = new CourseCatalog();
		a.addCourseToCatalog("CSC216", "Computer Science", "005", 4, "oerwijgr", 10, "MW", 1230, 1330);
		assertEquals(1, a.catalog.size());
	}

	/**
	 * Test method for removeCourseFromCatalog()
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog a = new CourseCatalog();
		a.loadCoursesFromFile("test-files/expected_course_catalog.txt");
		a.removeCourseFromCatalog("CSC116", "002");
		assertEquals(4, a.catalog.size());
	}

	/**
	 * Test method for getCourseFromCatalog()
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog a = new CourseCatalog();
		a.loadCoursesFromFile("test-files/expected_course_catalog.txt");
		Course b = new Course("CSC216", "Programming Concepts - Java", "001", 4, null, 10, "TH", 1330, 1445);
		assertTrue(a.getCourseFromCatalog("CSC216", "001").equals(b));
	}

	/**
	 * Test method for getCourseCatalog()
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog a = new CourseCatalog();
		a.loadCoursesFromFile("test-files/expected_course_catalog.txt");
		assertEquals(5, a.getCourseCatalog().length);
		assertEquals("CSC116", a.getCourseCatalog()[1][0]);
		assertEquals("CSC216", a.getCourseCatalog()[3][0]);
	}

	/**
	 * Test method for saveCourseCatalog()
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog c = new CourseCatalog();
		c.loadCoursesFromFile("test-files/expected_course_catalog.txt");
		c.saveCourseCatalog("test-files/actual_course_catalog.txt");
		
		CourseCatalog e = new CourseCatalog();
		e.loadCoursesFromFile("test-files/expected_course_catalog.txt");
		CourseCatalog a = new CourseCatalog();
		a.loadCoursesFromFile("test-files/actual_course_catalog.txt");
		
		assertArrayEquals(e.getCourseCatalog(), a.getCourseCatalog());
	}

}
