Delete Back [Complex Programming Problem]

Problem Description
--------------------------------------------------------------------------------
Write a method deleteBack() that removes the last node in the list and returns
the value of the deleted node. If the list is empty, your method should throw a
NoSuchElementException.


The table below shows examples of the result of the method call list.deleteBack().

-----------------------------------------------------------------------------------
+ [Original list values]   +   [Resulting list values]  +     [Returned value]    +
+          []	             +             []	            +  NoSuchElementException +
+          [3]	           +             []	            +            3            +
+    [3, 7, 7, -3, 4]	     +        [3, 7, 7, -3]	      +            4            +
-----------------------------------------------------------------------------------

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