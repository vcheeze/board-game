package impl.controller;

import api.Game;
import impl.game.Complica;

public class ComplicaController extends GraphicalController {
    public Game getGame() { // return Complica
        return new Complica();
    }
}