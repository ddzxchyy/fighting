package cn.jzq.xqg.module.algorithms.dynamic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 根据坐标计算出最短路径
 */
public class MinDist2 {

    public static int min = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] xy = new int[10];
        while (in.hasNextInt()) {
            for (int i = 0; i < 10; i++) {
                xy[i] = in.nextInt();
            }
            Queue<Integer> queue = new LinkedList<>();
            help(0, xy, queue);
            System.out.println(min);
        }

    }

    public static void help(int i, int[] xy, Queue<Integer> queue) {
        if (i >= xy.length - 2) {
            int x = 0, y = 0;
            double tempSum = 0.;
            queue.offer(xy[i]);
            queue.offer(xy[i + 1]);
            int cnt = 5;
            while (!queue.isEmpty()) {
                int xx = queue.peek();
                System.out.print("(" + xx + ",");
                queue.poll();
                int yy = queue.peek();
                System.out.print(yy + "),");
                queue.poll();
                tempSum += Math.sqrt((xx - x) * (xx - x) + (yy - y) * (yy - y));
                x = xx;
                y = yy;
            }
            tempSum += Math.sqrt((0 - x) * (0 - x) + (0 - y) * (0 - y));
            System.out.println("sum=" + (int) tempSum);
            if ((int) tempSum < min) {
                min = (int) tempSum;
                return;
            }
        } else {
            for (int j = i; j < xy.length; j += 2) {
                swap(i, j, xy);
                swap(i + 1, j + 1, xy);
                Queue copyQueue = new LinkedList();
                copyQueue.addAll(queue);
                copyQueue.offer(xy[i]);
                copyQueue.offer(xy[i + 1]);
                help(i + 2, xy, copyQueue);
                swap(i, j, xy);
                swap(i + 1, j + 1, xy);
            }
        }
    }

    public static void swap(int i, int j, int[] xy) {
        int temp = xy[i];
        xy[i] = xy[j];
        xy[j] = temp;
    }
}
