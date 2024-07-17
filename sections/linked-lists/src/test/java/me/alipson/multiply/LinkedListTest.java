package me.alipson.multiply;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LinkedListTest
 */
public class LinkedListTest {

    @Test
    public void removeFirstTest() {
        LinkedList<Integer> list1 = new LinkedList<>(1, 3, 4, 5);
        LinkedList<Integer> list2 = new LinkedList<>(1, 4, 5);
        list1.removeFirst(3);
        assertEquals(list2, list1);
    }

    @Test
    public void reduceTest() {
        LinkedList<Integer> empty = new LinkedList<>();
        LinkedList<Integer> list = new LinkedList<>(1, 2, 3, 4);

        Monoid<Integer> multiplication = new IntegerMultiplicationMonoid();

        assertEquals(multiplication.identity(), empty.reduce(multiplication));
        assertEquals(24, list.reduce(multiplication));
    }

    @Test
    public void mapTest() {
        LinkedList<Integer> empty = new LinkedList<>();
        LinkedList<Integer> list1 = new LinkedList<>(1, 2, 3, 4);
        LinkedList<Integer> list2 = new LinkedList<>(2, 4, 6, 8);

        Map<Integer, Integer> doubleScale = new IntegerScaleMap();

        empty.map(doubleScale);
        list1.map(doubleScale);

        assertEquals(new LinkedList<Integer>(), empty);
        assertEquals(list2, list1);
    }

    @Test
    public void zipTest() {
        LinkedList<Integer> empty = new LinkedList<>();
        LinkedList<Integer> list1 = new LinkedList<>(1, 2, 3, 4);
        LinkedList<Integer> list1copy = new LinkedList<>(1, 2, 3, 4);
        LinkedList<Integer> list2 = new LinkedList<>(1, 4, 9, 16);

        Monoid<Integer> multiplication = new IntegerMultiplicationMonoid();

        list1.zip(multiplication, empty);

        assertEquals(list1copy, list1);

        list1.zip(multiplication, list1); // will this work

        assertEquals(list2, list1);

    }

}
