package cn.jzq.xqg.module.algorithms.dynamic;

import cn.hutool.crypto.SecureUtil;

public class PackageQuestion {

    public static void main(String[] args) {
        int[] a = {2, 3, 5, 7};
//        f1(0, 0, a, a.length, 13);
//        System.out.println(maxW);
        System.out.println(dyPack1(a, a.length, 13));

    }

    //存储背包中物品总重量的最大值
    public static int maxW = Integer.MIN_VALUE;

    /**
     * 每个物品只有二种选择：装或者不装。考察完所有物品或者不满足条件，
     * 返回上一个决策点，做出相反的决策
     *
     * @param i     i表示考察到哪个物品了
     * @param cw    当前已经装进去的物品的重量总和
     * @param items 每个物品的重量
     * @param n     物品个数
     * @param w     背包重量
     */
    public static void f1(int i, int cw, int[] items, int n, int w) {
        // cw==w表示装满了;i==n表示已经考察完所有的物品
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        // 选择不装
        f1(i + 1, cw, items, n, w);
        // 已经超过可以背包承受的重量的时候，就不要再装了
        if (cw + items[i] <= w) {
            // 选择装
            f1(i + 1, cw + items[i], items, n, w);
        }
    }

    /**
     * 使用动态规划实现
     *
     * @param weight
     * @param n
     * @param w
     */
    public static int dyPack1(int[] weight, int n, int w) {
        // 可达的状态
        boolean[][] states = new boolean[n][w + 1];
        // 第一个状态是可达的
        states[0][0] = true;
        for (int i = 1; i < n; i++) {
            // 不装物品
            for (int j = 0; j <= w; j++) {
                // 接着上一次的决策，继续进行下一个物品放置的决策
                boolean lastDecision = states[i - 1][j];
                if (lastDecision) {
                    // 不装的话，上一行状态就是下一行的状态
                    states[i][j] = states[i - 1][j];
                }
            }
            // 在装入第 i 个物品后，剩余的最大重量
            int remainingItemMaxWeight = w - weight[i];
            for (int j = 0; j <= remainingItemMaxWeight; j++) {
                boolean lastDecision = states[i - 1][j];
                if (lastDecision) {
                    // 在上次的达到的状态，后移第 i 个物品的重量的位置
                    states[i][weight[i] + j] = true;
                }
            }
        }
        // 最后一行就是能达到的状态集合
        for (int i = w; i > 0; i--) {
            if (states[n - 1][i]) {
                return i;
            }
        }
        return 0;
    }

    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w + 1];
        // 初始化states
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < w + 1; ++j) {
                states[i][j] = -1;
            }
        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }
        for (int i = 1; i < n; ++i) { //动态规划，状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第i个物品
                if (states[i - 1][j] >= 0) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= w - weight[i]; ++j) { // 选择第i个物品
                if (states[i - 1][j] >= 0) {
                    int v = states[i - 1][j] + value[i];
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n - 1][j] > maxvalue) maxvalue = states[n - 1][j];
        }
        return maxvalue;
    }
}
