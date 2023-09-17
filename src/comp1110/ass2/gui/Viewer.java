package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class Viewer extends Application {
    /**
     * This class can draw the Assam and control it(rotate and move)
     */
    public class RealAssam extends Polygon {
        private Assam assam = new Assam();

        /**
         * This is used to draw the Assam.
         */
        RealAssam() {
            super();
            this.getPoints().addAll(0., 0., 30., 30., 0., -30., -30., 30.0, 0., 0.);
            this.setFill(Color.web("#0c8918"));
            setStatus("A33N");
        }

        /**
         * This is used to set rotate and layout of Assam.
         * @param state String of state.
         */
        void setStatus(String state) {
            assam.setStatus(state);
            this.setLayoutX(BOARD_START + (assam.positionX + 0.5) * GRID_SIZE);
            this.setLayoutY(BOARD_START + (assam.positionY + 0.5) * GRID_SIZE);
            this.setRotate(assam.facing.getDegrees());
        }
    }

    /**
     * This is used to draw segment of rug.
     */
    public class RugSegment extends StackPane {
        /**
         * This is used to draw segment of rug.
         * @param positionX The X coordinates of the board
         * @param positionY The Y coordinates of the board
         * @param color The color of the rug.
         * @param id The id of the rug.
         */
        public RugSegment(int positionX, int positionY, Color color, int id) {
            super();
            if(positionX == -1 || positionY == -1) {
                return;
            }
            Rectangle segment = new Rectangle(GRID_SIZE, GRID_SIZE);
            segment.setFill(color);
            segment.setStroke(Color.GREY);
            this.getChildren().add(segment);
            this.getChildren().add(new Label(String.valueOf(id)));
            this.setLayoutX(BOARD_START + positionX * GRID_SIZE);
            this.setLayoutY(BOARD_START + positionY * GRID_SIZE);
        }
    }
    // Width of the window
    private static final int VIEWER_WIDTH = 1200;

    // Height of the window
    private static final int VIEWER_HEIGHT = 700;

    // Size of ground Width = Height.
    private static final int GROUND_SIZE = VIEWER_HEIGHT - 60;

    //Size of board. Width = Height.
    private static final int BOARD_SIZE = GROUND_SIZE - 150;
    private static final int BOARD_START = (GROUND_SIZE - BOARD_SIZE) / 2;
    private static final int TILE_ROW = 7;
    private static final int GRID_SIZE = BOARD_SIZE / TILE_ROW;


    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group board = new Group();
    private TextField boardTextField;

    private final Tile[][] boardMatrix = new Tile[TILE_ROW][TILE_ROW];

    private final RealAssam realAssam = new RealAssam();
    private final HashMap<Character, Player> players = new HashMap<>();
    private final HashMap<Character, Label> rugLabels = new HashMap<>();
    private final HashMap<Character, Label> dirhamsLabels = new HashMap<>();
    private final HashMap<Character, Label> inGameLabels = new HashMap<>();
    private final ArrayList<RugSegment> rugSegments = new ArrayList<>();


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        // FIXME Task 5: implement the simple state viewer
        if (state.length() == 0) {
            resetRug();
            return;
        }
        int cutLen = 0;
        switch (state.charAt(0)) {
            case 'A' -> { // Assam
                cutLen = 4;
                if (state.length() <cutLen){
                    return;
                }
                realAssam.setStatus(state.substring(0, cutLen));
            }
            case 'P' -> { // Player
                cutLen = 8;
                if (state.length() <cutLen){
                    return;
                }
                String playerString = state.substring(0, cutLen);
                char colorChar = playerString.charAt(1);
                Player player = players.get(colorChar);
                player.setStatus(playerString);
                dirhamsLabels.get(colorChar).setText(String.valueOf(player.getDirhamsAmount()));
                rugLabels.get(colorChar).setText(String.valueOf(player.getRugsAmount()));
                inGameLabels.get(colorChar).setText(String.valueOf(player.isInGame()));
            }
            case 'c', 'y', 'r', 'p' -> { // Rug
                cutLen = 7;
                if (state.length() <cutLen){
                    return;
                }
                String rugString = state.substring(0, cutLen);
                Player p = players.get(rugString.charAt(0));
                Rug rug = new Rug(rugString);
                HashMap<Integer, Rug> rugs = p.getRugs();
                if (!rugs.containsKey(rug.getId())) {
                    int rugId = rug.getId();
                    rugs.put(rugId, rug);
                    Color color = p.getColor().getPainColor();
                    RugSegment segment1 = new RugSegment(rug.getSeg1X(), rug.getSeg1Y(), color, rugId);
                    RugSegment segment2 = new RugSegment(rug.getSeg2X(), rug.getSeg2Y(), color, rugId);
                    board.getChildren().addAll(segment1, segment2);
                    rugSegments.add(segment1);
                    rugSegments.add(segment2);
                }
            }
            case 'B' -> { // Board
                cutLen = 1 + 3 * 7 * 7;
                if (state.length() <cutLen){
                    return;
                }
                resetRug();
                String boardStr = state.substring(1);
                for (int x = 0; x < TILE_ROW; x++) {
                    for (int y = 0; y < TILE_ROW; y++) {
                        int begin = (TILE_ROW * x + y) * 3;
                        // new tile with player and rug id
                        String rugString = boardStr.substring(begin, begin + 3);
                        Player p = players.get(rugString.charAt(0));
                        boardMatrix[x][y] = new Tile(rugString, p);
                        if (!rugString.contains("n00")) {
                            Rug rug = new Rug(rugString);
                            HashMap<Integer, Rug> rugs = p.getRugs();
                            // exist a rug in rugs list of the player
                            if (rugs.containsKey(rug.getId())) {
                                rug = rugs.get(rug.getId());
                            } else {
                                rugs.put(rug.getId(), rug);
                            }
                            if (rug.getSeg1X() == -1) {
                                rug.setSegment1(x, y);
                            } else {
                                rug.setSegment2(x, y);
                            }
                            RugSegment segment = new RugSegment(x, y, p.getColor().getPainColor(), rug.getId());
                            board.getChildren().add(segment);
                            rugSegments.add(segment);
                        }
                    }
                }
            }
            default -> System.out.println("Invalid string: " + state);
        }
        // recursion
        if (cutLen != 0 && cutLen < state.length()) {
            displayState(state.substring(cutLen));
        }

    }


    /**
     *This is used to clear the all the rug in the board.
     */
    private void resetRug() {
        if (rugSegments.size() == 0) {
            return;
        }
        for (Player player : players.values()) {
            player.getRugs().clear();
        }
        for (RugSegment rugSegment : rugSegments) {
            board.getChildren().remove(rugSegment);
        }
        rugSegments.clear();
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    /**
     * Creates the board for the game.
     */
    private void makeBoard() {

        // Draw backGround
        Rectangle ground = new Rectangle(0, 0, GROUND_SIZE, GROUND_SIZE);
        ground.setFill(Color.web("#FFFFEE"));
        board.getChildren().add(ground);

        // Draw track on the edge of the board
        Color circleColor = Color.web("#FFCC99");
        for (int i = 0; i < 4; i++) {
            Circle circle = new Circle(BOARD_START + (1 + 2 * i) * GRID_SIZE, BOARD_START,
                    GRID_SIZE / 2., circleColor);
            board.getChildren().add(circle);
            circle = new Circle(BOARD_START + 2 * i * GRID_SIZE, BOARD_START + BOARD_SIZE,
                    GRID_SIZE / 2., circleColor);
            board.getChildren().add(circle);
            circle = new Circle(BOARD_START, BOARD_START + (1 + 2 * i) * GRID_SIZE,
                    GRID_SIZE / 2., circleColor);
            board.getChildren().add(circle);
            circle = new Circle(BOARD_START + BOARD_SIZE, BOARD_START + 2 * i * GRID_SIZE,
                    GRID_SIZE / 2., circleColor);
            board.getChildren().add(circle);

        }

        // Draw grid of the board
        Color lineColor = Color.GREY;
        double lineWidth = 3.0;
        Rectangle border = new Rectangle(BOARD_START, BOARD_START, BOARD_SIZE, BOARD_SIZE);
        border.setStroke(lineColor);
        border.setStrokeWidth(lineWidth);
        border.setFill(Color.web("#FF9900"));
        board.getChildren().add(border);
        for (int i = 1; i < 7; i++) {
            Line line = new Line(BOARD_START, BOARD_START + GRID_SIZE * i,
                    BOARD_START + BOARD_SIZE, BOARD_START + GRID_SIZE * i);
            line.setStrokeWidth(3);
            line.setStroke(lineColor);
            board.getChildren().add(line);

            line = new Line(BOARD_START + GRID_SIZE * i, BOARD_START,
                    BOARD_START + GRID_SIZE * i, BOARD_START + BOARD_SIZE);
            line.setStrokeWidth(lineWidth);
            line.setStroke(lineColor);
            board.getChildren().add(line);
        }

        // Add status display panel
        GridPane playersTable = new GridPane();
        playersTable.add(new Label("Player"), 0, 0);
        playersTable.add(new Label("Rugs"), 1, 0);
        playersTable.add(new Label("Dirhams"), 2, 0);
        playersTable.add(new Label("InGame"), 3, 0);
        int tableRow = 1;
        for (PlayerColor color : PlayerColor.values()) {
            Player player = new Player(color, 30, 15, true);
            char colorChar = color.getColorChar();
            players.put(colorChar, player);
            Rectangle colorFlag = new Rectangle(0, 0, 20, 10);
            colorFlag.setFill(player.getColor().getPainColor());
            playersTable.add(colorFlag, 0, tableRow);

            Label rugLabel = new Label(String.valueOf(player.getRugsAmount()));
            rugLabels.put(colorChar, rugLabel);
            playersTable.add(rugLabel, 1, tableRow);
            Label dirhamsLabel = new Label(String.valueOf(player.getDirhamsAmount()));
            dirhamsLabels.put(colorChar, dirhamsLabel);
            playersTable.add(dirhamsLabel, 2, tableRow);
            Label inGameLabel = new Label(String.valueOf(player.isInGame()));
            inGameLabels.put(colorChar, inGameLabel);
            playersTable.add(inGameLabel, 3, tableRow);

            playersTable.getRowConstraints().add(new RowConstraints(15));
            tableRow++;
        }
        board.getChildren().add(playersTable);
        ColumnConstraints columnConstraints = new ColumnConstraints(60);
        playersTable.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints, columnConstraints);
        playersTable.setLayoutX(VIEWER_WIDTH - 230);
        playersTable.setLayoutY(20);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);
        root.getChildren().add(board);
        //Draw a board.
        makeBoard();
        makeControls();

        root.getChildren().add(realAssam);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
