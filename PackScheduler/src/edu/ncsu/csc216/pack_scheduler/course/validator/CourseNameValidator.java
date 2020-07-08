package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * This class 
 * 
 * @author Xuhui Lin - xlin8
 * @author Ethan Taylor
 *
 */
public class CourseNameValidator {

	/** valid end state */
	private boolean validEndState;
	
	/** letter count */
	private int letterCount;
	
	/** digit count */
	private int digitCount;
	
	/** letter State */
	private State letterState;
	
	/** suffix State */
	private State suffixState;
	
	/** initial State */
	private State initialState;
	
	/** number State */
	private State numberState;
	
	/** current State */
	private State currentState;
	
	/**
	 * Initial CourseNameValidator
	 */
	public CourseNameValidator() {
		// Empty constructor
	}
	
	/**
	 * Return True and false statement
	 * @param courseName the String input 
	 * @return the True false statement
	 * @throws InvalidTransitionException when Invalid transition happen. 
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		letterState = new LetterState();
		suffixState = new SuffixState();
		initialState = new InitialState();
		numberState = new NumberState();
		currentState = initialState;
		int idx = 0;
		char a;
		
		while (idx < courseName.length()) {
			a = courseName.charAt(idx);
			try {
				if (Character.isLetter(a)) {
					currentState.onLetter();
				} else if (Character.isDigit(a)) {
					currentState.onDigit();
				} else {
					currentState.onOther();
				}
			} catch (InvalidTransitionException e) {
				throw new InvalidTransitionException(e.getMessage());
			}
			idx++;
		}

		validEndState = digitCount == 3 && currentState == numberState || currentState == suffixState;
		return validEndState;
	}
	
	/**
	 * Abstract class of State
	 * 
	 * @author Xuhui Lin
	 *
	 */
	public abstract class State {
		
		/**
		 * initial state. 
		 */
		public State() {
			//Empty constructor
		}
		
		/**
		 * abstract class 
		 * @throws InvalidTransitionException when exception happen
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/**
		 * abstract class 
		 * @throws InvalidTransitionException when exception happen
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * This class throws exceptions
		 * when input non digit or non letter
		 * 
		 * @throws InvalidTransitionException when input non digit or non letter
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	/**
	 * Letter State
	 * 
	 * @author Xuhui Lin
	 *
	 */
	public class LetterState extends State {
		
		/** the max amount of letters */
		public static final int MAX_PERFIX_LETTERS = 4;
		
		/**
		 * Initialize the Letter State
		 */
		private LetterState() {
			
		}
		
		/**
		 * Override the onLetter method
		 * @throws InvalidTransitionException when input more than 4 letters. 
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < MAX_PERFIX_LETTERS) {
				letterCount++;
			} else {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}
		
		/**
		 * Override the onDigit method
		 * change to numberState
		 */
		@Override
		public void onDigit() {
			digitCount++;
			currentState = numberState;
		}
	}
	
	/**
	 * Suffix State
	 * 
	 * @author Xuhui Lin
	 *
	 */
	public class SuffixState extends State {
		
		/**
		 * Initialize the Suffix State
		 */
		private SuffixState() {
			
		}
		
		/**
		 * Override the onLetter method
		 * @throws InvalidTransitionException when input 2 letter for suffix
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}
		
		/**
		 * Override the onDigit method
		 * @throws InvalidTransitionException when input digit after suffix
		 */
		@Override
		public void onDigit() throws InvalidTransitionException{
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
	
	/**
	 * Initial State
	 * 
	 * @author Xuhui Lin
	 *
	 */
	public class InitialState extends State {
		
		/**
		 * Set up value
		 */
		private InitialState() {
			letterCount = 0;
			digitCount = 0;
		}
		
		/**
		 * Override the onLetter method
		 * change state to letterState
		 */
		@Override
		public void onLetter() {
			letterCount++;
			currentState = letterState;
		}
		
		/**
		 * Override the onDigit method
		 * @throws InvalidTransitionException when input start with digit.
		 */
		@Override
		public void onDigit() throws InvalidTransitionException{
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}
	
	/**
	 * Number State.
	 * 
	 * @author Xuhui Lin
	 *
	 */
	public class NumberState extends State {
		
		/** course digit length */
		public static final int COURSE_NUMBER_LENGTH = 3;
		
		/**
		 * Initialize the NumberState
		 */
		private NumberState() {
			
		}
		
		/**
		 * Override the onLetter method
		 * @throws InvalidTransitionException when input does not have 3 continue digit
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				currentState = suffixState;
			} else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}
		
		/**
		 * Override the onDigit method
		 * @throws InvalidTransitionException when input digit after suffix
		 */
		@Override
		public void onDigit() throws InvalidTransitionException{
			if (digitCount < COURSE_NUMBER_LENGTH) {
				digitCount++;
			} else {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
	}
}