Collapse [Stacks and Queues Review]
-------------------------------------------------------------------------------
Write a method collapse that takes a Stack of integers as a parameter and that
collapses it by replacing each successive pair of integers with the sum of the
pair. 

For example, suppose a stack stores this sequence of values:

    bottom (7, 2, 8, 9, 4, 13, 7, 1, 9, 10) top

Assume that stack values appear from bottom to top. In other words, 7 is on the
bottom, with 2 on top of it, with 8 on top of it, and so on, with 10 at the top
of the stack.

The first pair should be collapsed into 9 (7 + 2), the second pair should be
collapsed into 17 (8 + 9), the third pair should be collapsed into 17 (4 + 13)
and so on to yield:

    bottom (9, 17, 17, 8, 19) top

As before, stack values appear from bottom to top (with 9 on the bottom of the
stack, 17 on top of it, etc). If the stack stores an odd number of elements,
the final element is not collapsed. 

For example, the sequence:

    bottom (1, 2, 3, 4, 5) top

would collapse into:

    bottom (3, 7, 5) top

with the 5 at the top of the stack unchanged.

Notes
-------------------------------------------------------------------------------
You are to use one queue as auxiliary storage to solve this problem.
You may not use any other auxiliary data structures to solve this problem,
although you can have as many simple variables as you like. You also may not
solve the problem recursively.

In writing your method, assume that you are using the Stack and Queue interfaces
and the ArrayStack and LinkedQueue implementations discussed in lecture. As a
result, values will be stored as Integer objects, not simple ints.

Your method should take a single parameter: the stack to collapse.