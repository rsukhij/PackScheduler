package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * ArrayList manages a list of elements of which can be added, removed,
 * returned, or changed.
 * 
 * @author Ethan Taylor
 * @param <E> the array type
 */
public class ArrayList<E> extends AbstractList<E> {
	private static final int INIT_SIZE = 10;
	private E[] list;
	private int size;
	
	/**
	 * Constructs an ArrayList by instantiating an array with default
	 * size of 10 and ArrayList size of 0
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		size = 0;
		list = (E[]) new Object[INIT_SIZE];
	}
	
	/**
	 * Adds an element a the specified index
	 * 
	 * @param index the index to add the element at
	 * @param element the element to add at the index
	 * @throws NullPointerException if the elemenet is null
	 * @throws IllegalArgumentException if the element is already in the list
	 * @throws IndexOutOfBoundsException if the index is less than 0 or
	 * 		greater than or equal to size
	 */
	@Override
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size(); i++) {
			if (element.equals(get(i))) {
				throw new IllegalArgumentException("Element is already in list");
			}
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (size() == list.length) {
			growArray();
		}
			
		for (int i = size(); i > index; i--) {
			list[i] = list[i - 1];
		}
		list[index] = element;
		size++;
	}
	
	/**
	 * Doubles the size of the internal array, allowing for automatic size increasing
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] temp = list;
		list = (E[]) new Object[size * 2];
		for (int i = 0; i < size(); i++) {
			list[i] = temp[i];
		}
	}
	
	/**
	 * Removes the element at the specified index and returns it
	 * 
	 * @param index the index of the element to remove
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index is less than 0 or
	 * 		greater than or equal to size
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E removedElement = get(index);
		for (int i = index; i < size(); i++) {
			list[i] = list[i + 1];
		}
		size--;
		return removedElement;
	}
	
	/**
	 * Sets the element at the specified index with the specified element
	 * 
	 * @param index the index to set the specified element
	 * @param element the elemenet to set at the specified index
	 * @return the element that was replaced
	 * @throws NullPointerException if the specified element is null
	 * @throws IllegalArgumentException if the specified element is already in the list
	 * @throws IndexOutOfBoundsException if the index is less than 0 or
	 * 		greater than or equal to the size
	 */
	@Override
	public E set(int index, E element) {
		if (element ==  null) {
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
		
		E replacedElement = get(index);
		list[index] = element;
		return replacedElement;
	}

	/**
	 * Returns the element at the specified index
	 * 
	 * @param index the index of the element to return
	 * @return the element at the specified index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or
	 * 		greater than or equal to the size
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}
	
	/**
	 * Returns the size of ArrayList
	 * 
	 * @return the size
	 */
	public int size() {
		return size;
	}
}
