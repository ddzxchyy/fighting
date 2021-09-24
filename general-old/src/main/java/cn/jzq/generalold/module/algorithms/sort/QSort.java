package cn.jzq.generalold.module.algorithms.sort;

import java.util.Arrays;

/**
 * 2020-01-03 实现快排
 * @author jzq
 */
public class QSort {

    public static void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int p, int r) {
        if (p < r) {
            int q = partition(array, p, r);
            quickSort(array, p, q - 1);
            quickSort(array, q + 1, r);
        }
    }

    private static int partition(int[] array, int p, int r) {
        int j = p - 1;
        int pivot = array[r];
        for (int i = p; i < r; i++) {
            if (array[i] <= pivot) {
                j++;
                ArrayUtil.swap(array, j, i);
            }
        }
        ArrayUtil.swap(array, j + 1, r);

        return j + 1;
    }

    public static void main(String[] args) {
        int[] a = ArrayUtil.getRandIntArray(8);
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
