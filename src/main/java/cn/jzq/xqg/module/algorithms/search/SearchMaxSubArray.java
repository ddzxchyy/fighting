package cn.jzq.xqg.module.algorithms.search;

/**
 * 获取最大子数组
 */
public class SearchMaxSubArray {

    public static void main(String[] args) {
        // maxSum(1, j + 1) = Max(maxSum(1, j), maxSum(i, j+ 1))
        int[] arr = new int[]{1, 2, 3, 4, 3, -10, 4, 4, 5, -20, 15};
    }

    public static int getMax(int[] a) {
        int result = Integer.MIN_VALUE;
        int sum = a[0];
        return result;
    }
}


