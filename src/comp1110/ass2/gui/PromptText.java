package comp1110.ass2.gui;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Description: Store some prompt texts
 *
 * @Author Xing Chen, u7725171
 * @Create 2023/10/15
 * Version 1.0
 */
public class PromptText extends Group {
    public Text copyright = new Text("ANU: Xing, Chuang, Diao");
    private Text welcome = new Text("Welcome to our Marrakech!\n");
    private Text characterChoosePrompt = new Text("Please choose your opponent type.\n");
    private Text roundPrompt;
    private Text roundPrompt2 = new Text("'s round.");
    private Text invalidPrompt;
    private Text rotatePrompt = new Text("You can left-click or right-click to rotate Assam.\n");
    private Text rollPrompt = new Text("When finish rotating, click on the \"Dice\" to roll.\n");
    private Text movePrompt1 = new Text("Assam will move ");
    private Text movePrompt2 = new Text(" steps.");
    private Text movePrompt3 = new Text(" step.");
    private Text paymentPrompt1 = new Text("Oops, you have to pay ");
    private Text paymentPrompt2 = new Text(" dirhams.\n");
    private Text paymentPrompt3 = new Text(" dirham.\n");
    private Text paymentPrompt4 = new Text("Lucky you! You don't need to pay anyone.\n");
    private Text placePrompt1 = new Text("Now it's time to place your rug.\n");
    private Text placePrompt2 = new Text("Drag the rug on the left to the place you want.\n");
    private Text placePrompt3 = new Text("Press any key to rotate it.");
    private Text gameOverPrompt1 = new Text("Game over!\n");
    private Text gameOverPrompt2 = new Text("Winner is ");
    private Text gameOverPrompt3 = new Text("Tie game!");
//    public Text copyright = new Text("ANU: Xing, Chuang, Diao");
//    private String welcome = new String("Welcome to Marrakech!\n");
//    private String characterChoosePrompt = new String("Please choose your opponent type.\n");
//    private String roundPrompt;
//    private String roundPrompt2 = new String("'s round.");
//    private String rotatePrompt = new String("Now you can left-click or right-click to rotate Assam.\n");
//    private String rollPrompt = new String("When finish rotating, click on the \"Dice\" to roll.\n");
//    private String movePrompt1 = new String("Assam will move");
//    private String movePrompt2 = new String(" steps.");
//    private String movePrompt3 = new String(" step.");
//    private String paymentPrompt1 = new String("Oops, you have to pay");
//    private String paymentPrompt2 = new String(" dirhams.");
//    private String paymentPrompt3 = new String(" dirham.");
//    private String paymentPrompt4 = new String("Lucky you! You don't need to pay anyone.");
//    private String gameOverPrompt1 = new String("Game over!\n");
//    private String gameOverPrompt2 = new String("Winner is ");
//    private String gameOverPrompt3 = new String("Tie game!");
    private TextFlow promptText = new TextFlow();
    public VBox textVBox;

    public PromptText(Game game){
        roundPrompt = new Text("Player " + game.getCurrentPlayer().getColor().toString() + "'s turn.\n");
        this.promptText.getChildren().addAll(welcome, characterChoosePrompt, roundPrompt, rotatePrompt, rollPrompt);
        this.promptText.setLineSpacing(10);
        this.promptText.setStyle("-fx-font-size: 15px;");
        this.getChildren().add(promptText);
    }

    public void enterMovingStage(int step){
        this.promptText.getChildren().clear();
        if (step == 1)
            this.promptText.getChildren().addAll(movePrompt1,new Text(String.valueOf(step)),movePrompt3);
        else
            this.promptText.getChildren().addAll(movePrompt1,new Text(String.valueOf(step)),movePrompt2);
    }
    public void enterPaymentStage(String paymentString){
        this.promptText.getChildren().clear();
        // null means no need to pay
        if (paymentString == null){
            this.promptText.getChildren().addAll(paymentPrompt4);
            return;
        }
        int amount = Integer.parseInt(paymentString.substring(0,2));
        String player = "player " + paymentString.substring(2) + " ";
        if (amount == 1)
            this.promptText.getChildren().addAll(paymentPrompt1, new Text(player), new Text(String.valueOf(amount)), paymentPrompt3);
        else
            this.promptText.getChildren().addAll(paymentPrompt1, new Text(player), new Text(String.valueOf(amount)), paymentPrompt2);
    }
    public void enterPlacementStage(){
        this.promptText.getChildren().addAll(placePrompt1, placePrompt2, placePrompt3);
    }
    public void enterRollingStage(Game game){
        this.promptText.getChildren().clear();
        roundPrompt = new Text("Now is player " + game.getCurrentPlayer().getColor().toString() + "'s turn.\n");
        this.promptText.getChildren().addAll(roundPrompt, rotatePrompt, rollPrompt);
    }
    public void invalidPrompt(String assamOriginalFacing){
        if (!this.promptText.getChildren().contains(invalidPrompt)) {
            invalidPrompt = new Text("Invalid rotation!\n" + "Assam's original facing " + assamOriginalFacing + ", cannot face backward.");
            this.promptText.getChildren().add(invalidPrompt);
        }
    }
    public void enterGameOverStage(){

    }
}
