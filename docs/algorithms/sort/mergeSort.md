# 归并排序

运用分治思想，将序列分解为 n /2 的序列，递归排序序列，合并子序列得到最后的有序的序列。当序列的长度为 1 时，递归开始回升。

归并算法的关键在于合并操作。这个过程和玩扑克牌差不多，假设桌上有两堆扑克牌，每堆都已排序，最小的牌在最顶上。想要将两堆牌合并成有序的一堆可以这么操作：**比较两堆扑克牌最顶上的一张牌，将小的那张牌面朝下的放置到输出堆上**。重复这个操作直到有一堆扑克牌空了(可以使用哨兵)，然后可以直接将剩下的一堆扑克牌牌面朝下的放置到输出堆上。

每次操作只是比较顶上的牌，所以每个步骤需要的都是常量时间。最多执行 n 个步骤，所以合并需要的时间是 Θ(n) 。

## 分治思想

分治在每层递归时的步骤：

1. **分解**原问题为若干子问题，这些子问题是原问题较小的示例。

2.  **解决**这些子问题，递归地求解各个子问题。如果子问题规模足够小，则直接解决。
3.  **合并**这些子问题的解成原问题的解。



## 实现

写递归代码的技巧就是，分析得出递推公式，然后找到终止条件，最后将递推公式翻译成递归代码。

递推公式：mergeSort(p, r) = merge(mergeSort(p, q), mergeSort(q+1, r))

终止条件：p >= r 不用再继续分解



```java
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
```

