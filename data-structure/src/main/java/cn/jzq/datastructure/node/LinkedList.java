package cn.jzq.datastructure.node;

public class LinkedList {

    Node head = null;

    public LinkedList() {
        head = new Node(0, null);
    }


    class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }


    public void add(int data) {
        Node addPrevNode = head;
        while (addPrevNode.next != null) {
            addPrevNode = addPrevNode.next;
        }
        Node node = new Node(data, null);
        addPrevNode.next = node;
    }

    public int get(int index) {
        return 0;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add(1);
        System.out.println(list);
    }
}
