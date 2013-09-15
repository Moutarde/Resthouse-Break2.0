package gui.contextMenu;

import gui.Displayable;
import gui.UserInterface;
import model.GameModel;

/**
 * @author Nicolas Kniebihler
 *
 */
public abstract class Menu extends Displayable {
    private String name;
    private int nbElements;
    private GameModel model;

    private int pointedElementId = 0;

    public Menu(String name, int nbElements, GameModel model) {
        this.name = name;
        this.nbElements = nbElements;
        this.model = model;
    }

    public int getNbElements() {
        return nbElements;
    }

    public void setNbElements(int i) {
        this.nbElements = i;
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

    @Override
    public void display(boolean value) {
        super.display(value);
        pointedElementId = 0;
    }

    public String getContent() {
        String content = name.isEmpty() ? "" : UserInterface.getLang().getString(name) + " :\n";
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
