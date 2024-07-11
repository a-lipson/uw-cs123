USCurrency [OOP Review]
-------------------------------------------------------------------------------
Define a class USCurrency that can be used to store a currency amount in dollars
and cents (both integers) where one dollar equals 100 cents. Your class should
include the following methods:

    USCurrency(dollars, cents) -- constructs a currency object with given dollars
                                  and cents
    
    dollars() -- returns the dollars
    
    cents() -- returns the cents
    
    toString() -- returns a String in standard $d.cc notation (-$d.cc for negative amounts)
    
    add(other) -- returns the result of adding another currency amount to this one
    
    subtract(other) -- returns the result of subtracting another currency amount
                       from this one
    
    compareCurrencies(other) -- returns an integer value that indicates if this
                                currency is greater than, less than, or equal
                                to the other currency

A currency amount can be negative. The cents method should return values in the
range of 0 to 99 for nonnegative currency amounts and should return values in
the range of 0 to -99 for negative currency amounts.

The add and subtract methods should return new USCurrency objects that represent
the result of adding or subtracting the other currency amount.

Note that the toString method should return the amount in a standard format ($d.cc)
with two digits for cents and with negative values indicated with a single minus
sign in front of the dollar sign (-$d.cc). For example, 4 dollars and 5 cents would
be expressed as "$4.05" while -19 dollars and -43 cents would be expressed as "-$19.43".

The compareCurrencies method should return an integer indicating how this currency
compares to the other currency. Smaller currency amounts should be considered "less"
than larger currency amounts. (e.g., -$13.45 < -$2.03 < $5.13 < $98.06).

It should return:

    1 if the amount of this currency is greater than the other currency
    
    -1 if the amount of this currency is less than the other currency
    
    0 if the amount of this currency is equal to the other currency