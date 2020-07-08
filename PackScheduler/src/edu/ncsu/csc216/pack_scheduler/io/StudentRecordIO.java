package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * StudentRecordIO provides static methods that support reading in student
 * records from a file and writing student records to a file.
 * 
 * @author Ethan Taylor
 * @author Dilli Wagley
 * @author Rohan Sukhija
 */
public class StudentRecordIO {

	/**
	 * Converts a file with the String representation of Students into Student
	 * objects
	 * 
	 * @param fileName the file to read from
	 * @return a SortedList of valid Students read from the file
	 * @throws FileNotFoundException if the file could not be found
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<Student>();
		while (fileReader.hasNextLine()) {
			try {
				Student student = processStudent(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < students.size(); i++) {
					Student s = students.get(i);
					if (student.getId().equals(s.getId())) {
						duplicate = true;
					}
				}
				if (!duplicate) {
					students.add(student);
				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return students;
	}

	/**
	 * Interprets a String as a Student, pulling out firstName, lastName, id, email,
	 * hashPW, and maxCredits.
	 * 
	 * @param line the String to interpret as a Student
	 * @return the Student interpreted from the String
	 * @throws IllegalArgumentException if a valid Student could not be interpreted
	 *                                  from the String
	 */
	private static Student processStudent(String line) {
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter(",");

		Student student = null;
		String firstName, lastName, id, email, hashPW;
		int maxCredits;

		try {
			firstName = lineReader.next();
			lastName = lineReader.next();
			id = lineReader.next();
			email = lineReader.next();
			hashPW = lineReader.next();
			maxCredits = lineReader.nextInt();
			student = new Student(firstName, lastName, id, email, hashPW, maxCredits);
		} catch (Exception e) {
			lineReader.close();
			throw new IllegalArgumentException();
		}

		lineReader.close();
		return student;
	}

	/**
	 * Writes a StudentDirectory to a file
	 * 
	 * @param fileName         the file that StudentDirectory will be written to
	 * @param studentDirectory the SortedList of Students
	 * @throws IOException if the file could not be found
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}
		fileWriter.close();
	}

}
