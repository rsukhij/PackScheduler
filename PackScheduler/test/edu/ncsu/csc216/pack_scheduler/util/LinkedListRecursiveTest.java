package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for RecursiveLinkedList
 * @author Rohan Sukhija
 *
 */
public class LinkedListRecursiveTest {
	
	/**
	 * Test method for constructor
	 */
	@Test
	public void testLinkedListRecursive() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
	}

	/**
	 * Test method for size()
	 */
	@Test
	public void testSize() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
	}

	/**
	 * Test method for isEmpty
	 */
	@Test
	public void testIsEmpty() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertTrue(list.isEmpty());
	}

	/**
	 * Test method for Contains()
	 */
	@Test
	public void testContains() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertTrue(!list.contains("Africa"));

		list.add("Africa");
		list.add("Asia");
		list.add("America");
		assertTrue(list.contains("Africa"));
		assertTrue(!list.contains("Europe"));
		assertTrue(list.contains("Asia"));

	}

	/**
	 * Test method for add(E)
	 */
	@Test
	public void testAddE() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("Africa");
		list.add("Asia");
		list.add("America");
		try {
			list.add("Africa");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(3, list.size());
		}
	}

	/**
	 * Test method for remove(E)
	 */
	@Test
	public void testRemoveE() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("Africa");
		list.add("Asia");
		list.add("America");
		list.add("Europe");
		list.add("Oceania");
		list.remove("Africa");
		list.remove("Oceania");
		list.remove("Europe");
		list.remove("China");
		assertEquals(2, list.size());
		assertEquals("Asia", list.get(0));
	}

	/**
	 * Test method for get(int)
	 */
	@Test
	public void testGet() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("Africa");
		list.add("Asia");
		list.add("America");
		list.add("Europe");
		assertEquals("Africa", list.get(0));
		assertEquals("America", list.get(2));

	}

	/**
	 * Test method for set(int,E)
	 */
	@Test
	public void testSet() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("Africa");
		list.add("Asia");
		list.add("America");
		list.add("Europe");
		list.set(2, "Replace");
		assertEquals("Replace", list.get(2));
		assertEquals(4, list.size());

	}

	/**
	 * Test method for add(int, E)
	 */
	@Test
	public void testAddIntE() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add(0, "Africa");
		list.add("Asia");
		list.add("America");
		list.add("Europe");
		list.add(0, "Add");
		list.add(5, "Add2");
		list.add(2, "Add3");

		assertEquals("Add", list.get(0));
		assertEquals("Africa", list.get(1));

		assertEquals(7, list.size());
	}

	/**
	 * Test method for remove(int)
	 */
	@Test
	public void testRemoveInt() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("Africa");
		list.add("Asia");
		list.add("America");
		list.add("Europe");
		list.remove(0);
		list.remove(2);
		assertEquals(2, list.size());
	}

}
