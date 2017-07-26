package api;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;

public interface Controller {
    public int getRow();
    public int getCol();
    public ObjectProperty<Paint>[][] getChips();
    public ObjectProperty<Paint> getCurrentPlayerColor();
    public boolean handlePlaceDisk(int row, int col);
    public Chip handleWinner();
}
