package cn.jzq.datastructure.stack;

import java.util.HashMap;
import java.util.Map;

public class SimpleArrayStack {

    private String[] items;

    private int count;

    private int capacity;

    public SimpleArrayStack(int capacity) {
        this.items = new String[capacity];
        this.capacity = capacity;
        this.count = 0;
    }

    public boolean push(String s) {
        if (count == capacity) {
            return false;
        }
        items[count] = s;
        count++;
        return true;
    }

    public String pop() {
        if (count == 0) {
            return null;
        }
        String value = items[count - 1];
        count--;
        return value;
    }

    public  static Map<Integer, Integer> map = new HashMap<>();
    public static void main(String[] args) {
        SimpleArrayStack stack = new SimpleArrayStack(4);
        stack.push("hello");
        stack.push("world");
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        System.out.println(f2(50));
    }

    static int f(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        if (map.get(n) != null) {
            return map.get(n);
        }
        int ret = f(n - 1) + f(n - 2);
        map.put(n, ret);
        return ret ;
    }

    static int f2(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        return f2(n - 1) + f2(n - 2);
    }
}
