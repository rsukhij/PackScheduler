package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests SortedList from CSC216Collections.jar
 * 
 * @author Ethan Taylor
 * @author Dilli Wagley
 * @author Rohan Sukhija
 */
public class SortedListTest {

	/**
	 * A general test method for SortedList class that tests adding and constructing
	 * of the object
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		assertFalse(list.contains("apple"));

		list.add("apple");
		list.add("banana");
		list.add("cantaloupe");
		list.add("dragonfruit");
		list.add("fig");
		list.add("grapefuit");
		list.add("kiwi");
		list.add("lemon");
		list.add("mango");
		list.add("orange");
		assertEquals(10, list.size());

		list.add("persimmon");
		assertEquals(11, list.size());
	}

	/** Test method for add() method of SortedList class */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));

		list.add("avocado");
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("avocado", list.get(1));
		assertEquals("banana", list.get(2));

		list.add("cantaloupe");
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("avocado", list.get(1));
		assertEquals("banana", list.get(2));
		assertEquals("cantaloupe", list.get(3));

		try {
			list.add(null);
			fail("Adding null element did not throw NullPointerException");
		} catch (NullPointerException e) {
			assertEquals(4, list.size());
		}

		try {
			list.add("banana");
			fail("Adding duplicate element did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("Element already in list.", e.getMessage());
			assertEquals(4, list.size());
		}
	}

	/** Test method for get() method of SortedList class */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		try {
			list.get(0);
			fail("Getting an element from an empty list did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(list.isEmpty());
		}

		list.add("banana");
		list.add("apple");
		list.add("avocado");
		list.add("cantaloupe");

		try {
			list.get(-1);
			fail("Getting an element at an index less than 0 did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}

		try {
			list.get(4);
			fail("Getting an element at an index greater than its size did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}
	}

	/** Tests the remove method of the SortedList class */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		// TODO Test removing from an empty list
		try {
			list.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		// TODO Add some elements to the list - at least 4
		list.add("Canada");
		list.add("US");
		list.add("Russia");
		list.add("China");
		// TODO Test removing an element at an index < 0
		try {
			list.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}
		// TODO Test removing an element at size
		try {
			list.remove(list.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}
		// TODO Test removing a middle element
		try {
			list.remove(2);
			assertEquals(3, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		// TODO Test removing the last element
		try {
			list.remove(list.size() - 1);
			assertEquals(2, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		// TODO Test removing the first element
		try {
			list.remove(0);
			assertEquals(1, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		// TODO Test removing the last element
		try {
			list.remove(list.size() - 1);
			assertEquals(0, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
	}

	/** Tests the indexOf method of the SortedList class */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		assertEquals(-1, list.indexOf("Nonexistent"));

		list.add("US");
		list.add("UK");
		list.add("China");

		assertEquals(-1, list.indexOf("Russia"));
		assertEquals(0, list.indexOf("China"));
		assertEquals(1, list.indexOf("UK"));
		assertEquals(2, list.indexOf("US"));
		try {
			list.indexOf(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(3, list.size());
		}

	}

	/** Tests the clear method of the SortedList class */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		list.add("US");
		list.add("UK");
		list.add("China");
		list.clear();
		assertEquals(0, list.size());
	}

	/** Tests that SortedLists with added elements are no longer empty */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// TODO Test that the list starts empty
		assertEquals(0, list.size());
		// TODO Add at least one element
		list.add("China");
		// TODO Check that the list is no longer empty
		assertNotEquals(0, list.size());
	}

	/** Tests the contains() method of the SortedList class */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// TODO Test the empty list case
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		assertFalse(list.contains("Dilli"));
		// TODO Add some elements
		list.add("Dilli");
		// TODO Test some true and false cases
		assertTrue(list.contains("Dilli"));
		assertFalse(list.contains("John"));
	}

	/** Tests the equals() method of the SortedList class */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// TODO Make two lists the same and one list different
		list1.add("Dilli");
		list2.add("Dilli");
		list3.add("Mike");

		// Test for equality and non-equality
		assertTrue(list1.equals(list2));
		assertFalse(list1.equals(list3));
	}

	/** Tests the hashcode() method of the SortedList class */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// TODO Make two lists the same and one list different
		list1.add("Dilli");
		list2.add("Dilli");
		list3.add("Mike");

		// TODO Test for the same and different hashCodes
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
	}

}
