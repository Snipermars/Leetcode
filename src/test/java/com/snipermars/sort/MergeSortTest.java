package com.snipermars.sort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    @Test
    void testNormalArray() {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        int[] expected = {1, 1, 2, 3, 6, 8, 10};
        int[] res = MergeSort.mergeSort(arr);
        assertArrayEquals(expected, res);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        int[] res = MergeSort.mergeSort(arr);
        assertArrayEquals(expected, res);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        int[] res = MergeSort.mergeSort(arr);
        assertArrayEquals(expected, res);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] arr = {5, 3, 8, 3, 1, 5};
        int[] expected = {1, 3, 3, 5, 5, 8};
        int[] res = MergeSort.mergeSort(arr);
        assertArrayEquals(expected, res);
    }
}