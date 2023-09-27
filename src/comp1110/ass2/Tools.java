package comp1110.ass2;

public class Tools {
    // a simple tool to determine if a string is a valid number
    public  static boolean isNumber(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}