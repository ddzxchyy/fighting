package cn.jzq.xqg.module.algorithms.sort;

import cn.hutool.core.util.RandomUtil;


/**
 * 冒泡排序
 *
 * @author jzq
 * @date 2020-02-18
 */
public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        int arrLength = arr.length;
        /*
         * 有标志位 10K 条数据耗时在 130 ms 左右, 无标志位平均耗时 180 ms。
         */
        boolean isNoSwapped = true;
        for (int i = 0; i < arrLength; i++) {
            int sortedArrLength = arrLength - i - 1;
            isNoSwapped = true;
            for (int j = 0; j < sortedArrLength; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    isNoSwapped = false;
                }
            }
            if (isNoSwapped) {
                return;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int[] getRandIntArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = RandomUtil.randomInt(1, 1000);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = getRandIntArray(100_000);
        long startTime = System.currentTimeMillis();
        bubbleSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
