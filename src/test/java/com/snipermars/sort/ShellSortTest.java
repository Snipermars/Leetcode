package com.snipermars.sort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShellSortTest {
    @Test
    void testNormalArray() {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        int[] expected = {1, 1, 2, 3, 6, 8, 10};
        ShellSort.shellSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        ShellSort.shellSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        ShellSort.shellSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] arr = {5, 3, 5, 3, 1};
        int[] expected = {1, 3, 3, 5, 5};
        ShellSort.shellSort(arr);
        assertArrayEquals(expected, arr);
    }
}