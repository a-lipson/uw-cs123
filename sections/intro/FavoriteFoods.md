Favorite Foods [Nested Collections Review]
-------------------------------------------------------------------------------
Write a method called favoriteFoods that takes a ratings map indicating how each
person rates various foods and a minimum rating and returns a map indicating all
the foods each person has rated with at least the minimum rating.

The input map will have keys that are names (strings) and values which are maps
with keys that are a food (strings) and values which are numbers in the range of
0.0 to 5.0 for the rating that person has given that food.

An example would be if we had a variable called ratings that stored the following
map in the format just described:

    {"Matt"={"pie"=5.0, "ice cream"=5.0, "mushrooms"=0.0},
    "Kasey"={"chicken strips"=4.3, "cranberry sauce"=4.2},
    "Miya"={"lettuce"=2.4},
    "Nathan"={}}

In this example, we see that Matt has rated pie and ice cream as a 5.0 each and
mushrooms as a 0.0, while Miya has only rated lettuce as a 2.4.

For example, suppose the following call is made:

    favoriteFoods(ratings, 4.3);

Given this call, the following map would be returned:

    {"Kasey"=["chicken strips"],
    "Matt"=["ice cream", "pie"],
    "Miya"=[],
    "Nathan"=[]}

Notice that the value for the key "Matt" is the set ["ice cream", "pie"] because
he rated only those foods with at least a rating of 4.3. The value for the key
"Miya" is [] because Karen rated no foods with a rating of at least 4.3. Note
that foods rated with a 4.3 should be included (see Kasey).

The map you return should have keys sorted alphabetically and the foods in
the values should appear in alphabetical order as well. Your method should not
modify the provided map. You may assume that the map and none of its contents
are null.