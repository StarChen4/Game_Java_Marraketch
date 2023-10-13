package comp1110.ass2.gui;

import comp1110.ass2.Coordinate;
import comp1110.ass2.PlayerColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Description: rugs to be placed on the board
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class RealRug extends ImageView {
    public static int BOARD_START = RealBoard.BOARD_START;
    public static int GRID_SIZE = RealBoard.GRID_SIZE;

    private final String CYAN_RUG_IMAGE= "file:assets/CyanRug.png";
    private final String YELLOW_RUG_IMAGE= "file:assets/YellowRug.png";
    private final String RED_RUG_IMAGE= "file:assets/RedRug.png";
    private final String PURPLE_RUG_IMAGE= "file:assets/PurpleRug.png";

    /**
     * initialization
     * The image will be different according to the color parameter
     * @param position Placed location coordinate
     * @param color    color of rug
     * @param rugId    id of rug
     */
    public RealRug(Coordinate position, PlayerColor color, int rugId) {
        super();
        if (position.x == -1 || position.y == -1) {
            return;
        }
        switch (color){
            case CYAN -> {
                Image rugImage = new Image(CYAN_RUG_IMAGE);
                this.setImage(rugImage);
            }
            case YELLOW -> {
                Image rugImage = new Image(YELLOW_RUG_IMAGE);
                this.setImage(rugImage);
            }
            case RED -> {
                Image rugImage = new Image(RED_RUG_IMAGE);
                this.setImage(rugImage);
            }
            case PURPLE -> {
                Image rugImage = new Image(PURPLE_RUG_IMAGE);
                this.setImage(rugImage);
            }
        }
        // Adjust the height and width and original position
        this.setFitHeight(GRID_SIZE * 2);
        this.setFitWidth(GRID_SIZE);
        this.setLayoutX(BOARD_START + position.x * GRID_SIZE);
        this.setLayoutY(BOARD_START + position.y * GRID_SIZE);
    }
}