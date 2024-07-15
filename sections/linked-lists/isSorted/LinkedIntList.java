package isSorted;

public class LinkedIntList {
    private ListNode front;
    private int size;

    public static void main(String[] args) {
        LinkedIntList sorted = new LinkedIntList(-1, 3, 3, 20);
        System.out.println(sorted.isSorted());
        LinkedIntList notSorted = new LinkedIntList(9, 8, -1, 0, 5);
        System.out.println(notSorted.isSorted());
    }

    /* TODO: */
    // implement isSorted() below





    // Constructs a list containing the given elements
    public LinkedIntList(int... elements) {
        size = 0;
        if (elements.length > 0) {
            front = new ListNode(elements[0]);
            size++;
            ListNode current = front;
            for (int i = 1; i < elements.length; i++) {
                size++;
                current.next = new ListNode(elements[i]);
                current = current.next;
            }
        }
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