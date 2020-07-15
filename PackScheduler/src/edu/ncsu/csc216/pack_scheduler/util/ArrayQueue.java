package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * ArrayQueue implements the Queue interface using the ArrayList class.
 * 
 * @param <E> the generic type for ArrayQueue
 * 
 * @author Ethan Taylor
 */
public class ArrayQueue<E> implements Queue<E> {
	
	/** The encapsulated list of elements */
	private ArrayList<E> list;
	/** The capacity of ArrayQueue */
	private int capacity = 0;
	
	/**
	 * Constructs ArrayQueue by instantiating list and setting capacity specified
	 * 
	 * @param capacity the capcity of the list
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds an element to the back of ArrayQueue
	 * 
	 * @param element the element to add
	 * @throws IllegalArgumentException if element is null or there is no room
	 */
	@Override
	public void enqueue(E element) {
		if (element == null) {
			throw new IllegalArgumentException("Cannot add null value");
		} else if (list.size() >= capacity) {
			throw new IllegalArgumentException("ArrayQueue is full");
		}
		list.add(list.size(), element);
	}

	/**
	 * Removes and returns the elemtn at the front of ArrayQueue
	 * 
	 * @return the element removed
	 * @throws NoSuchElementException if ArrayQueue is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("ArrayQueue is empty");
		}
		return list.remove(0);
	}

	/**
	 * Returns true if ArrayQueue is empty
	 * 
	 * @return true if empty
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
	 * Returns the number of elemnets in ArrayQueue
	 * 
	 * @return the size of ArrayQueue
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets ArrayQueue's capacity
	 * 
	 * @param capacity the number of elements allowed in ArrayQueue
	 * @throws IllegalArgumentException if capacity is negative or less than the number of elements in ArrayQueue
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity cannot be less than 0");
		} else if (capacity < size()) {
			throw new IllegalArgumentException("Capacity cannot be less than the number of elements");
		}
		this.capacity = capacity;
	}
}
