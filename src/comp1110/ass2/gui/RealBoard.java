package comp1110.ass2.gui;

import comp1110.ass2.Coordinate;
import comp1110.ass2.Player;
import comp1110.ass2.PlayerColor;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * Description: UI board.
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class RealBoard extends Group {
    private final String BOARD_IMAGE = "file:assets/images/BoardAndGrid.png";
    private static int WINDOW_WIDTH;

    public static int TILE_ROW = 7;
    public static int GROUND_SIZE;
    public static int BOARD_SIZE;
    public static int BOARD_START;
    public static int GRID_SIZE;

    private final HashMap<Character, Label> rugLabels = new HashMap<>();
    private final HashMap<Character, Label> dirhamsLabels = new HashMap<>();
    private final HashMap<Character, Label> inGameLabels = new HashMap<>();
    private final HashMap<Character, ComboBox<String>> typeComboBoxes = new HashMap<>();

    // Highlight effect
    private final Rectangle[] highLightSquare = new Rectangle[2];

    public String getPlayerType(Player player) {
        ComboBox<String> comboBox = typeComboBoxes.get(player.getColor().getColorChar());
        return  comboBox.getValue();
    }

    /**
     * initialization
     *
     * @param windowWidth  width of window
     * @param windowHeight height of  window
     */
    RealBoard(int windowWidth, int windowHeight) {
        WINDOW_WIDTH = windowWidth;
        GROUND_SIZE = windowHeight - 60;
        BOARD_SIZE = GROUND_SIZE - 150;
        BOARD_START = (GROUND_SIZE - BOARD_SIZE) / 2;
        GRID_SIZE = BOARD_SIZE / TILE_ROW;
        makeBoard();

        for (int i = 0; i < 2; i++) {
            highLightSquare[i] = new Rectangle(GRID_SIZE, GRID_SIZE);
            highLightSquare[i].setStroke(Color.GREY);
            highLightSquare[i].setFill(Color.GREY);
            highLightSquare[i].setOpacity(0.5);
        }
    }


    /**
     * Highlight the location nearest to the rug icon
     *
     * @param pointX        The x-axis where the rug icon is located
     * @param pointY        The y-axis where the rug icon is located
     * @param isHorizontal  the rug icon is horizontal
     * @param lightPosition The coordinates of the highlighted position returned
     */
    public void highLightNearest(double pointX, double pointY, boolean isHorizontal, Coordinate[] lightPosition) {

        // Not within the valid range of the board
        if (pointX < BOARD_START || pointX > BOARD_START + TILE_ROW * GRID_SIZE ||
                pointY < BOARD_START - GRID_SIZE * 0.5 || pointY > BOARD_START + TILE_ROW * GRID_SIZE){
            return;
        }

        // enable high light square
        if (!this.getChildren().contains(highLightSquare[0])) {
            this.getChildren().add(highLightSquare[0]);
            this.getChildren().add(highLightSquare[1]);
        }

        // compute the squares position
        int matrixColumn1, matrixColumn2, matrixRow1, matrixRow2;
        if (isHorizontal) {
            matrixColumn1 = Math.min(TILE_ROW - 2, (int) ((pointX - BOARD_START) / GRID_SIZE));
            matrixRow1 = Math.min(TILE_ROW - 1, (int) ((pointY - BOARD_START) / GRID_SIZE + 1));
            matrixColumn2 = matrixColumn1 + 1;
            matrixRow2 = matrixRow1;
        } else {
            matrixColumn1 = Math.min(TILE_ROW - 1, (int) ((pointX - BOARD_START) / GRID_SIZE + 0.5));
            matrixRow1 = Math.min(TILE_ROW - 2, (int) ((pointY - BOARD_START) / GRID_SIZE + 0.5));
            matrixColumn2 = matrixColumn1;
            matrixRow2 = matrixRow1 + 1;
        }

        highLightSquare[0].setLayoutX(BOARD_START + matrixColumn1 * GRID_SIZE);
        highLightSquare[0].setLayoutY(BOARD_START + matrixRow1 * GRID_SIZE);
        highLightSquare[1].setLayoutX(BOARD_START + matrixColumn2 * GRID_SIZE);
        highLightSquare[1].setLayoutY(BOARD_START + matrixRow2 * GRID_SIZE);

        lightPosition[0] = new Coordinate(matrixColumn1, matrixRow1);
        lightPosition[1] = new Coordinate(matrixColumn2, matrixRow2);
    }

    /**
     * cancel high light effect
     */
    public void cancelHighLightNearest() {
        if (this.getChildren().contains(highLightSquare[0])) {
            this.getChildren().remove(highLightSquare[1]);
            this.getChildren().remove(highLightSquare[0]);
        }
    }

    /**
     * set scoreboard for each player
     *
     * @param players players of game
     */
    public void setScoreBoard(HashMap<Character, Player> players) {
        for (Player player : players.values()) {
            char pChar = player.getColor().getColorChar();
            rugLabels.get(pChar).setText(String.valueOf(player.getRugsAmount()));
            dirhamsLabels.get(pChar).setText(String.valueOf(player.getDirhamsAmount()));
            inGameLabels.get(pChar).setText(String.valueOf(player.isInGame()));
        }
    }

    /**
     * Creates the board for the game.
     */
    private void makeBoard() {

        final double board_size = 640;
        // use image as game board
        Image boardImage = new Image(BOARD_IMAGE);
        ImageView board = new ImageView(boardImage);
        board.setFitWidth(board_size);
        board.setFitHeight(board_size);
        this.getChildren().add(board);

        // Add status display panel
        GridPane playersTable = new GridPane();
        playersTable.add(new Label("Type"), 0, 0);
        playersTable.add(new Label("Player"), 1, 0);
        playersTable.add(new Label("Rugs"), 2, 0);
        playersTable.add(new Label("Dirhams"), 3, 0);
        playersTable.add(new Label("InGame"), 4, 0);
        int tableRow = 1;
        for (PlayerColor color : PlayerColor.values()) {
            char colorChar = color.getColorChar();

            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Human", "Random", "AI");
            comboBox.setValue("Human");
            playersTable.add(comboBox, 0, tableRow);
            typeComboBoxes.put(colorChar, comboBox);

            Rectangle colorFlag = new Rectangle(0, 0, 20, 10);
            colorFlag.setFill(color.getPaintColor());
            playersTable.add(colorFlag, 1, tableRow);

            Label rugLabel = new Label("15");
            playersTable.add(rugLabel, 2, tableRow);
            rugLabels.put(colorChar, rugLabel);

            Label dirhamsLabel = new Label("30");
            playersTable.add(dirhamsLabel, 3, tableRow);
            dirhamsLabels.put(colorChar, dirhamsLabel);

            Label inGameLabel = new Label("True");
            playersTable.add(inGameLabel, 4, tableRow);
            inGameLabels.put(colorChar, inGameLabel);

            playersTable.getRowConstraints().add(new RowConstraints(30));
            tableRow++;
        }
        this.getChildren().add(playersTable);

        ColumnConstraints c100 = new ColumnConstraints(100);
        ColumnConstraints c60 = new ColumnConstraints(60);
        playersTable.getColumnConstraints().addAll(c100, c60, c60, c60, c60);
        playersTable.setLayoutX(WINDOW_WIDTH - 360);
        playersTable.setLayoutY(20);
    }
}
