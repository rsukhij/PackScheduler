package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Tests RegistrationManager
 * 
 * @author Ethan Taylor
 */
public class RegistrationManagerTest {
	
	private RegistrationManager manager;
	
	/**
	 * Resets the courseCatalog and studentDirectory before each test method is run
	 * 
	 * @throws Exception if error
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.clearData();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager#getCourseCatalog()}
	 */
	@Test
	public void testGetCourseCatalog() {
		assertEquals(0, manager.getCourseCatalog().getCourseCatalog().length);
		
		manager.getCourseCatalog().loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, manager.getCourseCatalog().getCourseCatalog().length);
		
		manager.clearData();
		assertEquals(0, manager.getCourseCatalog().getCourseCatalog().length);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager#getStudentDirectory()}
	 */
	@Test
	public void testGetStudentDirectory() {
		assertEquals(0, manager.getStudentDirectory().getStudentDirectory().length);
		
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		assertEquals(10, manager.getStudentDirectory().getStudentDirectory().length);
		
		manager.clearData();
		assertEquals(0, manager.getStudentDirectory().getStudentDirectory().length);
	}
	
	/**
	 * Test for getFacultyDirectory() method
	 */
	@Test
    public void testGetFacultyDirectory() {
        assertEquals(0, manager.getFacultyDirectory().getFacultyDirectory().length);
        
        manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");
        assertEquals(8, manager.getFacultyDirectory().getFacultyDirectory().length);
        
        manager.clearData();
        assertEquals(0, manager.getFacultyDirectory().getFacultyDirectory().length);
    }

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager#login(String, String)}
	 */
	@Test
	public void testLogin() {
		try {
			manager.login("daustin", "pw");
			fail("If there are no students in studentDirectory, a student should not be able to login");
		} catch (IllegalArgumentException e) {
			assertEquals("User doesn't exist.", e.getMessage());
			assertNull(manager.getCurrentUser());
		}
		
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		assertTrue(manager.login("daustin", "pw"));
		assertNotNull(manager.getCurrentUser());
		User user = manager.getCurrentUser();
		assertEquals("Demetrius", user.getFirstName());
		assertEquals("Austin", user.getLastName());
		assertEquals("Curabitur.egestas.nunc@placeratorcilacus.co.uk", user.getEmail());
		
		assertFalse(manager.login("lberg", "pw"));
		
		manager.logout();
		
		try {
            manager.login("awitt", "pw");
            fail("If there are no faculty in facultyDirectory, a faculty should not be able to login");
        } catch (IllegalArgumentException e) {
            assertEquals("User doesn't exist.", e.getMessage());
            assertNull(manager.getCurrentUser());
        }
        
        manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");
        assertTrue(manager.login("awitt", "pw"));
        assertNotNull(manager.getCurrentUser());
        user = manager.getCurrentUser();
        assertEquals("Ashely", user.getFirstName());
        assertEquals("Witt", user.getLastName());
        assertEquals("mollis@Fuscealiquetmagna.net", user.getEmail());
        
        assertFalse(manager.login("lberg", "pw"));
        
        manager.logout();
		
		try {
			manager.login("maustin", "pw");	
			fail("Invalid id should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("User doesn't exist.", e.getMessage());
			assertNull(manager.getCurrentUser());
		}
		
		assertFalse(manager.login("daustin", "password"));
		
		assertTrue(manager.login("registrar", "Regi5tr@r"));
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		
		assertFalse(manager.login("registrar", "Registrar"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager#logout()}
	 */
	@Test
	public void testLogout() {
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		
		manager.logout();
		
		assertTrue(manager.login("daustin", "pw"));
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		assertNull(manager.getCurrentUser());
		
		assertTrue(manager.login("registrar", "Regi5tr@r"));
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		assertNull(manager.getCurrentUser());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager#getCurrentUser()}
	 */
	@Test
	public void testGetCurrentUser() {
		manager.logout();
		
		assertNull(manager.getCurrentUser());
		
		assertTrue(manager.login("registrar", "Regi5tr@r"));
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		assertNull(manager.getCurrentUser());
		
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		assertTrue(manager.login("daustin", "pw"));
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		assertNull(manager.getCurrentUser());
	}

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	        manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(3, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(1, scheduleFrostArray.length);
	    assertEquals("CSC226", scheduleFrostArray[0][0]);
	    assertEquals("001", scheduleFrostArray[0][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleFrostArray[0][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleFrostArray[0][3]);
	    assertEquals("9", scheduleFrostArray[0][4]);
	            
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    //Check Student Schedule
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(10, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(3, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC226", scheduleHicksArray[1][0]);
	    assertEquals("001", scheduleHicksArray[1][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
	    assertEquals("8", scheduleHicksArray[1][4]);
	    assertEquals("CSC116", scheduleHicksArray[2][0]);
	    assertEquals("003", scheduleHicksArray[2][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
	    assertEquals("9", scheduleHicksArray[2][4]);
	    
	    manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	        manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    assertFalse("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: False - student not enrolled in the course", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(0, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(0, scheduleFrostArray.length);
	    
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(10, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(3, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC226", scheduleHicksArray[1][0]);
	    assertEquals("001", scheduleHicksArray[1][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
	    assertEquals("9", scheduleHicksArray[1][4]);
	    assertEquals("CSC116", scheduleHicksArray[2][0]);
	    assertEquals("003", scheduleHicksArray[2][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
	    assertEquals("9", scheduleHicksArray[2][4]);
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(7, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(2, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC116", scheduleHicksArray[1][0]);
	    assertEquals("003", scheduleHicksArray[1][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3]);
	    assertEquals("9", scheduleHicksArray[1][4]);
	    
	    assertFalse("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: False - already dropped", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(3, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(1, scheduleHicksArray.length);
	    assertEquals("CSC116", scheduleHicksArray[0][0]);
	    assertEquals("003", scheduleHicksArray[0][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC116-003\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(0, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(0, scheduleHicksArray.length);
	    
	    manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //Test if not logged in
	    try {
	        manager.resetSchedule();
	        fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.resetSchedule();
	        fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    manager.resetSchedule();
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(0, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(0, scheduleFrostArray.length);
	    
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    manager.resetSchedule();
	    //Check Student schedule
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(0, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(0, scheduleHicksArray.length);
	    
	    manager.logout();
	}
	
	/**
	 * Test method for RegistrationManager.addFacultyToCourse(Course, Faculty)
	 */
	@Test
	public void testAddFacultyToCourse() {
		FacultyDirectory directory = manager.getFacultyDirectory();
	    directory.loadFacultyFromFile("test-files/faculty_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	        manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC216", "001"), directory.getFacultyById("awitt"));
	        fail("Non-logged in user should throw exception");
	    } catch (IllegalArgumentException e) {
	        assertNull(manager.getCurrentUser());
	        assertEquals("Illegal Action", e.getMessage());
	    }
	    
	    //test fail if registrar is not logged in
	    assertTrue(manager.login("awitt", "pw"));
	    try {
	    	manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC216", "001"), directory.getFacultyById("awitt"));
	        fail("Non-registrar user should throw exception");
	    } catch (IllegalArgumentException e) {
	    	assertEquals("Illegal Action", e.getMessage());
	    }
	    manager.logout();
	    
	    //test if registrar is logged in
	    assertTrue(manager.login("registrar", "Regi5tr@r"));
	    try {
	    	catalog.getCourseFromCatalog("CSC116", "002").setInstructorId(null);
	    	assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC116", "002"), directory.getFacultyById("awitt")));
	    } catch (IllegalArgumentException e) {
	    	fail("Adding faculty to course with registrar should not throw exception");
	    }
	    manager.resetFacultySchedule(directory.getFacultyById("awitt"));
	    
	    catalog.getCourseFromCatalog("CSC116", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC116", "001"), directory.getFacultyById("fmeadow")));
	    catalog.getCourseFromCatalog("CSC216", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC216", "001"), directory.getFacultyById("fmeadow")));
	    catalog.getCourseFromCatalog("CSC230", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC230", "001"), directory.getFacultyById("fmeadow")));
	    
	   //Check Student Schedule
	    Faculty fmeadow = directory.getFacultyById("fmeadow");
	    FacultySchedule scheduleMeadow = fmeadow.getSchedule();
	    assertEquals(3, scheduleMeadow.getNumScheduledCourses());
	    String[][] scheduleMeadowArray = scheduleMeadow.getScheduledCourses();
	    assertEquals(3, scheduleMeadowArray.length);
	    assertEquals("CSC116", scheduleMeadowArray[0][0]);
	    assertEquals("001", scheduleMeadowArray[0][1]);
	    assertEquals("Intro to Programming - Java", scheduleMeadowArray[0][2]);
	    assertEquals("MW 9:10AM-11:00AM", scheduleMeadowArray[0][3]);
	    assertEquals("10", scheduleMeadowArray[0][4]);
	    assertEquals("CSC216", scheduleMeadowArray[1][0]);
	    assertEquals("001", scheduleMeadowArray[1][1]);
	    assertEquals("Programming Concepts - Java", scheduleMeadowArray[1][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleMeadowArray[1][3]);
	    assertEquals("10", scheduleMeadowArray[1][4]);
	    assertEquals("CSC230", scheduleMeadowArray[2][0]);
	    assertEquals("001", scheduleMeadowArray[2][1]);
	    assertEquals("C and Software Tools", scheduleMeadowArray[2][2]);
	    assertEquals("MW 11:45AM-1:00PM", scheduleMeadowArray[2][3]);
	    assertEquals("10", scheduleMeadowArray[2][4]);
	    
	    manager.logout();
	}
	
	/**
	 * Test method for RegistrationManager.removeFacultyFromCourse(Course, Faculty)
	 */
	@Test
	public void testRemoveFacultyFromCourse() {
		FacultyDirectory directory = manager.getFacultyDirectory();
	    directory.loadFacultyFromFile("test-files/faculty_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    manager.login("registrar", "Regi5tr@r");
	    catalog.getCourseFromCatalog("CSC116", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC116", "001"), directory.getFacultyById("fmeadow")));
	    catalog.getCourseFromCatalog("CSC216", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC216", "001"), directory.getFacultyById("fmeadow")));
	    catalog.getCourseFromCatalog("CSC230", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC230", "001"), directory.getFacultyById("fmeadow")));
	    
	    manager.logout();
	    
	    try {
	    	manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC116", "001"), directory.getFacultyById("fmeadow"));
	    	fail("Removing faculty when not logged in should be invalid");
	    } catch (IllegalArgumentException e) {
	    	assertEquals("Illegal Action", e.getMessage());
	    }
	    
	    manager.login("awitt", "pw");
	    try {
	    	manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC116", "001"), directory.getFacultyById("fmeadow"));
	    	fail("Removing faculty when not logged in as registrar should be invalid");
	    } catch (IllegalArgumentException e) {
	    	assertEquals("Illegal Action", e.getMessage());
	    }
	    manager.logout();

	    manager.login("registrar", "Regi5tr@r");
	    assertTrue(manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC116", "001"), directory.getFacultyById("fmeadow")));
	    assertTrue(manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC216", "001"), directory.getFacultyById("fmeadow")));
	    assertTrue(manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC230", "001"), directory.getFacultyById("fmeadow")));
	    assertEquals(0, directory.getFacultyById("fmeadow").getSchedule().getNumScheduledCourses());
	}
	
	/**
	 * Test method for RegistrationManager.resetFacultySchedule(Faculty)
	 */
	@Test
	public void testResetFacultySchedule() {
		FacultyDirectory directory = manager.getFacultyDirectory();
	    directory.loadFacultyFromFile("test-files/faculty_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    manager.login("registrar", "Regi5tr@r");
	    catalog.getCourseFromCatalog("CSC116", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC116", "001"), directory.getFacultyById("fmeadow")));
	    catalog.getCourseFromCatalog("CSC216", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC216", "001"), directory.getFacultyById("fmeadow")));
	    catalog.getCourseFromCatalog("CSC230", "001").setInstructorId(null);
	    assertTrue(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC230", "001"), directory.getFacultyById("fmeadow")));
	    
	    manager.logout();
	    
	    try {
	    	manager.resetFacultySchedule(directory.getFacultyById("fmeadow"));
	    	fail("Reseting faculty schedule when not logged in should be invalid");
	    } catch (IllegalArgumentException e) {
	    	assertEquals("Illegal Action", e.getMessage());
	    	assertEquals(3, directory.getFacultyById("fmeadow").getSchedule().getNumScheduledCourses());
	    }
	    
	    manager.login("awitt", "pw");
	    try {
	    	manager.resetFacultySchedule(directory.getFacultyById("fmeadow"));
	    	fail("Reseting faculty schedule when not logged in as registrar should be invalid");
	    } catch (IllegalArgumentException e) {
	    	assertEquals("Illegal Action", e.getMessage());
	    	assertEquals(3, directory.getFacultyById("fmeadow").getSchedule().getNumScheduledCourses());
	    }
	    manager.logout();
	    
	    manager.login("registrar", "Regi5tr@r");
	    manager.resetFacultySchedule(directory.getFacultyById("fmeadow"));
	    assertEquals(0, directory.getFacultyById("fmeadow").getSchedule().getNumScheduledCourses());
	}
}