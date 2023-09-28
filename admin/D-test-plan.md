
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
* void payTo(Player player); need to check the dirhamsAmount after execution
* void receiveDirhams(int dirhamsAmount);need to check the dirhamsAmount after execution

### Rug class
* Rug(Player owner,int id); After execution, we need to use getId() and getOwner() method to check the correctness.
* Rug(String status); After execution, we need to use getId(), getSeg1X(), getSeg2X(), getSeg1Y(), getSeg2Y() methods to check the correctness.
* void setStatus(String status); After execution, we need to use getId(), getSeg1X(), getSeg2X(), getSeg1Y(), getSeg2Y() methods to check the correctness.
* void setSegment1(int x, int y); After execution, we need to use getSeg1X(), getSeg1Y() methods to check the correctness.
* void setSegment2(int x, int y); After execution, we need to use getSeg2X(), getSeg2Y() methods to check the correctness.


### Tile class
* void setCovered(boolean covered); After execution, we need to use isCovered() method to check the correctness.
* void setRug(Rug rug);we need to use isCovered() , getRugAmount() and getRugsList() to check the correctness;
* void removePlayersRug(); need to check the state of the tile after remove a player's rugs
* void removeAllRugs(); need to check the state of the tile after remove all of the rugs on it.
* Player getTopPlayer();need test top rug's owner to process the payment

### Board class
* void getTile()
* 
### Tools class
* static boolean isNumber(String str)
