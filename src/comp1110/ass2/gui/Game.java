package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
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
    private final RealDice dice = new RealDice(this);
    // avoid new game when moving assam
    private boolean isMoveOver = true;
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
    private IconsAndMusic iconsAndMusic = new IconsAndMusic(WINDOW_WIDTH, WINDOW_HEIGHT, this);
    // HBox for game buttons and volume bar when the game is on
    private HBox hBox = new HBox();
    private PromptText textVBox;

    /**
     * initialization of game, firstly, game will enter a starting scene
     * setting bgm, start scene, buttons and slider
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        // Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Set click event
        iconsAndMusic.buttons.get("newGameButton").setOnAction(event -> {if (isMoveOver) newGame();});
        iconsAndMusic.buttons.get("ruleButton").setOnAction(event -> showRule());
        // display everything
        this.root.getChildren().add(iconsAndMusic.startSceneImage);
        root.getChildren().add(iconsAndMusic.buttons.get("newGameButton"));
        root.getChildren().add(iconsAndMusic.buttons.get("ruleButton"));
        hBox.getChildren().add(iconsAndMusic.buttons.get("muteButton"));
        hBox.getChildren().add(iconsAndMusic.volumeSlider);
        hBox.setLayoutX(WINDOW_WIDTH-200);
        hBox.setLayoutY(WINDOW_HEIGHT-50);
        hBox.setAlignment(Pos.CENTER);
        this.root.getChildren().add(hBox);
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Create a new game or reset current game.
     */
    private void newGame() {
        // re-display game board
        board = new Board();
        if (!root.getChildren().contains(realBoard))
            root.getChildren().add(realBoard);

        // remove the start scene and reallocate the buttons
        this.reallocateIcons();

        // Get players from logic board to a list,
        // In order to implement the game in the order of players in the list
        players.clear();
        for (PlayerColor color : PlayerColor.values()) {
            char colorChar = color.getColorChar();
            players.add(board.getPlayers().get(colorChar));
        }
        realBoard.setScoreBoard(board.getPlayers());

        //display prompt text
        if(this.root.getChildren().contains(textVBox))
            this.root.getChildren().remove(textVBox);
        textVBox = new PromptText(this);
        textVBox.setLayoutX(WINDOW_WIDTH - 375);
        textVBox.setLayoutY(WINDOW_HEIGHT - 400);
        root.getChildren().add(textVBox);

        // Start the game with player 0
        currPlayerIndex = 0;

        // Set the initial state of assam
        Assam assam = board.getAssam();
        realAssam.move(assam.getPosition(), assam.getFacing());
        if (!root.getChildren().contains(realAssam)) {
            root.getChildren().add(realAssam);
        }

        // Remove existing rugs from the board
        realBoard.getChildren().removeAll(RealRugs);
        RealRugs.clear();

        rugFlag = new DraggableRugFlag(this, this.getCurrentPlayer().getColor());

        // Step 1. Enable rotate assam, enable the dice button and wait for the currently active player to roll the dice
        enableRotateAssamAndRollDie();
    }

    /**
     * display the rule window with a close button
     */
    public void showRule(){
        iconsAndMusic.buttons.get("closeRuleButton").setOnAction(event -> this.root.getChildren().remove(iconsAndMusic.ruleWindow));
        // display ruleWindow
        this.root.getChildren().add(iconsAndMusic.formRuleWindow());
    }

    /**
     * move the icons to right-bottom corner when new game is started
     */
    public void reallocateIcons(){
        // avoid duplicate children adding
        if (!hBox.getChildren().contains(iconsAndMusic.buttons.get("newGameButton"))
                && !hBox.getChildren().contains(iconsAndMusic.buttons.get("ruleButton"))) {
            this.root.getChildren().remove(iconsAndMusic.startSceneImage);
            hBox.setLayoutX(WINDOW_WIDTH - 400);
            hBox.setLayoutY(WINDOW_HEIGHT - 50);
            // change icons
            iconsAndMusic.changeIconsFeatures();
            // new game and rule buttons are moved to the right-bottom corner, into a HBox
            hBox.getChildren().add(0, iconsAndMusic.buttons.get("ruleButton"));
            hBox.getChildren().add(0, iconsAndMusic.buttons.get("newGameButton"));
            hBox.setSpacing(10);
        }
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
//        if (!root.getChildren().contains(rugFlag)) {
//            root.getChildren().add(rugFlag);
//        }
        rugFlag.setDraggable(false);

        // Enable the dice button
        if (!root.getChildren().contains(dice)) {
            root.getChildren().add(dice);
        }
        dice.removeAllImages();
        dice.setDisable(false);
        dice.getButton().setText("Dice");

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
            dice.fire();
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
        else{
            textVBox.invalidPrompt(originFacing);
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
        isMoveOver = false;
        moveAssam(point);
        textVBox.enterMovingStage(point);
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
            textVBox.enterPaymentStage(board.payment(players.get(currPlayerIndex)));
            realBoard.setScoreBoard(board.getPlayers());
            iconsAndMusic.playPaymentSound(board.payment(players.get(currPlayerIndex)));
            // Step 3. Enable dragging of rug icon and wait for the currently active player to place a rug.
            enableDragRug();
            isMoveOver = true;
        });
        timeline.play();

        this.updateRealAssamFacing();
    }

    /**
     * Enable dragging of rug icon
     */
    public void enableDragRug() {
        rugFlag.setDraggable(true);
        if (!root.getChildren().contains(rugFlag)) {
            root.getChildren().add(rugFlag);
        }
        // Hidden dice
        root.getChildren().remove(dice);
        textVBox.enterPlacementStage();
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
            iconsAndMusic.playPlaceRugSound();

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
                    textVBox.enterGameOverStage(board.getPlayers().get(winner).getColor().toString());
                }
                else textVBox.enterGameOverTieStage();
                root.getChildren().add(new GameOver(winnerColor));
                return;
            }

            // Start next player action
            // Step 1. Enable the dice button and wait for the currently active player to roll the dice
            enableRotateAssamAndRollDie();
        }
        textVBox.enterRollingStage(this);
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
