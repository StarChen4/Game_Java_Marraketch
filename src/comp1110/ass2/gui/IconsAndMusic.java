package comp1110.ass2.gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;

/**
 * Description: Store the icons(mostly buttons) and music of this game
 * Buttons:
 *  start scene, new game, rule, rule close, mute, volume slider
 * Music:
 *  BGM, rollDice
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
    private final String SOUND_MUTE_IMAGE = "file:assets/Images/Mute.png";
    private static final String DICE_ROLL = "assets/Audio/Dice.mp3";
    private static final String PAYMENT_MUSIC = "assets/Audio/Payment.mp3";
    private static final String PLACE_RUG_MUSIC = "assets/Audio/PlaceRug.mp3";
    // background music
    private final Media bgmMusic = new Media(new File(BGM).toURI().toString());
    private final MediaPlayer bgm = new MediaPlayer(bgmMusic);
    // sound effects
    private static final AudioClip diceRollSound = new AudioClip(new File(DICE_ROLL).toURI().toString());
    private static final AudioClip paymentSound = new AudioClip(new File(PAYMENT_MUSIC).toURI().toString());
    private static final AudioClip placeRugSound = new AudioClip(new File(PLACE_RUG_MUSIC).toURI().toString());
    // Mute status
    private boolean soundMuted = false;
    // start scene
    private final Image startScene = new Image(START_SCENE);
    public final ImageView startSceneImage;
    // Rule window
    public Group ruleWindow;
    // Buttons of game
    public HashMap<String, Button> buttons =  new HashMap<>();
    // Slider of volume
    public Slider volumeSlider;
    private double volumeRecorder;
    // Sound Icon
    private final Image soundIconImage;
    private final Image soundUnmuteIconImage;
    private final ImageView soundIcon;
    /**
     * constructor: initialize everything
     * @param windowWidth game window width
     * @param windowHeight game window height
     */
    public IconsAndMusic(int windowWidth, int windowHeight){
        // play BGM
        bgm.setCycleCount(MediaPlayer.INDEFINITE); // play infinitely
        bgm.play();
        // Start Scene
        startSceneImage = new ImageView(startScene);
        startSceneImage.setFitHeight(windowHeight);
        startSceneImage.setFitWidth(windowWidth);

        // Set a volume Slider, to control the bgm and sound effects
        volumeSlider = new Slider(0, 1, 0.5);
        diceRollSound.volumeProperty().bind(volumeSlider.valueProperty());
        bgm.volumeProperty().bind(volumeSlider.valueProperty());
        paymentSound.volumeProperty().bind(volumeSlider.valueProperty());
        placeRugSound.volumeProperty().bind(volumeSlider.valueProperty());
        volumeRecorder = volumeSlider.getValue();

        // Buttons initialization
        buttons.put("newGameButton",new Button("NEW GAME")); // NEW GAME
        buttons.put("ruleButton",new Button("RULE")); // RULE
        buttons.put("closeRuleButton",new Button("BACK")); // BACK FROM RULE
        buttons.put("muteButton",new Button()); // MUTE BUTTON

        // Set up a new game button on the UI board; press this button to begin or reset a game.
        buttons.get("newGameButton").setLayoutX(windowWidth - 750);
        buttons.get("newGameButton").setLayoutY(windowHeight - 400);
        buttons.get("newGameButton").setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        // Set a rules button on the UI board; press this button to show the rule
        buttons.get("ruleButton").setLayoutX(windowWidth - 690);
        buttons.get("ruleButton").setLayoutY(windowHeight - 250);
        buttons.get("ruleButton").setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        // Set a mute button on the UI board; it is displayed as an icon
        soundIconImage = new Image(SOUND_IMAGE);
        soundUnmuteIconImage = new Image(SOUND_MUTE_IMAGE);
        soundIcon = new ImageView(soundIconImage); // unmute by default
        soundIcon.setFitWidth(20);
        soundIcon.setFitHeight(20);
        buttons.get("muteButton").setLayoutX(windowWidth - 100);
        buttons.get("muteButton").setLayoutY(windowHeight - 100);
        buttons.get("muteButton").setGraphic(soundIcon);
        buttons.get("muteButton").setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-color: transparent;");
        buttons.get("muteButton").setOnAction(event ->{
            // record the volume before muted and will scroll back to it
            if (!soundMuted) {
                volumeRecorder = volumeSlider.getValue();
                volumeSlider.setValue(0.0);
                soundIcon.setImage(soundUnmuteIconImage);
                soundMuted = true;}
            else {
                volumeSlider.setValue(volumeRecorder);
                soundIcon.setImage(soundIconImage);
                soundMuted = false;
            }
        });

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
    }
    public Group formRuleWindow(){return ruleWindow;}

    /**
     * change the rule and newGame button to make it smaller,
     * so they can be moved to the right-bottom corner when new game is started.
     */
    public void changeIconsFeatures(){
        buttons.get("ruleButton").setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        buttons.get("newGameButton").setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
    }
    public static void playDiceSound(){
        diceRollSound.play();
    }
    public void playPaymentSound(String paymentString){
        if (paymentString != null) {
            paymentSound.play();
        }
    }
    public void playPlaceRugSound(){
        placeRugSound.play();
    }
}
