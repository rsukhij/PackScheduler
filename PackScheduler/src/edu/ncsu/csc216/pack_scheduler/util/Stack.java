/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Stack interface for implementing with a list. Elements are added and removed
 * from the same end of the list.
 * 
 * @author Ethan Taylor
 * @author Raymond Dong
 * @param <E>
 *
 */
public interface Stack<E> {
    /**
     * Puts an element onto the stack
     * @param element the element being put on the stack
     */
    void push(E element);
    
    /**
     * Takes an element off of the stack
     * @return the element taken off of the stack
     */
    E pop();
    
    /**
     * Checks to see if the stack is empty
     * @return true if stack is empty, else returns false
     */
    boolean isEmpty();
    
    /**
     * Returns the size of the stack
     * @return size of stack
     */
    int size();
    
    /**
     * Sets the capacity of the stack
     * @param capacity the capacity the stack is being set to
     */
    void setCapacity(int capacity);
}
