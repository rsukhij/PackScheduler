/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Stack implemented using a dynamic array. Contains functionality for a generic
 * stack as well as for setting a capacity for the stack
 * 
 * @author Raymond Dong
 *
 * @param <E> the generic element type
 */
public class ArrayStack<E> implements Stack<E> {
	/** The stack using an ArrayList */
	private ArrayList<E> stack;
	/** The capacity of the stack */
	private int capacity;

	/**
	 * Constructor for ArrayStack
	 * 
	 * @param capacity the initial capacity for the ArrayStack
	 */
	public ArrayStack(int capacity) {
		stack = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds an element onto the stack
	 * 
	 * @param element the element being added onto the stack
	 * @throws IllegalArgumentException if element is null or size of stack is
	 *                                  greater than capacity
	 */
	@Override
	public void push(E element) {
		if (element == null || capacity <= stack.size()) {
			throw new IllegalArgumentException();
		}
		stack.add(element);
	}

	/**
	 * Removes and returns the E at the top of the stack
	 * 
	 * @return the E at the top of the stack
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return stack.remove(stack.size() - 1);
	}

	/**
	 * Checks if the stack is empty
	 * 
	 * @return true if the size of stack is 0, else returns false
	 */
	@Override
	public boolean isEmpty() {
		if (stack.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the size of the stack
	 * 
	 * @return the size of the stack
	 */
	@Override
	public int size() {
		return stack.size();
	}

	/**
	 * Sets the capacity of the stack
	 * 
	 * @param capacity the capacity the stack is being set to
	 * @throws IllegalArgumentException if capacity is less than size of list
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < stack.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;

	}

}
