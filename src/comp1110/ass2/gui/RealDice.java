package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.util.Duration;

/**
 * Description: the button of die With animated effects.
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class RealDice extends Button {
    private final Game game;

    // final points of die
    private int diePoint;

    /**
     * initialization
     *
     * @param game handle for callback
     */
    RealDice(Game game) {
        this.game = game;
        setLayoutX(680);
        setLayoutY(300);
        setMinHeight(80);
        setMinWidth(80);
        setText("Dice");
        setStyle("-fx-font-size:30");
        setOnAction(event -> rollDice());
    }

    /**
     * roll the dice
     */
    private void rollDice() {
        setDisable(true);

        // Animated effects
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            diePoint = Marrakech.rollDie();
            setText(String.valueOf(diePoint));
        }));
        timeline.setCycleCount(10);
        timeline.setOnFinished(event -> {
            // Call back, Send the final points of die when Animated end.
            game.receiveDicePoint(diePoint);
        });
        timeline.play();
    }
}
