ClockTime [OOP Review]
-------------------------------------------------------------------------------
Suppose that you are provided with a pre-written class ClockTime as shown below.

Write an instance method named advance that will be placed inside the ClockTime
class to become a part of each ClockTime object's behavior. The advance method
accepts a number of minutes as its parameter and moves the ClockTime object 
forward in time by that amount of minutes. The minutes passed could be any
non-negative number, even a large number such as 500 or 1000000. If necessary,
your object might wrap into the next hour or day, or it might wrap from the
morning ("AM") to the evening ("PM") or vice versa. A ClockTime object
doesn't care about what day it is; if you advance by 1 minute from 11:59 PM,
it becomes 12:00 AM. 

For example, if the following object is declared in client code:

    ClockTime time = new ClockTime(6, 27, "PM");

Then, the following calls to your method would modify the object's state as
indicated in the comments.

    time.advance(1); // 6:28 PM
    time.advance(30); // 6:58 PM
    time.advance(5); // 7:03 PM
    time.advance(60); // 8:03 PM
    time.advance(128); // 10:11 PM
    time.advance(180); // 1:11 AM
    time.advance(1440); // 1:11 AM (1 day later)
    time.advance(21075); // 4:26 PM (2 weeks later)

Assume that the state of the ClockTime object is valid at the start of
the call and that the amPm field stores either "AM" or "PM". 