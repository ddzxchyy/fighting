package cn.jzq.xqg.module.algorithms.sort;

import java.util.Arrays;

/**
 * 快速排序
 *
 * @author jzq
 * @date 2020-04-12
 */
public class QuickSort {

    public static void sort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    private static void quickSort(int[] a, int p, int r) {
        if (p < r) {
            int q = partition(a, p, r);
            quickSort(a, p, q - 1);
            quickSort(a, q + 1, r);
        }
    }

    /**
     * 获取分区点
     * partition 方法需要实现以下功能：
     * 1. 返回主元索引，即下一次快排数组的最后索引
     * 2. 遍历数组，将数组分为小于主元的部分a[p, i]和大于主元的部分 a[i + 1,j]
     * 3. 将主元交换合适的位置
     **/
    private static int partition(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (a[j] <= pivot) {
                i++;
                ArrayUtil.swap(a, i, j);
            }
        }
        ArrayUtil.swap(a, i + 1, r);
        return i + 1;
    }

    public static void main(String[] args) {
        int[] a = ArrayUtil.getRandIntArray(8);
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
