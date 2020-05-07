package cn.jzq.xqg.module.algorithms.dynamic;

/**
 * 矩阵最短路径
 */
public class MinDist {

    public static void main(String[] args) {
        int[][] w = {{1, 3, 5, 9}, {2, 1, 3, 5}, {5, 2, 6, 7}, {6, 8, 4, 3}};
        minDist(0, 0, 0, w, 3);
        System.out.println(min + w[3][3]);
    }

    static int min = Integer.MAX_VALUE;

    //写出递推公式，找到终止条件
    // min_dist(i, j) = w[i][j] + min(min_dist(i, j-1), min_dist(i-1, j));
    public static void minDist(int i, int j, int dist, int[][] w, int n) {
        if (i == n  && j == n ) {
            if (dist < min) {
                min = dist;
            }
            return;
        }
        if (i < n) {
            minDist(i + 1, j, dist + w[i][j], w, n);
        }
        if (j < n) {
            minDist(i, j + 1, dist + w[i][j], w, n);
        }
    }
}
