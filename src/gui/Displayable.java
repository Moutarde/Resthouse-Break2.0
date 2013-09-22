package gui;

import java.util.Observable;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Displayable extends Observable {

    private boolean isDisplayed = false;

    public void display(boolean value) {
        assert isDisplayed != value : "display value allready at " + isDisplayed;
        isDisplayed = value;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

}
