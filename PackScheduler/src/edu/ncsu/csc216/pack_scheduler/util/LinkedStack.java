/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * @author Raymond Dong
 *
 */
public class LinkedStack<E> implements Stack<E>{
    /** The stack using LinkedAbstractList */
    private LinkedAbstractList<E> stack;


    
    /**
     * Constructor for LinkedStack with custom size and capacity
     */
    public LinkedStack(int capacity) {
        stack = new LinkedAbstractList<E>(capacity);
        setCapacity(capacity);
    }
    
    /**
     * Adds an element onto the stack
     * @param element the element being added onto the stack
     * @throws IllegalArgumentExcpetion if element is null or capacity is smaller than the size of stack
     */
    @Override
    public void push(E element) throws IllegalArgumentException {
        stack.add(0, element);
    }

    /**
     * Removes and returns the top of the stack
     * @return the top of the stack
     */
    @Override
    public E pop() {
        return stack.remove(0);
    }

    /**
     * Checks if the stack is empty
     * @return true if size of stack equals 0, else returns false
     */
    @Override
    public boolean isEmpty() {
        if(stack.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the size of the stack
     * @return the size of the stack
     */
    @Override
    public int size() {
        return stack.size();
    }

    /**
     * Sets the capacity of the stack
     * @param capacity the capacity the stack is being set to
     * @throws IllegalArgumentException if capacity is less than size of list
     */
    @Override
    public void setCapacity(int capacity) {
        if(capacity < stack.size()) {
            throw new IllegalArgumentException();
        }
        stack.setCapacity(capacity);
        
    }

}
