package impl.view;


import api.Chip;
import api.Controller;

import impl.controller.ComplicaController;
import impl.controller.ConnectFourController;
import impl.controller.GraphicalController;

import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class Graphical extends Application {
    private Controller controller;
    private Circle current, winner;

    public void init() {
        String gameType = getParameters().getRaw().get(0); // get the parameter - type of the game
        // factory method --> deciding which controller to use
        if (gameType.equalsIgnoreCase("ConnectFour")) {
            this.controller = new ConnectFourController();
        }
        else if (gameType.equalsIgnoreCase("Complica")) {
            this.controller = new ComplicaController();
        }
    }

    // panel for current and winning player
    private VBox addVBox() {
        VBox v = new VBox();
        v.setPadding(new Insets(20));

        // current player
        Text curPlayer = new Text();
        curPlayer.setText("Current Player:");
        this.current = new Circle(15);
        this.current.setFill(Color.WHITE);
        this.current.setStroke(Color.BLACK);
        this.current.fillProperty().bind(this.controller.getCurrentPlayerColor());

        // winning player
        Text winPlayer = new Text();
        winPlayer.setText("Winning Player:");
        this.winner = new Circle(15);
        this.winner.setFill(Color.WHITE);
        this.winner.setStroke(Color.BLACK);

        v.getChildren().addAll(curPlayer, this.current, winPlayer, this.winner);

        return v;
    }

    // disable clicking and reset current player when game is over
    private void closeBoard(GridPane b) {
        ObservableList<Node> children = b.getChildren();

        // disable all clicking
        for (int i = 0; i < children.size(); i++) {
            Circle c = (Circle) children.get(i);
            c.setOnMouseClicked(null);
        }

        // reset current player
        this.current.fillProperty().unbind();
        this.current.setFill(Color.WHITE);
    }

    // the board with chips
    private GridPane addBoard() {
        GridPane board = new GridPane();

        // add circles to the board
        for (int row = 0; row < this.controller.getRow(); row++) {
            for (int col = 0; col < this.controller.getCol(); col++) {
                Circle circle = new Circle(30);
                circle.setFill(Color.WHITE); // color of the circle
                circle.setStroke(Color.BLACK); // outline of the circle
                circle.fillProperty().bind(this.controller.getChips()[row][col]);
                board.add(circle, col, row);
                circle.setOnMouseClicked(e -> { // mouse click event handler
                    Circle c = (Circle) e.getSource();

                    if (this.controller.handlePlaceDisk(GridPane.getRowIndex(c), GridPane.getColumnIndex(c))) {
                        Chip winnerChip = this.controller.handleWinner();
                        if (winnerChip != null) {
                            if (winnerChip == Chip.RED) {
                                this.winner.setFill(Color.RED);
                            }
                            else if (winnerChip == Chip.BLUE) {
                                this.winner.setFill(Color.BLUE);
                            }
                            this.closeBoard(board); // disable any further clicking
                        }
                    }
                });
            }
        }

        return board;
    }

    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();

        root.getChildren().add(addVBox());
        root.getChildren().add(addBoard());

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args[0]); // takes either "Complica" or "ConnectFour"
    }
}