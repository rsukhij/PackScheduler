package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayStackTest {

    /**
     * Test for push() method
     */
    @Test
    public void testPush() {
        ArrayStack<Integer> a = new ArrayStack<Integer>();
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
        ArrayStack<Integer> a = new ArrayStack<Integer>();
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
        ArrayStack<Integer> a = new ArrayStack<Integer>();
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
        ArrayStack<Integer> a = new ArrayStack<Integer>();
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
        ArrayStack<Integer> a = new ArrayStack<Integer>();
        a.setCapacity(5);
        a.push(0);
        a.push(1);
        a.push(2);
        a.push(3);
        a.push(4);
        try {
            a.setCapacity(4);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals(5, a.size());
        }
    }

}
