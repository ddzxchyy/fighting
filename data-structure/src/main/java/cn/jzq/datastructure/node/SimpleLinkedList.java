package cn.jzq.datastructure.node;

public class SimpleLinkedList {

    Node head = null;

    private int size = 0;

    public SimpleLinkedList() {
        head = new Node(null, null);
    }


    class Node {
        Integer data;
        Node next;

        public Node(Integer data, Node next) {
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
        size++;
    }

    public void remove(int index) {
        Node node = head;
        // 获取要删除节点的前一个节点
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        Node prevNode = node;
        Node needRemoveNode = node.next;
        if (needRemoveNode == null) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        prevNode.next = needRemoveNode.next;
        size--;
    }

    /**
     * 根据索引查询值
     *
     * @param index 索引
     * @return data
     */
    public Integer get(int index) {
        Node node = head;
        for (int i = 0; i <= index; i++) {
            node = node.next;
        }
        return node.data;
    }

    @Override
    public String toString() {
        String s = "[";
        Node temp = head;
        while (temp.next != null) {
            s += temp.next.data + ", ";
            temp = temp.next;
        }
        if (s.length() > 1) {
            s = s.substring(0, s.length() - 2);
        }
        s += "]";
        return s;
    }

    public static void main(String[] args) {
        SimpleLinkedList list = new SimpleLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(0);
        list.remove(0);
        list.remove(1);
        System.out.println(list);
    }
}
