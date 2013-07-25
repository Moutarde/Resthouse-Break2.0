package gui.contextMenu;

import model.GameModel;
import gui.UserInterface;

/**
 * @author Nicolas Kniebihler
 *
 */
public abstract class Menu {
    private String name;
    private int nbElements;
    private GameModel model;

    private int pointedElementId = 0;
    private boolean isDisplayed = false;

    public Menu(String name, int nbElements, GameModel model) {
        this.name = name;
        this.nbElements = nbElements;
        this.model = model;
    }

    public int getNbElements() {
        return nbElements;
    }

    public int getPointedElementId() {
        return pointedElementId;
    }

    public GameModel getModel() {
        return model;
    }

    public void changePointedElement(int i) {
        int index = pointedElementId + i;
        if (index < 0) {
            index = nbElements - 1;
        }
        else if (index >= nbElements) {
            index = 0;
        }

        pointedElementId = index;
    }

    public void display(boolean value) {
        isDisplayed = value;
        pointedElementId = 0;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public String getContent() {
        String content = UserInterface.getLang().getString(name) + " :\n";
        for (int i = 0 ; i < nbElements ; ++i) {
            content += "  ";
            if (i == pointedElementId) {
                content += "> ";
            }
            content += getElementString(i) + "\n";
        }

        return content;
    }

    public abstract void selectElement();
    public abstract String getElementString(int index);
}
