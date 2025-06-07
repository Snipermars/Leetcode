package com.snipermars.sort;

import java.util.Arrays;

public class MergeSort {
    public static int[] mergeSort(int[] arr) {
        int len = arr.length;
        if (len < 2) {
            return arr;
        }

        int mIdx = len / 2;
        return merge(mergeSort(Arrays.copyOfRange(arr, 0, mIdx)), mergeSort(Arrays.copyOfRange(arr, mIdx, len)));
    }

    private static int[] merge(int[] arrLeft, int[] arrRight) {
        int leftLen = arrLeft.length, rightLen = arrRight.length, leftIdx = 0, rightIdx = 0, idx = 0;
        int[] result = new int[leftLen + rightLen];
        while (leftIdx < leftLen && rightIdx < rightLen) {
            if (arrLeft[leftIdx] < arrRight[rightIdx]) {
                result[idx++] = arrLeft[leftIdx++];
            } else {
                result[idx++] = arrRight[rightIdx++];
            }
        }
        while (leftIdx < leftLen) {
            result[idx++] = arrLeft[leftIdx++];
        }
        while (rightIdx < rightLen) {
            result[idx++] = arrRight[rightIdx++];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        int[] res = mergeSort(arr);
        for(int i = 0; i < res.length; i++){
            System.out.print(res[i] + " ");
        }
    }
}
