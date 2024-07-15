Take Smaller From [Complex Programming Problem]

Problem Description
--------------------------------------------------------------------------------
Write a method called weave that takes a list of integers as a parameter and
that combines the values from the second list into the first list in an
alternating fashion, leaving the second list empty. The new list should start
with the first value of the first list followed by the first value of the second
list followed by the second value of the first list followed by the second value
of the second list, and so on.

  For example, if the variables list1 and list2 store the following:

    list1 = [0, 2, 4, 6]
    list2 = [1, 3, 5, 7]

  If you make the following call:

    list1.weave(list2);

  then the lists should store the following values after the call:

    list1 = [0, 1, 2, 3, 4, 5, 6, 7]
    list2 = []

  Notice that the second list is empty after the call and that the values that
  were in the second list have been moved into the first list in an alternating
  fashion. If the call had instead been:

    list2.weave(list1);

  then the lists would have stored the following values after the call:

    list1 = []
    list2 = [1, 0, 3, 2, 5, 4, 7, 6]

  These examples use sequential integers to make it easier to see the intended
  order, but the values can be any numbers at all. It is also possible that one
  list will have more values than the other, in which case its values should
  simply be appended to the end of the list. For example, if the lists store
  these values:

    list1 = [3, 18, 9], list2 = [-5, 4, 13, 42, 0, 23]

  then the call list1.weave(list2) should produce this result:

    list1 = [3, -5, 18, 4, 9, 13, 42, 0, 23], list2 = []

  while the call list2.weave(list1) should produce this result:

    list1 = [], list2 = [-5, 3, 4, 18, 13, 9, 42, 0, 23]

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