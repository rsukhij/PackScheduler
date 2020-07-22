package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Reads Course records from text files.
 * 
 * @author Sarah Heckman
 * @author Ethan Taylor
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Course> courses = new SortedList<Course>();
		while (fileReader.hasNextLine()) {
			try {
				Course course = readCourse(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < courses.size(); i++) {
					Course c = courses.get(i);
					if (course.getName().equals(c.getName()) && course.getSection().equals(c.getSection())) {
						// it's a duplicate
						duplicate = true;
					}
				}
				if (!duplicate) {
					courses.add(course);
				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return courses;
	}

	/**
	 * Interprets a Course from a line of text
	 * 
	 * @param nextLine the line to interpret
	 * @return the Course interpreted
	 * @throws IllegalArgumentException if unable to
	 */
	private static Course readCourse(String nextLine) {
		Scanner lineReader = new Scanner(nextLine);
		lineReader.useDelimiter(",");

		Course course = null;
		String name, title, section, instructorId = null, meetingDays;
		int credits, enrollmentCap, startTime, endTime;

		try {
			name = lineReader.next();
			title = lineReader.next();
			section = lineReader.next();
			credits = lineReader.nextInt();
			instructorId = lineReader.next();
			enrollmentCap = lineReader.nextInt();
			meetingDays = lineReader.next();
			if (meetingDays.equals("A")) {
				if (lineReader.hasNext()) {
					lineReader.close();
					throw new IllegalArgumentException();
				}
				course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
			} else {
				startTime = lineReader.nextInt();
				endTime = lineReader.nextInt();
				course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
			}
		} catch (Exception e) {
			lineReader.close();
			throw new IllegalArgumentException();
		}		
		lineReader.close();
		
		Faculty faculty = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
		if (faculty != null) {
			faculty.getSchedule().addCourseToSchedule(course);
		}

		return course;
	}

	/**
	 * Writes the given list of Courses to a file
	 * 
	 * @param fileName name of the file to write to
	 * @param courses  Courses to write to the file
	 * @throws IOException if unable to write to the file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}
		fileWriter.close();
	}
}