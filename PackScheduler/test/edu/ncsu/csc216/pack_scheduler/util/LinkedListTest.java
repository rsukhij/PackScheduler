package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.ListIterator;

import org.junit.Test;

/**
 * Test class for the LinkedList class
 * @author Rohan Sukhija
 *
 */
public class LinkedListTest {

	/**
	 * Test case for the size() method 
	 */
	@Test
	public void testSize() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(0, "melon");
		list.add(1, "banana");
		list.add(2, "apple");
		assertEquals(3, list.size());
	}

	/**
	 * Test for the constructor
	 */
	@Test
	public void testLinkedList() {
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(0, list.size());
	}

	/**
	 * Test for the iterator of the linked list
	 */
	@Test
	public void testListIteratorInt() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(0, "peach");
		list.add(1, "soda");
		list.add(2, "cocktail");
		list.set(1, "ginger");
		list.remove(1);

		String[] expected = new String[] { "peach", "cocktail" };
		ListIterator<String> iterator = list.listIterator(0);
		int i = 0;
		while (iterator.hasNext()) {
			assertEquals(expected[i], iterator.next());
			i++;
		}
		list.add(2, "sun");
		list.add(3, "salad");
		iterator = list.listIterator(4);
		i = 3;
		expected = new String[] { "peach", "cocktail", "sun", "salad" };
		while (iterator.hasPrevious()) {
			assertEquals(expected[i], iterator.previous());
			i--;
		}

	}
}
