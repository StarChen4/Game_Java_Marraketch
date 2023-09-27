
# Test plan

## List of classes

* List below all classes in your implementation that should have unit tests.
* For each class, list methods that can be tested in isolation.
* For each class, if there are conditions on the class' behaviour that cannot
  be tested by calling one method in isolation, give at least one example of
  a test for such a condition.

Do **not** include in your test plan the `Marrakech` class or the predefined
static methods for which we have already provided unit tests.

### Assam class
* Assam(); After execution, we need to use getFacing(), getPositionX(), getPositionY() methods to check the correctness.
* Assam(String status); After execution, we need to use getFacing(), getPositionX(), getPositionY() methods to check the correctness.
* void setStatus(String status); After execution, we need to use getFacing(), getPositionX(), getPositionY() methods to check the correctness.
* void setFacing(AssamFacing facing); After execution, we need to use getFacing() method to check the correctness.
* void setPositionX(int positionX); After execution, we need to use getPositionX() method to check the correctness.
* void setPositionY(int positionY); After execution, we need to use getPositionY() method to check the correctness.

### Player class
* Player(PlayerColor color, int dirhamsAmount, int rugsAmount, boolean inGame);
* Player(String status); After execution, we need to use getDirhamsAmount(), getRugsAmount(), isInGame() methods to check the correctness.
* void setStatus(String status); After execution, we need to use getDirhamsAmount(), getRugsAmount(), isInGame() methods to check the correctness.
* void setDirhamsAmount(int dirhamsAmount); After execution, we need to use getDirhamsAmount() method to check the correctness.
* void setRugsAmount(int rugsAmount); After execution, we need to use getRugsAmount() method to check the correctness.
* public void setInGame(boolean inGame); After execution, we need to use isInGame() method to check the correctness.
* String statusString();

### Rug class
* Rug(Player owner,int id); After execution, we need to use getId() and getOwner() method to check the correctness.
* Rug(String status); After execution, we need to use getId(), getSeg1X(), getSeg2X(), getSeg1Y(), getSeg2Y() methods to check the correctness.
* void setStatus(String status); After execution, we need to use getId(), getSeg1X(), getSeg2X(), getSeg1Y(), getSeg2Y() methods to check the correctness.
* void setSegment1(int x, int y); After execution, we need to use getSeg1X(), getSeg1Y() methods to check the correctness.
* void setSegment2(int x, int y); After execution, we need to use getSeg2X(), getSeg2Y() methods to check the correctness.


### Tile class
* Tile(String status, Player player); After execution, we need to use getPlayer(), isCovered(), getRugId() methods to check the correctness.
* void setStatus(String status, Player player); After execution, we need to use getPlayer(), isCovered(), getRugId() methods to check the correctness.
* void setPlayer(Player player); After execution, we need to use getPlayer() method to check the correctness.
* void setCovered(boolean covered); After execution, we need to use isCovered() method to check the correctness.
* void setRugId(int rugId); After execution, we need to use getRugId() method to check the correctness.
* void setRug(Rug rug);we need to use isCovered() , getRugAmount() and getRugsList() to check the correctness;
* Player getTopPlayer;need test top rug's owner to process the payment
### Tools class
* static boolean isNumber(String str)
