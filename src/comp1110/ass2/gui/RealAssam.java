package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Coordinate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Description: Assam icon.
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class RealAssam extends Polygon {
    public static int BOARD_START = RealBoard.BOARD_START;
    public static int GRID_SIZE = RealBoard.GRID_SIZE;

    /**
     * draw an icon
     */
    RealAssam() {
        super();
        this.getPoints().addAll(0., 0., 30., 30., 0., -30., -30., 30.0, 0., 0.);
        this.setFill(Color.web("#0c8918"));
    }


    /**
     * set position and direction
     *
     * @param position position on the board
     * @param facing   direction
     */
    void move(Coordinate position, Assam.AssamFacing facing) {
        this.setLayoutX(BOARD_START + (position.x + 0.5) * GRID_SIZE);
        this.setLayoutY(BOARD_START + (position.y + 0.5) * GRID_SIZE);
        this.setRotate(facing.getDegrees());
    }
}
