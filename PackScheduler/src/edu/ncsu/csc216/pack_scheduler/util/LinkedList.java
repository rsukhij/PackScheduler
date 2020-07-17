package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * LinkedList class implemented using a list iterator, with a doubly linked
 * state to result in two-way traversable linked list. Extends from
 * AbstractSequentialList that utilizes the iterator.
 * 
 * @author Rohan Sukhija
 *
 * @param <E> the generic data type
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	/** Null leading node */
	private ListNode front;

	/** Null trailing node */
	private ListNode back;

	/** The size of the list */
	private int size;

	/**
	 * Constructor for the class. Initializes front and back nodes as well as size.
	 */
	public LinkedList() {
		front = new ListNode(null, null, null);
		back = new ListNode(null, null, null);
		front.next = back;
		back.prev = front;
		size = 0;
	}

	/**
	 * Returns the iterator for the list given a starting index
	 * 
	 * @param index the starting index for the iterator
	 * @return the iterator object
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}

	/**
	 * Returns the size of the list
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds a element to the list at a specified index. Checks for duplicate
	 * additions to the list.
	 * 
	 * @param index   the index for the element to be added to
	 * @param element the element to be added to the list
	 * @throws IllegalArgumentException if a duplicate element is trying to be added
	 */
	@Override
	public void add(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);
	}

	/**
	 * Sets a given index to contain a given element. Returns the element that was
	 * replaced. Checks for duplicate additions to the list.
	 * 
	 * @param index   the index at which the elements are switched
	 * @param element the element to be switched in
	 * @throws IllegalArgumentException if a duplicate element is trying to be added
	 */
	@Override
	public E set(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}

	/**
	 * Class for the linked list's iterator. Contains state for the current location
	 * of the iterator within the list. Has functionality to traverse the list at
	 * any given starting point, towards any of the two directions. Utilizes this
	 * traversal ability to perform standard list functions such as add, remove, and
	 * set.
	 * 
	 * @author Rohan Sukhija
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {

		/** The node to the left of the iterator */
		private ListNode previous;
		/** the node to the right of the iterator */
		private ListNode next;
		/** the index of the node to the left of the iterator */
		private int previousIndex;
		/** the index of the node to the right of the iterator */
		private int nextIndex;
		/** the index of the node last retrieved by method that retrieves a node */
		private ListNode lastRetrieved;

		/**
		 * Constructor for the LinkedListIterator. Initializes the iterator at a given
		 * index in the list.
		 * 
		 * @param index the index to start the iterator at
		 * @throws IndexOutOfBoundsException if the index is out of bounds of the list
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size()) {
				throw new IndexOutOfBoundsException();
			}
			ListNode current = front;

			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			previous = current;
			next = current.next;
			previousIndex = index - 1;
			nextIndex = index;
		}

		/**
		 * Method to see if the iterator has a non-null next node
		 * 
		 * @return if the iterator has a non-null next node
		 */
		@Override
		public boolean hasNext() {
			return nextIndex() < size();
		}

		/**
		 * Returns the elements in the next node. Moves the iterator one index to the
		 * right.
		 * 
		 * @return the element in the next node
		 * @throws NoSuchElementException if the there is no element in the next node
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			previous = next;
			next = next.next;
			previousIndex++;
			nextIndex++;
			return lastRetrieved.data;
		}

		/**
		 * Method to see if the iterator has a non null previous node
		 * 
		 * @return if the iterator has a non null previous node
		 */
		@Override
		public boolean hasPrevious() {
			return nextIndex() > 0;

		}

		/**
		 * Returns the element in the previous node. Moves the index to the left by one
		 * 
		 * @return the element in the previous node
		 * @throws NoSuchElementException if the there is no element in the previous
		 */
		@Override
		public E previous() {
			if (previous == null) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			next = previous;
			previous = previous.prev;
			previousIndex--;
			nextIndex--;
			return lastRetrieved.data;
		}

		/**
		 * Returns the index of the next node
		 * 
		 * @return the index of the next node
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns the index of the next node
		 * 
		 * @return the index of the next node
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * Removes from the list the last element that was returned by next() or
		 * previous(). Can only be called once and only after a call to next or
		 * previous.
		 * 
		 * @throws IllegalStateException if there has not been a call to next or
		 *                               previous
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			lastRetrieved.prev.next = lastRetrieved.next;
			lastRetrieved.next.prev = lastRetrieved.prev;
			lastRetrieved = null;
			size--;
		}

		/**
		 * Replaces the last element retrieved by either next() or previous(). Cannot be
		 * used if remove() or add() have been called right before.
		 * 
		 * @throws IllegalStateException if there has not been a call to next or
		 *                               previous or there has been a call to remove()
		 *                               or add() after a call to next() or previous()
		 * @throws NullPointerException  if the element is null
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			lastRetrieved.data = e;

		}

		/**
		 * Adds the element into the list. It is added before the element in the next
		 * node, if any, and after the element stored as previous, if any.
		 * 
		 * @param e the element to be added to the list
		 * @throws NullPointerException if the element is null
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}

			ListNode newNode = new ListNode(e);
			previous.next = newNode;
			next.prev = newNode;
			newNode.next = next;
			newNode.prev = previous;
			previous = newNode;
			previousIndex++;
			nextIndex++;
			lastRetrieved = null;
			size++;
		}

	}

	/**
	 * Class representing a list node that contains a generic data field and a next
	 * ListNode pointer
	 * 
	 * @author Rohan Sukhija
	 *
	 */
	private class ListNode {

		/** The data contained in the node */
		public E data;
		/** The next node to the current node */
		public ListNode next;
		/** The previous node to the current node */
		public ListNode prev;

		/**
		 * ListNode constructor with only the element initialized
		 * 
		 * @param data the data input
		 */
		public ListNode(E data) {
			this.data = data;
		}

		/**
		 * ListNode constructor with both data and a next and previous pointer
		 * 
		 * @param data the data input
		 * @param prev the previous node input
		 * @param next the next node input
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}

}
