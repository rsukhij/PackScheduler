/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Raymond Dong
 *
 */
public class FacultyDirectoryTest {
    
    /** Valid course records */
    private final String validTestFile = "test-files/faculty_records.txt";

    /** inValid course records */
    private final String inValidTestFile = "test-files/invalid_file.txt";
    /** Test first name */
    private static final String FIRST_NAME = "Fac";
    /** Test last name */
    private static final String LAST_NAME = "Ulty";
    /** Test id */
    private static final String ID = "fulty";
    /** Test email */
    private static final String EMAIL = "fulty@ncsu.edu";
    /** Test password */
    private static final String PASSWORD = "pw";
    /** Test max credits */
    private static final int MAX_COURSES = 2;
    
    /**
     * Resets course_records.txt for use in other tests.
     * 
     * @throws Exception if something fails during setup.
     */
    @Before
    public void setUp() throws Exception {
        // Reset faculty_records.txt so that it's fine for other needed tests
        Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
        Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
        try {
            Files.deleteIfExists(destinationPath);
            Files.copy(sourcePath, destinationPath);
        } catch (IOException e) {
            fail("Unable to reset files");
        }
    }

    /**
     * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#FacultyDirectory()}.
     */
    @Test
    public void testFacultyDirectory() {
        FacultyDirectory sd = new FacultyDirectory();
        assertFalse(sd.removeFaculty("sesmith5"));
        assertEquals(0, sd.getFacultyDirectory().length);
    }

    /**
     * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#newFacultyDirectory()}.
     */
    @Test
    public void testNewFacultyDirectory() {
        FacultyDirectory fd = new FacultyDirectory();

        fd.loadFacultyFromFile(validTestFile);
        assertEquals(8, fd.getFacultyDirectory().length);

        fd.newFacultyDirectory();
        assertEquals(0, fd.getFacultyDirectory().length);
    }

    /**
     * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#loadFacultyFromFile(java.lang.String)}.
     */
    @Test
    public void testLoadFacultyFromFile() {
        FacultyDirectory fd = new FacultyDirectory();

        // Test valid file
        fd.loadFacultyFromFile(validTestFile);
        assertEquals(8, fd.getFacultyDirectory().length);

        FacultyDirectory fk = new FacultyDirectory();
        try {
            fk.loadFacultyFromFile(inValidTestFile);
            fail("file doesn't exist");

        } catch (IllegalArgumentException e) {
            assertEquals("Unable to read file test-files/invalid_file.txt", e.getMessage());
            assertEquals(0, fk.getFacultyDirectory().length);

        }
    }

    /**
     * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#addFaculty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     */
    @Test
    public void testAddFaculty() {
        FacultyDirectory sd = new FacultyDirectory();

        // Test valid Faculty
        sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
        String[][] facultyDirectory = sd.getFacultyDirectory();
        assertEquals(1, facultyDirectory.length);
        assertEquals(FIRST_NAME, facultyDirectory[0][0]);
        assertEquals(LAST_NAME, facultyDirectory[0][1]);
        assertEquals(ID, facultyDirectory[0][2]);

        FacultyDirectory invalidDirectory = new FacultyDirectory();
        try {
            sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", "", MAX_COURSES);

        } catch (IllegalArgumentException e) {
            assertEquals(1, sd.getFacultyDirectory().length);
        }

        try {
            sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, " ram", "hari", MAX_COURSES);

        } catch (IllegalArgumentException e) {
            assertEquals("Passwords do not match", e.getMessage());
            assertEquals(1, sd.getFacultyDirectory().length);
        }

        try {
            sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, null, MAX_COURSES);

        } catch (IllegalArgumentException e) {
            assertEquals("Invalid password", e.getMessage());
        }

        try {
            invalidDirectory.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_COURSES);

        } catch (IllegalArgumentException e) {
            assertEquals("Invalid password", e.getMessage());
            assertEquals(0, invalidDirectory.getFacultyDirectory().length);
        }
    }

    /**
     * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#removeFaculty(java.lang.String)}.
     */
    @Test
    public void testRemoveFaculty() {
        FacultyDirectory fd = new FacultyDirectory();

        // Add Faculty and remove
        fd.loadFacultyFromFile(validTestFile);
        assertEquals(8, fd.getFacultyDirectory().length);
        assertTrue(fd.removeFaculty("awitt"));
        String[][] facultyDirectory = fd.getFacultyDirectory();
        assertEquals(7, facultyDirectory.length);
        assertEquals("Norman", facultyDirectory[5][0]);
        assertEquals("Brady", facultyDirectory[5][1]);
        assertEquals("nbrady", facultyDirectory[5][2]);
    }

    /**
     * Test method for {@link edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory#saveFacultyDirectory(java.lang.String)}.
     */
    @Test
    public void testSaveFacultyDirectory() {
        FacultyDirectory fd = new FacultyDirectory();

        // Add a Faculty
        fd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 2);
        assertEquals(1, fd.getFacultyDirectory().length);
        fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
        checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");

        try {
            fd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 2);
            assertEquals(1, fd.getFacultyDirectory().length);
            fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
            checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");

        } catch (IllegalArgumentException e) {
            assertEquals("Unable to write to file", e.getMessage());
        }
    }
    
    /**
     * Helper method to compare two files for the same contents
     * 
     * @param expFile expected output
     * @param actFile actual output
     */
    private void checkFiles(String expFile, String actFile) {
        try {
            Scanner expScanner = new Scanner(new FileInputStream(expFile));
            Scanner actScanner = new Scanner(new FileInputStream(actFile));

            while (expScanner.hasNextLine()) {
                assertEquals(expScanner.nextLine(), actScanner.nextLine());
            }

            expScanner.close();
            actScanner.close();
        } catch (IOException e) {
            fail("Error reading files.");
        }
    }
}
