package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Conflict is a simple Interface to be implemented to check for conflict
 * between the Activity and another Activity.
 * 
 * @author Ethan Taylor
 */
public interface Conflict {

	/**
	 * Checks if the Activity conflicts with another Activity
	 * 
	 * @param possibleConflictingActivity the Activity to check conflict with
	 * @throws ConflictException if the Activity conflicts with the specified
	 *                           Activity
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
