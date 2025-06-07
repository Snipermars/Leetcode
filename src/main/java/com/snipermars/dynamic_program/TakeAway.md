# 动态规划

## 核心三要素
状态定义：用dp[i][j]表达问题本质（字节跳动面试官最爱追问）
转移方程：状态间的数学关系式（90%考生在此翻车）
边界处理：初始化与终止条件（美团真题高频扣分项）

## 解题步骤
1. 定义状态：dp[i]表示什么
2. 转移方程：dp[i] = dp[i-1] + dp[i-2]
3. 边界处理：dp[0] = 1, dp[1] = 1
## 解题模板
```java
public int solve(int n) {
    int[] dp = new int[n+1];
    dp[0] = 1;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2];
    }
    return dp[n];
}
```

## 从记忆搜索到递推的演变
记忆搜索：
```java
public int solve(int n) {
    int[] memo = new int[n+1];
    return dfs(n, memo);
}
```
递推：
```java
public int solve(int n) {
    int[] dp = new int[n+1];
    dp[0] = 1;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2];
    }
    return dp[n];
}
```

## 典型例题深入理解
### 1. 爬楼梯
状态定义：dp[i]表示爬到第i级楼梯的方法数。
转移方程：dp[i] = dp[i-1] + dp[i-2]，因为每次可以爬1级或2级。
边界处理：dp[0] = 1, dp[1] = 1。
### 2. 不同路径
状态定义：dp[i][j]表示从左上角到(i, j)位置的不同路径数。
转移方程：dp[i][j] = dp[i-1][j] + dp[i][j-1]，因为只能向下或向右走。
边界处理：dp[0][j] = 1, dp[i][0] = 1。

