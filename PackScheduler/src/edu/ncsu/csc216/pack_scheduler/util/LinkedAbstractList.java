package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;



/**
 * LinkedAbstractList manages a list of elements of which can be added, removed,
 * returned, or changed.
 * 
 * @author Xuhui Lin
 * @author Rohan Sukhija
 *
 * @param <E> the data type
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** size */
	private int size;
	/** capacity */
	private int capacity;
	/** front */
	private ListNode front;
	/** back of the list */
	private ListNode back;

	/**
	 * LinkedAbstractList constructor
	 * 
	 * @param capacity the capacity input
	 */
	public LinkedAbstractList(int capacity) {

		this.front = null;
		this.size = 0;
		back = new ListNode(null);
		setCapacity(capacity);

	}

	/**
	 * set the capacity
	 * 
	 * @param capacity the capacity input
	 * @throws IllegalArgumentException when capacity is less than 0 or size
	 */
	public void setCapacity(int capacity) {

		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity cannot be less than 0");
		}

		if (capacity < size) {
			throw new IllegalArgumentException();
		}

		this.capacity = capacity;
	}

	/**
	 * Returns the element at the specified index
	 * 
	 * @param index the index of the element to return
	 * @return the element at the specified index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   or equal to the size
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * set List "index" to "element"
	 * 
	 * @param index   the index input
	 * @param element the element input
	 * @throws NullPointerException      when input element is null
	 * @throws IllegalArgumentException  when element is repeat
	 * @throws IndexOutOfBoundsException when index is out of index
	 */
	@Override
	public E set(int index, E element) {

		if (element == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < size(); i++) {
			if (element.equals(get(i))) {
				throw new IllegalArgumentException("Element is already in list");
			}
		}

		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		E replacedElement = current.data;
		current.data = element;

		return replacedElement;
	}

	/**
	 * Adds the item to the specified index
	 * 
	 * @param index   the specified index for the element to be added to
	 * @param element the element to be added
	 * @throws IllegalArgumentException  if the capacity is reached
	 * @throws IllegalArgumentException  if the element is already in the list
	 * @throws NullPointerException      if the element is null
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	@Override
	public void add(int index, E element) {

		if (size == capacity) {
			throw new IllegalArgumentException();
		}

		if (element == null) {
			throw new NullPointerException();
		}

		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		ListNode current = front;

		if (front == null) {
			front = new ListNode(element);
			back = front;
		} else {
			while (current.next != null) {
				if (current.data.equals(element)) {
					throw new IllegalArgumentException("Element is already in list");
				}
				current = current.next;
			}

			current = front;

			if (index == 0) {
				front = new ListNode(element, current);
			} else if (index == size) {
				back.next = new ListNode(element);
				back = back.next;
			} else {
				for (int i = 0; i < index - 1; i++) {
					current = current.next;
				}
				ListNode temp = current.next;
				current.next = new ListNode(element);
				current.next.next = temp;
			}
		}
		size++;
	}

	/**
	 * Removes the element at the specified index and returns the removed element
	 * 
	 * @param index at which the element to removed is
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index is out of bounds of the list
	 */
	@Override
	public E remove(int index) {

		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E replacedElement = null;
		ListNode current = front;
		if (index == 0) {
			replacedElement = front.data;
			front = front.next;
		} else {
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			replacedElement = current.next.data;
			current.next = current.next.next;
			if (index == size() - 1) {
				replacedElement = back.data;
				back = current;
			}
		}

		size--;
		return replacedElement;
	}

	/**
	 * Returns the size of the list 
	 * 
	 * @return the size
	 */
	public int size() {
		return size;
	}

	/**
	 * Class representing a list node that contains
	 * a generic data field and a next ListNode pointer
	 * 
	 * @author Xuhui Lin
	 *
	 */
	private class ListNode {

		/** the data of the listnode */
		private E data;
		/** the next listnode */
		private ListNode next;

		/**
		 * ListNode constructor with a null next pointer
		 * 
		 * @param data the data input
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * ListNode constructor with both data and a next pointer
		 * 
		 * @param data the data input
		 * @param next the Node input
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
}
