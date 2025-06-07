package com.snipermars.sort;

public class QuickSort {

    public static void quickSort(int[] arr){
        sort(arr, 0, arr.length-1);
    }

    private static void sort(int[] arr, int left, int right){
        if(left >= right){
            return;
        }
        int pivot = partition(arr, left, right);
        sort(arr, left, pivot-1);
        sort(arr, pivot + 1, right);
    }

    private static int partition(int[] arr, int left, int right){
        int idx = left+1;
        for(int i = idx; i <= right; i++){
            if(arr[i] < arr[left]){
                swap(arr, i, idx++);
            }
        }
        swap(arr, left, idx-1);

        return idx-1;
    }

    private static void swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        quickSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
    }

}
