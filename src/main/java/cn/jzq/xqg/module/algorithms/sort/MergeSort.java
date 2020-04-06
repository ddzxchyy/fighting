package cn.jzq.xqg.module.algorithms.sort;

/**
 * 归并排序
 *
 * @author jzq
 * @date 2020-04-01
 */
public class MergeSort {

    public static void sort(int[] a) {

    }

    private static void mergeSort(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }
        int q = (p + r) / 2;
        mergeSort(a, p, q);
        mergeSort(a, q + 1, r);
//        merge();
    }

    private static void merge(int[] a, int p, int q, int r) {
        int leftArrayLength = q - p + 1;
        int rightArrayLength = r - q;
        int[] leftArray = new int[leftArrayLength];
        int[] rightArray = new int[rightArrayLength];
        for (int i = 0; i < leftArrayLength; i++) {
            leftArray[i] = a[p + i - 1];
        }
        for (int j = 0; j < rightArrayLength; j++) {
            rightArray[j] = a[q + j];
        }
    }
}
