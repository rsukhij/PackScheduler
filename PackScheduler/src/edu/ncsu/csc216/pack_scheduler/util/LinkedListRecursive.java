package edu.ncsu.csc216.pack_scheduler.util;

/**
 * LinkedList implemented using recursive methods. Contains 
 * state for the front ListNode and size. Has implementations of 
 * most common list methods.
 * @author Rohan Sukhija
 *
 * @param <E> the generic data type
 */
public class LinkedListRecursive<E> {

	/** The size of the LinkedList */
	private int size;
	/** The front ListNode of the list */
	private ListNode front;

	/** The constructor for the list. 
	 * initializes size to 0 and sets front to null
	 */
	public LinkedListRecursive() {
		size = 0;
		front = null;
	}
	
	/**
	 * Returns the size of the list
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns whether the list is empty or not
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns whether an element is in the list
	 * @param e the element being checked 
	 * @return whether the element is in the list
	 * @throws NullPointerException when the inputed element is null
	 */
	public boolean contains(E e) {
		if (size == 0) {
			return false;
		}
		if (e == null) {
			throw new NullPointerException();
		}
		return front.contains(e);
	}

	/**
	 * Adds an element to the end of the list
	 * @param e the element being added
	 * @return true 
	 * @throws IllegalArgumentException if the element already exists in the list
	 */
	public boolean add(E e) {
		if (contains(e)) {
			throw new IllegalArgumentException();
		}
		if (size == 0) {
			front = new ListNode(e, null);
			size++;
			return true;
		}
		return front.add(e);
	}

	/**
	 * Removes an element from the list based on a inputed element
	 * @param e the element to remove
	 * @return whether the element was found and removed
	 * @throws IllegalArgumentException if the element already exists in the list
	 */
	public boolean remove(E e) {
	    
		if (size == 0 || e == null) {
			return false;
		}
		if (front.data == e) {
			front = front.next;
			size--;
			return true;
		}
		return front.remove(e);

	}

