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

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests StudentRecordIO's methods.
 * 
 * @author Ethan Taylor
 */
public class StudentRecordIOTest {

	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";

	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9 };

	private String hashPW;
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Setup method that hashes the passwords from the student strings in the
	 * validStudents array
	 */
	@Before
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
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
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO#readStudentRecords(java.lang.String)}.
	 */
	@Test
	public void testReadStudentRecords() {
		SortedList<Student> studentRecords = null;
		try {
			studentRecords = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
		} catch (FileNotFoundException e) {
			fail("File not found");
		}

		for (int i = 0; i < validStudents.length; i++) {
			assertEquals(validStudents[i], studentRecords.get(i).toString());
		}

		try {
			assertEquals(0, StudentRecordIO.readStudentRecords("test-files/invalid_student_records.txt").size());
		} catch (FileNotFoundException e) {
			fail("File not found");
		}

		try {
			assertEquals(10, StudentRecordIO.readStudentRecords("test-files/actual_student_records.txt").size());
		} catch (FileNotFoundException e) {
			fail("File not found");
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO#writeStudentRecords(java.lang.String, edu.ncsu.csc216.collections.list.SortedList)}.
	 */
	@Test
	public void testWriteStudentRecords() {
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt",
					StudentRecordIO.readStudentRecords("test-files/student_records.txt"));
		} catch (FileNotFoundException e) {
//			fail("File not found");
		} catch (IOException e) {
			fail("Could not write to file");
		}

		checkFiles("test-files/student_records.txt", "test-files/actual_student_records.txt");
	}

}
