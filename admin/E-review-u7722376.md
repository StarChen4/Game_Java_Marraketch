## Code Review

Reviewed by: Diao Fu, u7722376

Reviewing code written by: Chuang Ma, u7703248

Component: <public class RealDice extends Button {
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
}>

### Comments 

<Strengths:
Advantages of the code:
This code provides good documentation, including class-to-class and method descriptions. 
This helps other developers on the team understand the code.
The code implements a custom JavaFX button class RealDice, which produces an animation effect of rolling dice.
It followed Java code conventions, methods and variables properly named, and the style consistent throughout.
Weaknesses:
The code calls game.receiveDicePoint (diePoint) in timely.setOnFinished,
assuming the game object is initialized correctly and the callback is handled correctly. 
If the game object is not properly initialized or cannot handle callbacks, the program may have problems at this point.
>


