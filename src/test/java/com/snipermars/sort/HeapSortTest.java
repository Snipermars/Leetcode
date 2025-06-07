package com.snipermars.sort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {
    @Test
    public void testNormalArray() {
        int[] arr = {4, 10, 3, 5, 1};
        int[] expected = {1, 3, 4, 5, 10};
        HeapSort.heapSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        HeapSort.heapSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        HeapSort.heapSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testArrayWithDuplicates() {
        int[] arr = {5, 3, 5, 1, 3};
        int[] expected = {1, 3, 3, 5, 5};
        HeapSort.heapSort(arr);
        assertArrayEquals(expected, arr);
    }
}