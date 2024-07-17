package me.alipson.removeall;

public class LinkedIntList {
    private ListNode front;
    private int size;

    public static void main(String[] args) {
        LinkedIntList list = new LinkedIntList(2, -4, 12, 0, 2, 2);
        System.out.println(list);
        System.out.println("Removed " + list.removeAll(2) + " values!");
        System.out.println(list);
    }

    /* TODO: */
    // implement removeAll() below
    public int removeAll(int toRemove) {
        if (front == null) { // empty check
            return 0;
        }

        int count = 0;

        ListNode curr = front;

        while (curr.next != null) {
            if (curr.data == toRemove) {
                curr = curr.next;
                count++;
            }

            curr = curr.next;
        }

        // update the object's list
        this.front = curr;

        return count;
    }

    // Constructs a list containing the given elements
    public LinkedIntList(int... elements) {
        if (elements.length > 0) {
            front = new ListNode(elements[0]);
            ListNode current = front;
            for (int i = 1; i < elements.length; i++) {
                current.next = new ListNode(elements[i]);
                current = current.next;
            }
        }
        size = elements.length;
    }

    public void add(int value) {
        if (front == null) {
            front = new ListNode(value);
        } else {
            ListNode current = front;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new ListNode(value);
        }
        size++;
    }

    public String toString() {
        String res = "";
        ListNode curr = front;
        while (curr != null) {
            res += curr.data + " ";
            curr = curr.next;
        }
        return res;
    }

    private static class ListNode {
        public int data;
        public ListNode next;

        public ListNode() {
            this(0, null);
        }

        public ListNode(int data) {
            this(data, null);
        }

        public ListNode(int data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }
}
