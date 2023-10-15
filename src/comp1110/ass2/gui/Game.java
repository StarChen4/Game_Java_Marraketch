package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Stage stage;

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    // bord UI
    private final RealBoard realBoard = new RealBoard(WINDOW_WIDTH, WINDOW_HEIGHT);
    // draggable rug
    private DraggableRugFlag rugFlag;
    // assam icon
    private final RealAssam realAssam = new RealAssam(this);
    // dice button
    private final RealDice diceButton = new RealDice(this);
    // logic board
    private Board board;
    // Counter when assam moves
    private int assamStepCount = 0;
    // list of player objects
    private final ArrayList<Player> players = new ArrayList<>();
    // The index of the currently active player in the player list
    private int currPlayerIndex = 0;
    // The UI segment of rug has been placed
    private final ArrayList<RealRug> RealRugs = new ArrayList<>();
//    private final String BGM = "file:///C:/Users/84208/IdeaProjects/comp1110-ass2/assets/Audio/BackgroundMusic.mp3";
    private final String BGM = "file:assets/Audio/BackgroundMusic.mp3";




    @Override
    public void start(Stage stage) {
        this.stage = stage;
        //BGM
        AudioClip bgm = new AudioClip(BGM);
        bgm.setCycleCount(AudioClip.INDEFINITE);// play infinitely
        bgm.setVolume(0.5);// set volume
        bgm.play();

        // Background
        Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT);
        background.setFill(Color.WHITE);
        this.root.getChildren().add(background);




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

        // Set a rules button on the UI board; press this button to show the rule
        Button ruleButton = new Button("Rule");
        ruleButton.setLayoutX(700);
        ruleButton.setLayoutY(150);
        ruleButton.setOnAction(event -> showRule());
        root.getChildren().add(ruleButton);

        this.stage.setScene(scene);
        this.stage.show();
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
        for (RealRug realRug : RealRugs) {
            realBoard.getChildren().remove(realRug);
        }
        RealRugs.clear();

        rugFlag = new DraggableRugFlag(this, this.getCurrentPlayer().getColor());

        // Step 1. Enable rotate assam, enable the dice button and wait for the currently active player to roll the dice
        enableRotateAssamAndRollDie();
    }

    private void showRule(){
        // rule window
        Group ruleWindow = new Group();
        ruleWindow.setLayoutX(WINDOW_WIDTH / 3.0);
        ruleWindow.setLayoutY(WINDOW_HEIGHT / 3.0);
        // rule text
        Image rule = new Image();
        ImageView ruleImage = new ImageView(rule);

        ruleWindow.getChildren().add(ruleImage);
        // close button at right-top corner, clicking will remove the window from game
        Button closeButton = new Button("×");
        closeButton.setLayoutX(300);
        closeButton.setLayoutY(300);
        closeButton.setTextFill(Color.RED);
        closeButton.setOnAction(event -> this.root.getChildren().remove(ruleWindow));
        ruleWindow.getChildren().add(closeButton);
        // display
        this.root.getChildren().add(ruleWindow);

    }

    /**
     * Enable rotate assam
     * Sets the color of the rug icon to the color of the currently active player
     * Enable the dice button and wait for the currently active player to roll the dice
     */
    public void enableRotateAssamAndRollDie() {
        Player player = players.get(currPlayerIndex);
        // draggable rug piece

        // enable click assam to rotate
        realAssam.setClickable(true, true);

        // Sets the color of the rug icon to the color of the currently active player
        rugFlag.setImageByColor(player.getColor());
        // Disable dragging of rug icon
        if (!root.getChildren().contains(rugFlag)) {
            root.getChildren().add(rugFlag);
        }
        rugFlag.setDraggable(false);

        // Enable the dice button
        if (!root.getChildren().contains(diceButton)) {
            root.getChildren().add(diceButton);
        }
        diceButton.removeAllImages();
        diceButton.setDisable(false);
        diceButton.getButton().setText("Dice");

        // if player type  is not Human, then Auto rotate assam and click dice button
        String playerType = realBoard.getPlayerType(player);
        if (!"Human".equals(playerType)) {
            realAssam.setClickable(false, false);
            int degrees;
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
     * if this rotation is legal, then rotate.
     * @param degrees degrees of rotate
     */
    public void receiveAssamRotate(int degrees, String originFacing) {
        // legality check
        if(board.isRotationValid(degrees, originFacing)){
            rotateAssam(degrees);
        }
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
        realAssam.setClickable(false, false);
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

        this.updateRealAssamFacing();
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
            // The rug is placed on the UI board and Update scoreboard
            RealRug realRug = buildRealRug(position1, position2, player, rug.getId());
            realBoard.getChildren().add(realRug);
            RealRugs.add(realRug);
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

//                rugFlag.setFill(Color.WHITE);
                char winner = board.getWinner();
                Color winnerColor = null;
                if (winner != 't') {
                    winnerColor = board.getPlayers().get(winner).getColor().getPaintColor();
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
     * @Author: Xing Chen, u7725171
     * We have this part of judgement because the image is special to place
     * check this pair of coordinates type
     * there are four possible combinations
     * for example: assuming the image is placed at (0,0)
     * (0,0) (0,1) vertical, downward
     * (0,0) (0,-1) vertical, upward
     * (0,0) (1,0) horizontal, right
     * (0,0) (-1,0) horizontal, left
     * after judgement, rotate the image accordingly
     * Author: Xing Chen
     * @param position1 coordinate 1
     * @param position2 coordinate 2
     * @param player current player
     * @param rugID id
     */
    private RealRug buildRealRug(Coordinate position1, Coordinate position2, Player player, int rugID){
        RealRug realRug = new RealRug(position1, player.getColor(), rugID);
        if (position1.x == position2.x && position1.y == position2.y - 1){
            //vertical, downward, do nothing
        }
        else if (position1.x == position2.x && position1.y == position2.y + 1) {
            //vertical, upward
            realRug.setTranslateY(-realBoard.GRID_SIZE);
        }
        else if (position1.y == position2.y && position1.x == position2.x - 1) {
            // horizontal, right
            Rotate rotate = new Rotate(90, realBoard.GRID_SIZE, realBoard.GRID_SIZE);
            realRug.getTransforms().add(rotate);
        }
        else if (position1.y == position2.y && position1.x == position2.x + 1) {
            // horizontal, left
            Rotate rotate = new Rotate(90, realBoard.GRID_SIZE, realBoard.GRID_SIZE);
            realRug.getTransforms().add(rotate);
            realRug.setTranslateX(-realBoard.GRID_SIZE);
        }
        return realRug;
    }

    /**
     * Get UI board handle
     *
     * @return UI board
     */
    public RealBoard getRealBoard() {
        return realBoard;
    }
    public Assam.AssamFacing getAssamFacing(){return this.board.getAssam().getFacing();}
    public void updateRealAssamFacing(){this.realAssam.setFacing(getAssamFacing().getFacingString());}
    public Player getCurrentPlayer(){return this.players.get(currPlayerIndex);}
}
