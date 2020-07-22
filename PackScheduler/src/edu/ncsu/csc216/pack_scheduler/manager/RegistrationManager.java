package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * RegistrationManager handles the login of users, both students and a registrar, by keeping
 * track of the current user. Also stores a course catalog and student directory
 * which can be loaded by a user that doesn't have to be logged in.
 * 
 * @author Ethan Taylor
 */
public class RegistrationManager {
	
	/** The current instance of RegistrationManager */
	private static RegistrationManager instance;
	/** The CourseCatalog loaded in or created */
	private CourseCatalog courseCatalog;
	/** The StudentDirectory loaded in or created */
	private StudentDirectory studentDirectory;
	/** The FacultyDirecctory loaded in or created */
	private FacultyDirectory facultyDirectory;
	/** The Registrar user used to check if the current User is logged in as registrar */
	private User registrar;
	/** The current User logged in */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** File to check Registrar login info */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Constructs a RegistrationManager object with an empty CourseCatalog and StudentDirectory
	 */
	private RegistrationManager() {
		createRegistrar();
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
		facultyDirectory = new FacultyDirectory();
	}
	
	/**
	 * Loads in the registrar.properties file to get the registrar account
	 * details used for loggin in as a registrar
	 * 
	 * @throws IllegalArgumentException if the registrar user could not be created from the file
	 */
	private void createRegistrar() {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			
			String hashPW = hashPW(prop.getProperty("pw"));
			
			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}
	
	/**
	 * Hashes a password
	 * 
	 * @param pw the password to hash
	 * @throws IllegalArgumentException if the password could not be hashed
	 * @return the hashed password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Returns the current instance of RegistrationManager, or creates and returns
	 * a new one if there is no instance already
	 * 
	 * @return the current instance of RegisrationManager
	 */
	public static RegistrationManager getInstance() {
		  if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	
	/**
	 * Returns the CourseCatalog object that has been loaded in or created
	 * 
	 * @return the CourseCatalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	
	/**
	 * Returns the StudentDirectory object that has been loaded in or created
	 * 
	 * @return the StudentDirectory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	/**
     * Returns the FacultyDirectory object that has been loaded in or created
     * 
     * @return the FacultyDirectory
     */
    public FacultyDirectory getFacultyDirectory() {
        return facultyDirectory;
    }

	/**
	 * Logs in a user based on the specified credentials (id and password).
	 * If the credentials match the registrar's creditials loaded in from
	 * registrar.properties, the user will be logged in as registrar
	 * 
	 * @param id the id of the account to login to
	 * @param password the password of the account to login to
	 * @throws IllegalArgumentException if unable to hash the password or...
	 * @return true if the user is logged in
	 */
	public boolean login(String id, String password) {
		if (currentUser != null) {
			return false;
		}
				
		if (registrar.getId().equals(id)) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		} else if(getStudentDirectory().getStudentById(id) != null) {
		    Student s = studentDirectory.getStudentById(id);
		    if (s == null) {
		        throw new IllegalArgumentException("User doesn't exist.");
		    }
		    try {
		        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
		        digest.update(password.getBytes());
		        String localHashPW = new String(digest.digest());
		        if (s.getPassword().equals(localHashPW)) {
		            currentUser = s;
		            return true;
		        }
		    } catch (NoSuchAlgorithmException e) {
		        throw new IllegalArgumentException();
		    }
		} else {
		
		Faculty f = facultyDirectory.getFacultyById(id);
            if (f == null) {
                throw new IllegalArgumentException("User doesn't exist.");
            }
            try {
                MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
                digest.update(password.getBytes());
                String localHashPW = new String(digest.digest());
                if (f.getPassword().equals(localHashPW)) {
                    currentUser = f;
                    return true;
                }
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalArgumentException();
            }
		}
		return false;
	}

	/**
	 * Logs the currentUser out by setting the currentUser variable to null
	 */
	public void logout() {
		currentUser = null; 
	}
	
	/**
	 * Returns the currentUser
	 * 
	 * @return the current User
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Constructs a new courseCatalog and studentDirectory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}
	
	/**
	 * Registrar is a special type of User with extended permissions
	 * 
	 * @author Ethan Taylor
	 */
	private static class Registrar extends User {
		
		/**
		 * Creates a registrar user with the user id and password 
		 * in the registrar.properties file.
		 * 
		 * @param firstName the Registrar User's first name
		 * @param lastName the Registrar User's first name
		 * @param id the Registrar User's id
		 * @param email the Registrar User's email
		 * @param hashPW the Registrar User's hashed password
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * Returns true if successfully adds course to faculty's schedule
	 * 
	 * @param course the Course to add to the schedule
	 * @param faculty the Faculty to add course to
	 * @return true if successfully added course to faculty's schedule
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (currentUser == null || !currentUser.equals(registrar)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		faculty.getSchedule().addCourseToSchedule(course);
		return true;
	}
	
	/**
	 * Returns true if successfully removes course from faculty's schedule
	 * 
	 * @param course the Course to remove from the schedule
	 * @param faculty the Faculty to remove course from
	 * @return true if successfully removed course from faculty's schedule
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		if (currentUser == null || !currentUser.equals(registrar)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		faculty.getSchedule().removeCourseFromSchedule(course);
		return true;
	}
	
	/**
	 * Empties faculty's schedule
	 * 
	 * @param faculty the Faculty to reset schedule
	 */
	public void resetFacultySchedule(Faculty faculty) {
		if (currentUser == null || !currentUser.equals(registrar)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		faculty.getSchedule().resetSchedule();
	}
}