rarestAge [Map Review]
-------------------------------------------------------------------------------
Write a method rarestAge that takes a map from names (Strings) to ages (integers),
and returns the least frequently-occurring age. Assume the maps are not null
and no key is null.

Suppose a map contains the following entries:

    {Trien=22,
    Sara=25,
    Ajay=25,
    Audrey=20,
    Ben=20,
    Eric=20,
    Joe=25,
    Joshua=25,
    Nicole=22,
    Rohini=21}

3 people are age 20, 2 people are age 22, and 4 people are age 25. rarestAge() 
returns 22 because only two people are age 22.

If there is a tie (multiple rarest ages that occur the same number of times),
return the youngest age among them. For example, if we added another pair Andrew=22
to the map above, there would now be a tie of 3 people of age 20 and 3 people of
age 22. rarestAge would now return 20 (as 20 is the smaller of 20 and 22).