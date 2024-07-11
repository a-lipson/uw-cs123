package me.alipson.strategy;

import java.util.*;

/**
 * Chomp is an AbstractStrategyGame that has two players who take turns removing
 * volumes from a grid.
 * The game class offers two- and three-dimensional game arrays.
 * Each chomp action removes grid cells below and to the right, but only on the
 * same layer, of the chosen cell.
 */
public class Chomp extends AbstractStrategyGame {

    private final int width;
    private final int height;
    private final int layers;

    private int[][] board;

    private boolean firstPlayerTurn;

    private Move lastMove;

    /**
     * Create a 2D Chomp game.
     */
    public Chomp(int width, int height) {
        this(width, height, 1);
    }

    /**
     * Create a 3D Chomp game.
     */
    public Chomp(int width, int height, int layers) {
        this.width = width;
        this.height = height;
        this.layers = layers;

        this.board = newBoard();

        this.firstPlayerTurn = true;
    }

    /**
     * Returns the index of the game winner.
     * 
     * @return the game winner int; <code>1<\code> if player one, <code>2<\code> if
     *         player two, and <code>-1<\code> if the game is not finished.
     */
    @Override
    public int getWinner() {
        if (board[0][0] != 0) {
            return -1;
        }
        return getCurrentPlayerIndex();
    }

    /**
     * Returns the index of the next player.
     * 
     * @return the player index int; <code>1<\code> for player one, and
     *         <code>2<\code> for player two.
     */
    @Override
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        return getCurrentPlayerIndex();
    }

    /**
     * Gets the current active player.
     * 
     * @return the current player's index int.
     */
    private int getCurrentPlayerIndex() {
        return firstPlayerTurn ? 1 : 2;
    }

    /**
     * Takes the users input to make a move in game and changes current turn.
     * 
     * @param input the Scanner input to process.
     * 
     * @throws IllegalArgumentException if the input cannot be processed.
     * @see #parseMove(String)
     */
    @Override
    public void makeMove(Scanner input) throws IllegalArgumentException {
        System.out.print("Chomp location > ");

        processMove(input.nextLine());

        firstPlayerTurn = !firstPlayerTurn;
    }

    /**
     * Processes the user's move by removing decrementing the lower right grid cells
     * in the game board until zero.
     * 
     * @param input the input line String to process.
     * 
     * @throws IllegalArgumentException if move cannot be parsed to a valid move
     *                                  object.
     * @see #parseMove(String)
     */
    private void processMove(String input) throws IllegalArgumentException {
        if (!parseMove(input)) {
            throw new IllegalArgumentException("Cannot interpret move.");
        }

        int currentLayer = board[lastMove.y][lastMove.x];

        if (currentLayer == 0) { // don't chomp on the last layer
            throw new IllegalArgumentException("Cannot chomp empty volume.");
        }

        for (int y = lastMove.y; y < height; y++) {
            for (int x = lastMove.x; x < width; x++) {
                // only chomp on current layer
                if (board[y][x] == currentLayer) {
                    board[y][x]--;
                }
            }
        }
    }

    /**
     * Parses the move from user input and sets it as the latest move.
     * 
     * @param the user's move String.
     * 
     * @return the success of the parsing operation; <code>true<\code> if successful
     *         and <code>false<\code> if not.
     */
    private boolean parseMove(String input) {
        // validate input with regex match for digit , digit with any spacing;
        if (!input.matches("\\s*\\d+\\s*,\\s*\\d+\\s*")) {
            return false;
        }

        // could use regex groups, but those still require int parsing
        String[] moveCoords = input.split(",");

        // assuming that parsings will pass because of regex match sentinel
        int y = Integer.parseUnsignedInt(moveCoords[0].trim());
        int x = Integer.parseUnsignedInt(moveCoords[1].trim());

        this.lastMove = new Move(x, y);

        return true;
    }

    /**
     * Generates a new board matrix with full layers.
     * 
     * @return a new int[][] with full layers.
     */
    private int[][] newBoard() {
        board = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] = layers;
            }
        }

        return board;
    }

    /**
     * Gives the instructions for playing the game.
     *
     * @return the game instructions String.
     */
    @Override
    public String instructions() {
        return """
                Instructions:
                Player 1 goes first and choses a grid point to chomp using an x,y coordinate pair,
                where the origin is the top left of the game array.
                When a square is chomped, all other squares to the right and below that are on the same layer
                get chomped too!
                The game ends when a player takes the top-left square and loses.
                                """;
    }

    /**
     * Provides a readible representation of the current game board state.
     * 
     * @return the game board String.
     */
    @Override
    public String toString() {
        String result = "";
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result += board[y][x] + " ";
            }
            result += "\n";
        }
        return result;
    }

    /**
     * A 2D Vector object to store a game move with x and y coordinates as fields.
     */
    private class Move {
        public int x;
        public int y;

        /**
         * Create a new move data object.
         */
        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
