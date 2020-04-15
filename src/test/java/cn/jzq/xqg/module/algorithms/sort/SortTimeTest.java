package cn.jzq.xqg.module.algorithms.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static cn.jzq.xqg.module.algorithms.sort.ArrayUtil.getRandIntArray;
import static java.lang.System.currentTimeMillis;

@Slf4j
public class SortTimeTest {

    @Test
    public void timeTest() {
        int length = 10_000_000;
//        int[] a  = getRandIntArray(length);
//        long timeAS = currentTimeMillis();
//        InsertionSort.insertSort(a);
//        long timeAE = currentTimeMillis();
//        System.out.println("插入排序耗时:" + (timeAE - timeAS));

        int[] b = getRandIntArray(length);
        long timeBS = currentTimeMillis();
        MergeSort.sort(b);
        long timeBE = currentTimeMillis();
        System.out.println("归并排序耗时:" + (timeBE - timeBS));

//        int[] c = getRandIntArray(length);
//        long timeCS = currentTimeMillis();
//        QuickSort.sort(c);
//        long timeCE = currentTimeMillis();
//        System.out.println("快速排序耗时:" + (timeCE - timeCS));

        int[] d = getRandIntArray(length);
        long timeDS = currentTimeMillis();
        CountingSort.sort(d);
        long timeDE = currentTimeMillis();
        System.out.println("计数排序耗时:" + (timeDE - timeDS));
    }

}
