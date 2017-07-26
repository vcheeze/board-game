package impl.game;

import api.Chip;
import api.Game;
import exc.GameStateException;
import exc.IllegalMoveException;




public class ConnectFour extends Game {
    private int row = 6, col = 7;
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
        for (int i = 0; i < this.getRows(); i++) {
            if (!this.board[row][col].isEmpty()) { // if the row is taken, go one row up
                row--;
                if (row < 0) { // the column is full
                    // place chip and trickle down
                    return false;
                }
            }
            else {
                Chip currentPlayer = getCurrentPlayer();
                this.board[row][col] = currentPlayer; // if row is empty, assign the new Chip
                return true;
            }
        }

        return false;
    }

    public void updatePlayers(Chip winner) { // parameter taken from checkWinner()
        this.winner = winner;
        if (this.winner.isEmpty()) {
            this.current = (this.getCurrentPlayer() == Chip.RED) ? Chip.BLUE : Chip.RED;
        }
        else {
            this.current = Chip.EMPTY;
        }
    }

    public Chip checkWinner() {
        for (int row = 0; row < this.getRows(); row++) {
            for (int col = 0; col < this.getColumns(); col++) {
                if (!this.getBoard()[row][col].isEmpty()) { // if there is a chip inside this slot
                    int ord = this.getBoard()[row][col].ordinal();
                    Chip c = this.checkFour(row, col, ord);
                    if (!c.isEmpty()) {
                        return c; // return whoever has won
                    }
                }
            }
        }

        return Chip.EMPTY;
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
        // check for full board
        int check = 0;
        for (int r = 0; r < this.getRows(); r++) {
            for (int c = 0; c < this.getColumns(); c++) {
                if (board[r][c].isEmpty()) { // if any empty cells is found
                    check++;
                }
            }
        }
        if (check == 0) {
            this.current = Chip.EMPTY;
            return true;
        }

        // if board not full, check for winner
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

    public ConnectFour() {
        // construct an empty board
        for (int row = 0; row < this.row; row++) {
            for (int col = 0; col < this.col; col++) {
                this.board[row][col] = Chip.EMPTY;
            }
        }
    }
}