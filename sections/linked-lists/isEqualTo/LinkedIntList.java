package isEqualTo;

public class LinkedIntList {
    private ListNode front;
    private int size;

    public static void main(String[] args) {
        LinkedIntList list1 = new LinkedIntList(1, 2, 3);
        LinkedIntList list2 = new LinkedIntList(1, 2, 3);
        LinkedIntList list3 = new LinkedIntList(3, 2, 1);
        System.out.println(list1.isEqualTo(list1));
        System.out.println(list1.isEqualTo(list2));
        System.out.println(list1.isEqualTo(list3));
    }

    /* TODO: */
    // implement isEqualTo() below





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