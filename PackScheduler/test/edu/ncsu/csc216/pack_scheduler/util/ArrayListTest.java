package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests ArrayList
 * 
 * @author Ethan Taylor
 */
public class ArrayListTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayList#ArrayList()}
	 * and {@link edu.ncsu.csc216.pack_scheduler.util.ArrayList#size()}.
	 */
	@Test
	public void testArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(0, list.size());
		list.add(0, "Apple");
		assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayList#add(int, java.lang.Object)}.
	 */
	@Test
	public void testAdd() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(0, "banana");
		list.add(1, "cantaloupe");
		list.add(2, "dragonfruit");
		list.add(3, "eggplant");
		list.add(4, "fig");
		list.add(5, "honeydew");
		list.add(6, "igneous rock");
		list.add(7, "jumping jacks");
		list.add(8, "kiwi");
		list.add(0, "apple");
		list.add(6, "grape");
		assertEquals(11, list.size());
		
		try {
			list.add(11, null);
			fail("Adding null element should be invalid");
		} catch (NullPointerException e) {
			assertEquals(11, list.size());
		}
		
		try {
			list.add(11, "fig");
			fail("Adding duplicate element should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Element is already in list", e.getMessage());
			assertEquals(11, list.size());
		}
		
		try {
			list.add(-1, "lemon");
			fail("Adding element at negative index should be invalid");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(11, list.size());
		}
		
		try {
			list.add(13, "lemon");
			fail("Adding element at index greater than size should be invalid");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(11, list.size());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayList#remove(int)}.
	 */
	@Test
	public void testRemove() {
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			list.remove(0);
			fail("Removing element from empty list should be invalid");
		} catch (IndexOutOfBoundsException e) {
			//Test passed
		}
		
		list.add(0, "apple");
		try {
			list.remove(-1);
			fail("Removing element at negative index should be invald");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(1, list.size());
		}
		
		assertEquals("apple", list.remove(0));
		assertEquals(0, list.size());
		
		list.add(0, "apple");
		list.add(1, "banana");
		list.add(2, "cantaloupe");
		list.add(3, "dragonfruit");
		
		assertEquals("dragonfruit", list.remove(3));
		assertEquals("banana", list.remove(1));
		
		try {
			list.remove(2);
			fail("Removing element from index greater than size should be invalid");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(2, list.size());
		}
		
		assertEquals("apple", list.get(0));
		assertEquals("cantaloupe", list.get(1));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayList#set(int, java.lang.Object)}.
	 */
	@Test
	public void testSet() {
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			list.set(0, "apple");
			fail("Setting element in empty list should be invalid");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		list.add(0, "apple");
		try {
			list.set(0, null);
			fail("Setting null element should be invalid");
		} catch (NullPointerException e) {
			assertEquals("apple", list.get(0));
		}
		
		try {
			list.set(-1, "banana");
			fail("Setting element at negative index should be invalid");
		} catch (IndexOutOfBoundsException e) {
			assertEquals("apple", list.get(0));
		}
		
		try {
			list.set(1, "banana");
			fail("Setting element at index equal to size should be invalid");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(1, list.size());
		}
		
		list.add(1, "banana");
		list.add(2, "cantaloupe");
		try {
			list.set(1, "apple");
			fail("Setting duplicate element should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Element is already in list", e.getMessage());
			assertEquals("banana", list.get(1));
		}
		
		assertEquals("cantaloupe", list.set(2, "dragonfruit"));
		assertEquals("dragonfruit", list.get(2));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayList#get(int)}.
	 */
	@Test
	public void testGet() {
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			list.get(0);
			fail("Getting element in empty list should be invald");
		} catch (IndexOutOfBoundsException e) {
			//Test passed
		}
		
		list.add(0, "banana");
		assertEquals("banana", list.get(0));
		
		list.add(1, "dragonfruit");
		list.add(0, "apple");
		list.add(2, "cantaloupe");
		
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("cantaloupe", list.get(2));
		assertEquals("dragonfruit", list.get(3));
		
		try {
			list.get(4);
			fail("Getting element at index equal to size should be invalid");
		} catch (IndexOutOfBoundsException e) {
			//Test Passed
		}
	}

}
