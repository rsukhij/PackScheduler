package edu.ncsu.csc216.pack_scheduler.util;

public class LinkedListRecursive<E> {

	private int size;

	private ListNode front;

	public LinkedListRecursive() {
		size = 0;
		front = null;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(E e) {
		if (size == 0) {
			return false;
		}
		if (e == null) {
			throw new NullPointerException();
		}
		return front.contains(e);
	}

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

	public boolean remove(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (size == 0) {
			return false;
		}
		if (front.data == e) {
			front = front.next;
			size--;
			return true;
		}
		return front.remove(e);

	}

	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return front.get(index);
	}

	public E set(int index, E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return front.set(index, e);
	}

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

	private class ListNode {
		public E data;

		public ListNode next;

		public ListNode(E element, ListNode next) {
			data = element;
			this.next = next;
		}

		public void add(int index, E e) {
			addToMiddle(index, 0, e);
		}

		private void addToMiddle(int index, int current, E e) {
			if (current == index - 1) {
				ListNode newNode = new ListNode(e, next);
				this.next = newNode;
				size++;
				return;
			}
			next.addToMiddle(index, current + 1, e);
		}

		public boolean add(E e) {
			if (next == null) {
				next = new ListNode(e, null);
				size++;
				return true;
			}
			return next.add(e);
		}

		public E get(int index) {
			return get(index, 0);
		}

		private E get(int index, int current) {
			if (index == current) {
				return data;
			}
			return next.get(index, current + 1);
		}

		public E remove(int index) {
			return remove(index, 0);
		}

		private E remove(int index, int current) {
			if (current == index - 1) {
				E removed = next.data;
				next = next.next;
				size--;
				return removed;
			}
			return next.remove(index, current + 1);
		}

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

		public E set(int index, E e) {
			return set(0, index, e);
		}

		private E set(int current, int index, E e) {
			if (current == index) {
				E replace = data;
				data = e;
				return replace;
			}
			return next.set(current + 1, index, e);
		}

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
