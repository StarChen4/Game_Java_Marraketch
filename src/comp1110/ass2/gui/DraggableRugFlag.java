package comp1110.ass2.gui;

import comp1110.ass2.Coordinate;
import comp1110.ass2.Player;
import comp1110.ass2.PlayerColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Description: Draggable rug icon.
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/20
 * Version 1.0
 */
public class DraggableRugFlag extends ImageView {
    private double mouseX = 0;
    private double mouseY = 0;
    boolean mousePressed = false;

    // origin position of the icon.
    private final double originX = RealBoard.GROUND_SIZE + RealBoard.GRID_SIZE + 10;
    private final double originY = RealBoard.GROUND_SIZE / 2.0;

    // The coordinates of the highlighted area
    private final Coordinate[] lightPosition = new Coordinate[2];

    // whether it can be dragged
    private boolean draggable = false;

    private final String CYAN_RUG_IMAGE= "file:assets/images/CyanRug.png";
    private final String YELLOW_RUG_IMAGE= "file:assets/images/YellowRug.png";
    private final String RED_RUG_IMAGE= "file:assets/images/RedRug.png";
    private final String PURPLE_RUG_IMAGE= "file:assets/images/PurpleRug.png";

    /**
     * initialization
     *
     * @param game handle for callback
     */
    DraggableRugFlag(Game game, PlayerColor color) {
        super();
        this.setImageByColor(color);
        this.setOnMousePressed(event -> {
            mousePressed = true;
            this.requestFocus();
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
            for (int i = 0; i < lightPosition.length; i++) {
                lightPosition[i] = new Coordinate(-1, -1);
            }
        });

        this.setOnKeyTyped(event -> {
            // Press any key and it rotates 90 degrees
            if (mousePressed && draggable) {
                if (this.getRotate() == 0) {
                    this.setRotate(90);
                } else {
                    this.setRotate(0);
                }
            }
        });

        this.setOnMouseDragged(event -> {
            if (!draggable) {
                return;
            }
            double moveX = event.getSceneX() - mouseX;
            double moveY = event.getSceneY() - mouseY;
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            // move it
            this.setLayoutX(this.getLayoutX() + moveX);
            this.setLayoutY(this.getLayoutY() + moveY);

            // Highlight the nearest checkerboard and return the highlighted position coordinates(lightPosition)
            // according to the left-top corner of rug flag
            game.getRealBoard().highLightNearest(this.getLayoutX(),
                    this.getLayoutY(), this.getRotate() == 90, lightPosition);

        });

        this.setOnMouseReleased(event -> {
            mousePressed = false;
            // Reset position
            this.setLayoutX(originX);
            this.setLayoutY(originY);
            this.setRotate(0);
            game.getRealBoard().cancelHighLightNearest();
            // call back, Send the highlighted position coordinates when mouse released
            game.receiveRugPos(lightPosition[0], lightPosition[1]);
        });

    }

    /**
     * set the displaying image of this draggable rug by color
     * @param color
     */
    public void setImageByColor(PlayerColor color){
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
        this.setFitHeight(RealBoard.GRID_SIZE * 2);
        this.setFitWidth(RealBoard.GRID_SIZE);
        this.setLayoutX(originX);
        this.setLayoutY(originY);

    }
    /**
     * Set whether it can be dragged
     *
     * @param draggable whether it can be dragged
     */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }
}
