package cn.jzq.generalold.module.algorithms.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * 2021-01-02 重新实现
 */
public class MergeSort {

    public static void sort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    public static void mergeSort(int[] array, int startIndex, int lastIndex) {
        if (startIndex < lastIndex) {
            int midIndex = (startIndex + lastIndex) / 2;
            mergeSort(array, startIndex, midIndex);
            mergeSort(array, midIndex + 1, lastIndex);
            merge(array, startIndex, midIndex, lastIndex);
        }
    }

    /**
     * 比较两堆扑克牌最顶上的一张牌，将小的那张牌面朝下的放置到输出堆上
     */
    public static void merge(int[] array, int startIndex, int midIndex, int lastIndex) {
        // lastIndex - midIndex + (midIndex - startIndex + 1) 才等于 array.length
        // java 的除法是向下取整，+1 保证左边不为 0
        int leftArraySize = midIndex - startIndex + 1;
        int rightArraySize = lastIndex - midIndex;
        // 为了存放哨兵 加1
        int[] leftArray = new int[leftArraySize + 1];
        int[] rightArray = new int[rightArraySize + 1];
        // 将数组的划分为 2 个子数组
        for (int i = 0; i < leftArraySize; i++) {
            leftArray[i] = array[startIndex + i];
        }
        for (int i = 0; i < rightArraySize; i++) {
            // midIndex 是左边数组的最后一个索引
            rightArray[i] = array[midIndex + 1 + i];
        }
        leftArray[leftArraySize] = Integer.MAX_VALUE;
        rightArray[rightArraySize] = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        for (int k = startIndex; k <= lastIndex; k++) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = ArrayUtil.getRandIntArray(8);
        System.out.println(Arrays.toString(array));
        sort(array);
        System.out.println(Arrays.toString(array));

       String s = new String("123");
        String ss = new String("123");
        String sss = new String("123");
        System.out.println(s == ss);
        System.out.println(sss == ss);
    }

}
