package cn.jzq.xqg.module.algorithms.sort;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author jzq
 * @date 2020-04-07
 */
public class SelectionSort {

    public static void selectionSort(int[] a) {
        // 最后一个自然就是最大值，不需要比较了
        for (int i = 0; i < a.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                ArrayUtil.swap(a, i, minIndex);
            }
        }
    }

    public static void main(String[] args) {
        int[] array = ArrayUtil.getRandIntArray(10);
        System.out.println(Arrays.toString(array));
        selectionSort(array);
        System.out.println(Arrays.toString(array));
    }
}
