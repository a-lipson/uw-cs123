Length Count [Sets Review]
-------------------------------------------------------------------------------
Write a method called lengthCount that takes a Set<String> as a parameter and
returns a Map mapping how many times a certain word length occurs in the Set.

For example, if a variable called words contains the following set of strings:

    [to, be, or, not, that, is, the, question]

then the call lengthCount(words) should return a map whose values are counts
of a certain String length and whose keys are the String lengths:

    {2=4, 3=2, 4=1, 8=1}

Notice that the key 2 maps to 4 because there are 4 Strings that have a length
of 2 (to, be, or, is). Note the ordering of the elements of the resultant Map.

