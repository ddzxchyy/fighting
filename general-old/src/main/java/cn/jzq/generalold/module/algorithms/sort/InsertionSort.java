package cn.jzq.generalold.module.algorithms.sort;

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
    public static void insertSort(int[] a) {
        int lastIndex = a.length - 1;
        // 刚开始循环不变式为 [a[0]], 待排序区间为 [a[1], a[lastIndex]]
        for (int i = 1; i <= lastIndex; i++) {
            int j = i - 1;
            int key = a[i];
            // 循环不变式为 [a[0], a[j]], 待排序区间 a[[i], a[lastIndex]]
            while (j >= 0 && key < a[j]) {
                // 如果要插入的 key 小于与之比较的循环不变式值 a[j], 则将 a[j] 后移一位
                // 第一次移动时，a[j+1] 的值等同于 key，已经被记录下来了
                // 最后把 key 插入到应在在的位置就可以了
                a[j + 1] = a[j];
                // j-- key 与 循环不变式的前一个值做比较
                j--;
            }
            // 插入 key
            a[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] arr = ArrayUtil.getRandIntArray(100_000);
        long startTime = System.currentTimeMillis();
        insertSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
