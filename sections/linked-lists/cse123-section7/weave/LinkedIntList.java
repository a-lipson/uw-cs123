package weave;

import java.util.*;
public class LinkedIntList {
    private ListNode front;
    private int size;

    public static void main(String[] args) {
        LinkedIntList list1 = new LinkedIntList(0, 2, 4, 6, 8);
        LinkedIntList list2 = new LinkedIntList(1, 3, 5, 7, 9);
        System.out.println(list1);
        list1.weave(list2);
        System.out.println(list1);
    }

    /* TODO: */
    // implement weave() below




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
        public final int data;
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