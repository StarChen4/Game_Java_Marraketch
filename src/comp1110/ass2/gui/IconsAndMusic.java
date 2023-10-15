package comp1110.ass2.gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;

/**
 * Description: Store the icons(mostly buttons) and music of this game
 * Buttons:
 *  start scene, new game, rule, mute, volume slider
 * Music:
 *  BGM
 *
 * @Author Xing Chen, u7725171
 * @Create 2023/10/15
 * Version 1.0
 */
public class IconsAndMusic {
    // resources file path
    private final String BGM = "assets/Audio/BackgroundMusic.mp3";
    private final String RULE_BOARD = "file:assets/Images/RuleBoard.png";
    private final String START_SCENE = "file:assets/Images/StartScene.png";
    private final String SOUND_IMAGE = "file:assets/Images/Sound.png";
    private final String SOUND_MUTED_IMAGE = "file:assets/Images/Mute.png";
    // background music
    private Media bgm1 = new Media(new File(BGM).toURI().toString());
    private MediaPlayer bgm = new MediaPlayer(bgm1);
    // Mute status
    private boolean soundMuted = false;
    private final RealDice dice;
    // start scene
    private Image startScene = new Image(START_SCENE);
    public final ImageView startSceneImage;
    // Rule window
    public Group ruleWindow;
    // Buttons of game
    public HashMap<String, Button> buttons =  new HashMap<>();
    // Slider of volume
    public Slider volumeSlider;
    private double volumeRecorder;


    /**
     * constructor: initialize everything
     * @param windowWidth game window width
     * @param windowHeight game window height
     * @param game game
     */
    public IconsAndMusic(int windowWidth, int windowHeight, Game game){
        dice = new RealDice(game);
        // play BGM
        bgm.setCycleCount(MediaPlayer.INDEFINITE); // play infinitely
        bgm.play();

        // Start Scene
        startSceneImage = new ImageView(startScene);
        startSceneImage.setFitHeight(windowHeight);
        startSceneImage.setFitWidth(windowWidth);

        // Set a volume Slider, to control the bgm and sound effects
        volumeSlider = new Slider(0, 1, 0.5);
        dice.diceRollSound.volumeProperty().bind(volumeSlider.valueProperty());
        bgm.volumeProperty().bind(volumeSlider.valueProperty());
        volumeRecorder = volumeSlider.getValue();

        // Buttons initialization
        buttons.put("newGameButton",new Button("NEW GAME")); // NEW GAME
        buttons.put("ruleButton",new Button("RULE")); // RULE
        buttons.put("closeRuleButton",new Button("BACK")); // BACK FROM RULE
        buttons.put("muteButton",new Button("MUTE")); // MUTE BUTTON

        // Set up a new game button on the UI board; press this button to begin or reset a game.
        buttons.get("newGameButton").setLayoutX(windowWidth - 750);
        buttons.get("newGameButton").setLayoutY(windowHeight - 400);
        buttons.get("newGameButton").setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        // Set a rules button on the UI board; press this button to show the rule
        buttons.get("ruleButton").setLayoutX(windowWidth - 690);
        buttons.get("ruleButton").setLayoutY(windowHeight - 250);
        buttons.get("ruleButton").setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        // Set a mute button on the UI board; it is displayed as an icon
        buttons.get("muteButton").setLayoutX(windowWidth - 100);
        buttons.get("muteButton").setLayoutY(windowHeight - 100);
        buttons.get("muteButton").setOnAction(event ->{
            // record the volume before muted and will scroll back to it
            if (!soundMuted) {
                volumeRecorder = volumeSlider.getValue();
                volumeSlider.setValue(0.0);
                soundMuted = true;}
            else {
                volumeSlider.setValue(volumeRecorder);
                soundMuted = false;
            }
        });
    }

    public Group formRuleWindow(int windowWidth, int windowHeight){
        // rule window
        ruleWindow = new Group();

        // rule picture
        Image rule = new Image(RULE_BOARD);
        ImageView ruleImage = new ImageView(rule);
        ruleImage.setFitHeight(windowHeight);
        ruleImage.setFitWidth(windowWidth);
        ruleWindow.getChildren().add(ruleImage);

        // close button at right-top corner, clicking will remove the window from game
        buttons.get("closeRuleButton").setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
        buttons.get("closeRuleButton").setMinSize(20,20);
        ruleWindow.getChildren().add(buttons.get("closeRuleButton"));
        return ruleWindow;
    }

    public void changeIconsFeatures(){
        buttons.get("ruleButton").setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        buttons.get("newGameButton").setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
    }
}
