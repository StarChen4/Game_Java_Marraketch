package comp1110.ass2;

/**
 * Description: Some useful static methods.
 *
 * @Author Diao Fu u7722376
 * @Create 2023/10/04
 * Version 1.0
 */
public class Tools {

    /**
     * Checks a string and returns whether the string is a number.
     *
     * @param str The string to be checked
     * @return whether it is the number
     */
    public static boolean isNumber(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
