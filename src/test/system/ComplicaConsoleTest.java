package test.system;

import api.Chip;
import api.View;
import api.Game;
import exc.GameStateException;
import exc.IllegalMoveException;
import impl.view.Console;
import impl.game.Complica;

import java.util.Scanner;

public class ComplicaConsoleTest {
    public static void main(String[] args) {
		Game game = new Complica();
		View view = new Console();
		game.addObserver(view);
		view.render(game);


		/*
		 * Code to make your game interact should go here. Feel free
		 * to alter this code depending on your implementation (what
		 * is here is just an example).
		 */

		while (!game.isGameOver()) {
			Chip player = game.getCurrentPlayer();
			System.out.println(player + "'s turn\nPlease enter a column: ");
			Scanner input = new Scanner(System.in);
			int col = input.nextInt();

			// place the chip
			try {
				game.placeDisk(col);
			}
			catch (GameStateException | IllegalMoveException e) {
				System.out.println("It was a tie.");
			}

			try {
				Chip winner = game.getWinningPlayer();
				System.out.println(winner + " wins!");
				break;
			}
			catch (GameStateException e) {
				System.out.println("Keep playing...");
			}
		}
    }
}