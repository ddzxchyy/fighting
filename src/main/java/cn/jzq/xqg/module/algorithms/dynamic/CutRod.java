package cn.jzq.xqg.module.algorithms.dynamic;

/**
 * 切割钢条
 */
public class CutRod {

    public static void main(String[] args) {
        int[] p = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
//        cutRod(p, 4);
        System.out.println( bottomUpCutRod(p, 4));
    }

    /**
     * @param p 价格
     * @param n 钢条长度
     * @return 最大收益
     */
    public static int cutRod(int[] p, int n) {
        if (n == 0) {
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            q = Math.max(q, p[i] + cutRod(p, n - i - 1));
        }
        System.out.println(q);
        return q;
    }


    public static int bottomUpCutRod(int[] p, int n) {
        // 子问题的解
        int[] r = new int[n + 1];
        // 长度为 0 的钢条没有收益
        r[0] = 0;
        // 升序求解规模为 i 的子问题
        for (int i = 1; i <= n; i++) {
            int q = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                // 直接获取规模为 i - j 的子问题的解
                q = Math.max(q, p[j - 1] + r[i - j]);
            }
            // 保存子问题的解
            r[i] = q;
        }
        // 返回最优解
        return r[n];
    }
}
