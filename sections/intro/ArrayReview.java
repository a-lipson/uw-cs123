// import java.util.*;

public class ArrayReview {

  public static int sumLeftHalf(int[][] arr) {
    int sum = 0;
    for (int[] row : arr) {
      for (int i = 0; i < row.length / 2; i++) {
        sum += row[i];
      }
    }
    return sum;
  }

  /* SOLUTION */
  public static int _sumLeftHalf(int[][] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length / 2; j++) {
        sum += arr[i][j];
      }
    }
    return sum;
  }

  public static void main(String[] args) {
    int[][] arr1 = { { 3, 1, 4, 1 },
        { 5, 9, 2, 6 },
        { 5, 3, 5, 8 } };

    int[][] arr2 = { { 5, 2, 3 },
        { 1, 3, 6 },
        { 5, 8, 2 } };

    System.out.println(sumLeftHalf(arr1));
    System.out.println(sumLeftHalf(arr2));
  }
}
