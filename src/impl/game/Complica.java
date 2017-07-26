package impl.game;

import api.Chip;
import api.Game;
import exc.GameStateException;
import exc.IllegalMoveException;




public class Complica extends Game {
    private int row = 7, col = 4, redCount = 0, bluecCount = 0;
    private Chip[][] board = new Chip[this.row][this.col];
    private Chip winner = Chip.EMPTY;
    private Chip current = Chip.RED;


    @Override
    public int getRows() {
        return this.row;
    }

    @Override
    public int getColumns() {
        return this.col;
    }

    @Override
    public Chip[][] getBoard() {
        return this.board;
    }

    public boolean actualPlace(int row, int col) {
        Chip currentPlayer = this.getCurrentPlayer();
        for (int i = 0; i < this.getRows(); i++) {
            if (!this.board[row][col].isEmpty()) { // if the row is taken, go one row up
                row--;
                if (row < 0) { // if column is full
                    // trickle down and change color of each chip
                    for (int r = this.getRows() - 1; r > 0; r--) {
                        this.board[r][col] = this.board[r-1][col];
                    }
                    // place the new chip
                    this.board[0][col] = currentPlayer;
                    return true;
                }
            }
            else {
                this.board[row][col] = currentPlayer; // if row is empty, assign the new Chip
                return true;
            }
        }

        return false;
    }

    public void updatePlayers(Chip winner) {
        this.winner = winner;
        if (this.winner.isEmpty()) {
            this.current = (this.getCurrentPlayer() == Chip.RED) ? Chip.BLUE : Chip.RED;
        }
    }

    public Chip checkWinner() {
        for (int row = 0; row < this.getRows(); row++) {
            for (int col = 0; col < this.getColumns(); col++) {
                if (!this.getBoard()[row][col].isEmpty()) { // if there is a chip inside this slot
                    int ord = this.getBoard()[row][col].ordinal();
                    Chip c = checkFour(row, col, ord);
                    if (c.equals(Chip.RED)) {
                        this.redCount++;
                    }
                    else if (c.equals(Chip.BLUE)) {
                        this.bluecCount++;
                    }
                }
            }
        }

        if (this.redCount > this.bluecCount) {
            return Chip.RED;
        }
        else if (this.bluecCount > this.redCount) {
            return Chip.BLUE;
        }
        else {
            return Chip.EMPTY;
        }
    }

    public Chip getWinningPlayer() throws GameStateException {
        if (this.winner.isEmpty()) {
            throw new GameStateException("No winner yet");
        }
        return this.winner;
    }

    public Chip getCurrentPlayer() {
	    return this.current;
    }

    public boolean isGameOver() {
        // check for winner
        try {
            if (!this.getWinningPlayer().isEmpty()) {
                this.current = Chip.EMPTY;
                return true;
            }
        }
        catch (GameStateException e) {
            return false;
        }

        return false;
    }

    public Complica() {
        // construct an empty board
        for (int row = 0; row < this.row; row++) {
            for (int col = 0; col < this.col; col++) {
                this.board[row][col] = Chip.EMPTY;
            }
        }
    }
}