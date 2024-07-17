package me.alipson.multiply;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        LinkedList<Integer> list1 = new LinkedList<Integer>(3, 16, 7, 23);
        LinkedList<Integer> list2 = new LinkedList<Integer>(2, 12, 6, 54);
        System.out.println(list1);
        list1.multiply();
        System.out.println(list1);
    }

}
