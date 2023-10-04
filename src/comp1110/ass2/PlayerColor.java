package comp1110.ass2;

import javafx.scene.paint.Color;

/**
 * Description: This enum defines the Color of player
 *
 * @Author Diao Fu u7722376
 * @Create 2023/10/03
 * Version 1.0
 */
public enum PlayerColor {
    CYAN('c', Color.CYAN),
    YELLOW('y', Color.YELLOW),
    RED('r', Color.RED),
    PURPLE('p', Color.PURPLE);
    private final char colorChar;
    private final Color painColor;

    PlayerColor(char colorChar, Color painColor) {
        this.colorChar = colorChar;
        this.painColor = painColor;
    }

    public char getColorChar() {
        return colorChar;
    }

    public Color getPainColor() {
        return painColor;
    }
}
