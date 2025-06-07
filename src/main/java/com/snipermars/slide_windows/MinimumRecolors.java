package com.snipermars.slide_windows;

public class MinimumRecolors {
    public static int minimumRecolors(String blocks, int k) {
        // 在K的限制下，分别计数黑色和白色块的个数，白色块个数表示操作数
        int n = blocks.length();
        int changes = 0;
        int min_change = 100;

        for(int i = 0; i < n; i++){
            if('W' == blocks.charAt(i)){
                changes++;
            }
            if(i + 1 < k){
                continue;
            }

            min_change = Math.min(changes, min_change);

            if('W' == blocks.charAt(i-k+1)){
                changes--;
            }
        }

        return min_change;
    }

    public static void main(String[] args) {
        String blocks = "WBBWWBBWBW";
        int k = 7;
        int result = minimumRecolors(blocks, k);
        System.out.println("Minimum number of changes: " + result);
    }
}