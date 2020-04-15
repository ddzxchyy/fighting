# 冒泡排序

选择排序将数组分为二个部分：**已排序区间**（循环不变式）和**未排序区间**。每次循环记录未排序区间的最小值，然后与插入到循环不变式的末尾，直到未排序区间长度为 1 ，此时数组有序。

[算法演示](https://visualgo.net/en/sorting)

## 实现

```java
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
```



## 特点

**一、是否是原地排序？**

排序过程不需要额外的存储空间，所以空间复杂度为 O(1), 既是原地排序。



**二、是否是稳定排序？**

算法每次将最小值和循环不变式的下一个值做交换，破坏了稳定性。所以选择排序不是稳定的排序算法。

