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

    /**
     * 比较两堆扑克牌最顶上的一张牌，将小的那张牌面朝下的放置到输出堆上
     */
    private static void merge(int[] a, int p, int q, int r) {
        int leftArrayLength = q - p + 1;
        int rightArrayLength = r - q;
        int[] leftArray = new int[leftArrayLength + 1];
        int[] rightArray = new int[rightArrayLength + 1];
        for (int i = 0; i < leftArrayLength; i++) {
            leftArray[i] = a[p + i - 1];
        }
        for (int j = 0; j < rightArrayLength; j++) {
            rightArray[j] = a[q + j];
        }
        leftArray[leftArrayLength] = Integer.MAX_VALUE;
        rightArray[rightArrayLength] = Integer.MAX_VALUE;
        // 比较左右数组，将较大值赋给数组a
        int i = 0;
        int j = 0;
        for (int k = p; k < r; k++) {
            if (leftArray[i] <= rightArray[j]) {
                a[k] = leftArray[i];
                i++;
            } else {
                a[k] = rightArray[j];
                j++;
            }
        }
    }
}
