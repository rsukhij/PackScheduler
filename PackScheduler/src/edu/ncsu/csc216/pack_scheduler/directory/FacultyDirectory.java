/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a directory of all faculty members.
 * @author Raymond Dong
 *
 */
public class FacultyDirectory {
    /** List of faculty in the directory */
    private LinkedList<Faculty> facultyDirectory;
    /** Hashing algorithm */
    private static final String HASH_ALGORITHM = "SHA-256";
    
    /**
     * Constructor for FacultyDirectory class that creates an empty faculty directory.
     */
    public FacultyDirectory() {
        newFacultyDirectory();
    }

    /**
     * Creates an empty faculty directory. All faculty in previous directory is cleared.
     */
    public void newFacultyDirectory() {
        facultyDirectory = new LinkedList<Faculty>();
    }
    
    /**
     * Constructs a faculty directory by reading information from a given file.
     * @param fileName file containing list of faculty.
     * @throws IllegalArgumentException if the file cannot be found.
     */
    public void loadFacultyFromFile(String fileName) {
        try {
            facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to read file " + fileName);
        }
    }
    
    /**
     * Adds faculty to the faculty directory
     * @param firstName faculty's first name
     * @param lastName faculty's last name
     * @param id faculty's id
     * @param email faculty's email
     * @param password faculty's password
     * @param repeatPassword faculty's password repeated
     * @param maxCourses faculty's maximum amount of courses
     * @return true if faculty is added successfully, else return false.
     * @throws IllegalArgumentException if password and repeatPassword do not match or are null or empty string.
     */
    public boolean addFaculty(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCourses) {
        String hashPW = "";
        String repeatHashPW = "";
        if (password == null || repeatPassword == null || password.equals("") || repeatPassword.equals("")) {
            throw new IllegalArgumentException("Invalid password");
        }
        try {
            MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
            digest1.update(password.getBytes());
            hashPW = new String(digest1.digest());

            MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
            digest2.update(repeatPassword.getBytes());
            repeatHashPW = new String(digest2.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Cannot hash password");
        }

        if (!hashPW.equals(repeatHashPW)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // If an IllegalArgumentException is thrown, it's passed up from Faculty
        // to the GUI
        Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);

        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            if (f.getId().equals(faculty.getId())) {
                return false;
            }
        }
        return facultyDirectory.add(faculty);
    }
    
    /**
     * Removes a faculty from faculty directory based on the id of the faculty.
     * @param id id of the faculty in the directory.
     * @return true if faculty is successfully removed, else return false.
     */
    public boolean removeFaculty(String id) {
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            if (f.getId().equals(id)) {
                facultyDirectory.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns all faculty in the directory with a column for first name, last name, and id.
     * @return String array containing a column for first name, last name, and id for every faculty in the directory.
     */
    public String[][] getFacultyDirectory(){
        String[][] directory = new String[facultyDirectory.size()][3];
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            directory[i][0] = f.getFirstName();
            directory[i][1] = f.getLastName();
            directory[i][2] = f.getId();
        }
        return directory;
    }
    
    /**
     * Save the faculty directory to a file.
     * @param fileName name of file that the faculty directory is being saved to.
     * @throws IllegalArgumentException if file cannot be saved.
     */
    public void saveFacultyDirectory(String fileName) {
        try {
            FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to write to file " + fileName);
        }
    }
    
    /**
     * Returns a faculty with the given id.
     * @param id id of the faculty that need to be returned.
     * @return a faculty with the given id, or null if the faculty with the given id does not exist.
     */
    public Faculty getFacultyById(String id) {
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }
}
