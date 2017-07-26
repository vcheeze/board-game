package api;

import exc.GameStateException;
import exc.IllegalMoveException;

import java.util.Observable;

public abstract class Game extends Observable {
    public abstract int getRows();
    public abstract int getColumns();
    public abstract Chip[][] getBoard();

    /*
     * @throws GameStateException if the game is a tie
     * @throws IllegalMoveException if the move is out-of-bounds or is
     *         to a column that is not legal according to the rules of
     *         the concrete game
     * The Template Design Pattern is applied to placeDisk
     */
    public void placeDisk(int row, int col)
        throws GameStateException,
               IllegalMoveException {
        // check for out of bounds
        if (isOutOfBounds(row, col)) { // defined in Game
            throw new IllegalMoveException("Illegal move");
        }
        // check if game is over
        if (isGameOver()) { // abstract: defined in child class
            throw new GameStateException("Game over"); // or return?
        }
        // place the disk
        if (!actualPlace(row, col)) { // abstract: defined in child class
            throw new IllegalMoveException("Illegal move");
        }
        else {
            Chip winner = checkWinner(); // abstract: defined in child class
            updatePlayers(winner); // abstract: defined in child class
            notifyObs(); // defined in Game
        }
    }

    public final void placeDisk(int col) throws GameStateException,
            IllegalMoveException {
        placeDisk(this.getRows() - 1, col);
    }

    /*
     * Check for valid placement
     * returns true if placement is out of bounds
     */
    private boolean isOutOfBounds(int row, int col) {
        if (row < 0 || row >= this.getRows()) {
            return true;
        }
        else if (col < 0 || col >= this.getColumns()) {
            return true;
        }
        else {
            return false; // the placement is valid
        }
    }

    /*
     * Actually place the disk
     * and then update the current and winning player accordingly
     */
    public abstract boolean actualPlace(int row, int col);
    public abstract void updatePlayers(Chip winner);
    private void notifyObs() {
        setChanged();
        notifyObservers(this.getBoard());
    }

    /*
     * checking the winner
     */
    public abstract Chip checkWinner();
    /*
     * checkFour() is used to iterate over the board and find four-in-a-rows
     * this method can be utilized in checkWinner() when defined in child classes
     */
    public Chip checkFour(int row, int col, int ord) {
        Chip checker = (ord == 1) ? Chip.RED : Chip.BLUE;
        // check vertically
        if (row <= (this.getRows() - 4)) {
            if (this.getBoard()[row+1][col].equals(checker) && this.getBoard()[row+2][col].equals(checker) &&
                    this.getBoard()[row+3][col].equals(checker)) {
                return checker;
            }
        }
        // check horizontally
        if (col <= (this.getColumns() - 4)) {
            if (this.getBoard()[row][col+1].equals(checker) && this.getBoard()[row][col+2].equals(checker) &&
                    this.getBoard()[row][col+3].equals(checker)) {
                return checker;
            }
        }
        // check diagonally: top left to bottom right
        if (row <= (this.getRows() - 4) && col <= (this.getColumns() - 4)) {
            if (this.getBoard()[row+1][col+1].equals(checker) && this.getBoard()[row+2][col+2].equals(checker) &&
                    this.getBoard()[row+3][col+3].equals(checker)) {
                return checker;
            }
        }
        // check diagonally: top right to bottom left
        if (row <= (this.getRows() - 4) && col >= 3) {
            if (this.getBoard()[row+1][col-1].equals(checker) && this.getBoard()[row+2][col-2].equals(checker) &&
                    this.getBoard()[row+3][col-3].equals(checker)) {
                return checker;
            }
        }

        // if none of the checks is satisfied, return EMPTY;
        return Chip.EMPTY;
    }

    /*
     * @throws GameStateException if no winner has been established.
     */
    public abstract Chip getWinningPlayer() throws GameStateException;
    
    public abstract Chip getCurrentPlayer();
    public abstract boolean isGameOver();
}