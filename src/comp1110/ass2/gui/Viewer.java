package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    public class RugSegment extends StackPane {
        public static int BOARD_START = RealBoard.BOARD_START;
        public static int GRID_SIZE = RealBoard.GRID_SIZE;

        /**
         * initialization
         *
         * @param position Placed location coordinate
         * @param color    color of rug
         * @param rugId    id of rug
         */
        public RugSegment(Coordinate position, Color color, int rugId) {
            super();
            if (position.x == -1 || position.y == -1) {
                return;
            }
            Rectangle segment = new Rectangle(GRID_SIZE, GRID_SIZE);
            segment.setFill(color);
            segment.setStroke(Color.GREY);
            this.getChildren().add(segment);
            this.getChildren().add(new Label(String.valueOf(rugId)));
            this.setLayoutX(BOARD_START + position.x * GRID_SIZE);
            this.setLayoutY(BOARD_START + position.y * GRID_SIZE);
        }
    }

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
    private final ArrayList<RugSegment> rugSegments = new ArrayList<>();

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
                    Color color = tile.getPlayer().getColor().getPaintColor();
                    RugSegment seg = new RugSegment(new Coordinate(x, y), color, tile.getRugId());
                    realBoard.getChildren().add(seg);
                    rugSegments.add(seg);
                }
            }
        }
    }


    /**
     * Clear the rugs from the UI board
     */
    private void resetRug() {
        for (RugSegment rugSegment : rugSegments) {
            realBoard.getChildren().remove(rugSegment);
        }
        rugSegments.clear();
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
