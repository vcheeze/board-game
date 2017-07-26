package impl.view;

import api.Chip;
import api.Game;
import api.View;
import javafx.beans.value.ObservableValue;

import java.util.Observable;

public class Console extends View {
    private Game g = null;

    @Override
    public void render(Game game) {
        this.g = game;
        for (int i = 0; i < game.getColumns(); i++) {
            System.out.print(" " + Integer.toString(i) + " ");
        }
        System.out.println("");
        for (Chip[] row : game.getBoard()) {
            for (Chip c : row) {
                if (c.isEmpty()) {
                    System.out.print(" . ");
                }
                else if (c.equals(Chip.RED)) {
                    System.out.print(" o ");
                }
                else if (c.equals(Chip.BLUE)) {
                    System.out.print(" x ");
                }
            }
            System.out.println("");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.g) { // check whether the Observable is the game
            render(this.g);
        }
    }
}