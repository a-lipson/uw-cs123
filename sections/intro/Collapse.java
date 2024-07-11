import java.util.*;

public class Collapse {

  public static Stack<Integer> collapse(Stack<Integer> stack) {
    Queue<Integer> queue = new LinkedList<>();

    // flip stack
    while (!stack.empty()) {
      queue.add(stack.pop());
    }
    while (!queue.isEmpty()) {
      stack.push(queue.remove());
    }
    while (!stack.empty()) {
      queue.add(stack.pop());
    }

    while (queue.size() > 1) { // pair up while there's at least a pair in the queu
      stack.push(queue.remove() + queue.remove());
    }

    if (!queue.isEmpty()) { // add if there's a remaining element in the queue
      stack.push(queue.remove());
    }

    return stack;
  }

  /* SOLUTION */
  public static void _collapse(Stack<Integer> s) {
    Queue<Integer> q = new LinkedList<Integer>();
    while (!s.isEmpty()) {
      q.add(s.pop());
    }
    while (!q.isEmpty()) {
      s.push(q.remove());
    }
    while (!s.isEmpty()) {
      q.add(s.pop());
    }
    while (q.size() > 1) {
      s.push(q.remove() + q.remove());
    }
    if (!q.isEmpty()) {
      s.push(q.remove());
    }
  }

  public static void main(String[] args) {
    Stack<Integer> s1 = new Stack<>();
    s1.push(7);
    s1.push(2);
    s1.push(8);
    s1.push(9);
    s1.push(4);
    s1.push(13);
    s1.push(7);
    s1.push(1);
    s1.push(9);
    s1.push(10);
    System.out.println(s1);
    collapse(s1);
    System.out.println(s1);

    Stack<Integer> s2 = new Stack<>();
    s2.push(1);
    s2.push(2);
    s2.push(3);
    s2.push(4);
    s2.push(5);
    System.out.println(s2);
    collapse(s2);
    System.out.println(s2);
  }
}
