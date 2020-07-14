package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * A Queue class will contain a list of elements where elements will only be added
 * to the back and removed only from the front. Other methods include returning if 
 * the list is empty, the list's size, and setting the number of elements the list
 * can hold.
 * 
 * @param <E> the generic type for Queue
 * 
 * @author Ethan Taylor
 */
public interface Queue<E> {

	/**
	 * Adds an element to the back of Queue
	 * 
	 * @param element the element to add
	 * @throws IllegalArgumentException if there is no room
	 */
	void enqueue(E element);
	
	/**
	 * Removes and returns the elemtn at the front of Queue
	 * 
	 * @return the element removed
	 * @throws NoSuchElementException if Queue is empty
	 */
	E dequeue();
	
	/**
	 * Returns true if Queue is empty
	 * 
	 * @return true if empty
	 */
	boolean isEmpty();
	
	/**
	 * Returns the number of elemnets in Queue
	 * 
	 * @return the size of Queue
	 */
	int size();

	/**
	 * Sets Queue's capacity
	 * 
	 * @param capacity the number of elements allowed in Queue
	 * @throws IllegalArgumentException if capacity is negative or less than the number of elements in Queue
	 */
	void setCapacity(int capacity);
}
