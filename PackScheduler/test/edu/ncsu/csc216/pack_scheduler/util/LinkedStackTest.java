package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedStackTest {
    /**
     * Test for push() method
     */
    @Test
    public void testPush() {
        LinkedStack<Integer> a = new LinkedStack<Integer>();
        assertEquals(0, a.size());
        a.push(1);
        assertEquals(1, a.size());
        a.setCapacity(2);
        a.push(0);
        try {
            a.push(0);
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
        LinkedStack<E> a = new LinkedStack<E>();
        assertEquals(0, a.size());
        a.push(1);
        assertEquals(1, a.size());
        assertEquals(1, a.pop());
        assertEquals(0, a.size());
    }

    /**
     * Test for isEmpty() method
     */
    @Test
    public void testIsEmpty() {
        LinkedStack<Integer> a = new LinkedStack<Integer>();
        assertTrue(a.isEmpty());
        a.push(1);
        assertFalse(a.isEmpty());
        a.pop();
        assertTrue(a.isEmpty());
    }

    /**
     * Test for size() method
     */
    @Test
    public void testSize() {
        LinkedStack<Integer> a = new LinkedStack<Integer>();
        assertEquals(0, a.size());
        a.push(0);
        assertEquals(1, a.size());
        a.push(1);
        assertEquals(2, a.size());
        a.pop();
        assertEquals(1, a.size());
    }

    /**
     * Test for setCapacity() method
     */
    @Test
    public void testSetCapacity() {
        LinkedStack<Integer> a = new LinkedStack<Integer>();
        a.setCapacity(5);
        a.push(0);
        a.push(0);
        a.push(0);
        a.push(0);
        a.push(0);
        try {
            a.setCapacity(4);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals(5, a.size());
        }
    }

}
