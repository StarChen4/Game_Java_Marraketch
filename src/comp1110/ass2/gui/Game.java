package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Program entry
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    // bord UI
    private final RealBoard realBoard = new RealBoard(WINDOW_WIDTH, WINDOW_HEIGHT);
    // assam icon
    private final RealAssam realAssam = new RealAssam(this);
    // draggable rug icon
    private final DraggableRugFlag rugFlag = new DraggableRugFlag(this);
    // dice button
    private final Button diceButton = new RealDice(this);
    // logic board
    private Board board;
    // Counter when assam moves
    private int assamStepCount = 0;
    // list of player objects
    private final ArrayList<Player> players = new ArrayList<>();
    // The index of the currently active player in the player list
    private int currPlayerIndex = 0;
    // The UI segment of rug has been placed
    private final ArrayList<RugSegment> rugSegments = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        // Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().add(realBoard);

        // Set up a new game button on the UI board; press this button to begin or reset a game.
        Button newGameButton = new Button();
        newGameButton.setLayoutX(700);
        newGameButton.setLayoutY(100);
        newGameButton.setText("NewGame");
        newGameButton.setOnAction(event -> newGame());
        root.getChildren().add(newGameButton);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Create a new game or reset current game.
     */
    private void newGame() {
        board = new Board();

        // Get players from logic board to a list,
        // In order to implement the game in the order of players in the list
        players.clear();
        for (PlayerColor color : PlayerColor.values()) {
            char colorChar = color.getColorChar();
            players.add(board.getPlayers().get(colorChar));
        }
        realBoard.setScoreBoard(board.getPlayers());

        // Start the game with player 0
        currPlayerIndex = 0;

        // Set the initial state of assam
        Assam assam = board.getAssam();
        realAssam.move(assam.getPosition(), assam.getFacing());
        if (!root.getChildren().contains(realAssam)) {
            root.getChildren().add(realAssam);
        }

        // Remove existing rugs from the board
        for (RugSegment rugSegment : rugSegments) {
            realBoard.getChildren().remove(rugSegment);
        }
        rugSegments.clear();

        // Step 1. Enable rotate assam, enable the dice button and wait for the currently active player to roll the dice
        enableRotateAssamAndRollDie();
    }

    /**
     * Enable rotate assam
     * Sets the color of the rug icon to the color of the currently active player
     * Enable the dice button and wait for the currently active player to roll the dice
     */
    public void enableRotateAssamAndRollDie() {
        Player player = players.get(currPlayerIndex);

        // enable click assam to rotate
        realAssam.setClickable(true);

        // Sets the color of the rug icon to the color of the currently active player
        // Disable dragging of rug icon
        if (!root.getChildren().contains(rugFlag)) {
            root.getChildren().add(rugFlag);
        }
        rugFlag.setFill(player.getColor().getPainColor());
        rugFlag.setDraggable(false);

        // Enable the dice button
        if (!root.getChildren().contains(diceButton)) {
            root.getChildren().add(diceButton);
        }
        diceButton.setDisable(false);
        diceButton.setText("Dice");

        // if player type  is not Human, then Auto rotate assam and click dice button
        String playerType = realBoard.getPlayerType(player);
        if (!"Human".equals(playerType)) {
            realAssam.setClickable(false);
            int degrees = 0;
            if ("AI".equals(playerType)) {
                degrees = board.getAssamRotateAI(player);
            } else {
                degrees = board.getRandomAssamRotate();
            }
            rotateAssam(degrees);
            diceButton.fire();
        }
    }

    /**
     * Call back method, receive degrees of assam rotate
     *
     * @param degrees degrees of rotate
     */
    public void receiveAssamRotate(int degrees) {
        rotateAssam(degrees);
    }

    /**
     * Rotate assam
     * @param degrees degrees of rotate
     */
    public void rotateAssam(int degrees) {
        Assam assam = board.getAssam();
        assam.rotate(degrees);
        realAssam.move(assam.getPosition(), assam.getFacing());
    }


    /**
     * Call back method, receive point of the dice from realDic
     *
     * @param point point of dice
     */
    public void receiveDicePoint(int point) {
        // Step 2. Move the assam according to the number of dice
        moveAssam(point);
    }

    /**
     * Move Assam
     *
     * @param step move step
     */
    private void moveAssam(int step) {
        // Disable click assam
        realAssam.setClickable(false);
        // Move assam on the logic board
        board.getAssam().move(step);
        List<Assam> moveRecords = board.getAssam().getLastMoveRecords();

        // Animated effects on UI board
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Assam a = moveRecords.get(assamStepCount);
            realAssam.move(a.getPosition(), a.getFacing());
            assamStepCount += 1;
        }));
        timeline.setCycleCount(moveRecords.size());
        timeline.setOnFinished(event -> {
            assamStepCount = 0;

            // Payment between players， Update scoreboard
            board.payment(players.get(currPlayerIndex));
            realBoard.setScoreBoard(board.getPlayers());

            // Step 3. Enable dragging of rug icon and wait for the currently active player to place a rug.
            enableDragRug();
        });
        timeline.play();
    }

    /**
     * Enable dragging of rug icon
     */
    public void enableDragRug() {
        rugFlag.setDraggable(true);
        // Hidden dice
        root.getChildren().remove(diceButton);

        // if player type  is random or AI, then auto place a rug
        Player player = players.get(currPlayerIndex);
        String playerType = realBoard.getPlayerType(player);
        if (!"Human".equals(playerType)) {
            Rug rug;
            if ("AI".equals(playerType)) {
                rug = board.getBestRug(player);
            } else {
                rug = board.getRandomValidRug();
            }
            placeRug(rug.getSeg1(), rug.getSeg2());
        }
    }

    /**
     * Call back method, receive the position where the rug will be placed from draggableRugFlag
     *
     * @param position1 The position of the section 1 of rug
     * @param position2 The position of the section 2 of rug
     */
    public void receiveRugPos(Coordinate position1, Coordinate position2) {
        // Step 4. Place rug based on player selected position
        placeRug(position1, position2);
    }

    /**
     * Put a rug to the board.
     *
     * @param position1 The position of the section 1 of rug
     * @param position2 The position of the section 2 of rug
     */
    public void placeRug(Coordinate position1, Coordinate position2) {
        Player player = players.get(currPlayerIndex);
        // Place a rug on the logic board
        Rug rug = board.placeRug(player, position1, position2);
        // if rug is null, It means that the position selected by the player is illegal and needs to be reselected.
        if (rug != null) {
            // The rug is divided into two sections and placed on the UI board and Update scoreboard
            RugSegment seg1 = new RugSegment(position1, player.getColor().getPainColor(), rug.getId());
            RugSegment seg2 = new RugSegment(position2, player.getColor().getPainColor(), rug.getId());
            realBoard.getChildren().add(seg1);
            realBoard.getChildren().add(seg2);
            rugSegments.add(seg1);
            rugSegments.add(seg2);
            realBoard.setScoreBoard(board.getPlayers());

            // Switch to next player
            do {
                currPlayerIndex++;
                if (currPlayerIndex > 3) {
                    currPlayerIndex = 0;
                }
            } while (!players.get(currPlayerIndex).isInGame());

            // if game over
            if (board.isGameOver()) {
                rugFlag.setFill(Color.WHITE);
                char winner = board.getWinner();
                Color winnerColor = null;
                if (winner != 't') {
                    winnerColor = board.getPlayers().get(winner).getColor().getPainColor();
                }
                root.getChildren().add(new GameOver(winnerColor));
                return;
            }

            // Start next player action
            // Step 1. Enable the dice button and wait for the currently active player to roll the dice
            enableRotateAssamAndRollDie();
        }
    }

    /**
     * Get UI board handle
     *
     * @return UI board
     */
    public RealBoard getRealBoard() {
        return realBoard;
    }
}
