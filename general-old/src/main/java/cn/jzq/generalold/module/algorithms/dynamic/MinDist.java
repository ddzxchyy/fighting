package cn.jzq.generalold.module.algorithms.dynamic;

/**
 * 矩阵最短路径
 * 经过最后一个点，必然会经过它左边的点或者上边的点，
 * 这样问题的解变成了：最后一个点的距离 + min(左边点经历的距离, 上边点经历的距离)
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
        if (i == n && j == n) {
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

    public static int dynamicMinDist(int[][] matrix, int n) {
        int sum = 0;
        int[][] states = new int[n][n];
        // 初始化第一行第一列的数据
        if (n >= 0) System.arraycopy(matrix[0], 0, states[0], 0, n);
        for (int i = 0; i < n; i++) {
            states[i][0] = matrix[i][0];
        }
        // 初始化第一行第一列的数据
        // 初始化第一行第一列的数据
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < n; ++j) {
                states[i][j] = matrix[i][j] + Math.min(states[i][j - 1], states[i - 1][j]);
            }
        }
        return sum;
    }


}
