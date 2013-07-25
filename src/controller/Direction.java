package controller;

/**
 * @author Nicolas Kniebihler
 *
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT, NONE;

    static public Direction getDirectionFromString(String str) {
        if (str.equals("UP")) {
            return UP;
        }
        else if (str.equals("DOWN")) {
            return DOWN;
        }
        else if (str.equals("LEFT")) {
            return LEFT;
        }
        else if (str.equals("RIGHT")) {
            return RIGHT;
        }
        else if (str.equals("NONE")) {
            return NONE;
        }
        else {
            assert false : "String discribing direction unknown : " + str;
            return null;
        }
    }
}
