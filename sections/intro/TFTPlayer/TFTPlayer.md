JUnit Practice - TFTPlayer
-------------------------------------------------------------------------------
This example is a little bit more complex due to the rules of a game you might
not be familiar with. You should read over the specification and try to write
some tests as the task indicates, but we will pick up with this as an example
in class to work on with your peers so it's okay if you don't feel super
confident yet!

TFT is an eight-player competition where you are playing against the other
players to be the last person standing. The game is pretty complicated with
lots of different mechanics to add variance and skill expression to the game,
but we will be focusing on a much smaller scope of the game to keep this
problem as simple as possible.

Don't worry if you don't know this game or other games, we will provide a
simplified set of background rules necessary to solve this problem and no
outside experience is needed.

Rules of the Game
-------------------------------------------------------------------------------
There are a lot of complicated rules and systems in TFT, but we will focus on
the fundamental rules for attributes that players progress throughout the game:
gold, experience, and levels. Every player has some amount of gold. They can
spend gold to gain experience, and when they hit certain experience thresholds,
they level up. Players passively gain gold based on an interest formula that
rewards players for saving up gold before spending it.

Below are a simplified set of rules for this aspect of the game that we will
use in our coding problem, no outside rules or knowledge about the game should
be needed figure out these mechanics. If you do have experience with the game,
note that the rules we are presenting are simplified to make this problem more
manageable to code up.

Start of game: Every player starts with 10 gold, 0 experience, and are level 1.

Buy Experience: Players can spend 4 gold to gain 4 experience.

Leveling Up (Simplified): Every time a player reaches 20 experience, they level
up (level increases by 1). The maximum level is 9 at which point the player can't
purchase any more experience.

Gain Gold (Simplified): Every turn the player receives some interest based on
their current gold in addition to every player receiving 1 additional gold
"for free". So in short, when gaining gold every player receives some amount of
interest (described next) plus 1 additional gold.

Interest: The interest rule works as follows. Each player receives one extra
gold in interest for every 10 gold they have. There is no additional interest
for having more than 50 gold, so the maximum interest is 5 gold per turn. Here
are some examples for gold being gained:

If a player has 24 gold currently, they would gain 3 gold (2 for their interest,
and the 1 gold "for free"). The interest is 2 in this example because the player
has 20 gold to earn 2 interest, but not 30 gold to earn 3.

If a player has 39 gold, they would gain 4 gold (3 for interest, 1 "for free").

If player has 55 gold, they would gain 6 gold (5 for interest, 1 "for free").

If a player has 72 gold, they would gain 6 gold (5 for interest, 1 "for free").

That's a lot of rules, but we wanted to spell it out clearly to make sure the
scope of the problem was unambiguous.

Task
We have already written a TFTPlayer to represent each player in the game with
the following methods to implement the rules of the game described above. Note
that our implementation is potentially buggy. Your task is to write JUnit tests
to test the behavior of this class and hopefully expose the presence of these bugs.

Task: Write at least one JUnit test for each of the methods. Ideally you'll have
multiple tests for each method to test out different circumstances to ensure good
test coverage. Because you aren't expected to fix the bugs for this problem, it is
okay if some of your tests are failing due to our incorrect implementation.

Optional: You can fix the bugs if you would like, but that is not the focus of our
exercise. When we release the solutions, you can see an explanation of all the bugs
present and how to fix them.

TFTPlayer class

    public TFTPlayer()

    // Sets up a TFTPlayer with the start of game state (10 gold, 0 experience, level 1).

    public int getGold()
    
    // Returns the current amount of gold
    
    public int getXP()
    
    // Returns the current amount of experience
    
    public int getLevel()
    
    // Returns the current level of the players
    
    public boolean buyXP()
    
    // Spends 4 gold of the player's gold and increases their experience by 4 points.
    // Returns true if the player was able to purchase experience and false otherwise.

    // Some important cases to consider:
     - If the player has less than 4 gold, no gold should be spent and no experience should be gained.
     - The player can't purchase experience if they are already the maximum level (9).
     - If the player accumulates 20 experience points, those 20 experience points they should
       "level up" and gain a level in exchange for those 20 experience points.

    public int gainGold()

    // Increases the amount of gold using the rule described above of adding interest + 1 gold
    // to the player's gold. Returns the new amount of gold the player has after this operation.