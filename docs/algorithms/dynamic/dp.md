# 动态规划

## 钢条切割问题

| 长度 i             | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   |
| ------------------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| 收益 p<sub>i</sub> | 1    | 5    | 8    | 9    | 10   | 17   | 17   | 20   | 24   | 30   |

观察所有最优收益以及切割方案:

r1 = 1, 切割方案 1 = 1 + 0

r2 = 5, 切割方案 2 = 2 + 0

r3 = 8, 切割方案 3 = 3 + 0

r4 = 10, 切割方案 4 = 2 + 2

r5 = 13, 切割方案 5 = 2 + 3

r6 = 17, 切割方案 6 = 6 + 0

r7 = 18, 切割方案 7 = 1 + 6 或  7 = 2 + 2 + 3

r8 = 22, 切割方案 8 = 2 + 6

r9 = 25, 切割方案 9 = 3 + 6

r10 = 30, 切割方案 10 = 10 + 0

更一般地，对于 r<sub>n</sub>，可以用更短的钢条切割的最优切割来描述它：

​				r<sub>n</sub> = max(p<sub>n</sub>, r<sub>1</sub> + r<sub>n-1</sub> , r<sub>2</sub> + r<sub>n - 2</sub> ,,,  r<sub>n - 1</sub> + r<sub>1></sub>)

更简单的递归求解方法：将钢条从左边切割长度为 i 的一段， 只对右边剩下的 n - i 的一段继续进行切割，对左边的一段不在进行切割。

​				r<sub>n</sub> = for(i to n) { max(p<sub>i</sub> + r<sub>n - i</sub>) }

**实现**

```java
  /**
     * 钢条切割
     * @param p 价格
     * @param n 钢条长度
     * @return 最大收益
     */
    public static int cutRod(int[] p, int n) {
        if (n == 0) {
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            q = Math.max(q, p[i] + cutRod(p, n - i - 1));
        }
        return q;
    }
```

这个算法的效率非常差，因为它重复地用相同的参数进行递归调用，递归树中有 2<sup>n - 1</sup> 个叶节点

