package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Activity contains information useful for appearing on a schedule, such as
 * title and meeting days and times. Activity is the abstract super class to
 * Event and Course, providing the abstract {@link #getShortDisplayArray()},
 * {@link #getLongDisplayArray()}, and {@link #isDuplicate(Activity)} methods
 * 
 * @author Ethan Taylor
 */
public abstract class Activity implements Conflict {

	/** Upper bound for a time int */
	private static final int UPPER_TIME = 2400;
	/** Upper bound for the minute time int */
	private static final int UPPER_HOUR = 60;
	/** Activity's title. */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;

	/**
	 * Creates an Activity with the given title, meetingDays, startTime, and endTime
	 * 
	 * @param title       the title of the Activity
	 * @param meetingDays the meeting days for the Activity
	 * @param startTime   the start time of the Activity
	 * @param endTime     the end time of the Activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
	}

	/**
	 * Returns the Activity's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Coures's title
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException("Invalid course title");
		}
		this.title = title;
	}

	/**
	 * Returns the Activity's meetingDays
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the Activity's meetingDays
	 * 
	 * @param meetingDays the meetingDays to set
	 */
	public void setMeetingDays(String meetingDays) {
		this.meetingDays = meetingDays;
	}

	/**
	 * Returns the Activity's startTime
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Sets the Activity's startTime and endTime
	 * 
	 * @param startTime the startTime to set
	 * @param endTime   the endTime to set
	 */
	public void setActivityTime(int startTime, int endTime) {
		if (startTime < 0 || startTime >= UPPER_TIME || startTime % 100 >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid start time");
		} else if (endTime < 0 || endTime >= UPPER_TIME || endTime % 100 >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid end time");
		} else if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid course times");
		} else if (meetingDays.equals("A") && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException("Invalid course times");
		}
		this.endTime = endTime;
		this.startTime = startTime;
	}

	/**
	 * Returns the Activity's endTime
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Returns the Activity's meeting days and times as a String
	 * 
	 * @return the meeting days and times
	 */
	public String getMeetingString() {
		return meetingDays.equals("A") ? "Arranged"
				: String.format("%s %d:%02d%s-%d:%02d%s", meetingDays,
						(startTime > 1259 ? startTime - 1200 : startTime) / 100, startTime % 100,
						startTime < 1200 ? "AM" : "PM", (endTime > 1259 ? endTime - 1200 : endTime) / 100,
						endTime % 100, endTime < 1200 ? "AM" : "PM");
	}

	/**
	 * Returns a String array of Activity using Activity name, section, title, and
	 * meeting days and time
	 * 
	 * @return the short String array
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Returns a String array of Activity using all fields
	 * 
	 * @return the long String Array
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Checks if Activity is the same as the specified Acivity
	 * 
	 * @param activity the Activity to compare
	 * @return true if the Activity is a duplicate
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Checks if the Activity conflicts with another Activity
	 * 
	 * @param possibleConflictingActivity the Activity to check conflict with
	 * @throws ConflictException if the Activity conflicts with the specified
	 *                           Activity
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		if (!(meetingDays.equals("A") || possibleConflictingActivity.getMeetingDays().equals("A"))
				&& startTime <= possibleConflictingActivity.getEndTime()
				&& endTime >= possibleConflictingActivity.getStartTime()) {
			for (char day : meetingDays.toCharArray()) {
				if (possibleConflictingActivity.getMeetingDays().contains(Character.toString(day))) {
					throw new ConflictException();
				}
			}
		}
	}

	/**
	 * Generates a hashCode for Actvity using its fields
	 * 
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equalitiy on its fields
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the smae on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}