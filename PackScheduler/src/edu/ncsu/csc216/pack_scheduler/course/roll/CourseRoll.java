package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * CourseRoll encapsulates a LinkedAbstractList to hold Students enrolled in a Course.
 * When enrolling a Student, there must be extra room in the Course and the Student
 * cannot have already enrolled in the Course. A Student can also be dropped from a Course.
 * 
 * @author Ethan Taylor
 */
public class CourseRoll {
	
	/** The list of Students encapsulated in CourseRoll */
	private LinkedAbstractList<Student> roll;
	/** The maximum allowed students that can be enrolled for this Course */
	private int enrollmentCap;
	/** The minimum enrollmentCap allowed */
	private static final int MIN_ENROLLMENT = 10;
	/** The maximum enrollmentCap allowed */
	private static final int MAX_ENROLLMENT = 250;
	
	/**
	 * Constructs a CourseRoll by instantiating a LinkedAbstractList and setting the
	 * specified enrollmentCap
	 * 
	 * @param enrollmentCap the enrollment cap to set
	 */
	public CourseRoll(int enrollmentCap) {
		roll = new LinkedAbstractList<Student>(MAX_ENROLLMENT);
		setEnrollmentCap(enrollmentCap);
	}
	
	/**
	 * Returns the enrollmentCap
	 * 
	 * @return the enrollmentCap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}
	
	/**
	 * Sets the specified enrollmentCap
	 * 
	 * @param enrollmentCap the enrollmentCap to set
	 * @throws IllegalArgumentException if the specified enrollmentCap is less than 10
	 * 		or greater than 250
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Enrollment cap must be between 10 and 250");
		} else if (enrollmentCap < roll.size()) {
			throw new IllegalArgumentException("Enrollment cap must be at least the number of students enrolled");
		}
		roll.setCapacity(enrollmentCap);
		this.enrollmentCap = enrollmentCap;
	}
	
	/**
	 * Adds a Student to the CourseRoll
	 * 
	 * @param s the Student to add
	 * @throws IllegalArgumentException if the Student is null, a duplicate, the Course
	 * 		is already full, or if there was an error when adding the Student to the list
	 */
	public void enroll(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Null student can't be enrolled");
		} else if (!canEnroll(s)) {
			throw new IllegalArgumentException("Student can't be enrolled");
		}
		try {
			roll.add(0, s);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to add student to enrollment list");
		}
	}
	
	/**
	 * Removes Student from the CourseRoll
	 * 
	 * @param s the Student to remove
	 * @throws IllegalArgumentException if the Student is null
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Null student can't be dropped");
		}
		for (int i = 0; i < roll.size(); i++) {
			if (s.equals(roll.get(i))) {
				roll.remove(i);
				return;
			}
		}
	}
	
	/**
	 * Returns the number of available spaces left in the Course
	 * 
	 * @return the spaces available
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	/**
	 * Checks if a Student can in enroll in the Course by checking
	 * if there are open seats and the Student isn't already enrolled in
	 * the Course
	 * 
	 * @param s the Student to check if they can enroll
	 * @return true if the Student can enroll
	 */
	public boolean canEnroll(Student s) {
		if (getOpenSeats() == 0) {
			return false;
		}
		for (int i = 0; i < roll.size(); i++) {
			if (s.equals(roll.get(i))) {
				return false;
			}
 		}
		return true;
	}
}
