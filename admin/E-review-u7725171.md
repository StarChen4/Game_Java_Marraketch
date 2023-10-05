## Code Review

Reviewed by:   Xing Chen,   u7725171

Reviewing code written by:   Diao Fu,   u7722376

Component: 
    
    several classes: 
        Player, Coordinate, Rug and Tool

### Comments 
Overall:
    
    Well done. 
    Good object-oriented coding.
    I've put some opinions below.

Class Player:
    
    Good overall:
        1. Well commented
        2. Easy to read
        3. Well object-oriented
        4. Good encapsulation

    Some personal opinions and suggestions:
        1. Some methods of player have never been used like equals() and getColor()
        2. Method useRug() name is kind of confusing, maybe better with setRugUsed() because
        the purpose of this method is that

Class Coordinate:
    
    Good.

Class Rug:
    
    Good overall
    Some personal opinions and suggestions:
        1. SetStatus has only been used once, maybe can be integrated into the String constructor
        2. Little confusing about method setSegment1/2() and useSegment(), maybe can give more details

Class Tool:
    
    It's very simple and has only one method, maybe can be replaced by using some libraries
    


