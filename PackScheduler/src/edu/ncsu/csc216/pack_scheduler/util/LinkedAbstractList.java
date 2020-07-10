package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * LinkedAbstractList manages a list of elements of which can be added, removed,
 * returned, or changed.
 * 
 * @author Xuhui Lin
 *
 * @param <E> the array type
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
			throw new IllegalArgumentException();
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
	 * add Item in the list specify index
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
		if (index == size) {
			back.data = element;
		}
		if (front == null) {
			front = new ListNode(element);
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
			} else {
				for (int i = 0; i < index - 1; i++) {
					current = current.next;
				}
				current.next = new ListNode(element, current.next);
			}
		}
		size++;
	}

	/**
	 * remove item from list specify index Return the replacement item
	 * 
	 * @return the replacement item
	 */
	@Override
	public E remove(int index) {

		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E replacedElement = null;
		ListNode current = front;
		if (index == size - 1 && size > 1) {
			back.data = get(index - 1);
		} else if (size == 1) {
			back.data = null;
		}
		if (index == 0) {
			replacedElement = front.data;
			front = front.next;
		} else {
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			replacedElement = current.next.data;
			current.next = current.next.next;
		}
		size--;
		return replacedElement;
	}

	/**
	 * return the size
	 * 
	 * @return the size
	 */
	public int size() {
		return size;
	}

	/**
	 * ListNode Class control the Node
	 * 
	 * @author Xuhui Lin
	 *
	 */
	private class ListNode {

		/** data */
		private E data;
		/** next */
		private ListNode next;

		/**
		 * ListNode constructor
		 * 
		 * @param data the data input
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * ListNode constructor
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
