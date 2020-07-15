package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedQueue implemnents the Queue interface using the LinkedAbstractList class.
 * 
 * @param <E> the generic type for LinkedQueue
 * 
 * @author Ethan Taylor
 */
public class LinkedQueue<E> implements Queue<E> {

	/** The encapsulated list of elements */
	private LinkedAbstractList<E> list;
	/** The capacity of LinkedQueue */
	private int capacity = 0;
	
	/**
	 * Constructs LinkedQueue by instantiating list and setting capacity specified
	 * 
	 * @param capacity the capcity of the list
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}
	
	/**
	 * Adds an element to the back of LinkedQueue
	 * 
	 * @param element the element to add
	 * @throws IllegalArgumentException if element is null or there is no room
	 */
	@Override
	public void enqueue(E element) {
		if (element == null) {
			throw new IllegalArgumentException("Cannot add null value");
		} else if (list.size() >= capacity) {
			throw new IllegalArgumentException("LinkedQueue is full");
		}
		list.add(list.size(), element);
	}

	/**
	 * Removes and returns the elemtn at the front of LinkedQueue
	 * 
	 * @return the element removed
	 * @throws NoSuchElementException if LinkedQueue is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("LinkedQueue is empty");
		}
		return list.remove(0);
	}

	/**
	 * Returns true if LinkedQueue is empty
	 * 
	 * @return true if empty
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
	 * Returns the number of elemnets in LinkedQueue
	 * 
	 * @return the size of LinkedQueue
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets LinkedQueue's capacity
	 * 
	 * @param capacity the number of elements allowed in LinkedQueue
	 * @throws IllegalArgumentException if capacity is negative or less than the number of elements in LinkedQueue
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

	/**
	 * Returns true if the element is in LinkedQueue
	 * 
	 * @param element the element to check if in LinkedQueue
	 * @return true if the element is in LinkedQueue
	 */
	public boolean contains(E element) {
		E comparator = null;
		boolean inWaitlist = false;
		for (int i = 0; i < size(); i++) {
			comparator = dequeue();
			enqueue(comparator);
			if (element.equals(comparator)) {
				inWaitlist = true;
			}
		}
		return inWaitlist;
	}
}
