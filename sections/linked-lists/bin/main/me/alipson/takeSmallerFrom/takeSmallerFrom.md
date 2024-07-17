Take Smaller From [Complex Programming Problem]

Problem Description
--------------------------------------------------------------------------------
Write a method takeSmallerFrom that compares two lists of integers, making sure
that the first list has smaller values in corresponding positions.

  For example,suppose the variables list1 and list2 refer to lists that contain
  the following values:

    list1: [3, 16, 7, 23]
    list2: [2, 12, 6, 54]

  If the call of list1.takeSmallerFrom(list2); is made, the method will compare
  values in corresponding positions and move the smaller values to list1. It
  will find that among the first pair, 2 is smaller than 3, so it needs to move.
  In the second pair, 12 is smaller than 16 so it needs to move. In the third
  pair, 6 is smaller than 7, so it needs to move. In the fourth pair, 54 is not
  smaller than 23, so those values can stay where they are. Thus, after the
  call, the lists should store these values:

    list1: [2, 12, 6, 23]
    list2: [3, 16, 7, 54]

  One list might be longer than the other, in which case those values stay at
  the end of the list.

  For these lists:

    list3: [2, 4, 6, 8, 10, 12]
    list4: [1, 3, 6, 9]

  The call of list3.takeSmallerFrom(list4) should leave the lists with these
  values:

    list3: [1, 3, 6, 8, 10, 12]
    list4: [2, 4, 6, 9]

Step 1 - Pseudocode
--------------------------------------------------------------------------------
Write pseudocode that will enable us to perform the expected behavior above.

Step 2 - Implementation
--------------------------------------------------------------------------------
Once you write pseudocode for this problem, implement the code using Java syntax.

Notes
--------------------------------------------------------------------------------
  - You should not create any additional data structures.
  - Assume you are adding this method to the LinkedIntList class.