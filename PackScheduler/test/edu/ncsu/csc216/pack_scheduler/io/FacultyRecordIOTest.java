package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests FacultyRecordIO's methods
 * 
 * @author Ethan Taylor
 */
public class FacultyRecordIOTest {
	
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";
	
	private String[] validFaculties = {validFaculty0, validFaculty1, validFaculty2, validFaculty3,
			validFaculty4, validFaculty5, validFaculty6, validFaculty7};
	
	private String hashPW;
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Setup method that hashes the passwords from the Faculty String in the
	 * validStudents array
	 */
	@Before
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validFaculties.length; i++) {
				validFaculties[i] = validFaculties[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}
	
	/**
	 * Compares the contents of two files to see if they are the same
	 * 
	 * @param expFile expected file to check against
	 * @param actFile actual file that may or may not be the same
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO#readFacultyRecords(String)}.
	 */
	@Test
	public void testReadFacultyRecords() {
		LinkedList<Faculty> facultyRecords = null;
		try {
			facultyRecords = FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt");
		} catch (FileNotFoundException e) {
			fail("File not found");
		}
		for (int i = 0; i < validFaculties.length; i++) {
			assertEquals(validFaculties[i], facultyRecords.get(i).toString());
		}
		
		try {
			facultyRecords = FacultyRecordIO.readFacultyRecords("test-files/invalid_faculty_records.txt");
		} catch (FileNotFoundException e) {
			fail("File not found");
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO#writeStudentRecords(String, LinkedList)}.
	 */
	@Test
	public void testWriteFacultyRecords() {
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt",
					FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt"));
		} catch (FileNotFoundException e) {
			fail("File not found");
		} catch (IOException e) {
			fail("Could not write to file");
		}

		checkFiles("test-files/faculty_records.txt", "test-files/actual_faculty_records.txt");
	}

}
