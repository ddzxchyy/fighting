package cn.jzq.xqg.module.algorithms.sort;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 计数排序
 *
 * @author jzq
 * @date 2020-04-15
 */
public class CountingSort {

    public static void sort(int[] a) {
        int length = a.length;
        // 找出数组中最大的值
        int max = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        // 计算数组中每个元素的个数
        int[] c = new int[max + 1];
        for (int i = 0; i <= max; i++) {
            c[i] = 0;
        }
        for (int i = 0; i < a.length; i++) {
            c[a[i]]++;
        }

        // 计算数组对应每一个 i = 0, 1, 2, 3, ... k, 有多少元素是小于等于 i 的
        for (int i = 1; i <= max; i++) {
            c[i] = c[i] + c[i - 1];
        }
//        System.out.println(Arrays.toString(c));
        int[] b = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            int index = c[a[i]] - 1;
            b[index] = a[i];
            c[a[i]]--;
        }
        for (int i = 0; i < length; i++) {
            a[i] = b[i];
        }
    }

    public static void main(String[] args) {
        int[] a = ArrayUtil.getRandIntArray(10);
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
