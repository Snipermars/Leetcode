package com.snipermars.algorithm;

public class DeleteAndGet {
    // https://leetcode.cn/problems/delete-and-earn/
    public int deleteAndGet(int[] nums){
        int mx = 0;
        for(int x: nums){
            mx = Math.max(mx, x);
        }

        // 值域数组构建
        int[] a = new int[mx + 1];
        for(int x: nums){
            a[x] += x;
        } 

        return rob(a);
        
    } 

    private int rob(int[] nums){
        int f0 = 0;
        int f1 = 0;
        for(int x: nums){
            int newF = Math.max(f1, f0 + x);
            f0 = f1;
            f1 = newF;
        }

        return f1;
    }
}
