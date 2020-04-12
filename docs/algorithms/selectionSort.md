# 选择排序

选择排序将数组分为二个部分：**已排序区间**（循环不变式）和**未排序区间**。每次循环记录未排序区间的最小值，然后与插入到循环不变式的末尾，直到未排序区间长度为 1 ，此时数组有序。

[算法演示](https://visualgo.net/en/sorting)

## 实现

```java
public static void selectionSort(int[] a) {
    // 最后一个自然就是最大值，不需要比较了
    for (int i = 0; i < a.length - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < a.length; j++) {
            if (a[j] < a[minIndex]) {
                minIndex = j;
            }
        }
        if (mainIndex != i) {
            ArrayUtil.swap(a, i, minIndex);
        }
    }
}
```



## 特点

**一、是否是原地排序？**

排序过程不需要额外的存储空间，所以空间复杂度为 O(1), 既是原地排序。



**二、是否是稳定排序？**

算法每次将最小值和循环不变式的下一个值做交换，破坏了稳定性。所以选择排序不是稳定的排序算法。



**三、时间复杂度是多少？**

都是 O(n^2)