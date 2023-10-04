package comp1110.ass2.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Description: Display game over message and winner
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class GameOver extends VBox {
    /**
     * Constructor: creates a new game over box with winner color
     *
     * @param winnerColor color of winner
     */
    GameOver(Color winnerColor) {

        setLayoutX(500);
        setLayoutY(300);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #8a8282;");
        setPadding(new Insets(20, 10, 20, 10));
        setSpacing(20);

        Label label = new Label("Game Over");
        label.setStyle("-fx-font-size:30");
        this.getChildren().add(label);

        if (winnerColor != null) {
            Label winner = new Label("Winner");
            winner.setStyle("-fx-font-size:20");
            getChildren().add(winner);
            Rectangle rectangle = new Rectangle(20, 20);
            rectangle.setFill(winnerColor);
            getChildren().add(rectangle);
        } else {
            Label tie = new Label("TIE");
            tie.setStyle("-fx-font-size:20");
            getChildren().add(tie);
        }

        Button button = new Button("OK");
        getChildren().add(button);

        button.setOnAction(event -> ok());
    }

    /**
     * remove self
     */
    void ok() {
        ((Group) getParent()).getChildren().remove(this);
    }
}
