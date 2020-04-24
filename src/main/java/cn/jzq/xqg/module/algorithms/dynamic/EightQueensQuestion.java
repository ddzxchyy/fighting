package cn.jzq.xqg.module.algorithms.dynamic;

/**
 * 八皇后问题
 * 有冲突解决冲突，没有冲突往前走，无路可走往回退，走到最后是答案。
 * 第一步按照顺序放一个皇后，然后第二步符合要求放第 2 个皇后，如果没有位置符合要求，
 * 那么就要改变第一个皇后的位置，重新放第 2 个皇后的位置，直到找到符合条件的位置就可以了。
 * 回溯算法
 */
public class EightQueensQuestion {

    public static void main(String[] args) {
        calRowQueen(0);
        System.out.println(count);
    }

    static int[] result = new int[8];
    static int count = 0;

    public static void calRowQueen(int row) {
        if (row == 8) {
            printQueens(result);
            return;
        }
        for (int column = 0; column < 8; column++) {
            // 满足条件，计算下一行，递归回来的时候，进行 column + 1 计算
            if (isOk(row, column)) {
                // 把 queen 放置在 row 行的第 column 列上
                result[row] = column;
                // 计算下一行
                calRowQueen(row + 1);
            }
        }
    }

    /**
     * 在 8×8 格的国际象棋上摆放八个皇后，使其不能互相攻击，即任意两个皇后都不能处于同一行、同一列或同一斜线上
     * result[i] 存储的是棋子所在的列
     */
    private static boolean isOk(int row, int column) {
        int leftUp = column - 1;
        int rightUp = column + 1;
        // 考察上面的几行，是否满足条件
        for (int i = row - 1; i >= 0; i--) {
            // 第 i 行上是否有其它棋子
            if (result[i] == column) return false;
            // 左上角是否有棋子
            if (leftUp >= 0) {
                if (result[i] == leftUp) return false;
            }
            // 右上角是否有棋子
            if (rightUp < 8) {
                if (result[i] == rightUp) return false;
            }
            // 下一轮到了上一行，左上角要 -1, 右上角 +1
            leftUp--;
            rightUp++;
        }
        return true;
    }

    private static void printQueens(int[] result) { // 打印出一个二维矩阵
        count++;
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                if (result[row] == column) System.out.print("Q ");
                else System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
