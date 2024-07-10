package me.alipson.strategy;

import java.util.*;

/**
 * TODO: class documentation
 * Chomp game
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
     * TODO: finish documentation
     * 
     * @see #parseMove(String)
     */
    @Override
    public void makeMove(Scanner input) throws IllegalArgumentException {
        System.out.print("Chomp location > ");

        processMove(input.nextLine());

        firstPlayerTurn = !firstPlayerTurn;
    }

    /**
     * Processes the user's move
     */
    private void processMove(String input) throws IllegalArgumentException {
        if (!parseMove(input)) {
            throw new IllegalArgumentException("Cannot interpret move.");
        }

        int currentLayer = board[lastMove.x][lastMove.y];

        if (currentLayer == 0) { // don't chomp on the last layer
            throw new IllegalArgumentException("Cannot chomp empty volume.");
        }

        for (int x = lastMove.x; x < width; x++) {
            for (int y = lastMove.y; y < height; y++) {
                // only chomp on current layer
                if (board[x][y] == currentLayer) {
                    board[x][y]--;
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
        int x = Integer.parseUnsignedInt(moveCoords[0].trim());
        int y = Integer.parseUnsignedInt(moveCoords[1].trim());

        this.lastMove = new Move(x, y);

        return true;
    }

    /**
     * Generates a new board matrix with full layers.
     * 
     * @return a new int[][] with full layers.
     */
    private int[][] newBoard() {
        board = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = layers;
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
                    Instructions Here!
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
                result += board[x][y] + " ";
            }
            result += "\n";
        }
        return result;
    }

    // getters for Chomp2#toString

    // public int getWidth() {
    // return this.width;
    // }
    //
    // public int getHeight() {
    // return this.height;
    // }
    //
    // public int getLayers() {
    // return this.layers;
    // }
    //
    // public int[][] getBoard() {
    // return this.board;
    // }

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
