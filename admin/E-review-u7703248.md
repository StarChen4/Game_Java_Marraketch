## Code Review

Reviewed by:   Chuang Ma,   u7703248

Reviewing code written by:   Xing Chen,   u7725171

Component:

    several classes: 
        Board

### Comments
Overall:

    1. Well commented
    2. Good naming
    3. Constant coding style
    4. good readability

Class Player:

    That is a very long piece of code. Respect to the effort.
    This class is the core part of the game and implements many other classes within it.
    Good:
        1. Well commented
        2. Relatively easy to read, although very long.
        3. Well object-oriented
        4. I like the countPaymentRecursion() method. 
            It uses DFS algorithm to find all connected rug segment.
            Very clear and concise.

    Some advices:
        1. Too long , maybe can leave those determination method into a tool class like marrakech.
        2. Method payment() not used at all, can be deleted.
        3. All players on this board share the same rugID using a global variant maxRugId
        maybe cause some confusion.

Class Assam:

    Good.
    1. One constructor is useless because it is empty
    2. Creative idea on using recursive function to move assam step by step.

Class Tile:

    Using player and rugID as fields maybe useful to check some validity,
    but maybe it will be less convenient to implement the case when many rug segments
    are on a same tile. Could use a array to store them.
    

    


