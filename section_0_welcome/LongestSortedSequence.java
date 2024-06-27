import java.util.*;

public class LongestSortedSequence {
  // takes a list of integers and returns the length of the longest non-decreasing
  // subsequence
  public static int longestSortedSequence(List<Integer> list) {
    if (list.size() == 0) {
      return 0;
    }

    int currentNumber = list.get(0);
    int currentSequenceLength = 1;
    int maxSequenceLength = 1;

    for (int nextNumber : list.subList(1, list.size())) {
      if (nextNumber >= currentNumber) {
        currentSequenceLength++;
      } else { // reset current sequence count when subsequence is not non-decreasing
        currentSequenceLength = 1;
      }

      maxSequenceLength = Math.max(maxSequenceLength, currentSequenceLength);

      currentNumber = nextNumber;
    }

    return maxSequenceLength;
  }

  /* SOLUTION */
  // Takes an List<Integer> and returns the length of the
  // longest sorted sequence of integers in the list.
  public static int _longestSortedSequence(List<Integer> list) {
    int max = 0;
    int count = 1;
    for (int i = 0; i < list.size() - 1; i++) {
      if (list.get(i + 1) >= list.get(i)) {
        count++;
      } else {
        count = 1;
      }
      max = Math.max(count, max);
    }
    return max;
  }

  public static void main(String[] args) {
    List<Integer> list1 = addValues(new int[] { 1, 3, 5, 2, 9, 7, -3, 0, 42, 308, 17 });
    List<Integer> list2 = addValues(new int[] { 30, 25, 0, 12, 16, 11 });
    List<Integer> list3 = addValues(new int[] { 1, 2, 3, 4, 5 });

    System.out.println(longestSortedSequence(list1));
    System.out.println(longestSortedSequence(list2));
    System.out.println(longestSortedSequence(list3));
  }

  // private helper method for program setup
  private static List<Integer> addValues(int[] arr) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < arr.length; i++) {
      list.add(arr[i]);
    }
    return list;
  }
}
