package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test Case for LinkedAbstractList
 * @author Xuhui Lin
 *
 */
public class LinkedAbstractListTest {

	/**
	 * Test method for LinkedAbstractList
	 */
	@Test
	 public void testLinkedAbstractList() {
		LinkedAbstractList<String> l = new LinkedAbstractList<String>(20);
		assertEquals(0, l.size());
	 }


	/**
	 * Test method for add
	 */
	@Test
	public void testAdd() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(20);
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
	 * Test method for remove.
	 */
	@Test
	public void testRemove() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(20);
		
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
		
		list.add(2, "dragonfruit");
		assertEquals("apple", list.remove(0));
	}

	/**
	 * Test method for Set.
	 */
	@Test
	public void testSet() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(20);
		
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
		
		assertEquals("apple", list.set(0, "blackberry"));
	}

	/**
	 * Test method for Get.
	 */
	@Test
	public void testGet() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(20);
		
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
