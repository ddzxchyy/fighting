package cn.jzq.xqg.module.algorithms.search;

public class BinarySearchAdvance {

    public int search(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] == value) {
                return mid;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 找到数组中第一个元素是 value 的索引
     * 如果找到了 value， 则和找到的前一位做比较是否相等，不相等说明这个索引就是最终答案，否则继续查找
     * 找到数组中最后一个元素等于给定值的算法，思想也一样。
     */
    public static int binarySearch(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == 0) || (a[mid - 1] != value)) return mid;
                else high = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 6, 6, 7, 8};
        int index = binarySearch(a, a.length, 6);
        System.out.println(index);

    }


}
