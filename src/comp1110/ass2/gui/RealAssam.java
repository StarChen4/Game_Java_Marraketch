package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;



/**
 * Description: Assam icon.
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class RealAssam extends ImageView {
    public static int BOARD_START = RealBoard.BOARD_START;
    public static int GRID_SIZE = RealBoard.GRID_SIZE;
    // whether it can be clicked for rotate
    private boolean leftClickable = false;
    private boolean rightClickable = false;
    private final String ASSAM_PATH = "file:assets/Assam.png";
    // To make the image position right
    private final double imageOffsetY = 10;

    /**
     * Set Assam with an image
     * Mouseclick will rotate Assam, which can rotate 90 when right-clicked, 270 when left-clicked
     * cannot rotate 180 degrees
     * @param game the current game
     */
    RealAssam(Game game) {
        super();
        Image assamImage = new Image(ASSAM_PATH);
        this.setImage(assamImage);
        this.setFitWidth(71.5);
        this.setFitHeight(50);
        this.setOnMousePressed(event -> {
            if (!isClickable()) {
                return;
            }
//            clickable = false;
            int degrees = 270;
            if (event.isSecondaryButtonDown()) {
                degrees = 90;
            }
            game.receiveAssamRotate(degrees);
        });
    }


    /**
     * set position and direction
     *
     * @param position position on the board
     * @param facing   direction
     */
    void move(Coordinate position, Assam.AssamFacing facing) {
        this.setLayoutX(BOARD_START + position.x * GRID_SIZE);
        this.setLayoutY(BOARD_START + position.y * GRID_SIZE + imageOffsetY);
        this.setRotate(facing.getDegrees());
    }

    public void setClickable(boolean leftClickable, boolean rightClickable) {
        this.leftClickable = leftClickable;
        this.rightClickable = rightClickable;
    }
    public boolean isClickable(){return leftClickable || rightClickable;}

}
