package com.snipermars.sort;

public class HeapSort {
    private static int heaplen;

    public static void heapSort(int[] arr){
        heaplen = arr.length;
        for(int i = heaplen - 1; i >= 0; i--){
            heapify(arr, i); 
        }

        for(int i = heaplen - 1; i > 0; i--){
            swap(arr, 0, heaplen - 1);
            heaplen--;
            heapify(arr, 0);
        }
    }

    private static void heapify(int[] arr, int idx){
        int left = idx * 2 + 1;
        int right = idx * 2 + 2;
        int largest = idx;
        if(left < heaplen && arr[left] > arr[largest]){
            largest = left;
        }
        if(right < heaplen && arr[right] > arr[largest]){
            largest = right;
        }
        if(largest != idx){
            swap(arr, largest, idx);
            heapify(arr, largest);
        }
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        heapSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
    }
}
