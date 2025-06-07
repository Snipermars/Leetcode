package com.snipermars.dynamic_program;

public class TryFibonacci{

    public static int tryFibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return tryFibonacci(n - 1) + tryFibonacci(n - 2);
        }
    }

    // 额外空间复杂度为O(n)
    public static int tryFibonacci2(int n) {
        if (n <= 1) {
            return n;
        } else {
            int[] fib = new int[n + 1];
            fib[0] = 0;
            fib[1] = 1;
            for (int i = 2; i <= n; i++) {
                fib[i] = fib[i - 1] + fib[i - 2];
            }
            return fib[n];
        }
    }

    public static int tryFibonacci3(int n) {
        if (n <= 1) {
            return n;
        } else {
            int a = 0;
            int b = 1;
            int c = 0;
            for (int i = 2; i <= n; i++) {
                c = a + b;
                a = b;
                b = c;
            }
            return c;
        }
    }

    public static void main(String[] args) {
        int n = 10; // 计算第10个斐波那契数
        int result = tryFibonacci(n);
        System.out.println("第 " + n + " 个斐波那契数是：" + result);
        int result2 = tryFibonacci2(n);
        System.out.println("第 " + n + " 个斐波那契数是：" + result2);
        int result3 = tryFibonacci3(n);
        System.out.println("第 " + n + " 个斐波那契数是：" + result3);
    }
}