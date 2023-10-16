package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Description: the button of die With animated effects.
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class RealDice extends StackPane {
    private final Game game;

    // final points of die
    private int diePoint;
    private final Button button = new Button("Dice");
    private ImageView diceImage;
    private final ArrayList<ImageView> diceImages = new ArrayList<>();
    private final double DICEWIDTH = 100;


    /**
     * initialization
     *
     * @param game handle for callback
     */
    RealDice(Game game) {
        this.game = game;
        setLayoutX(RealBoard.GROUND_SIZE + 60);
        setLayoutY(RealBoard.GROUND_SIZE - 130);
        this.button.setMinHeight(80);
        this.button.setMinWidth(80);
        this.button.setStyle("-fx-font-size:30; -fx-font-weight: bold;");
        this.button.setOnAction(event -> rollDice());
        this.getChildren().add(button);
    }

    /**
     * roll the dice
     */
    private void rollDice() {
        setDisable(true);
        IconsAndMusic.playDiceSound();

        // Animated effects
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            diePoint = Marrakech.rollDie();
            diceImage = getImageByPoint(diePoint);
            this.getChildren().add(diceImage);
            this.diceImages.add(diceImage);
        }));
        timeline.setCycleCount(10);
        timeline.setOnFinished(event -> {
            // Call back, Send the final points of die when Animated end.
            game.receiveDicePoint(diePoint);
        });
        timeline.play();
    }

    /**
     * Choose file according to the roll point
     * Automatically adjust the scale
     * @param rollPoint outcome of roll
     * @return an imageView to display
     */
    private ImageView getImageByPoint(int rollPoint){
        Image image = new Image("file:assets/images/Dice" + rollPoint + ".png");
        ImageView diceImage = new ImageView(image);
        diceImage.setFitWidth(DICEWIDTH);
        diceImage.setFitHeight(DICEWIDTH);
        return diceImage;
    }
    public void fire(){this.button.fire();}
    public Button getButton(){return button;}
    public void removeAllImages(){this.getChildren().removeAll(diceImages);}
}
