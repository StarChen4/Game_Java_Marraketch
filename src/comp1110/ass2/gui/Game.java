package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * Method that creates the buttons, sliders and text for the game.
     */
    private void makeControls() {

    }

    /**
     * roll die
     * @param point point of die to display
     */
    private void rollDie(int point) {

    }

    /**
     * Move Assam
     * @param step
     */
    private void moveAssam(int step) {

    }

    /**
     * Rote Assam
     * @param direction
     */
    private void rotateAssam(int direction) {

    }

    /**
     * Put a rug to the board.
     **/
    private void placeRug() {

    }

    /**
     * A method that creates an Assam and adds it to the board.
     **/
    private void createAssam() {

    }

    /**
     * A method that creates a die and adds it to the board.
     **/
    private void createDie() {

    }

    /**
     * Creates the board for the application.
     */
    private void makeBoard() {

    }

    /**
     * Create a new game.
     */
    private void newGame() {

    }

    /**
     * Restart the game.
     */
    private void restart() {
    }




}

