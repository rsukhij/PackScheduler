package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;


import org.junit.Test;

/**
 * Class to test the LinkedStack class
 * @author Raymond Dong
 *
 * @param <E> the generic element type
 */
public class LinkedStackTest<E> {
	
    /**
     * Test for push() method
     */
    @Test
    public void testPush() {
        LinkedStack<String> a = new LinkedStack<String>(10);
        assertEquals(0, a.size());
        a.push("a");
        assertEquals(1, a.size());
        a.setCapacity(2);
        a.push("b");
        try {
            a.push("c");
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals(2, a.size());
        }
    }

    /**
     * Test for pop() method
     */
    @Test
    public void testPop() {
        LinkedStack<String> a = new LinkedStack<String>(10);
        assertEquals(0, a.size());
        a.push("a");
        assertEquals(1, a.size());
        assertEquals("a", a.pop());
        assertEquals(0, a.size());
    }

    /**
     * Test for isEmpty() method
     */
    @Test
    public void testIsEmpty() {
        LinkedStack<String> a = new LinkedStack<String>(10);
        assertTrue(a.isEmpty());
        a.push("a");
        assertFalse(a.isEmpty());
        a.pop();
        assertTrue(a.isEmpty());
    }

    /**
     * Test for size() method
     */
    @Test
    public void testSize() {
        LinkedStack<String> a = new LinkedStack<String>(10);
        assertEquals(0, a.size());
        a.push("a");
        assertEquals(1, a.size());
        a.push("b");
        assertEquals(2, a.size());
        a.pop();
        assertEquals(1, a.size());
    }

    /**
     * Test for setCapacity() method
     */
    @Test
    public void testSetCapacity() {
        LinkedStack<String> a = new LinkedStack<String>(10);
        a.setCapacity(5);
        a.push("a");
        a.push("b");
        a.push("c");
        a.push("d");
        a.push("e");
        try {
            a.setCapacity(4);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals(5, a.size());
        }
    }

}
