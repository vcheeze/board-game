package impl.controller;

import api.Game;
import impl.game.ConnectFour;

public class ConnectFourController extends GraphicalController {
    public Game getGame() { // return ConnectFour
        return new ConnectFour();
    }
}