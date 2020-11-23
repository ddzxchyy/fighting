package cn.jzq.generalold.module.algorithms.search;


import cn.jzq.generalold.module.algorithms.sort.QuickSort;

/**
 * 二分查找
 * 针对有序序列，每次都通过跟区间的中间元素对比，将待查找的区间缩小为之前的一半，
 * 直到找到要查找的元素，或者区间被缩小为 0。
 *
 * @author jzq
 * @date 2020-04-18
 **/
public class BinarySearch {

    /**
     * 二分查找，找到返回索引，找不到返回 -1
     */
    public static Integer search(int[] a, int target) {
        if (a.length == 0) {
            return -1;
        }
        QuickSort.sort(a);
        int lowIndex = 0;
        int highIndex = a.length - 1;
        int midIndex;
        while (lowIndex <= highIndex) {
            midIndex = (highIndex + lowIndex) / 2;
            if (a[midIndex] == target) {
                return midIndex;
            } else if (a[midIndex] < target) {
                lowIndex = midIndex + 1;
            } else {
                highIndex = midIndex - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(search(a, 10));
        System.out.println(search(a, -1));
        System.out.println(search(a, 11));
    }
}
