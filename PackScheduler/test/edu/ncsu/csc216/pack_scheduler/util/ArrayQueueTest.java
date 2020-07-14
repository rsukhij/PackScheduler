package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests ArrayQueue
 * 
 * @author Ethan Taylor
 */
public class ArrayQueueTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayQueue#ArrayQueue()}.
	 */
	@Test
	public void testArrayQueue() {
		ArrayQueue<String> q = new ArrayQueue<String>();
		assertTrue(q.isEmpty());
		q.enqueue("apple");
		q.enqueue("banana");
		q.enqueue("coconut");
		q.enqueue("dragonfruit");
		q.enqueue("eggplant");
		q.enqueue("fig");
		q.enqueue("grape");
		q.enqueue("honeydew");
		q.enqueue("ice");
		q.enqueue("java");
		try {
			q.enqueue("kiwi");
			fail("ArrayQueue should have default capacity of 10, so adding an 11th element should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("ArrayQueue is full", e.getMessage());
			assertEquals(10, q.size());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayQueue#enqueue(java.lang.Object)}.
	 */
	@Test
	public void testEnqueue() {
		ArrayQueue<String> q = new ArrayQueue<String>();
		
		try {
			q.enqueue(null);
			fail("Adding null to ArrayQueue should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Cannot add null value", e.getMessage());
			assertEquals(0, q.size());
		}
		
		String s = null;
		try {
			q.enqueue(s);
			fail("Adding a null element to ArrayQueue should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Cannot add null value", e.getMessage());
			assertEquals(0, q.size());
		}
		
		q.enqueue("apple");
		q.enqueue("banana");
		assertEquals("apple", q.dequeue());
		q.enqueue("coconut");
		assertEquals("banana", q.dequeue());
		
		q.setCapacity(2);
		q.enqueue("dragonfruit");
		try {
			q.enqueue("eggplant");
			fail("Adding elements above capacity should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("ArrayQueue is full", e.getMessage());
			assertEquals(2, q.size());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayQueue#dequeue()}.
	 */
	@Test
	public void testDequeue() {
		ArrayQueue<String> q = new ArrayQueue<String>();
		
		try {
			q.dequeue();
			fail("Removing element from an empty ArrayQueue should be invalid");
		} catch (NoSuchElementException e) {
			assertEquals("ArrayQueue is empty", e.getMessage());
		}
		
		q.enqueue("apple");
		assertEquals("apple", q.dequeue());
		q.enqueue("banana");
		assertEquals("banana", q.dequeue());
		q.enqueue("cantaloupe");
		q.enqueue("dragonfruit");
		assertEquals("cantaloupe", q.dequeue());
		q.enqueue("eggplant");
		q.enqueue("fig");
		assertEquals("dragonfruit", q.dequeue());
		assertEquals("eggplant", q.dequeue());
		assertEquals("fig", q.dequeue());
		
		try {
			q.dequeue();
			fail("Removing element from an empty ArrayQueue should be invalid");
		} catch (NoSuchElementException e) {
			assertEquals("ArrayQueue is empty", e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayQueue#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		ArrayQueue<String> q = new ArrayQueue<String>();
		
		assertTrue(q.isEmpty());
		q.enqueue("apple");
		assertFalse(q.isEmpty());
		q.enqueue("banana");
		assertFalse(q.isEmpty());
		q.dequeue();
		assertFalse(q.isEmpty());
		q.dequeue();
		assertTrue(q.isEmpty());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayQueue#size()}.
	 */
	@Test
	public void testSize() {
		ArrayQueue<String> q = new ArrayQueue<String>();
		
		assertEquals(0, q.size());
		q.enqueue("apple");
		assertEquals(1, q.size());
		q.enqueue("banana");
		assertEquals(2, q.size());
		q.dequeue();
		assertEquals(1, q.size());
		q.dequeue();
		assertEquals(0, q.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.ArrayQueue#setCapacity(int)}.
	 */
	@Test
	public void testSetCapacity() {
		ArrayQueue<String> q = new ArrayQueue<String>();
		
		try {
			q.setCapacity(-1);
			fail("Setting negative capacity should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Capacity cannot be less than 0", e.getMessage());
		}
		
		q.enqueue("apple");
		q.enqueue("banana");
		
		try {
			q.setCapacity(1);
			fail("Setting capcity less than size should be invalid");
		} catch (IllegalArgumentException e) {
			assertEquals("Capacity cannot be less than the number of elements", e.getMessage());
		}
		
		try {
			q.setCapacity(2);
			q.enqueue("cantaloupe");
		} catch (IllegalArgumentException e) {
			assertEquals("ArrayQueue is full", e.getMessage());
			assertEquals(2, q.size());
		}
		
		q.setCapacity(4);
		q.enqueue("cantaloupe");
		q.dequeue();
		q.dequeue();
		q.setCapacity(1);
	}

}
