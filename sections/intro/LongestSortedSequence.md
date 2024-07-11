Longest Sorted Sequence [ArrayList Review]
-------------------------------------------------------------------------------
Write a method longestSortedSequence that returns the length of the longest
sorted sequence within a list of integers. A sequence is considered to be sorted
when the numbers are in an order which is non-decreasing, e.g. (0, 0, 1, 2).

For example, if a variable called list stores the following sequence of values:

    [1, 3, 5, 2, 9, 7, -3, 0, 42, 308, 17]

then the call: longestSortedSequence(list) would return the value 4 because it
is the length of the longest sorted sequence within this list
(the sequence -3, 0, 42, 308).

If the list is empty, your method should return 0.

Notice that for a non-empty list the method will always return a value of at
least 1 because any individual element constitutes a sorted sequence. 