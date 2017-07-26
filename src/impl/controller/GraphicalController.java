package impl.controller;


import api.Controller;
import api.Chip;
import api.Game;
import exc.GameStateException;
import exc.IllegalMoveException;
import impl.game.ConnectFour;
import impl.game.Complica;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;




public abstract class GraphicalController implements Controller {
    private Game model;
    private int row, col;
    private ObjectProperty<Paint>[][] chips;
    private ObjectProperty<Paint> cp; // Color for current player and next player

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public ObjectProperty<Paint>[][] getChips() {
        return this.chips;
    }


    public ObjectProperty<Paint> getCurrentPlayerColor() {
        return this.cp;
    }

    public void updateBoard() {
        for (int r = 0; r < this.row; r++) {
            for (int c = 0; c < this.col; c++) {
                Chip[][] board = this.model.getBoard();
                if (board[r][c].isEmpty()) {
                    this.chips[r][c].setValue(Color.WHITE);
                }
                else if (board[r][c] == Chip.RED) {
                    this.chips[r][c].setValue(Color.RED);
                }
                else if (board[r][c] == Chip.BLUE) {
                    this.chips[r][c].setValue(Color.BLUE);
                }
            }
        }
    }

    public boolean handlePlaceDisk(int row, int col) {
        try {
            this.model.placeDisk(col);
            updateBoard();
            Chip current = this.model.getCurrentPlayer();

            if (current == Chip.RED) {
                this.cp.setValue(Color.RED);
            }
            else if (current == Chip.BLUE) {
                this.cp.setValue(Color.BLUE);
            }

            return true;
        }
        catch (GameStateException | IllegalMoveException e) {
            return false;
        }
    }

    public Chip handleWinner() {
        try {
            Chip winner = this.model.getWinningPlayer();
            return winner;
        }
        catch (GameStateException e) {
            return null;
        }
    }

    /*
     * Call to return the correct type of game
     */
    public abstract Game getGame();

    public GraphicalController() {
        this.model = this.getGame();
        this.row = this.model.getRows();
        this.col = this.model.getColumns();

        // instantiate ObjectProperties for the board
        this.chips = new ObjectProperty[this.row][this.col];
        for (int r = 0; r < this.row; r++) {
            for (int c = 0; c < this.col; c++) {
                this.chips[r][c] = new SimpleObjectProperty<>();
                this.chips[r][c].setValue(Color.WHITE);
            }
        }
        this.cp = new SimpleObjectProperty<>();
    }
}