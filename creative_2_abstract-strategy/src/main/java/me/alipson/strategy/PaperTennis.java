package me.alipson.strategy;

import java.util.*;

public class PaperTennis extends AbstractStrategyGame {
    // default init value for boolean is false
    private boolean firstPlayerTurn;
    private Scoreboard scoreboard;
    private Court court;

    public PaperTennis(int maxRounds, int courtSize, int startingScore) {
        this();
        // replace defaults
        this.court = new Court(courtSize);
        this.scoreboard = new Scoreboard(maxRounds, startingScore);
    }

    public PaperTennis() {
        this.court = new Court();
        this.scoreboard = new Scoreboard();
        this.firstPlayerTurn = true;
    }

    @Override
    public int getWinner() {
        if (scoreboard.currentRound < scoreboard.maxRounds) { // game still in progress
            return -1;
        }

        int roundsDiff = scoreboard.playerOneScore.roundsWon - scoreboard.playerTwoScore.roundsWon;

        if (roundsDiff > 0) {
            return 1; // player one won more rounds
        } else if (roundsDiff < 0) {
            return 2; // player two won more rounds
        }

        return 0; // otherwise a tie
    }

    // returns index of the round winner
    private int getRoundWinner() {
        if (!(scoreboard.isRoundOver() || court.isBallOutsideCourt())) {
            return -1;
        }
        // first player's side is negative, second player's is positive
        return court.ballPosition < 0 ? 1 : 2;
    }

    private void assignRoundScore() {

    }

    @Override
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        return getCurrentPlayerIndex();
    }

    private int getCurrentPlayerIndex() {
        return firstPlayerTurn ? 1 : 2;
    }

    @Override
    public void makeMove(Scanner input) throws IllegalArgumentException {

        if (getRoundWinner() != -1) { // round is finished
            court.reset();
            scoreboard.reset();

            assignRoundScore();
        }

        Scoreboard.PlayerScore currentPlayer = firstPlayerTurn ? scoreboard.playerOneScore
                : scoreboard.playerTwoScore;

        System.out.print(String.format(
                "Enter your score bid (%s remaining) > ", currentPlayer.currentScore));

        int bid = input.nextInt();

        if (bid < 0 || bid > currentPlayer.currentScore) {
            throw new IllegalArgumentException("Invalid bid amount.");
        }

        currentPlayer.currentScore -= bid;
        currentPlayer.lastBid = bid;

        // attempt clear the screen (works on Unix terminals only)
        // so that the second player can't see the first player's bid or remainder
        // System.out.print("\033[H\033[2J");
        // System.out.flush();

        // evaluate ball after both players have bid, on second player's turn
        if (!firstPlayerTurn) {
            court.sendBall(scoreboard.bidDifference());
        }

        // next player on successful move
        firstPlayerTurn = !firstPlayerTurn;
    }

    /**
     * Provides the instructions for the game.
     * 
     * @return the game instructions String.
     */
    @Override
    public String instructions() {
        return """
                """;
    }

    /**
     * Provides a readible representation of the current game board state.
     * 
     * @return the game board String.
     */
    @Override
    public String toString() {
        String out = court.courtHistory;
        // only update court history after both bids have been made
        if (firstPlayerTurn) {
            out += makeCourtRow();
            court.courtHistory = out;
        }
        return out;
    }

    // build court line string from ball position
    private String makeCourtRow() {
        String courtLines = "";
        String courtBox = "|   "; // only works if of even length;
        char ball = 'o';

        // build a one side of the court to prepend and append
        for (int i = 0; i <= court.size; i++) {
            // drop the left-most court line on the first iteration
            courtLines += i == 0 ? courtBox.substring(1) : courtBox;
        }
        String courtRow = courtLines + ":" + courtLines;

        // should be the same as courtBox.length() * court.size - 1;
        int courtMiddleIndex = courtRow.indexOf(":");
        int ballIndex = courtMiddleIndex + court.ballPosition * courtBox.length()
                - 2 * Integer.signum(court.ballPosition);
        courtRow = replaceCharAtIndex(courtRow, ballIndex, ball);

        return courtRow + "\n";
    }

    /**
     * Replaces a char in a given string at a provided index.
     * 
     * @param input       the input String to update
     * @param index       the index in the String to be updated
     * @param replacement the char to replace with
     * 
     * @return the updated String.
     */
    private String replaceCharAtIndex(String input, int index, char replacement) {
        return input.substring(0, index) + replacement + input.substring(index + 1);
    }

    // type contains the course state information of size and current ball location
    private class Court {
        public int size;
        public int ballPosition;
        public String courtHistory;

        private static final int DEFAULT_COURT_SIZE = 2;

        public Court() {
            this(DEFAULT_COURT_SIZE);
        }

        public Court(int size) {
            this.ballPosition = 0;
            this.size = size;
            reset(); // start with empty court history and ball at zero
        }

        /**
         * Checks if ball position magnitude is outside the court
         * 
         * @return whether the ball is outside of the court; <code>true<\code> if yes,
         *         and <code>false<\code> if now.
         */
        public boolean isBallOutsideCourt() {
            return Math.abs(ballPosition) > size;
        }

        /**
         * Sets court history to empty string and the ball position to zero.
         */
        public void reset() {
            this.courtHistory = "";
            this.ballPosition = 0;
        }

        /**
         * Moves the ball according to the sign of the input direction int and avoids
         * the zero position.
         * 
         * @param direction the direction int used to move the ball, negative for left
         *                  and positive for right.
         */
        public void sendBall(int direction) {
            int move = Integer.signum(direction);
            // ensure ball position doesn't become zero
            ballPosition += (ballPosition + move == 0) ? 2 * move : move;
        }
    }

    // type contains player scores and round number
    private class Scoreboard {
        private int maxRounds;
        private int currentRound;
        private int startingScore;

        private PlayerScore playerOneScore;
        private PlayerScore playerTwoScore;

        private static final int DEFAULT_MAX_ROUNDS = 3;
        private static final int DEFAULT_STARTING_SCORE = 50;

        public Scoreboard(int maxRounds, int startingScore) {
            this.maxRounds = maxRounds;
            this.currentRound = 0;
            this.startingScore = startingScore;

            this.playerOneScore = new PlayerScore(startingScore);
            this.playerTwoScore = new PlayerScore(startingScore);
        }

        public Scoreboard() {
            this(DEFAULT_MAX_ROUNDS, DEFAULT_STARTING_SCORE);
        }

        public boolean isRoundOver() {
            return scoreboard.playerOneScore.currentScore == 0 && scoreboard.playerTwoScore.currentScore == 0;
        }

        public int bidDifference() {
            return playerOneScore.lastBid - playerTwoScore.lastBid;
        }

        /**
         * Resets both player scores to the round starting values.
         */
        public void reset() {
            playerOneScore.reset();
            playerTwoScore.reset();
        }

        // contains rounds won and current score score for a player
        private class PlayerScore {
            public int roundsWon;
            public int currentScore;
            public int lastBid;

            private int startingScore;

            public PlayerScore(int startingScore) {
                this.roundsWon = 0;
                this.startingScore = startingScore;
                reset();
            }

            /**
             * Resets player's current score and last bid to the round starting amounts.
             */
            public void reset() {
                currentScore = startingScore;
                lastBid = 0;
            }
        }

    }
}
