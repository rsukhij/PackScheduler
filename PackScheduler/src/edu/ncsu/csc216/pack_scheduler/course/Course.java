package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.Activity;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Course contains information useful for appearing on a schedule, such as name,
 * title, section, credits, instructor ID, and meeting days and times. Course
 * extends Activity.
 * 
 * @author Ethan Taylor
 */
public class Course extends Activity implements Comparable<Course> {

	/** Required length of the section */
	private static final int SECTION_LENGTH = 3;
	/** Maximum credits for Course */
	private static final int MAX_CREDITS = 5;
	/** Minimum credits for Course */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Students enrolled in Course */
	private CourseRoll roll;

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * meetingDays, startTime, and endTime
	 * 
	 * @param name         the name of Course
	 * @param title        the title of Course
	 * @param section      the section of Course
	 * @param credits      the credits of Course
	 * @param instructorId the instructorId of Course
	 * @param enrollmentCap		the enrollmentCap for Course
	 * @param meetingDays  the meetingDays of Course
	 * @param startTime    the startTime of Course
	 * @param endTime      the endTime of Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, 
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(this, enrollmentCap);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays
	 * 
	 * @param name         the name of Course
	 * @param title        the title of Course
	 * @param section      the section of Course
	 * @param credits      the credits of Course
	 * @param instructorId the instructorId of Course
	 * @param enrollmentCap		the enrollmentCap for Course
	 * @param meetingDays  the meetingDays of Course
	 */
	public Course(String name, String title, String section, int credits, 
			String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name
	 * 
	 * @param name the name to set
	 */
	private void setName(String name) {
		CourseNameValidator validator = new CourseNameValidator();	
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name");
		}
		try {	
			if (!validator.isValid(name)) {	
				throw new IllegalArgumentException("Invalid course name");	
			}	
		} catch (InvalidTransitionException e) {	
			throw new IllegalArgumentException("Invalid course name");
		}
		this.name = name;
	}

	/**
	 * Returns the Course's section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section
	 * 
	 * @param section the section to set
	 */
	public void setSection(String section) {
		if (section == null) {
			throw new IllegalArgumentException("Invalid section number");
		} else if (section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section number");
		}
		this.section = section;
	}

	/**
	 * Returns the Course's credits
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits
	 * 
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credit hours");
		}
		this.credits = credits;
	}

	/**
	 * Returns the Course's instructorId
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructorId
	 * 
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || instructorId.equals("")) {
			throw new IllegalArgumentException("Invalid instructor unity id");
		}
		this.instructorId = instructorId;
	}

	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.equals("")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		for (int i = 0; i < meetingDays.length(); i++) {
			switch (meetingDays.charAt(i)) {
			case 'M':
			case 'T':
			case 'W':
			case 'H':
			case 'F':
				// Valid char, check the next char
				break;
			case 'A':
				if (meetingDays.length() > 1) {
					throw new IllegalArgumentException("Invalid meeting days");
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid meeting days");
			}
		}
		super.setMeetingDays(meetingDays);
	}

	/**
	 * Generates a hashCode for Course using all fields
	 * 
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares a given object to its object for equalitiy on all fields
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the smae on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (getMeetingDays().equals("A")) {
			return String.format("%s,%s,%s,%d,%s,%d,%s", name, getTitle(), section, credits, instructorId,
					roll.getEnrollmentCap(), getMeetingDays());
		}
		return String.format("%s,%s,%s,%d,%s,%d,%s,%d,%d", name, getTitle(), section, credits, instructorId,
				roll.getEnrollmentCap(), getMeetingDays(), getStartTime(), getEndTime());
	}

	/**
	 * Returns a String array of Course using Course name, section, title, and
	 * meeting days and time
	 * 
	 * @return the short String array
	 */
	public String[] getShortDisplayArray() {
		return new String[] { name, section, getTitle(), getMeetingString(), Integer.toString(roll.getOpenSeats()) };
	}

	/**
	 * Returns a String array of Course using all fields
	 * 
	 * @return the long String Array
	 */
	public String[] getLongDisplayArray() {
		return new String[] { name, section, getTitle(), Integer.toString(credits), instructorId, getMeetingString(),
				"" };
	}

	/**
	 * Checks if Course is a duplicate of the specified Activity
	 * 
	 * @param activity the Activity to check for duplicity
	 * @return true if Course is a duplicate of the Activity
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		return activity instanceof Course
				&& this.getName().equals(((Course) activity).getName());
	}

	/**
	 * Compares two Course objects based on the alphabetical order of their fields.
	 * The fields will be compared in the order of name and then section of the
	 * Courses.
	 * 
	 * @param o the Course object being compared to the current Course object
	 * @return a positive integer if this Course comes later in alphabetical order
	 *         than the Course o object. A negative integer if this Course comes
	 *         before in alphabetical order than the Course o object
	 * @throws NullPointerException if the inputted object is null
	 */
	@Override
	public int compareTo(Course o) {
		if (o == null) {
			throw new NullPointerException();
		}
		if (this.name.compareTo(o.getName()) != 0) {
			return this.name.compareTo(o.getName());
		} else {
			if (this.getSection().compareTo(o.getSection()) != 0) {
				return this.getSection().compareTo(o.getSection());
			}
		}
		return 0;
	}
	
	/**
	 * Returns the CourseRoll
	 * 
	 * @return the CourseRoll
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
}
