package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Class representing a Student: basic info and credentials, whilst
 * providing functionality such as creating a Student, setting info and
 * credentials, and comparing two Students.
 * 
 * Uses the abstract superclass User for the majority of core functionality
 * 
 * @author Rohan Sukhija
 * @author Ethan Taylor
 * @author Dilli Wagley
 */
public class Student extends User implements Comparable<Student> {

	/** The maximum credits a student can take */
	private int maxCredits;
	/** The maximum credits any student can take */
	public final static int MAX_CREDITS = 18;
	/** The schdule */
	private Schedule schedule;

	/**
	 * Constructs the student object
	 * initial schedule
	 * 
	 * @param firstName  the first name of student
	 * @param lastName   the last name of student
	 * @param id         the id of the student
	 * @param email      the email
	 * @param hashPW     the hashed password
	 * @param maxCredits the max credits student can take
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * Constructs the student object using default maxCredits value
	 * 
	 * @param firstName the first name of student
	 * @param lastName  the last name of student
	 * @param id        the id of the student
	 * @param email     the email
	 * @param hashPW    the hashed password
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}

	/**
	 * Gets the max credits a student is allowed to take
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the max credits a student is allowed to take
	 * 
	 * @param maxCredits the maxCredits to set
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Generates a hashCode that represents the fields of Student and User
	 * 
	 * @return the hashCode for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Compares a given course object for equality to all fields in Student and User
	 * 
	 * @param obj the object to compare
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Student fields.
	 * 
	 * @return String representation of Student
	 */
	@Override
	public String toString() {
		return firstName + "," + lastName + "," + id + "," + email + "," + hashedPassword + "," + maxCredits;

	}

	/**
	 * Compares two student object based on the alphabetical order of their fields.
	 * The fields will be compared in the order of lastName, firstName, and Id
	 * 
	 * @param s the Student object being compared to the current Student object
	 * @return a positive integer if this Student comes later in alphabetical order
	 *         than the Student s object. A negative integer if this Student comes
	 *         before in alphabetical order than the Student s object
	 * @throws NullPointerException if the inputted object is null
	 * @throws ClassCastException   if the inputted object is not of type Student
	 */
	public int compareTo(Student s) {
		if (s == null) {
			throw new NullPointerException();
		}
		if (!(s instanceof Student)) {
			throw new ClassCastException();
		}
		if (this.lastName.compareTo(s.lastName) <= -1) {
			return this.lastName.compareTo(s.lastName);
		} else if (this.lastName.compareTo(s.lastName) >= 1) {
			return this.lastName.compareTo(s.lastName);
		} else if (this.lastName.compareTo(s.lastName) == 0) {
			if (this.firstName.compareTo(s.firstName) >= 1) {
				return this.firstName.compareTo(s.firstName);
			} else if (this.firstName.compareTo(s.firstName) <= -1) {
				return this.firstName.compareTo(s.firstName);
			} else if (this.firstName.compareTo(s.firstName) == 0) {
				if (this.id.compareTo(s.id) >= 1) {
					return this.id.compareTo(s.id);
				} else if (this.id.compareTo(s.id) <= -1) {
					return this.id.compareTo(s.id);
				}
			}
		}
		return 0;
	}

	/**
	 *  Return the schedule
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	/**
	 * Returns true if the Course can be added to the Schedule, false if Student does
	 * not have enough available credits
	 * 
	 * @param c the Course to check if can add
	 * @return true if the Course can be added
	 */
	public boolean canAdd(Course c) {
		return maxCredits - schedule.getScheduleCredits() >= c.getCredits() && schedule.canAdd(c);
	}
}
