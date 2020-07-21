package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Faculty is a subclass of User of which can choose to teach a Course.
 * 
 * @author Ethan Taylor
 */
public class Faculty extends User {
	/** The maximum Courses Faculty can teach */
	private int maxCourses;
	/** The minimum Courses a Faculty can teach */
	private static final int MIN_COURSES = 1;
	/** The maximum Courses a Faculty can teach */
	private static final int MAX_COURSES = 3;
	/** Schedule for Faculty */
	private FacultySchedule fSchedule;
	
	/**
	 * Constructs a Faculty object by calling the super's constructor and then setting maxCourses
	 * 
	 * @param firstName the Faculty's first name
	 * @param lastName the Faculty's last name
	 * @param id the Faculty's id
	 * @param email the Faculty's email
	 * @param hashPW the Faculty's hashed password
	 * @param maxCourses the maximum Courses allowed for Faculty to teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCourses(maxCourses);
		fSchedule = new FacultySchedule(id);
	}
	
	/**
	 * Sets the maximum Courses Faculty can teach
	 * 
	 * @param maxCourses the maximum number of Courses
	 * @throws IllegalArgumentException if maxCourses is less than 1 or greater than 3
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}
	
	/**
	 * Returns the maximum Courses Faculty can teach
	 * 
	 * @return the maxCourses for Faculty
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Generates a hashCode that represents the fields of Faculty and User
	 * 
	 * @return the hashCode for Faculty
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Compares a given Faculty object for equality of all fields in User and Faculty
	 * 
	 * @param obj the object to compare
	 * @return true if the objects' fields are all the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}

	/**
	 * Returns a comma-separated-value String of all Faculty and User fields
	 * 
	 * @return the String representation of Faculty
	 */
	@Override
	public String toString() {
		return firstName + "," + lastName + "," + id + "," + email + "," + hashedPassword + "," + maxCourses;
	}
	
	/**
	 * Returns the Faculty Schedule
	 * @return the Faculty Schedule
	 */
	public FacultySchedule getSchedule() {
	    return fSchedule;
	}
	
	/**
	 * Checks whether number of scheduled courses is greater than faculty's max courses
	 * @return true if faculty's max courses is greater than number of scheduled courses
	 */
	public boolean isOverloaded() {
	    if(fSchedule.getNumScheduledCourses() > maxCourses) {
	        return true;
	    }
	    return false;
	}
}
