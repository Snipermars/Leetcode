package com.snipermars.algorithm;

import java.util.*;

public class MaxiumTotalDamage {

    private static long dfs(int[] a, Map<Integer, Integer> cnt, long[] memo, int i){
        if( i < 0){
            return 0;
        }

        if(memo[i] != -1){
            return memo[i];
        }

        int x = a[i];
        int j = i;
        while(j > 0 && a[j-1] >= x - 2 ){ // 补充咒语限制
            j--;
        }
        return memo[i] = Math.max(dfs(a, cnt, memo, i - 1), dfs(a, cnt, memo, j-1) + (long) x * cnt.get(x));
    }

    public long maxiumTotalDamage(int[] power) {
        // https://leetcode.cn/problems/maximum-total-damage-with-spell-casting/submissions/626280074/
        // 构建值域集合
        Map<Integer, Integer> cnt = new HashMap<>();
        for(int x:power){
            cnt.merge(x, 1, Integer::sum);
        }

        // 值域数组的构建
        // 之前基于值 = 索引的构建方式不可行，会占用大量内存，超出限制
        // 所以现在基于Map处理后后续在记忆化搜索中添加条件判断
        int n = cnt.size();
        int[] a = new int[n];
        int k = 0;
        for(int x: cnt.keySet()){
            a[k++] = x;
        }
        Arrays.sort(a);

        long[] memo = new long[n];
        Arrays.fill(memo, -1);
        return dfs(a, cnt, memo, n-1);
    }
    
}