	/**
	 * Returns an element at a given index of the list
	 * @param index the index of the list the element is on
	 * @return the element at the given index
	 * @throws IndexOutOfBoundsException if the index is not on the list
	 */
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return front.get(index);
	}

	/**
	 * Sets a given index of the list to the element given.
	 * @param index the index of the list to change
	 * @param e the element to replace the existing element
	 * @return the element replaced
	 * @throws NullPointerException when the inputed element is null
	 * @throws IndexOutOfBoundsException if the index is not on the list
	 */
	public E set(int index, E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (contains(e)) {
			throw new IllegalArgumentException();
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return front.set(index, e);
	}

	/**
	 * Inserts a given element to a given index on the list 
	 * @param index the index for the element 
	 * @param e the element to be added
	 * @throws IllegalArgumentException if the element already exists in the list
	 * @throws IndexOutOfBoundsException if the index is not on the list
	 * @throws NullPointerException when the inputed element is null
	 */
	public void add(int index, E e) {
		if (contains(e)) {
			throw new IllegalArgumentException();
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (e == null) {
			throw new NullPointerException();
		}
		if (index == 0) {
			front = new ListNode(e, front);
			size++;
			return;
		}
		if (index == size()) {
			add(e);
			return;
		}
		front.add(index, e);

	}
	
	/**
	 * Removes an element at a given index of the list
	 * @param index the index of the element to remove
	 * @return E the element removed
	 * @throws IndexOutOfBoundsException if the index is not on the list
	 */
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			E removed = front.data;
			front = front.next;
			size--;
			return removed;
		}
		return front.remove(index);
	}

	/**
	 * Private inner class for a node of the list. Contains recursive helper
	 * methods for the superclass methods. Contains state for the data and 
	 * next node of the list. 
	 * @author Rohan Sukhija
	 *
	 */
	private class ListNode {
		/** The element contained in the node */
		public E data;
		/** The next node to the current node */
		public ListNode next;

		/**
		 * Constructor for the ListNode. Initializes the 
		 * element and next node.
		 * @param element
		 * @param next
		 */
		public ListNode(E element, ListNode next) {
			data = element;
			this.next = next;
		}

		/**
		 * Calls a recursive helper method to complete the add functionality 
		 * of the list.
		 * @param index the index for the element to added to 
		 * @param e the element to be added
		 */
		public void add(int index, E e) {
			addToMiddle(index, 0, e);
		}

		/**
		 * Recursive helper to add(int, E) which takes care of cases when 
		 * adding to some index in the middle of the list
		 * @param index the index for the element to added to
		 * @param current the current index of the recursive function
		 * @param e the element to be added
		 */
		private void addToMiddle(int index, int current, E e) {
			if (current == index - 1) {
				ListNode newNode = new ListNode(e, next);
				this.next = newNode;
				size++;
				return;
			}
			next.addToMiddle(index, current + 1, e);
		}
		
		/**
		 * Recursively adds an element to the end of the list
		 * @param e the element being added
		 * @return true 
		 * @throws IllegalArgumentException if the element already exists in the list
		 */
		public boolean add(E e) {
			if (next == null) {
				next = new ListNode(e, null);
				size++;
				return true;
			}
			return next.add(e);
		}

		/**
		 * Recursively gets an element at a given index of the list
		 * by calling a recursive helper method. 
		 * @param index the index of the list the element is on
		 * @return the element at the given index
		 */
		public E get(int index) {
			return get(index, 0);
		}

		/**
		 * Recursive helper method for get(int). Gets an element 
		 * at a inputed index. 
		 * @param index the index of the element to get
		 * @param current the current index of the recursive function
		 * @return
		 */
		private E get(int index, int current) {
			if (index == current) {
				return data;
			}
			return next.get(index, current + 1);
		}

		/**
		 * Recursively removes a element at a given index.
		 * Uses a recursive helper with additional counter 
		 * parameter. 
		 * @param index the index of the element to remove
		 * @return the element removed
		 */
		public E remove(int index) {
			return remove(index, 0);
		}

		/**
		 * Recursive helper method for remove(int). Removes
		 * an element at a given index.
		 * @param index the index of the element to remove
		 * @param current the current index of the recursive function
		 * @return the element removed
		 */
		private E remove(int index, int current) {
			if (current == index - 1) {
				E removed = next.data;
				next = next.next;
				size--;
				return removed;
			}
			return next.remove(index, current + 1);
		}

		/**
		 * Recursively removes a given element on the list
		 * @param e the element of the list to remove
		 * @return if the element was found and removed
		 */
		public boolean remove(E e) {
			if (next == null) {
				return false;
			}
			if (next.data == e) {
				next = next.next;
				size--;
				return true;
			}
			return next.remove(e);
		}
		
		/**
		 * Calls a recursive helper method to set a particular 
		 * node to contain a different, provided value. 
		 * @param index the index of the node to change
		 * @param e the element that will replace the current element
		 * @return the element replaced
		 */
		public E set(int index, E e) {
			return set(0, index, e);
		}
		
		/**
		 * Recursive helper method to set a particular 
		 * node to contain a different, provided value.
		 * @param current the current index of the recursive function 
		 * @param index the index of the node to change
		 * @param e the element that will replace the current element
		 * @return the element replaced
		 */
		private E set(int current, int index, E e) {
			if (current == index) {
				E replace = data;
				data = e;
				return replace;
			}
			return next.set(current + 1, index, e);
		}

		/**
		 * Recursive method to check whether inputed element exists within 
		 * the list
		 * @param e element contained in the list(or not)
		 * @return whether inputed element exists within the list
		 */
		public boolean contains(E e) {
			if (e == data) {
				return true;
			}
			if (next == null) {
				return false;
			}
			return next.contains(e);
		}

	}

}
