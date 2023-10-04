package comp1110.ass2.gui;

import comp1110.ass2.Coordinate;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Description: Segments of rugs placed on the board, each rug is divided into two segments
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class RugSegment extends StackPane {
    public static int BOARD_START = RealBoard.BOARD_START;
    public static int GRID_SIZE = RealBoard.GRID_SIZE;

    /**
     * initialization
     *
     * @param position Placed location coordinate
     * @param color    color of rug
     * @param rugId    id of rug
     */
    public RugSegment(Coordinate position, Color color, int rugId) {
        super();
        if (position.x == -1 || position.y == -1) {
            return;
        }
        Rectangle segment = new Rectangle(GRID_SIZE, GRID_SIZE);
        segment.setFill(color);
        segment.setStroke(Color.GREY);
        this.getChildren().add(segment);
        this.getChildren().add(new Label(String.valueOf(rugId)));
        this.setLayoutX(BOARD_START + position.x * GRID_SIZE);
        this.setLayoutY(BOARD_START + position.y * GRID_SIZE);
    }
}