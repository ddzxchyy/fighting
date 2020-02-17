package cn.jzq.xqg.module.algorithms.sort;

import java.util.Arrays;

/**
 * 插入排序
 * 插入排序和冒泡排序的时间复杂度相同，都是 O(n2)，在实际的软件开发里，为什么我们更倾向于使用插入排序算法而不是冒泡排序算法呢？
 *
 * @author 蒋正强
 * @date 2020-02-15
 */
public class InsertionSort {

    /**
     * 插入排序
     * <p>
     * 主要步骤：
     * 1. 要插入的 key 与循环不变式作比较
     * 2. 满足条件，则移动循环不变式中对应的元素
     * 3. 插入 key
     */
    public static void insertSort(int[] arr) {
        for (int i = 1; i <= arr.length - 1; i++) {
            int j = i - 1;
            int key = arr[i];
            // 循环不变式 b[0] 到 b[j], 待排序区间 c[i] 到 c[arr.length - 1]
            while (j >= 0 && key < arr[j]) {
                // 如果要插入的 key 小于与之比较的循环不变式 b[j], 则将 b[j] 后移一位
                arr[j + 1] = arr[j];
                j--;
            }
            // 插入 key
            arr[j + 1] = key;
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 5, 6, 1, 3, 2};
        insertSort(arr);
//        System.out.println(Arrays.toString(arr));
    }
}
