package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

import java.util.Arrays;
import java.util.stream.Stream;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * This class control the Schedule
 * @author Xuhui Lin
 * @author Ethan Taylor
 *
 */
public class Schedule {

	/** schedule */
	private ArrayList<Course> schedule;
	/** title */
	private String title;
	
	/**
	 * the Schedule constructor
	 * title has default value "My Schedule"
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		setTitle("My Schedule");
	}
	
	/**
	 * add course to the end of the list
	 * @param course the course input
	 * @return true if the course is added, false if the course is null
	 * @throws IllegalArgumentException if the Course is a duplicate or there is a conflict
	 */
	public boolean addCourseToSchedule(Course course) {
		try {
			for (int i = 0; i < schedule.size(); i++) {
				schedule.get(i).checkConflict(course);
				if (schedule.get(i).isDuplicate(course)) {
					throw new IllegalArgumentException();
				}
			}
			schedule.add(course);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("You are already enrolled in " + course.getName());
		} catch (ConflictException e) {
			throw new IllegalArgumentException("The course cannot be added due to a conflict.");
		}
		return true;
	}
	
	/**
	 * remove the course from the schedule
	 * @param course the course input
	 * @return true if the course is remove, false if the course does not exist in the schedule
	 */
	public boolean removeCourseFromSchedule(Course course) {
		if (course == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			if (course.isDuplicate(schedule.get(i))) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * reset the schedule
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
		setTitle("My Schedule");
	}
	
	/**
	 *  Return the 2D String array
	 *  which saves courses Name, Section, title, and Meeting String
	 * @return String[][] the 2D String array
	 */
	public String[][] getScheduledCourses() {
		String[][] courses = null;
		try {
			courses = new String[schedule.size()][4];
			for (int i = 0; i < schedule.size(); i++) {
				courses[i] = schedule.get(i).getShortDisplayArray();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage() + ": " + schedule.toString());
		}
		return courses;
		
	}
	
	/**
	 * set the Title to input
	 * @param title the title input
	 * @throws IllegalArgumentException when title is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null");
		}
		this.title = title;
	}
	
	/**
	 *  Return the title
	 * @return the title 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the total credits in the Schedule
	 * 
	 * @return the total credits
	 */
	public int getScheduleCredits() {
		int credits = 0;
		for (Course course : schedule) {
			credits += course.getCredits();
		}
		return credits;
	}
	
	/**
	 * Returns true if the Course can be added to Schedule, false if the Course
	 * is null or is already in Course
	 * 
	 * @param c the Course to check if can add
	 * @return true if the Course can be added
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		try {
			for (Course course : schedule) {
				c.checkConflict(course);
				if (c.isDuplicate(course)) {
					return false;
				}
			}
		} catch (ConflictException e) {
			return false;
		}
		return true;
	}
}
