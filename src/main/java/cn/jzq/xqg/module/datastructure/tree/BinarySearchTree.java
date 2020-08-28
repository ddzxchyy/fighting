package cn.jzq.xqg.module.datastructure.tree;

import lombok.Data;

import java.util.Hashtable;
import java.util.Objects;

/**
 *
 */
public class BinarySearchTree {
    private Node tree;

    public Node find(int data) {
        Node p = tree;
        while (p != null) {
            if (data < p.data) {
                p = p.left;
            } else if (data > p.data) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }


    public void insert(int data) {
        if (tree == null) {
            tree = new Node(data);
            return;
        }
        Node p = tree;
        while (true) {
            if (data > p.data) {
                // 没有就插入
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                // tree 没有变化
                p = p.right;
            } else { // data < p.data
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }
        }
    }

    /**
     * 节点
     */
    public static class Node {
        private int data;

        private Node left;

        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Node root = new Node(33);
        Node right1 = new Node(50);
        Node right2 = new Node(58);
        Node right3 = new Node(34);
        Node left1 = new Node(17);
        root.left = left1;
        root.right = right1;
        BinarySearchTree tree = new BinarySearchTree();
        tree.tree = root;
        tree.insert(58);
        Hashtable hashtable = new Hashtable();
    }
}
