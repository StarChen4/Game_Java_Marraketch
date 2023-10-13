package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Description: Some useful static methods.
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    // Logic bord
    private Board board = new Board();
    // UI board
    private final RealBoard realBoard = new RealBoard(VIEWER_WIDTH, VIEWER_HEIGHT);
    private static final int TILE_ROW = RealBoard.TILE_ROW;

    // UI assam
    private final RealAssam realAssam = new RealAssam(null);
    // The UI segment of rug has been placed
    private final ArrayList<RealRug> RealRugs = new ArrayList<>();

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField boardTextField;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        // Task 5: implement the simple state viewer

        if (state.length() == 0) {
            resetRug();
            return;
        }

        // set UI assam position and direction
        board.setGameStatus(state);
        Assam assam = board.getAssam();
        realAssam.move(assam.getPosition(), assam.getFacing());

        // set scoreboard for each player
        realBoard.setScoreBoard(board.getPlayers());

        // set UI rugs
        for (int x = 0; x < TILE_ROW; x++) {
            for (int y = 0; y < TILE_ROW; y++) {
                Tile tile = board.getBoardMatrix()[x][y];
                // tile covered with rug
                if (tile.getRugId() != -1) {
                    PlayerColor color = tile.getPlayer().getColor();
                    RealRug seg = new RealRug(new Coordinate(x, y), color, tile.getRugId());
                    realBoard.getChildren().add(seg);
                    RealRugs.add(seg);
                }
            }
        }
    }


    /**
     * Clear the rugs from the UI board
     */
    private void resetRug() {
        for (RealRug realRug : RealRugs) {
            realBoard.getChildren().remove(realRug);
        }
        RealRugs.clear();
        board = new Board();
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(e -> displayState(boardTextField.getText()));
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);
        root.getChildren().add(realBoard);
        root.getChildren().add(realAssam);

        // Set the initial state of the UI assam
        Assam assam = board.getAssam();
        realAssam.move(assam.getPosition(), assam.getFacing());

        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
