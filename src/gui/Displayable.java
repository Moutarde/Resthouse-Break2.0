package gui;

import java.util.Observable;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Displayable extends Observable {

    private boolean isDisplayed = false;

    public void display(boolean value) {
        isDisplayed = value;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

}
