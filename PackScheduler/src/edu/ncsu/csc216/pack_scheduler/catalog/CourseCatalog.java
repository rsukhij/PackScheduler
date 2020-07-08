package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * CourseCatalog stores a list of Courses for adding to Student schedules. It
 * also allows for adding and removing Courses to the catalog.
 * 
 * @author Ethan Taylor
 */
public class CourseCatalog {
	/** The catalog of Courses to add to the schedule */
	SortedList<Course> catalog;

	/**
	 * Constructs a CourseCatalog and creates an empty SortedList of Courses
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Empties the catalog by overwriting it with a new SortedList of Courses
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Loads Courses from the specified file into the catalog
	 * 
	 * @param fileName the file to load Courses from
	 * @throws IllegalArgumentException if the file could not be found
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot find file");
		}
	}

	/**
	 * Creates and adds a Course to the catalog
	 * 
	 * @param name         the Course's name
	 * @param title        the Course's title
	 * @param section      the Course's section
	 * @param credits      the Course's credits
	 * @param instructorId the Course's instructorId
	 * @param enrollmentCap		the enrollmentCap for Course
	 * @param meetingDays  the Course's meetingDays
	 * @param startTime    the Course's startTime
	 * @param endTime      the Course's endTime
	 * @return true if the Course was added to the catalog successfully
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		Course course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		for (int i = 0; i < catalog.size(); i++) {
			if (course.compareTo(catalog.get(i)) == 0) {
				return false;
			}
		}
		return catalog.add(course);
	}

	/**
	 * Removes a Course specified by its name and section from the catalog
	 * 
	 * @param name    the Course's name
	 * @param section the Course's section
	 * @return true if the Course was removed from the catalog successfully
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		int i = 0;
		for (; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the Course specified by its name and section from the catalog if it
	 * exists
	 * 
	 * @param name    the Course's name
	 * @param section the Course's section
	 * @return the specified Course, or null if the Course was not in the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				return course;
			}
		}
		return null;
	}

	/**
	 * Returns the catalog as an array of the Courses' name, section, title, and
	 * meetingString
	 * 
	 * @return a two-dimensional array representation of the catalog
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][4];
		for (int i = 0; i < catalog.size(); i++) {
			catalogArray[i] = catalog.get(i).getShortDisplayArray();
		}
		return catalogArray;
	}

	/**
	 * Saves the catalog of Courses to the specified file
	 * 
	 * @param fileName the file to save the catalog to
	 * @throws IllegalArgumentException if the file could not be saved
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}
}
