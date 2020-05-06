package cn.jzq.xqg.module.algorithms.dynamic;

public class PackageQuestion {

    public static void main(String[] args) {
        int[] a = {2, 3, 5, 7};
        f(0, 0, a, a.length, 13);
        System.out.println(maxW);
    }

    //存储背包中物品总重量的最大值
    public static int maxW = Integer.MIN_VALUE;

    /**
     * 每个物品只有二种选择：装或者不装。考察完所有物品或者不满足条件，
     * 返回上一个决策点，做出相反的决策
     * @param i     i表示考察到哪个物品了
     * @param cw    当前已经装进去的物品的重量总和
     * @param items 每个物品的重量
     * @param n     物品个数
     * @param w     背包重量
     */
    public static void f(int i, int cw, int[] items, int n, int w) {
        // cw==w表示装满了;i==n表示已经考察完所有的物品
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        // 选择不装
        f(i + 1, cw, items, n, w);
        // 已经超过可以背包承受的重量的时候，就不要再装了
        if (cw + items[i] <= w) {
            // 选择装
            f(i + 1, cw + items[i], items, n, w);
        }
    }
}
