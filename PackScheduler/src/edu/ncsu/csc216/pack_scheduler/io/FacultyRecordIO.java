package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * FacultyRecordIO provides static methods that support reading in Faculty records
 * from a file and writing Faculty reocrds to a file.
 * 
 * @author Ethan Taylor
 */
public class FacultyRecordIO {

	/**
	 * Converts a file with the String representation of Faculty into Faculty objects
	 * 
	 * @param fileName the file to read from
	 * @return a LinkedList of valid Faculties read from the file
	 * @throws FileNotFoundException if the file could not be found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> faculties = new LinkedList<Faculty>();
		while (fileReader.hasNextLine()) {
			try {
				Faculty faculty = processFaculty(fileReader.nextLine());
				boolean duplicate = false;
				for (Faculty f : faculties) {
					if (faculty.getId().equals(f.getId())) {
						duplicate = true;
					}
				}
				if (!duplicate) {
					faculties.add(faculty);
				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return faculties;
	}
	
	/**
	 * Interprets a String as a Faculty, pulling out firstName, lastName, id, 
	 * email, hashPW, and maxCourses
	 * 
	 * @param line the String to interpret as a Faculty
	 * @return the Faculty interpreted from the String
	 * @throws IllegalArgumentException if a valid Faculty could not be interpreted from the String
	 */
	private static Faculty processFaculty(String line) {
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter(",");

		Faculty faculty = null;
		String firstName, lastName, id, email, hashPW;
		int maxCourses;

		try {
			firstName = lineReader.next();
			lastName = lineReader.next();
			id = lineReader.next();
			email = lineReader.next();
			hashPW = lineReader.next();
			maxCourses = lineReader.nextInt();
			faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		} catch (Exception e) {
			lineReader.close();
			throw new IllegalArgumentException();
		}

		lineReader.close();
		return faculty;
	}
	
	/**
	 * Writes a FacultyDirectory to a file
	 * 
	 * @param fileName the file that FacultyDirectory will be written to
	 * @param facultyDirectory the LinkedList of Faculties
	 * @throws IOException if the file could not be found
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (Faculty f : facultyDirectory) {
			fileWriter.println(f.toString());
		}
		fileWriter.close();
	}
}
