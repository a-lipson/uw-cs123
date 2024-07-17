package me.alipson.multiply;

// TODO: do this in haskell as well

/**
 * LinkedList
 */
public class LinkedList<T extends Comparable<T>> {
    private ListNode head;
    private int size;

    @SuppressWarnings("unchecked")
    public LinkedList(T... elements) {
        for (T element : elements) {
            add(element);
        }
        size = elements.length;
    }

    public void add(T value) {
        if (head == null) {
            head = new ListNode(value);
        } else {
            ListNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new ListNode(value);
        }

        size++;
    }

    public void removeFirst(T value) {
        // remove(indexOf(value));
        if (head != null && head.data.equals(value)) {
            this.head = this.head.next;
        } else if (head != null) {
            ListNode curr = head;
            while (curr.next != null && !curr.next.data.equals(value)) {
                curr = curr.next;
            }
            if (curr.next != null) {
                curr.next = curr.next.next;
            }
        }
    }

    public void remove(int index) {

    }

    public void zip(Monoid<T> monoid, LinkedList<T> other) {
        if (this.head == null) {
            this.head = other.head;
        } else if (other.head != null) {

            ListNode currThis = this.head;
            ListNode currOther = other.head;
            ListNode lastThis = null;

            while (currThis != null && currOther != null) {
                currThis.data = monoid.combine(currThis.data, currOther.data);
                currThis = currThis.next;
                currOther = currOther.next;
                lastThis = currThis;
            }

            if (lastThis != null) {
                lastThis.next = currOther;
            }
        }

    }

    public T reduce(Monoid<T> monoid) {

        if (head == null) {
            return monoid.identity();
        }

        ListNode curr = head;
        T acc = monoid.identity();

        while (curr != null) {
            acc = monoid.combine(acc, curr.data);
            curr = curr.next;
        }

        return acc;

    }

    public void map(Map<T, T> map) {
        // don't do anything in the empty case
        if (head != null) {

            ListNode curr = head;

            while (curr != null) {
                curr.data = map.apply(curr.data);
                curr = curr.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        return head.data;
    }

    public int indexOf(T value) {
        return -1;
    }

    // public T multiply() {}

    public int multiply_() {
        if (head == null) {
            throw new IllegalStateException("Cannot multiply reduce empty list.");
        }

        int product = 1;
        ListNode curr = head;
        while (curr.next != null) {
            product *= 1/* curr.data */;
            curr = curr.next;
        }

        return product;

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        LinkedList<T> list = (LinkedList<T>) other;

        ListNode currThis = this.head;
        ListNode currOther = list.head;

        while (currThis != null && currOther != null) {
            if (!currThis.data.equals(currOther.data)) {
                return false;
            }
            currThis = currThis.next;
            currOther = currOther.next;
        }

        return currThis == null && currOther == null;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        String out = "[" + head.data;
        ListNode curr = head.next;
        while (curr != null) {
            out += ", " + curr.data;
            curr = curr.next;
        }

        return out + "]";
    }

    private class ListNode {
        T data;
        ListNode next;

        ListNode(T data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        ListNode(T data) {
            this(data, null);
        }
    }

}
