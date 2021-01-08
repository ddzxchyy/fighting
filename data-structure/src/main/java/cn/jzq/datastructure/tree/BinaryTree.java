package cn.jzq.datastructure.tree;

import lombok.Data;

/**
 * 二叉树
 *
 * @author jzq
 * @date 2021-01-08
 */
@Data
public class BinaryTree {
    Node node;

    Node lastNode;

    public void preOrder(Node node) {
        if (node != null) {
            System.out.println(node.data);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
    }
}

@Data
class Node {
    int data;
    Node left;
    Node right;
}

